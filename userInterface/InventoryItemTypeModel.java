package userInterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

public class InventoryItemTypeModel {

	private final SimpleStringProperty ItemTypeName;
	private final SimpleStringProperty Units;
	private final SimpleStringProperty UnitMeasure;
	private final SimpleStringProperty ValidityDays;
	private final SimpleStringProperty ReorderPoint;
	private final SimpleStringProperty Notes;
	private final SimpleStringProperty Status;
	
	public InventoryItemTypeModel(Vector<String> accountData)
	{
		ItemTypeName =  new SimpleStringProperty(accountData.elementAt(0));
		Units =  new SimpleStringProperty(accountData.elementAt(1));
		UnitMeasure =  new SimpleStringProperty(accountData.elementAt(2));
		ValidityDays =  new SimpleStringProperty(accountData.elementAt(3));
		ReorderPoint =  new SimpleStringProperty(accountData.elementAt(4));
		Notes =  new SimpleStringProperty(accountData.elementAt(5));
		Status =  new SimpleStringProperty(accountData.elementAt(6));
	}

	public String getItemTypeName() {
		return ItemTypeName.get();
	}

	public String getUnits() {
		return Units.get();
	}

	public String getUnitMeasure() {
		return UnitMeasure.get();
	}

	public String getValidityDays() {
		return ValidityDays.get();
	}

	public String getReorderPoint() {
		return ReorderPoint.get();
	}

	public String getNotes() {
		return Notes.get();
	}

	public String getStatus() {
		return Status.get();
	}
}
