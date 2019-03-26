// specify the package
package model;

import java.util.Enumeration;
import java.util.Hashtable;
// system imports
import java.util.Properties;
import java.util.Vector;

// project imports
import exception.InvalidPrimaryKeyException;
import event.Event;
import database.*;

import impresario.IView;
import impresario.ModelRegistry;
import javafx.scene.Scene;
import userInterface.PatronCollectionView;
import userInterface.View;
import userInterface.ViewFactory;

//==============================================================
public class PatronZipCollection  extends EntityBase implements IView
{
	private static final String myTableName = "patron";

	private Vector<Patron> patrons;

	// For Impresario
		private Properties dependencies;
		private ModelRegistry myRegistry;
		
		// GUI Components
			private Hashtable<String, Scene> myViews=new Hashtable<String, Scene>();

	// constructor for this class
	//----------------------------------------------------------
	public PatronZipCollection( String zip) throws
	Exception
	{
		super(myTableName);

		if (zip == null)
		{
			new Event(Event.getLeafLevelClassName(this), "<init>",
				"Missing account holder information", Event.FATAL);
			throw new Exception
				("UNEXPECTED ERROR: AccountCollection.<init>: account holder information is null");
		}


		String query = "SELECT * FROM " + myTableName + " WHERE (zip = " + zip + ")";

		Vector allDataRetrieved = getSelectQueryResult(query);

		if (allDataRetrieved != null)
		{
			patrons = new Vector<Patron>();

			for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
			{
				Properties nextAccountData = (Properties)allDataRetrieved.elementAt(cnt);

				Patron account = new Patron(nextAccountData);

				if (account != null)
				{
					addAccount(account);
				}
			}

		}
		else
		{
			throw new InvalidPrimaryKeyException("No patrons in zip : "
				+ zip + ". Name : ");
		}
	}


		private void addAccount(Patron p) {
		// TODO Auto-generated method stub
			int index = findIndexToAdd(p);
			patrons.insertElementAt(p,index);
	}


		private int findIndexToAdd(Patron p) {
			//users.add(u);
			int low=0;
			int high = patrons.size()-1;
			int middle;

			while (low <=high)
			{
				middle = (low+high)/2;

				Patron midSession = patrons.elementAt(middle);

				int result = Patron.compare(p,midSession);

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


		private void setDependencies() {
			dependencies = new Properties();
			
			myRegistry.setDependencies(dependencies);
		
	}

		/**
		 *
		 */
		//----------------------------------------------------------
		public Object getState(String key)
		{
			if (key.equals("Patron"))
				return patrons;
			else if(key.equals("PatronList"))
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

		public Vector<String> getEntryListView()
		{
			Vector<String> v = new Vector<String>();

			v.addElement(persistentState.getProperty("PatronId"));
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

		protected Scene createAndShowView(){
			Scene localScene = myViews.get("patronCollection");

			if (localScene == null)
			{
				// create our initial view
				View newView = ViewFactory.createView("patronCollection", this); // USE VIEW FACTORY
				localScene = new Scene(newView);
				myViews.put("patronCollection", localScene);
			}	
			return localScene;
		}
}
