// specify the package
package model;

import java.util.Enumeration;
// system imports
import java.util.Properties;
import java.util.Vector;

// project imports
import exception.InvalidPrimaryKeyException;
import event.Event;
import database.*;

import impresario.IView;

//==============================================================
public class InventoryItemTypeCollection  extends EntityBase implements IView
{
	private static final String myTableName = "InventoryItemType";

	private Vector <InventoryItemType>itemType;
	// GUI Components

	// constructor for this class
	//----------------------------------------------------------
	public InventoryItemTypeCollection(Object value) throws
		Exception
	{
		super(myTableName);

		if (value == null)
		{
			new Event(Event.getLeafLevelClassName(this), "<init>",
				"Missing Innetory Item Type information", Event.FATAL);
			throw new Exception
				("UNEXPECTED ERROR: InventoryItemType.<init>: Inventory Item Type information is null");
		}

		String query = "SELECT * FROM InventoryItemType WHERE ItemTypeName LIKE '%"+value+"%'";

		Vector allDataRetrieved = getSelectQueryResult(query);
		if (allDataRetrieved != null)
		{
			itemType = new Vector<InventoryItemType>();

			for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
			{
				Properties nextItemTypeData = (Properties)allDataRetrieved.elementAt(cnt);

				InventoryItemType iIT = new InventoryItemType(nextItemTypeData);

				if (iIT != null)
				{
					addItemType(iIT);
				}
			}

		}
		else
			System.out.println("No vendors found for "+value);
	}

	private void addItemType(InventoryItemType p) {
			int index = findIndexToAdd(p);
			itemType.insertElementAt(p,index);
	}


		private int findIndexToAdd(InventoryItemType p) {
			//users.add(u);
			int low=0;
			int high = itemType.size()-1;
			int middle;

			while (low <=high)
			{
				middle = (low+high)/2;

				InventoryItemType midSession = itemType.elementAt(middle);

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
				return itemType;
			else if(key.equals("InventoryItemTypeList"))
				return this;
			return null;
		}

	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		// Class is invariant, so this method does not change any attributes

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
}