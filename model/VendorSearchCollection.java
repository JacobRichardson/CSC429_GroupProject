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
public class VendorSearchCollection  extends EntityBase implements IView
{
	private static final String myTableName = "Vendor";

	private Vector vendors;
	// GUI Components

	// constructor for this class
	//----------------------------------------------------------
	public VendorSearchCollection(String date) throws
		Exception
	{
		super(myTableName);

		if (date == null)
		{
			new Event(Event.getLeafLevelClassName(this), "<init>",
				"Missing account holder information", Event.FATAL);
			throw new Exception
				("UNEXPECTED ERROR: PatronAgeCollection.<init>: account holder information is null");
		}

		String query = "SELECT * FROM patron WHERE dateOfBirth > '"+date+"'";

		Vector allDataRetrieved = getSelectQueryResult(query);
		
		String result="";
		if (allDataRetrieved != null)
		{
			vendors = new Vector();

			result = ("==============================================\n");

			Properties p1 = (Properties) allDataRetrieved.firstElement();

			Enumeration props1 = p1.propertyNames();

			while (props1.hasMoreElements())

				result += (props1.nextElement() + "\t");

			result += "\n";

			result += ("----------------------------------------------\n");
			Vector<Properties> data= allDataRetrieved;
			for (Properties p : data) {

				Enumeration props = p.propertyNames();

				while (props.hasMoreElements())

					result += (p.getProperty((String) (props.nextElement())) + "\t");

				result += "\n";

			}

			result += ("==============================================");

		}
		else
			System.out.println("No vendors found for "+date);
		System.out.println(result);
	}

	/**
	 *
	 */
	//----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("Title"))
			return vendors;
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