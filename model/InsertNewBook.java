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
public class InsertNewBook  extends EntityBase implements IView
{
	private static final String myTableName = "Books";

	private Vector patrons;
	// GUI Components

	// constructor for this class
	//----------------------------------------------------------
	public InsertNewBook( Properties newBook) throws
		Exception
	{
		super(myTableName);

		if (newBook == null)
		{
			new Event(Event.getLeafLevelClassName(this), "<init>",
				"Missing account holder information", Event.FATAL);
			throw new Exception
				("UNEXPECTED ERROR: PatronAgeCollection.<init>: account holder information is null");
		}

		insertAutoIncrementalPersistentState(mySchema, newBook);
		System.out.println("Book inserted");
	}

	/**
	 *
	 */
	//----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("Title"))
			return patrons;
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
