// specify the package
package model;

// system imports
import java.util.Hashtable;
import java.util.Properties;

import javafx.stage.Stage;
import userInterface.MainStageContainer;
import userInterface.View;
import userInterface.ViewFactory;
import userInterface.WindowPosition;
import javafx.scene.Scene;

// project imports
import impresario.IModel;
import impresario.ISlideShow;
import impresario.IView;
import impresario.ModelRegistry;

import event.Event;

/** The class containing the Teller  for the Librarian application */
//==============================================================
public class Librarian implements IView, IModel
// This class implements all these interfaces (and does NOT extend 'EntityBase')
// because it does NOT play the role of accessing the back-end database tables.
// It only plays a front-end role. 'EntityBase' objects play both roles.
{
	// For Impresario
	private Properties dependencies;
	private ModelRegistry myRegistry;
	
	// GUI Components
		private Hashtable<String, Scene> myViews;
		private Stage	  	myStage;

	// constructor for this class
	//----------------------------------------------------------
	public Librarian()
	{
		myStage = MainStageContainer.getInstance();
		myViews = new Hashtable<String, Scene>();

		// STEP 3.1: Create the Registry object - if you inherit from
		// EntityBase, this is done for you. Otherwise, you do it yourself
		myRegistry = new ModelRegistry("Librarian");
		if(myRegistry == null)
		{
			new Event(Event.getLeafLevelClassName(this), "Librarian",
				"Could not instantiate Registry", Event.ERROR);
		}

		// STEP 3.2: Be sure to set the dependencies correctly
		setDependencies();

		// Set up the initial view
		createAndShowLibrarianView();
	}


	//-----------------------------------------------------------------------------------
	private void setDependencies()
	{
		dependencies = new Properties();
		dependencies.setProperty("searchPatron", "patron");
		dependencies.setProperty("titleSearch", "title");
		dependencies.setProperty("LibrarianView", "Librarian");
		dependencies.setProperty("zip", "zipCode");
		dependencies.setProperty("enterBookView", "bookTitle");
		dependencies.setProperty("BookCollectionView", "CancelbookList");

		myRegistry.setDependencies(dependencies);
	}

	/**
	 * Method called from client to get the value of a particular fieldf
	 * held by the objects encapsulated by this object.
	 *
	 * @param	key	Name of database column (field) for which the client wants the value
	 *
	 * @return	Value associated with the field
	 */
	//----------------------------------------------------------

	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		if(key.endsWith("searchPatron"))
			createAndShowPatronSearch();
		else if(key.equals("titleSearch"))
			createAndShowTitleSearch();
		else if(key.equals("LibrarianView")||key.equals("CancelBookList"))
			createAndShowLibrarianView();
		else if(key.equals("zip"))
			searchPatrons((String)value);
		else if(key.equals("title"))
			searchBooks((String)value);
		else if(key.equals("enterBookView")) {
			createAndShowEnterBookView();
		}
		else if(key.equals("enterPatronView")) {
			createAndShowEnterPatronView();
		}
		else
			System.out.println("No screen for key.");
		myRegistry.updateSubscribers(key, this);
	}



	private void searchPatrons(String zip) {
		try {
			PatronZipCollection p =new PatronZipCollection(zip);
			createAndShowPatronView(p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	//Search for books
	private void searchBooks(String title) {
		// TODO Auto-generated method stub
		try {
			BookCatalog b=new BookCatalog(title);
			//b.createAndShowView();
			createAndShowCollectionView(b);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/** Called via the IView relationship */
	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		// DEBUG System.out.println("Teller.updateState: key: " + key);

		stateChangeRequest(key, value);
	}



	/** Register objects to receive state updates. */
	//----------------------------------------------------------
	public void subscribe(String key, IView subscriber)
	{
		// DEBUG: System.out.println("Cager[" + myTableName + "].subscribe");
		// forward to our registry
		myRegistry.subscribe(key, subscriber);
	}

	/** Unregister previously registered objects. */
	//----------------------------------------------------------
	public void unSubscribe(String key, IView subscriber)
	{
		// DEBUG: System.out.println("Cager.unSubscribe");
		// forward to our registry
		myRegistry.unSubscribe(key, subscriber);
	}

	@Override
	public Object getState(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public void swapToView(Scene newScene)
	{		
		if (newScene == null)
		{
			System.out.println("Librarian.swapToView(): Missing view for display");
			new Event(Event.getLeafLevelClassName(this), "swapToView",
				"Missing view for display ", Event.ERROR);
			return;
		}

		myStage.setScene(newScene);
		myStage.sizeToScene();
		
			
		//Place in center
		WindowPosition.placeCenter(myStage);

	}

//---------			Create and show methods			-----------------------------------------------------------------------------------------------//
	private void createAndShowLibrarianView() {
		Scene currentScene = (Scene)myViews.get("LibrarianView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("LibrarianView", this); // USE VIEW FACTORY
			currentScene = new Scene(newView);
			myViews.put("TellerView", currentScene);
		}
				
		swapToView(currentScene);
		
	}
	
	private void createAndShowPatronSearch() {
		Scene currentScene = (Scene)myViews.get("searchPatron");
		
		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("searchPatron", this); // USE VIEW FACTORY
			currentScene = new Scene(newView);
			myViews.put("searchPatron", currentScene);
		}
				

		// make the view visible by installing it into the frame
		swapToView(currentScene);
		
	}
	
	private void createAndShowTitleSearch() {
		Scene currentScene = (Scene)myViews.get("titleSearch");
		
		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("titleSearch", this); // USE VIEW FACTORY
			currentScene = new Scene(newView);
			myViews.put("titleSearch", currentScene);
		}
				

		// make the view visible by installing it into the frame
		swapToView(currentScene);
		
	}
	
	private void createAndShowEnterBookView() {
		Scene currentScene = (Scene)myViews.get("enterBookView");
		
		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("enterBookView", this); // USE VIEW FACTORY
			currentScene = new Scene(newView);
			myViews.put("enterBookView", currentScene);
		}
		swapToView(currentScene);
	}
	
	private void createAndShowEnterPatronView() {
		Scene currentScene = (Scene)myViews.get("enterPatronView");
		
		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("enterPatronView", this); // USE VIEW FACTORY
			currentScene = new Scene(newView);
			myViews.put("enterPatronView", currentScene);
		}
		swapToView(currentScene);
	}
	
	protected void createAndShowCollectionView(BookCatalog b){
		Scene localScene = myViews.get("BookCollectionView");

		if (localScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("BookCollectionView", b); // USE VIEW FACTORY
			localScene = new Scene(newView);
			myViews.put("BookCollectionView", localScene);
		}	
		swapToView(localScene);
		
	}
	
	private void createAndShowPatronView(PatronZipCollection p) {
		Scene localScene = myViews.get("patronCollection");

		if (localScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("patronCollection", p); // USE VIEW FACTORY
			localScene = new Scene(newView);
			myViews.put("patronCollection", localScene);
		}	
		swapToView(localScene);
	}
	
//-----------------------------------------------------------------------------

}