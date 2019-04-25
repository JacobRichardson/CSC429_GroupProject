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
import impresario.IModel;
import impresario.IView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import userInterface.MainStageContainer;
import userInterface.View;
import userInterface.ViewFactory;
import userInterface.WindowPosition;

//==============================================================
public class VendorSearchCollection  extends EntityBase implements IView, IModel
{
	private static final String myTableName = "Vendor";
	String history;
	private Vector <Vendor>vendors;
	// GUI Components
	private Hashtable<String, Scene> myViews;
	private Stage	  	myStage;

	// constructor for this class
	//----------------------------------------------------------
	public VendorSearchCollection(String details, String history) throws
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
		this.history=history;
		myViews = new Hashtable<String, Scene>();
		myStage = MainStageContainer.getInstance();
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
		if(history=="ModifyVendor" && value!=null) {
			modifyVendor((String)value);
		}
		else if(key.equals("VendorSelected") && ( Manager.getChoice() == "AddVIIT" || Manager.getChoice() == "deleteVIIT")) {
			Vendor.setSelectedVendorId((String)value);
			createAndShowIventoryItemTypeSearch();
		}
		else if(key.equals("IITCollectionView")) {
			searchIIT((String)value);
		}
		else if(key.equals("VendorSelected") && (Manager.getChoice() == "processInvoice")) {
			//Go to submit item type name screen.
			try {
				Vendor.setSelectedVendorId((String)value);
				createAndShowEnterIITView();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(key.equals("Back")) {
			createAndShowVendorSearch();
		}
		else if(key.equals("BackIIT"))
			createAndShowVendorCollection();
		else if(key.equals("VendorSelectionScreen")) {
			searchVendors((String) value);
		}
		else if(key.equals("EnterBarcodeNotes") ) {
			try {
				createAndShowEnterBarcodeNotesView();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		else if(key.equals("BackVendor")) {
			new Manager();
		}
		else
			System.out.print(key+" | "+value);
		myRegistry.updateSubscribers(key, this);
	}
	
	private void searchIIT(String s) {
		try {
			createAndShowInventoryCollectionView(new IITCollection(s,history));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	public void modifyVendor(String Id) {
		try {
			Vendor v=new Vendor((String)Id);
			createAndShowModifyVendor(v);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createAndShowModifyVendor(Vendor v) {
    	Scene localScene = (Scene)myViews.get("vendorModify");

		if (localScene == null)
		{
			// create our initial view
		    View newView = ViewFactory.createView("vendorModify", v); // USE VIEW FACTORY
		    localScene = new Scene(newView);
		    localScene.getStylesheets().add("style.css");
		    myViews.put("vendorModify", localScene);
		}
		swapToView(localScene);
	}
	
    
    private void createAndShowIventoryItemTypeSearch () {
    	
    	Scene localScene = myViews.get("FindInventoryItemTypeView");

		if (localScene == null)
		{
			// create our initial view
		    View newView = ViewFactory.createView("FindInventoryItemTypeView", this); // USE VIEW FACTORY
		    localScene = new Scene(newView);
		    localScene.getStylesheets().add("style.css");
		    myViews.put("FindInventoryItemTypeView", localScene);
		}
		swapToView(localScene);
    }
	
    private void createAndShowInventoryCollectionView (IITCollection iit){
    	Scene localScene = myViews.get("IITCollectionView");
		if (localScene == null)
		{
			// create our initial view
		    View newView = ViewFactory.createView("IITCollectionView", iit); // USE VIEW FACTORY
		    localScene = new Scene(newView);
		    localScene.getStylesheets().add("style.css");
		    myViews.put("IITCollectionView", localScene);
		}
		swapToView(localScene);
    }
    
    private void createAndShowEnterIITView () throws Exception {
    	
    	Scene localScene = myViews.get("enterIIT");
    	
		if (localScene == null)
		{
			// create our initial view
		    View newView = ViewFactory.createView("enterIIT", this); // USE VIEW FACTORY
		    localScene = new Scene(newView);
		    localScene.getStylesheets().add("style.css");
		    myViews.put("enterIIT", localScene);
		}
		swapToView(localScene);
    }
    
    private void createAndShowVendorSearch() {
		Scene currentScene = (Scene)myViews.get("searchVendor");
		
		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("searchVendor", this); // USE VIEW FACTORY
			currentScene = new Scene(newView);
			currentScene.getStylesheets().add("style.css");
			myViews.put("searchVendor", currentScene);
		}
		swapToView(currentScene);
		
	}
    
    private void searchVendors(Object value) {
		try {
			String history=Manager.getChoice();
			VendorSearchCollection v=new VendorSearchCollection((String)value, history);
			createAndShowVendorCollection(v);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    private void createAndShowVendorCollection(VendorSearchCollection v) {
		Scene localScene = myViews.get("vendorCollection");

		if (localScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("vendorCollection", v); // USE VIEW FACTORY
			localScene = new Scene(newView);
			localScene.getStylesheets().add("style.css");
			myViews.put("vendorCollection", localScene);
		}	
		swapToView(localScene);
	}
    private void createAndShowEnterBarcodeNotesView () throws Exception {
    	
    	System.out.println("CREATE AND SHOW ENTER BARCODE/NOTES VIEW!!!!");
    	Scene localScene = myViews.get("EnterItemBarcodeNotesView");
    	
		if (localScene == null)
		{
			// create our initial view
		    View newView = ViewFactory.createView("EnterItemBarcodeNotesView", this); // USE VIEW FACTORY
		    localScene = new Scene(newView);
		    localScene.getStylesheets().add("style.css");
		    myViews.put("EnterItemBarcodeNotesView", localScene);
		}
		swapToView(localScene);
    }
    
    private void createAndShowVendorCollection() {
		Scene localScene = myViews.get("vendorCollection");

		if (localScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("vendorCollection", this); // USE VIEW FACTORY
			localScene = new Scene(newView);
			localScene.getStylesheets().add("style.css");
			myViews.put("vendorCollection", localScene);
		}	
		swapToView(localScene);
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
	
}