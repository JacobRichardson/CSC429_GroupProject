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
		// Class is invariant, so this method does not change any attributes
		if(history=="ModifyIIT") {
			modifyIIT((String)value);
		}
		else if(history=="DeleteIIT"){
			deleteIIT((String)value);
		}
		else if(history =="AddVIIT") {
			System.out.println("HITORY == AddVIIT");
			addVIIT((String)value);
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
			InventoryItemType iIT=new InventoryItemType((String)itemTypeName);
			createAndShowModifyIIT(iIT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteIIT(String itemTypeName) {
		try {
			InventoryItemType iIT=new InventoryItemType((String)itemTypeName);
			createAndShowDeleteIIT(iIT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addVIIT(String itemTypeName) {
		try {
			InventoryItemType iIT=new InventoryItemType((String)itemTypeName);
			InventoryItemType.setSelectedInventoryItemTypeName(itemTypeName);
			createAndShowPriceView(iIT);
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
		    myViews.put("VendorInventoryPrice", localScene);
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
}