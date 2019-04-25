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

public class InventoryItemType extends EntityBase implements IView{

	private String history;
	private static final String myTableName = "InventoryItemType";
	
	private Hashtable<String, Scene> myViews;
	private Stage	  	myStage;
	
	private static String selectedInventoryItemTypeName;
	
	private String updateStatusMessage = "";
	
	protected Properties dependencies;
	
	public InventoryItemType(Properties props)
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
	
	public InventoryItemType(String itemTypeName, String history) throws InvalidPrimaryKeyException {
		super(myTableName);
		this.history=history;
		String query = "SELECT * FROM " + myTableName + " WHERE (ItemTypeName = '" + itemTypeName + "')";

		myViews = new Hashtable<String, Scene>();
		myStage = MainStageContainer.getInstance();
		Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

		// You must get one item at least
		if (allDataRetrieved != null)
		{
			int size = allDataRetrieved.size();

			// There should be EXACTLY one item. More than that is an error
			if (size != 1)
			{
				throw new InvalidPrimaryKeyException("Multiple InventoryItemType matching name : "
					+ itemTypeName + " found.");
			}
			else
			{
				// copy all the retrieved data into persistent state
				Properties retrievedItemData = allDataRetrieved.elementAt(0);
				persistentState = new Properties();

				Enumeration<?> allKeys = retrievedItemData.propertyNames();
				while (allKeys.hasMoreElements() == true)
				{
					String nextKey = (String)allKeys.nextElement();
					String nextValue = retrievedItemData.getProperty(nextKey);

					if (nextValue != null)
					{
						persistentState.setProperty(nextKey, nextValue);
					}
				}

			}
		}
		// If no item found for this id, throw an exception
		else
		{
			throw new InvalidPrimaryKeyException("No InventoryItemType matching name : " + itemTypeName);
		}
	}
	
	public void update() {
		updateStateInDatabase();
	}
	
	public void updateStateInDatabase() {
		try
		{
			if (persistentState.getProperty("ItemTypeName") != null && Manager.getChoice() != "ModifyIIT")
			{
				Properties whereClause = new Properties();
				whereClause.setProperty("ItemTypeName",
				persistentState.getProperty("ItemTypeName"));
				insertPersistentState(mySchema, persistentState);
				updateStatusMessage = "Item Inventory Type data for : " + persistentState.getProperty("ItemTypeName") + " updated successfully in database!";
			} else 
			{
				Properties whereClause = new Properties();
				whereClause.setProperty("ItemTypeName",
				InventoryItemType.getSelectedInventoryItemTypeName());
				updatePersistentState(mySchema, persistentState, whereClause);
				updateStatusMessage = "Item Inventory Type data for : " + persistentState.getProperty("ItemTypeName") + " updated successfully in database!";
			}
		}
		catch (SQLException ex)
		{
			
			updateStatusMessage = "Error in installing account data in database!";
		}
		//DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
	}
	
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

	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub
		
	}

	public Object getState(String key)
	{
		if(key.equals("ItemTypeName") == true)
			return persistentState.getProperty(key);
		else if (key.equals("Units") == true)
			return persistentState.getProperty(key);
		else if((key.equals("UnitMeasure") == true))
			return persistentState.getProperty(key);
		else  if(key.equals("ValidityDays"))
			return persistentState.getProperty(key);
		else if(key.equals("ReorderPoint"))
			return persistentState.getProperty(key);
		else if(key.equals("Notes"))
			return persistentState.getProperty(key);
		else if((key.equals("Status") == true))
			return persistentState.getProperty(key);
		else return persistentState.getProperty(key);
	}

	//----------------------------------------------View Changing----------------------------------------------\\
	public void stateChangeRequest(String key, Object value) {
		if(history=="ModifyIIT" && value!=null) {
			modifyIIT((String)value);
		}
		else if(history=="DeleteIIT"){
			deleteIIT((String)value);
		}
		else if(key.equals("BackMIIT"))
			createAndShowFindInventoryItemTypeView();
		else if(history=="ModifyVendor")
			new Manager();
		else if(key.equals("BackIIT"))
			createAndShowVendorSearch();
		else if(key.equals("BackVendor")){
			new Manager();
		}
		else if(key.equals("BackPrice")) {
			createAndShowFindInventoryItemTypeView();
		}
		else if(key.equals("IITCollectionView")) {
			iitSearch((String)value);
		}
		else if(history =="AddVIIT") {
			addVIIT((String)value);
		}
		else if(history=="deleteVIIT" && value!=null) {
			deleteVIIT((String)value);
		}
		
	}

	@Override
	protected void initializeSchema(String tableName) {
		if (mySchema == null)
		{
			mySchema = getSchemaInfo(tableName);
		}
	}
	
	public void modifyIIT(String itemTypeName) {
		try {
			InventoryItemType iIT=new InventoryItemType((String)itemTypeName, history);
			createAndShowModifyIIT(iIT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void iitSearch(String q) {
		try {
			createAndShowIITCollectionView(new IITCollection(q, history));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteIIT(String itemTypeName) {
		try {
			InventoryItemType iIT=new InventoryItemType((String)itemTypeName, history);
			createAndShowDeleteIIT(iIT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addVIIT(String itemTypeName) {
		try {
			InventoryItemType iIT=new InventoryItemType((String)itemTypeName, history);
			InventoryItemType.setSelectedInventoryItemTypeName(itemTypeName);
			createAndShowPriceView(iIT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteVIIT(String itemTypeName) {
		try {
			InventoryItemType iIT=new InventoryItemType((String)itemTypeName, history);
			InventoryItemType.setSelectedInventoryItemTypeName(itemTypeName);
			createAndShowDeleteVIIT(iIT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createAndShowModifyIIT(InventoryItemType iIT) {
    	Scene localScene = (Scene)myViews.get("ModifyIITView");

		if (localScene == null)
		{
			// create our initial view
		    View newView = ViewFactory.createView("ModifyIITView", iIT); // USE VIEW FACTORY
		    localScene = new Scene(newView);
		    localScene.getStylesheets().add("style.css");
		    myViews.put("ModifyIITView", localScene);
		}
		swapToView(localScene);
	}
	
	private void createAndShowDeleteIIT(InventoryItemType iIT) {
    	Scene localScene = (Scene)myViews.get("iitDelete");

		if (localScene == null)
		{
			// create our initial view
		    View newView = ViewFactory.createView("iitDelete", iIT); // USE VIEW FACTORY
		    localScene = new Scene(newView);
		    localScene.getStylesheets().add("style.css");
		    myViews.put("iitDelete", localScene);
		}
		swapToView(localScene);
	}
	
	private void createAndShowPriceView(InventoryItemType iIT) {
    	Scene localScene = (Scene)myViews.get("VendorInventoryPrice");

		if (localScene == null)
		{
			// create our initial view
		    View newView = ViewFactory.createView("VendorInventoryPrice", iIT); // USE VIEW FACTORY
		    localScene = new Scene(newView);
		    localScene.getStylesheets().add("style.css");
		    myViews.put("VendorInventoryPrice", localScene);
		}
		swapToView(localScene);
	}
	
	
	private void createAndShowDeleteVIIT(InventoryItemType v) {
		Scene localScene = myViews.get("DeleteVIIT");

		if (localScene == null)
		{
			// create our initial view
		    View newView = ViewFactory.createView("DeleteVIIT", v);
		    localScene = new Scene(newView);
		    localScene.getStylesheets().add("style.css");
		    myViews.put("DeleteVIIT", localScene);
		}
		swapToView(localScene);
	}
	
	private void createAndShowFindInventoryItemTypeView() {
    	
    	Scene localScene = myViews.get("FindInventoryItemTypeView");

		if (localScene == null)
		{
			// create our initial view
		    View newView = ViewFactory.createView("FindInventoryItemTypeView", this); // USE VIEW FACTORY
		    localScene = new Scene(newView);
		    localScene.getStylesheets().add("style.css");
		    myViews.put("FindInventoryItemTypeView", localScene);
		}
		swapToView(localScene);
    }

	private void createAndShowIITCollectionView(IITCollection iIT) {
	Scene currentScene = (Scene)myViews.get("IITCollectionView");
	
	if (currentScene == null)
	{
		// create our initial view
		View newView = ViewFactory.createView("IITCollectionView", iIT); // USE VIEW FACTORY
		currentScene = new Scene(newView);
		currentScene.getStylesheets().add("style.css");
		myViews.put("IITCollectionView", currentScene);
	}
			

	// make the view visible by installing it into the frame
	swapToView(currentScene);
	
}

	private void createAndShowVendorSearch() {
	Scene currentScene = (Scene)myViews.get("searchVendor");
	
	if (currentScene == null)
	{
		// create our initial view
		View newView = ViewFactory.createView("searchVendor", this); // USE VIEW FACTORY
		currentScene = new Scene(newView);
		currentScene.getStylesheets().add("style.css");
		myViews.put("searchVendor", currentScene);
	}
			

	// make the view visible by installing it into the frame
	swapToView(currentScene);
	
}

	private void createAndShowIITCollectionView() {
	Scene currentScene = (Scene)myViews.get("IITCollectionView");
	
	if (currentScene == null)
	{
		// create our initial view
		View newView = ViewFactory.createView("IITCollectionView", this); // USE VIEW FACTORY
		currentScene = new Scene(newView);
		currentScene.getStylesheets().add("style.css");
		myViews.put("IITCollectionView", currentScene);
	}
			

	// make the view visible by installing it into the frame
	swapToView(currentScene);
	
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
	
	//------------------------------   SELECTED INVENTORY ITEM TYPE NAME METHODS - ---------------------------------------------//
	
	/*
	 * Sets the variable choice to the passed in string.
	 * 
	 * @param choice The value chocie is to be set to.
	 */
	public static void setSelectedInventoryItemTypeName(String selectedInventoryItemTypeName) {
		InventoryItemType.selectedInventoryItemTypeName = selectedInventoryItemTypeName;
	}

	/*
	 * Returns the value of the variable choice.
	 * 
	 * @return the value of choice.
	 */
	public static String getSelectedInventoryItemTypeName() {
		return InventoryItemType.selectedInventoryItemTypeName;
	}

	/*
	 * Sets the value of choice to an empty string.
	 * 
	 */
	public static void resetSelectedVendorId() {
		InventoryItemType.selectedInventoryItemTypeName = "";
	}
	
	//------------------------------   SELECTED INVENTORY ITEM TYPE NAME METHODS - ---------------------------------------------//
	
	public static int compare(InventoryItemType a, InventoryItemType b)
	{
		String aNum = (String)a.getState("ItemTypeName");
		String bNum = (String)b.getState("ItemTypeName");

		return aNum.compareTo(bNum);
	}
	
	private void setDependencies()
	{
		dependencies = new Properties();
	
		myRegistry.setDependencies(dependencies);
	}

	public Vector<String> getEntryListView()
	{
		Vector<String> v = new Vector<String>();

		v.addElement(persistentState.getProperty("ItemTypeName"));
		v.addElement(persistentState.getProperty("Units"));
		v.addElement(persistentState.getProperty("UnitMeasure"));
		v.addElement(persistentState.getProperty("ValidityDays"));
		v.addElement(persistentState.getProperty("ReorderPoint"));
		v.addElement(persistentState.getProperty("Notes"));
		v.addElement(persistentState.getProperty("Status"));
		
		return v;
	}
}