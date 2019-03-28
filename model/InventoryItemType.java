package model;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import exception.InvalidPrimaryKeyException;
import impresario.IView;

public class InventoryItemType extends EntityBase implements IView{

	private static final String myTableName = "ItemTypeName";
	
	public InventoryItemType(String itemTypeName) throws InvalidPrimaryKeyException {
		super(myTableName);
		
		String query = "SELECT * FROM " + myTableName + " WHERE (ItemTypeName = " + itemTypeName + ")";

		Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

		// You must get one item at least
		if (allDataRetrieved != null)
		{
			int size = allDataRetrieved.size();

			// There should be EXACTLY one item. More than that is an error
			if (size != 1)
			{
				throw new InvalidPrimaryKeyException("Multiple InventoryItemType matching name : "
					+ size + " found.");
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

	@Override
	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getState(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initializeSchema(String tableName) {
		// TODO Auto-generated method stub
		
	}
}
