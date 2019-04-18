package model;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import exception.InvalidPrimaryKeyException;
import impresario.IView;

public class InventoryItem extends EntityBase implements IView{

	private static final String myTableName = "InventoryItem";
	
	private String updateStatusMessage = "";
	
	protected Properties dependencies;
	
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
	
	
	public void update() {
		updateStateInDatabase();
	}
	
	public void updateStateInDatabase() {
		try
		{
			if (persistentState.getProperty("Barcode") != null && Manager.getChoice() != "ModifyIIT")
			{
				Properties whereClause = new Properties();
				whereClause.setProperty("Barcode",
				persistentState.getProperty("Barcode"));
				insertPersistentState(mySchema, persistentState);
				updateStatusMessage = "Item Inventory data for : " + persistentState.getProperty("ItemTypeName") + " updated successfully in database!";
			} else 
			{
				Properties whereClause = new Properties();
				whereClause.setProperty("Barcode",
				InventoryItemType.getSelectedInventoryItemTypeName());
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