package userInterface;
import java.util.Vector;
import javafx.beans.property.SimpleStringProperty;

public class ItemTableModel {

		private final SimpleStringProperty Barcode;
		private final SimpleStringProperty VendorId;
		private final SimpleStringProperty InventoryItemTypeName;
		private final SimpleStringProperty DateRecieved;
		private final SimpleStringProperty DateOfLastUse;
		private final SimpleStringProperty Notes;
		private final SimpleStringProperty Status;

		//----------------------------------------------------------------------------
		public ItemTableModel(Vector<String> data)
		{
			Barcode =  new SimpleStringProperty(data.elementAt(0));
			VendorId =  new SimpleStringProperty(data.elementAt(1));
			InventoryItemTypeName =  new SimpleStringProperty(data.elementAt(2));
			DateRecieved =  new SimpleStringProperty(data.elementAt(3));
			DateOfLastUse =  new SimpleStringProperty(data.elementAt(4));
			Notes =  new SimpleStringProperty(data.elementAt(5));
			Status =  new SimpleStringProperty(data.elementAt(6));
		}

		//----------------------------------------------------------------------------
		public String getBarcode() {
	        return Barcode.get();
	    }

		//----------------------------------------------------------------------------
	    public void setBarcode(String i) {
	        Barcode.set(i);
	    }

	    //----------------------------------------------------------------------------
	    public String getVendorId() {
	        return VendorId.get();
	    }

	    //----------------------------------------------------------------------------
	    public void setVendorId(String n) {
	        VendorId.set(n);
	    }

	    //----------------------------------------------------------------------------
	    public String getInventoryItemTypeName() {
	        return InventoryItemTypeName.get();
	    }

	    //----------------------------------------------------------------------------
	    public void setInventoryItemTypeName(String n) {
	        InventoryItemTypeName.set(n);
	    }

	    //----------------------------------------------------------------------------
	    public String getDateRecieved() {
	        return DateRecieved.get();
	    }

	    //----------------------------------------------------------------------------
	    public void setDateRecieved(String n) {
	        DateRecieved.set(n);
	    }
	    //----------------------------------------------------------------------------
	    public String getDateOfLastUse() {
	        return DateOfLastUse.get();
	    }

	    //----------------------------------------------------------------------------
	    public void setDateOfLastUse(String s)
	    {
	    	DateOfLastUse.set(s);
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
