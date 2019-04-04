package model;

import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

import event.Event;
import impresario.IModel;
import impresario.IView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import userInterface.MainStageContainer;

public class InventoryItemTypeSearchCollection extends EntityBase implements IView, IModel{

	private static final String myTableName = "InventoryItemType";
	String history;
	private Vector <InventoryItemType> inventoryItemTypes;
	// GUI Components
	private Hashtable<String, Scene> myViews;
	private Stage	  	myStage;
	
	protected InventoryItemTypeSearchCollection(String details, String history) throws Exception {
		super(myTableName);
		
		if (details == null)
		{
			new Event(Event.getLeafLevelClassName(this), "<init>",
				"Missing account holder information", Event.FATAL);
			throw new Exception
				("UNEXPECTED ERROR: PatronAgeCollection.<init>: account holder information is null");
		}

		String query = "SELECT * FROM " + myTableName + " where "+details;

		Vector allDataRetrieved = getSelectQueryResult(query);
		if (allDataRetrieved != null)
		{
			inventoryItemTypes = new Vector<InventoryItemType>();

			for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
			{
				Properties nextInventoryItemTypeData = (Properties)allDataRetrieved.elementAt(cnt);

				InventoryItemType inventoryItemType = new InventoryItemType(nextInventoryItemTypeData);

				if (inventoryItemType != null)
				{
					addInventoryItemType(inventoryItemType);
				}
			}

		}
		else
			System.out.println("No vendors found for "+details);
		this.history=history;
		myViews = new Hashtable<String, Scene>();
		myStage = MainStageContainer.getInstance();
	}
	
	private void addInventoryItemType(InventoryItemType p) {
		// TODO Auto-generated method stub
		inventoryItemTypes.add(p);
	}

	@Override
	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getState(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initializeSchema(String tableName) {
		// TODO Auto-generated method stub
		
	}

}
