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
	private static final String myTableName = "Vendor-InventoryItemType";

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
		}
		//DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
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
