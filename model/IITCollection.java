package model;

import java.util.Enumeration;
import java.util.Hashtable;
// system imports
import java.util.Properties;
import java.util.Vector;

// project imports
import exception.InvalidPrimaryKeyException;
import event.Event;
import database.*;
import impresario.IModel;
import impresario.IView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import userInterface.MainStageContainer;
import userInterface.View;
import userInterface.ViewFactory;
import userInterface.WindowPosition;

public class IITCollection  extends EntityBase implements IView, IModel
{
	private static final String myTableName = "InventoryItemType";
	String history;
	private Vector <InventoryItemType>iIT;
	// GUI Components
	private Hashtable<String, Scene> myViews;
	private Stage	  	myStage;

	// constructor for this class
	//----------------------------------------------------------
	public IITCollection(String details, String history) throws
		Exception
	{
		super(myTableName);

		if (details == null)
		{
			new Event(Event.getLeafLevelClassName(this), "<init>",
				"Missing account holder information", Event.FATAL);
			throw new Exception
				("UNEXPECTED ERROR: PatronAgeCollection.<init>: account holder information is null");
		}

		String query = details;

		Vector allDataRetrieved = getSelectQueryResult(query);
		if (allDataRetrieved != null)
		{
			iIT = new Vector<InventoryItemType>();

			for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
			{
				Properties nextIITData = (Properties)allDataRetrieved.elementAt(cnt);

				InventoryItemType itemType = new InventoryItemType(nextIITData);

				if (itemType != null)
				{
					addIIT(itemType);
				}
			}

		}
		else
			System.out.println("No IIT found for "+details);
		this.history=history;
		myViews = new Hashtable<String, Scene>();
		myStage = MainStageContainer.getInstance();
	}

	private void addIIT(InventoryItemType p) {
		// TODO Auto-generated method stub
			int index = findIndexToAdd(p);
			iIT.insertElementAt(p,index);
	}


		private int findIndexToAdd(InventoryItemType p) {
			//users.add(u);
			int low=0;
			int high = iIT.size()-1;
			int middle;

			while (low <=high)
			{
				middle = (low+high)/2;

				InventoryItemType midSession = iIT.elementAt(middle);

				int result = InventoryItemType.compare(p,midSession);

				if (result ==0)
				{
					return middle;
				}
				else if (result<0)
				{
					high=middle-1;
				}
				else
				{
					low=middle+1;
				}


			}
			return low;
		}
		
		public Object getState(String key)
		{
			if (key.equals("InventoryItemType"))
				return iIT;
			else if(key.equals("IITCollection"))
				return this;
			return null;
		}

	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		System.out.println(history);
		// Class is invariant, so this method does not change any attributes
		if(history=="ModifyIIT" && value!=null) {
			modifyIIT((String)value);
		}
		else if(key.equals("Back") && history=="DeleteIIT")
			createAndShowFindInventoryItemTypeView();
		else if(key.equals("BackIIT") &&history=="DeleteIIT")
			new Manager();
		else if(history=="DeleteIIT"){
			deleteIIT((String)value);
		}
		else if(key.equals("Back"))
			createAndShowFindInventoryItemTypeView();
		else if(key.equals("BackIIT") && history=="ModifyVendor")
			new Manager();
		else if(key.equals("BackIIT") && history=="ModifyIIT")
			new Manager();
		else if(key.equals("BackIIT"))
			createAndShowVendorSearch();
		else if(key.equals("BackVendor")){
			new Manager();
		}
		else if(key.equals("BackPrice")) {
			createAndShowIITCollectionView();
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
		myRegistry.updateSubscribers(key, this);
	}

	/** Called via the IView relationship */
	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		stateChangeRequest(key, value);
	}

	//-----------------------------------------------------------------------------------
	protected void initializeSchema(String tableName)
	{
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
}