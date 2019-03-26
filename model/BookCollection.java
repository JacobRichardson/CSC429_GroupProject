// specify the package
package model;

// system imports
import java.util.Properties;
import java.util.Vector;
import javafx.scene.Scene;

// project imports
import exception.InvalidPrimaryKeyException;
import event.Event;
import database.*;

import impresario.IView;

import userInterface.View;
import userInterface.ViewFactory;


/** The class containing the AccountCollection for the ATM application */
//==============================================================
public class BookCollection  extends EntityBase implements IView
{
	private static final String myTableName = "Account";

	private Vector<Book> books;
	// GUI Components

	// constructor for this class
	//----------------------------------------------------------
	public BookCollection( String title) throws
		Exception
	{
		super(myTableName);

		if (title == null)
		{
			new Event(Event.getLeafLevelClassName(this), "<init>",
				"Missing account holder information", Event.FATAL);
			throw new Exception
				("UNEXPECTED ERROR: AccountCollection.<init>: account holder information is null");
		}

		String query = "SELECT * FROM " + myTableName + " WHERE (title like'%" + title + "%')";

		Vector allDataRetrieved = getSelectQueryResult(query);

		if (allDataRetrieved != null)
		{
			books = new Vector<Book>();

			for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
			{
				Properties nextBookData = (Properties)allDataRetrieved.elementAt(cnt);

				Book book = new Book(nextBookData);

				if (book != null)
				{
					addBook(book);
				}
			}

		}
		else
		{
			throw new InvalidPrimaryKeyException("No books for title: "
				+ title);
		}

	}

	//----------------------------------------------------------------------------------
	private void addBook(Book b)
	{
		//accounts.add(a);
		int index = findIndexToAdd(b);
		books.insertElementAt(b,index); // To build up a collection sorted on some key
	}

	//----------------------------------------------------------------------------------
	private int findIndexToAdd(Book a)
	{
		//users.add(u);
		int low=0;
		int high = books.size()-1;
		int middle;

		while (low <=high)
		{
			middle = (low+high)/2;

			Book midSession = books.elementAt(middle);

			int result = Book.compare(a,midSession);

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


	/**
	 *
	 */
	//----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("Accounts"))
			return books;
		else
		if (key.equals("AccountList"))
			return this;
		return null;
	}

	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		
		myRegistry.updateSubscribers(key, this);
	}

	//----------------------------------------------------------
	public Book retrieve(String bookId)
	{
		Book retValue = null;
		for (int cnt = 0; cnt < books.size(); cnt++)
		{
			Book nextBook = books.elementAt(cnt);
			String nextBookId = (String)nextBook.getState("bookId");
			if (nextBookId.equals(bookId) == true)
			{
				retValue = nextBook;
				return retValue; // we should say 'break;' here
			}
		}

		return retValue;
	}

	/** Called via the IView relationship */
	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		stateChangeRequest(key, value);
	}

	//------------------------------------------------------
	protected void createAndShowView()
	{

		Scene localScene = myViews.get("BookCollectionView");

		if (localScene == null)
		{
				// create our new view
				View newView = ViewFactory.createView("BookCollectionView", this);
				localScene = new Scene(newView);
				myViews.put("BookCollectionView", localScene);
		}
		// make the view visible by installing it into the frame
		swapToView(localScene);
		
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
