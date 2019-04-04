// specify the package
package model;

// system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import javax.swing.JFrame;

// project imports
import exception.InvalidPrimaryKeyException;
import database.*;
import event.Event;
import impresario.IView;

import userInterface.View;
import userInterface.ViewFactory;

/** The class containing the Account for the ATM application */
//==============================================================
public class Vendor extends EntityBase implements IView
{
	private static final String myTableName = "Vendor";
	
	private static String selectedVendorId;

	protected Properties dependencies;

	// GUI Components

	private String updateStatusMessage = "";


	// Can also be used to create a NEW Account (if the system it is part of
	// allows for a new account to be set up)
	//----------------------------------------------------------
	public Vendor(Properties props)
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
	
	public Vendor(String Id)
			throws InvalidPrimaryKeyException
		{
			super(myTableName);

			setDependencies();
			String query = "SELECT * FROM " + myTableName + " WHERE (Id = " + Id + ")";

			Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

			// You must get one account at least
			if (allDataRetrieved != null)
			{
				int size = allDataRetrieved.size();

				// There should be EXACTLY one account. More than that is an error
				if (size != 1)
				{
					throw new InvalidPrimaryKeyException("Multiple accounts matching id : "
						+ Id + " found.");
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
					+ Id + " found.");
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
		if(key.equals("Id") == true)
			return persistentState.getProperty(key);
		else if (key.equals("Name") == true)
			return persistentState.getProperty(key);
		else if((key.equals("PhoneNumber") == true))
			return persistentState.getProperty(key);
		else if((key.equals("Status") == true))
			return persistentState.getProperty(key);
		else return persistentState.getProperty(key);
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

	/**
	 * Verify ownership
	 */
	//----------------------------------------------------------
	public boolean verifyOwnership(VendorSearchCollection vendor)
	{
		if (vendor == null)
		{
			return false;
		}
		else
		{
			String vendorID = (String)vendor.getState("Id");
			String myOwnerid = (String)getState("OwnerID");
			// DEBUG System.out.println("Account: custid: " + custid + "; ownerid: " + myOwnerid);

			return (vendor.equals(myOwnerid));
		}
	}
	
	//-----------------------------------------------------------------------------------
	public static int compare(Vendor a, Vendor b)
	{
		String aNum = (String)a.getState("Id");
		String bNum = (String)b.getState("Id");

		return aNum.compareTo(bNum);
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
			if (persistentState.getProperty("Id") != null)
			{
				Properties whereClause = new Properties();
				whereClause.setProperty("Id",
				persistentState.getProperty("Id"));
				updatePersistentState(mySchema, persistentState, whereClause);
				updateStatusMessage = "Account data for account number : " + persistentState.getProperty("Id") + " updated successfully in database!";
			}
			else
			{
				Integer accountNumber =
					insertAutoIncrementalPersistentState(mySchema, persistentState);
				persistentState.setProperty("Id", "" + accountNumber.intValue());
				updateStatusMessage = "Account data for new account : " +  persistentState.getProperty("Id")
					+ "installed successfully in database!";
			}
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
		v.addElement(persistentState.getProperty("Name"));
		v.addElement(persistentState.getProperty("PhoneNumber"));
		v.addElement(persistentState.getProperty("Status"));
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

	
	//------------------------------   SELECTED VENDOR ID METHODS - ---------------------------------------------//
	
	/*
	 * Sets the variable choice to the passed in string.
	 * 
	 * @param choice The value chocie is to be set to.
	 */
	public static void setSelectedVendorId(String selectedVendorId) {
		Vendor.selectedVendorId = selectedVendorId;
	}

	/*
	 * Returns the value of the variable choice.
	 * 
	 * @return the value of choice.
	 */
	public static String getSelectedVendorId() {
		return Vendor.selectedVendorId;
	}

	/*
	 * Sets the value of choice to an empty string.
	 * 
	 */
	public static void resetSelectedVendorId() {
		Vendor.selectedVendorId = "";
	}
}
	