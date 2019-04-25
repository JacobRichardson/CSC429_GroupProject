package model;

//system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import javax.swing.JFrame;

//project imports
import exception.InvalidPrimaryKeyException;
import database.*;

import impresario.IView;

import userInterface.View;
import userInterface.ViewFactory;

public class VendorInventoryItemType extends EntityBase implements IView 
{
	private static final String myTableName = "VendorInventoryItemType";

	protected Properties dependencies;

	// GUI Components

	private String updateStatusMessage = "";


	// Can also be used to create a NEW Account (if the system it is part of
	// allows for a new account to be set up)
	//----------------------------------------------------------
	public VendorInventoryItemType(Properties props)
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

	public VendorInventoryItemType(String vendorId, String itemTypeName) throws InvalidPrimaryKeyException {
		super(myTableName);
		
		String query = "SELECT * FROM " + myTableName + " WHERE (VendorId = " + vendorId + ") AND (InventoryItemTypeName = '" + itemTypeName + "')";

		//DEBUG:
		//System.out.println(query);
		
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
	
	//-----------------------------------------------------------------------------------
	private void setDependencies()
	{
		dependencies = new Properties();
	
		myRegistry.setDependencies(dependencies);
	}

	//----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("UpdateStatusMessage") == true)
			return updateStatusMessage;

		return persistentState.getProperty(key);
	}

	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		myRegistry.updateSubscribers(key, this);
	}

	/** Called via the IView relationship */
	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		stateChangeRequest(key, value);
	}
	//-----------------------------------------------------------------------------------
	public void update()
	{
		updateStateInDatabase();
	}
	
	//-----------------------------------------------------------------------------------
	public void updateStateInDatabase() 
	{
		
		
		try
		{
			Integer vendorInventoryItemTypeId = insertAutoIncrementalPersistentState(mySchema, persistentState);
			persistentState.setProperty("Id", "" + vendorInventoryItemTypeId.intValue());
			updateStatusMessage = "Vendor-InventoryItemType data for new vendor : " +  persistentState.getProperty("Id")
					+ "installed successfully in database!";
		}
		catch (SQLException ex)
		{
			updateStatusMessage = "Error in installing account data in database!";
			System.out.println(ex.getMessage());
		}
		//DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
	}
	
	public void delete() {
		
		try {
			System.out.println("Deleting");
			if (persistentState.getProperty("InventoryItemTypeName") != null) {
				
				Properties whereClause = new Properties();
				whereClause.setProperty("InventoryItemTypeName",
						persistentState.getProperty("InventoryItemTypeName"));
					deletePersistentState(mySchema, whereClause);
				updateStatusMessage = "InventoryItemType data for name: " + persistentState.getProperty("InventoryItemTypeName") + " delete successfully in database!";
		
			}	
		} catch (SQLException e) {
			updateStatusMessage = "Error in deleting InventoryItemType data in database!";
		}
		
	}


	/**
	 * This method is needed solely to enable the Account information to be displayable in a table
	 *
	 */
	//--------------------------------------------------------------------------
	public Vector<String> getEntryListView()
	{
		Vector<String> v = new Vector<String>();

		v.addElement(persistentState.getProperty("Id"));
		v.addElement(persistentState.getProperty("VendorId"));
		v.addElement(persistentState.getProperty("InventoryItemTypeName"));
		v.addElement(persistentState.getProperty("VendorPrice"));
		v.addElement(persistentState.getProperty("DateOfLastUpdate"));
		return v;
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
