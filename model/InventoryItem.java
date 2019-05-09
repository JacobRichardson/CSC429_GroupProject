package model;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

import event.Event;
import exception.InvalidPrimaryKeyException;
import impresario.IView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import userInterface.MainStageContainer;
import userInterface.View;
import userInterface.ViewFactory;
import userInterface.WindowPosition;

public class InventoryItem extends EntityBase implements IView{

	private static final String myTableName = "InventoryItem";
	private Hashtable<String, Scene> myViews;
	private Stage	  	myStage;
	
	
	private Vector<InventoryItem>items;
	
	private String updateStatusMessage = "";
	
	protected Properties dependencies;
	
	public InventoryItem(String barcode) throws InvalidPrimaryKeyException
	{
		super(myTableName);
		
		myStage = MainStageContainer.getInstance();
		myViews = new Hashtable<String, Scene>();

		setDependencies();
		String query = "SELECT * FROM InventoryItem WHERE Barcode=" + barcode;

		Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

		// You must get one account at least
		if (allDataRetrieved != null)
		{
			int size = allDataRetrieved.size();

			// There should be EXACTLY one account. More than that is an error
			if (size > 1)
			{
				throw new InvalidPrimaryKeyException("Multiple Items matching id : "
					+ barcode + " found.");
			}
			else if(size == 0) {
				
			}
			else
			{
				// copy all the retrieved data into persistent state
				Properties retrievedAccountData = allDataRetrieved.elementAt(0);
				persistentState = new Properties();

				Enumeration allKeys = retrievedAccountData.propertyNames();
				while (allKeys.hasMoreElements() == true)
				{
					String nextKey = (String)allKeys.nextElement();
					String nextValue = retrievedAccountData.getProperty(nextKey);
					// accountNumber = Integer.parseInt(retrievedAccountData.getProperty("accountNumber"));

					if (nextValue != null)
					{
						persistentState.setProperty(nextKey, nextValue);
					}
				}

			}
		}
		// If no account found for this user name, throw an exception
		else
		{
			throw new InvalidPrimaryKeyException("No account matching id : "
				+ barcode + " found.");
		}
	}
	
	public InventoryItem(Properties props)
	{
		super(myTableName);


		setDependencies();
		persistentState = new Properties();
		Enumeration allKeys = props.propertyNames();
		while (allKeys.hasMoreElements() == true)
		{
			String nextKey = (String)allKeys.nextElement();
			String nextValue = props.getProperty(nextKey);
			if (nextValue != null)
			{
				persistentState.setProperty(nextKey, nextValue);
			}
		}

	}
	
	
	public void update(String barcode) throws SQLException {
		updateStateInDatabase(barcode);
	}
	
	public void updateStateInDatabase(String barcode) throws SQLException {
		try
		{
			if (persistentState.getProperty("Barcode") != null && Manager.getChoice() != "EnterItemBarcodeView")
			{
				Properties whereClause = new Properties();
				whereClause.setProperty("Barcode",
				persistentState.getProperty("Barcode"));
				insertPersistentState(mySchema, persistentState);
				updateStatusMessage = "Item Inventory data for : " + persistentState.getProperty("ItemTypeName") + " updated successfully in database!";
			} else 
			{
				Properties whereClause = new Properties();
				whereClause.setProperty("Barcode",barcode);
				updatePersistentState(mySchema, persistentState, whereClause);
				updateStatusMessage = "Item Inventory data for : " + persistentState.getProperty("ItemTypeName") + " updated successfully in database!";
			}
		}
		catch (SQLException ex)
		{
			updateStatusMessage = "Error in installing account data in database!";
			throw ex;
		}
		//DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
	}
	
	public void modifyItem() {
		try {
		Properties whereClause = new Properties();
		whereClause.setProperty("Barcode",persistentState.getProperty("Barcode"));
		updatePersistentState(mySchema, persistentState, whereClause);
		updateStatusMessage = "Item Inventory data for : " + persistentState.getProperty("ItemTypeName") + " updated successfully in database!";
		}
		catch(SQLException e) {
			System.out.println(e);
		}
	}
	
	//FOR TAKING AN ITEM OUT OF INVENTORY
	
	/*
	public void delete() {
		
		try {
			
			if (persistentState.getProperty("ItemTypeName") != null) {
				
				Properties whereClause = new Properties();
				whereClause.setProperty("ItemTypeName",
						persistentState.getProperty("ItemTypeName"));
					deletePersistentState(mySchema, whereClause);
				updateStatusMessage = "InventoryItemType data for name: " + persistentState.getProperty("ItemTypeName") + " delete successfully in database!";
		
			}	
		} catch (SQLException e) {
			updateStatusMessage = "Error in deleting InventoryItemType data in database!";
		}
		
	}

	*/
	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub
		
	}

	public Object getState(String key)
	{
		if(key.equals("Barcode") == true)
			return persistentState.getProperty(key);
		else if (key.equals("VendorId") == true)
			return persistentState.getProperty(key);
		else if((key.equals("InventoryItemTypeName") == true))
			return persistentState.getProperty(key);
		else  if(key.equals("DateReceived"))
			return persistentState.getProperty(key);
		else if(key.equals("DateOfLastUse"))
			return persistentState.getProperty(key);
		else if(key.equals("Notes"))
			return persistentState.getProperty(key);
		else if((key.equals("Status") == true))
			return persistentState.getProperty(key);
		else return persistentState.getProperty(key);
	}

	public void stateChangeRequest(String key, Object value) {
		if(key.equals("Back"))
			createAndShowEnterItemBarcodeView();
		else if(key.equals("ConfirmItemRemovalView"))
			getInventoryItem(value);
		else if(key.equals("BackItem"))
			createAndShowSearchItem();
		else if(key.equals("SearchItemCollection"))
			searchItem((String)value);
	}
	
	private void getInventoryItem(Object value) {
		try {	
			InventoryItem item = new InventoryItem((String)value);
			createAndShowConfirmItemRemovalView(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void searchItem(String s) {
		try {
			createAndShowItemCollection(new ItemCollection(s,""));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createAndShowConfirmItemRemovalView(InventoryItem value) {
		Scene localScene = myViews.get("ConfirmItemRemovalView");

		if (localScene == null)
		{
			// create our initial view
		    View newView = ViewFactory.createView("ConfirmItemRemovalView", value); // USE VIEW FACTORY
		    localScene = new Scene(newView);
		    myViews.put("ConfirmItemRemovalView", localScene);
		    localScene.getStylesheets().add("style.css");
		}
		swapToView(localScene);
	}
	
	private void createAndShowItemCollection(ItemCollection itemCollection) {
		Scene currentScene = (Scene)myViews.get("SearchItemCollection");
		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("SearchItemCollection", itemCollection); // USE VIEW FACTORY
			currentScene = new Scene(newView);
			currentScene.getStylesheets().add("style.css");
			myViews.put("SearchItemCollection", currentScene);

			//Reset choice.
			Manager.setChoice("");
		}

		swapToView(currentScene);
		
	}

	@Override
	protected void initializeSchema(String tableName) {
		if (mySchema == null)
		{
			mySchema = getSchemaInfo(tableName);
		}
	}
	
	
	
	public static int compare(InventoryItem a, InventoryItem b)
	{
		String aNum = (String)a.getState("Barcode");
		String bNum = (String)b.getState("Barcode");

		return aNum.compareTo(bNum);
	}
	
	private void setDependencies()
	{
		dependencies = new Properties();
	
		myRegistry.setDependencies(dependencies);
	}
	private void createAndShowEnterItemBarcodeView() {
		Scene localScene = myViews.get("EnterItemBarcodeView");

		if (localScene == null)
		{
			// create our initial view
		    View newView = ViewFactory.createView("EnterItemBarcodeView", this); // USE VIEW FACTORY
		    localScene = new Scene(newView);
		    localScene.getStylesheets().add("style.css");
		    myViews.put("EnterItemBarcodeView", localScene);
		}
		swapToView(localScene);
	}
	
	private void createAndShowSearchItem () {

		Scene localScene = myViews.get("SearchItem");

		if (localScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("SearchItem", this); // USE VIEW FACTORY
			localScene = new Scene(newView);
			localScene.getStylesheets().add("style.css");
			myViews.put("SearchItem", localScene);
		}
		swapToView(localScene);
	}
	
	public void swapToView(Scene newScene)
	{		
		if (newScene == null)
		{
			System.out.println("Librarian.swapToView(): Missing view for display");
			new Event(Event.getLeafLevelClassName(this), "swapToView",
				"Missing view for display ", Event.ERROR);
			return;
		}

		myStage.setScene(newScene);
		myStage.sizeToScene();
		
			
		//Place in center
		WindowPosition.placeCenter(myStage);

	}
	public Vector<String> getEntryListView()
	{
		Vector<String> v = new Vector<String>();

		v.addElement(persistentState.getProperty("Barcode"));
		v.addElement(persistentState.getProperty("VendorId"));
		v.addElement(persistentState.getProperty("InventoryItemTypeName"));
		v.addElement(persistentState.getProperty("DateReceived"));
		v.addElement(persistentState.getProperty("DateOfLastUse"));
		v.addElement(persistentState.getProperty("Notes"));
		v.addElement(persistentState.getProperty("Status"));
		
		return v;
	}
}