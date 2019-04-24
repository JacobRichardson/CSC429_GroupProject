package model;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import exception.InvalidPrimaryKeyException;
import impresario.IView;

public class InventoryItem extends EntityBase implements IView{

	private static final String myTableName = "InventoryItem";
	
	private Vector<InventoryItem>items;
	
	private String updateStatusMessage = "";
	
	protected Properties dependencies;
	
	public InventoryItem(String barcode) throws InvalidPrimaryKeyException
	{
		super(myTableName);

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
				throw new InvalidPrimaryKeyException("Multiple accounts matching id : "
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
		System.out.print(getState("Status"));
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
	
	
	public void update(String barcode) {
		updateStateInDatabase(barcode);
	}
	
	public void updateStateInDatabase(String barcode) {
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
		}
		//DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
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
		else  if(key.equals("DateRecieved"))
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
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initializeSchema(String tableName) {
		if (mySchema == null)
		{
			mySchema = getSchemaInfo(tableName);
		}
	}
	
	
	
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