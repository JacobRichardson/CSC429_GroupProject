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

import impresario.IView;

import userInterface.View;
import userInterface.ViewFactory;

/** The class containing the Account for the ATM application */
//==============================================================
public class Patron extends EntityBase implements IView
{
	private static final String myTableName = "patron";

	protected Properties dependencies;

	// GUI Components

	private String updateStatusMessage = "";


	// Can also be used to create a NEW Account (if the system it is part of
	// allows for a new account to be set up)
	//----------------------------------------------------------
	public Patron(Properties props)
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

	/**
	 * Verify ownership
	 */
	//----------------------------------------------------------
	public boolean verifyOwnership(PatronZipCollection cust)
	{
		if (cust == null)
		{
			return false;
		}
		else
		{
			String custid = (String)cust.getState("ID");
			String myOwnerid = (String)getState("OwnerID");
			// DEBUG System.out.println("Account: custid: " + custid + "; ownerid: " + myOwnerid);

			return (custid.equals(myOwnerid));
		}
	}

	/**
	 * Credit balance (Method is public because it may be invoked directly as it has no possibility of callback associated with it)
	 */
	//----------------------------------------------------------
	public void credit(String amount)
	{
		String myBalance = (String)getState("Balance");
		double myBal = Double.parseDouble(myBalance);

		double incrementAmount = Double.parseDouble(amount);
		myBal += incrementAmount;

		persistentState.setProperty("Balance", ""+myBal);
	}

	/**
	 * Debit balance (Method is public because it may be invoked directly as it has no possibility of callback associated with it)
	 */
	//----------------------------------------------------------
	public void debit(String amount)
	{
		String myBalance = (String)getState("Balance");
		double myBal = Double.parseDouble(myBalance);

		double incrementAmount = Double.parseDouble(amount);
		myBal -= incrementAmount;

		persistentState.setProperty("Balance", ""+myBal);
	}

	/**
	 * Check balance -- returns true/false depending on whether
	 * there is enough balance to cover withdrawalAmount or not
	 * (Method is public because it may be invoked directly as it has no possibility of callback associated with it)
	 *
	 */
	//----------------------------------------------------------
	public boolean checkBalance(String withdrawalAmount)
	{
		String myBalance = (String)getState("Balance");
		double myBal = Double.parseDouble(myBalance);

		double checkAmount = Double.parseDouble(withdrawalAmount);

		if (myBal >= checkAmount)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	//----------------------------------------------------------
	public void setServiceCharge(String value)
	{
		persistentState.setProperty("ServiceCharge", value);
		updateStateInDatabase();
	}
	
	//-----------------------------------------------------------------------------------
	public static int compare(Patron a, Patron b)
	{
		String aNum = (String)a.getState("name");
		String bNum = (String)b.getState("name");

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
			if (persistentState.getProperty("patronID") != null)
			{
				Properties whereClause = new Properties();
				whereClause.setProperty("patronID",
				persistentState.getProperty("patronID"));
				updatePersistentState(mySchema, persistentState, whereClause);
				updateStatusMessage = "Account data for account number : " + persistentState.getProperty("AccountNumber") + " updated successfully in database!";
			}
			else
			{
				Integer accountNumber =
					insertAutoIncrementalPersistentState(mySchema, persistentState);
				persistentState.setProperty("patronID", "" + accountNumber.intValue());
				updateStatusMessage = "Account data for new account : " +  persistentState.getProperty("patronID")
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

		v.addElement(persistentState.getProperty("patronID"));
		v.addElement(persistentState.getProperty("name"));
		v.addElement(persistentState.getProperty("address"));
		v.addElement(persistentState.getProperty("city"));
		v.addElement(persistentState.getProperty("stateCode"));
		v.addElement(persistentState.getProperty("zip"));
		v.addElement(persistentState.getProperty("email"));
		v.addElement(persistentState.getProperty("dateOfBirth"));
		v.addElement(persistentState.getProperty("status"));
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

