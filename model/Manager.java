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
public class Manager implements IView, IModel
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
	public Manager()
	{
		myStage = MainStageContainer.getInstance();
		myViews = new Hashtable<String, Scene>();

		// STEP 3.1: Create the Registry object - if you inherit from
		// EntityBase, this is done for you. Otherwise, you do it yourself
		myRegistry = new ModelRegistry("Manager");
		if(myRegistry == null)
		{
			new Event(Event.getLeafLevelClassName(this), "Manager",
				"Could not instantiate Registry", Event.ERROR);
		}

		// STEP 3.2: Be sure to set the dependencies correctly
		setDependencies();

		// Set up the initial view
		createAndShowManagerView();
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
		if(key.equals("ModifyVendor")||key.equals("AddVIIT"))
			createAndShowVendorSearch();
		else if(key.equals("VendorSelectionScreen"))
			searchVendors(value);
		else if(key.equals("chooseActionScreen")||key.equals("cancel"))
			createAndShowManagerView();
		else
			System.out.println("No screen for key.");
		myRegistry.updateSubscribers(key, this);
	}



	private void searchVendors(Object value) {
		try {
			VendorSearchCollection v=new VendorSearchCollection((String)value);
			createAndShowVendorCollection(v);
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
	private void createAndShowManagerView() {
		Scene currentScene = (Scene)myViews.get("ManagerView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("ManagerView", this); // USE VIEW FACTORY
			currentScene = new Scene(newView);
			myViews.put("ManagerView", currentScene);
		}
				
		swapToView(currentScene);
		
	}
	
	private void createAndShowVendorSearch() {
		Scene currentScene = (Scene)myViews.get("searchVendor");
		
		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("searchVendor", this); // USE VIEW FACTORY
			currentScene = new Scene(newView);
			myViews.put("searchVendor", currentScene);
		}
				

		// make the view visible by installing it into the frame
		swapToView(currentScene);
		
	}
	
	private void createAndShowVendorCollection(VendorSearchCollection v) {
		Scene localScene = myViews.get("vendorCollection");

		if (localScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("vendorCollection", v); // USE VIEW FACTORY
			localScene = new Scene(newView);
			myViews.put("vendorCollection", localScene);
		}	
		swapToView(localScene);
	}
	
//-----------------------------------------------------------------------------

}