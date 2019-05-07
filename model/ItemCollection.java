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

public class ItemCollection  extends EntityBase implements IView, IModel
{
	protected ItemCollection(String tablename) {
		super(tablename);
		// TODO Auto-generated constructor stub
	}

	private static final String myTableName = "InventoryItem";
	String history;
	private Vector<InventoryItem> iIT;
	private int numRecords;
	// GUI Components
	private Hashtable<String, Scene> myViews;
	private Stage	  	myStage;

	// constructor for this class
	//----------------------------------------------------------
	public ItemCollection(String q, String history) throws
	Exception
	{
		super(myTableName);

		if (q == null)
		{
			new Event(Event.getLeafLevelClassName(this), "<init>",
					"Missing account holder information", Event.FATAL);
			throw new Exception
			("UNEXPECTED ERROR: PatronAgeCollection.<init>: account holder information is null");
		}

		String query = q;

		Vector allDataRetrieved = getSelectQueryResult(query);
		if (allDataRetrieved != null)
		{
			iIT = new Vector<InventoryItem>();
			
			numRecords = allDataRetrieved.size();
			
			for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
			{
				Properties nextIITData = (Properties)allDataRetrieved.elementAt(cnt);

				InventoryItem itemType = new InventoryItem(nextIITData);

				if (itemType != null)
				{
					addIIT(itemType);
				}
			}

		}
		else
			System.out.println("No IIT found for "+q);
		this.history=history;
		myViews = new Hashtable<String, Scene>();
		myStage = MainStageContainer.getInstance();
	}

	private void addIIT(InventoryItem p) {
		// TODO Auto-generated method stub
		int index = findIndexToAdd(p);
		iIT.insertElementAt(p,index);
	}


	private int findIndexToAdd(InventoryItem p) {
		//users.add(u);
		int low=0;
		int high = iIT.size()-1;
		int middle;

		while (low <=high)
		{
			middle = (low+high)/2;

			InventoryItem midSession = iIT.elementAt(middle);

			int result = InventoryItem.compare(p,midSession);

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
		if (key.equals("Item"))
			return iIT;
		else if(key.equals("ItemCollection"))
			return this;
		return null;
	}

	public int getNumRecords () 
	{
		return numRecords;
	}
	
	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		if(key.equals("ModifyItemView")) {
			
		}
		else if(key.equals("Back")) {
			createAndShowSearchItem();
		}
		else if(key.equals("itemSelected")) {
			createAndShowModifyItem();
		}
		else {
			System.out.println(key+" "+value);
		}
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


	private void createAndShowModifyItem() {

		Scene localScene = myViews.get("ModifyItem");

		if (localScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("ModifyItem", this); // USE VIEW FACTORY
			localScene = new Scene(newView);
			localScene.getStylesheets().add("style.css");
			myViews.put("ModifyItem", localScene);
		}
		swapToView(localScene);
	}


	private void createAndShowSearchItem() {

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
}