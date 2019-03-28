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

	private Vector <Vendor>vendors;
	// GUI Components

	// constructor for this class
	//----------------------------------------------------------
	public VendorSearchCollection(String details) throws
		Exception
	{
		super(myTableName);

		if (details == null)
		{
			new Event(Event.getLeafLevelClassName(this), "<init>",
				"Missing account holder information", Event.FATAL);
			throw new Exception
				("UNEXPECTED ERROR: PatronAgeCollection.<init>: account holder information is null");
		}

		String query = "SELECT * FROM Vendor where "+details;

		Vector allDataRetrieved = getSelectQueryResult(query);
		if (allDataRetrieved != null)
		{
			vendors = new Vector<Vendor>();

			for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
			{
				Properties nextVendorData = (Properties)allDataRetrieved.elementAt(cnt);

				Vendor vendor = new Vendor(nextVendorData);

				if (vendor != null)
				{
					addVendor(vendor);
				}
			}

		}
		else
			System.out.println("No vendors found for "+details);
	}

	private void addVendor(Vendor p) {
		// TODO Auto-generated method stub
			int index = findIndexToAdd(p);
			vendors.insertElementAt(p,index);
	}


		private int findIndexToAdd(Vendor p) {
			//users.add(u);
			int low=0;
			int high = vendors.size()-1;
			int middle;

			while (low <=high)
			{
				middle = (low+high)/2;

				Vendor midSession = vendors.elementAt(middle);

				int result = Vendor.compare(p,midSession);

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
			if (key.equals("Vendor"))
				return vendors;
			else if(key.equals("VendorList"))
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