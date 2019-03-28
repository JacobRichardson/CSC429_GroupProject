package userInterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

//==============================================================================
public class vendorTableModel
{
	private final SimpleStringProperty Id;
	private final SimpleStringProperty Name;
	private final SimpleStringProperty PhoneNumber;
	private final SimpleStringProperty Status;

	//----------------------------------------------------------------------------
	public vendorTableModel(Vector<String> accountData)
	{
		Id =  new SimpleStringProperty(accountData.elementAt(0));
		Name =  new SimpleStringProperty(accountData.elementAt(1));
		PhoneNumber =  new SimpleStringProperty(accountData.elementAt(2));
		Status =  new SimpleStringProperty(accountData.elementAt(3));
	}

	//----------------------------------------------------------------------------
	public String getId() {
        return Id.get();
    }

	//----------------------------------------------------------------------------
    public void setId(String i) {
        Id.set(i);
    }

    //----------------------------------------------------------------------------
    public String getName() {
        return Name.get();
    }

    //----------------------------------------------------------------------------
    public void setName(String n) {
        Name.set(n);
    }

    //----------------------------------------------------------------------------
    public String getPhoneNumber() {
        return PhoneNumber.get();
    }

    //----------------------------------------------------------------------------
    public void setPhoneNumber(String n) {
        PhoneNumber.set(n);
    }
    
    
    //----------------------------------------------------------------------------
    public String getStatus() {
        return Status.get();
    }

    //----------------------------------------------------------------------------
    public void setStatus(String s)
    {
    	Status.set(s);
    }
}
