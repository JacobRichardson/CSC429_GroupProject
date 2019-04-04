package model;
import java.util.Vector;
import javafx.beans.property.SimpleStringProperty;

public class iITTableModel {

		private final SimpleStringProperty ItemTypeName;
		private final SimpleStringProperty Unit;
		private final SimpleStringProperty UnitMeasure;
		private final SimpleStringProperty ValidityDays;
		private final SimpleStringProperty ReorderPoint;
		private final SimpleStringProperty Notes;
		private final SimpleStringProperty Status;

		//----------------------------------------------------------------------------
		public iITTableModel(Vector<String> data)
		{
			ItemTypeName =  new SimpleStringProperty(data.elementAt(0));
			Unit =  new SimpleStringProperty(data.elementAt(1));
			UnitMeasure =  new SimpleStringProperty(data.elementAt(2));
			ValidityDays =  new SimpleStringProperty(data.elementAt(3));
			ReorderPoint =  new SimpleStringProperty(data.elementAt(4));
			Notes =  new SimpleStringProperty(data.elementAt(5));
			Status =  new SimpleStringProperty(data.elementAt(6));
		}

		//----------------------------------------------------------------------------
		public String getItemTypeName() {
	        return ItemTypeName.get();
	    }

		//----------------------------------------------------------------------------
	    public void setItemTypeName(String i) {
	        ItemTypeName.set(i);
	    }

	    //----------------------------------------------------------------------------
	    public String getUnit() {
	        return Unit.get();
	    }

	    //----------------------------------------------------------------------------
	    public void setUnit(String n) {
	        Unit.set(n);
	    }

	    //----------------------------------------------------------------------------
	    public String getUnitMeasure() {
	        return UnitMeasure.get();
	    }

	    //----------------------------------------------------------------------------
	    public void setUnitMeasure(String n) {
	        UnitMeasure.set(n);
	    }

	    //----------------------------------------------------------------------------
	    public String getValidityDays() {
	        return ValidityDays.get();
	    }

	    //----------------------------------------------------------------------------
	    public void setValidityDays(String n) {
	        ValidityDays.set(n);
	    }
	    //----------------------------------------------------------------------------
	    public String getReorderPoint() {
	        return ReorderPoint.get();
	    }

	    //----------------------------------------------------------------------------
	    public void setReorderPoint(String s)
	    {
	    	ReorderPoint.set(s);
	    }
	   //----------------------------------------------------------------------------
	    public String getNotes() {
	        return Notes.get();
	    }

	    //----------------------------------------------------------------------------
	    public void setNotes(String s)
	    {
	    	Notes.set(s);
	    }
	    public String getStatus() {
	        return Status.get();
	    }

	    //----------------------------------------------------------------------------
	    public void setStatus(String s)
	    {
	    	Status.set(s);
	    }
	}
