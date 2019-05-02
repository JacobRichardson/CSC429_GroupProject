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
	private Vector <InventoryItemType>iIT;
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
			System.out.println("No IIT found for "+q);
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
		if (key.equals("Item"))
			return iIT;
		else if(key.equals("ItemCollection"))
			return this;
		return null;
	}

	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{

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