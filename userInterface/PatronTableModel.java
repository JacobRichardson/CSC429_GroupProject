package userInterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

//==============================================================================
public class PatronTableModel
{
	private final SimpleStringProperty patronID;
	private final SimpleStringProperty name;
	private final SimpleStringProperty address;
	private final SimpleStringProperty city;
	private final SimpleStringProperty stateCode;
	private final SimpleStringProperty zip;
	private final SimpleStringProperty email;
	private final SimpleStringProperty dateOfBirth;
	private final SimpleStringProperty status;

	//----------------------------------------------------------------------------
	public PatronTableModel(Vector<String> accountData)
	{
		patronID =  new SimpleStringProperty(accountData.elementAt(0));
		name =  new SimpleStringProperty(accountData.elementAt(1));
		address =  new SimpleStringProperty(accountData.elementAt(2));
		city =  new SimpleStringProperty(accountData.elementAt(3));
		stateCode =  new SimpleStringProperty(accountData.elementAt(4));
		zip =  new SimpleStringProperty(accountData.elementAt(5));
		email =  new SimpleStringProperty(accountData.elementAt(6));
		dateOfBirth =  new SimpleStringProperty(accountData.elementAt(7));
		status =  new SimpleStringProperty(accountData.elementAt(8));
	}

	//----------------------------------------------------------------------------
	public String getPatronID() {
        return patronID.get();
    }

	//----------------------------------------------------------------------------
    public void setPatronID(String number) {
        patronID.set(number);
    }

    //----------------------------------------------------------------------------
    public String getName() {
        return name.get();
    }

    //----------------------------------------------------------------------------
    public void setName(String n) {
        name.set(n);
    }

    //----------------------------------------------------------------------------
    public String getAddress() {
        return address.get();
    }

    //----------------------------------------------------------------------------
    public void setAddress(String a) {
        address.set(a);
    }
    
    //----------------------------------------------------------------------------
    public String getCity() {
        return city.get();
    }

    //----------------------------------------------------------------------------
    public void setCity(String c)
    {
    	city.set(c);
    }
    
    //----------------------------------------------------------------------------
    public String getStateCode() {
        return stateCode.get();
    }

    //----------------------------------------------------------------------------
    public void setStateCode(String state)
    {
    	stateCode.set(state);
    }
    
    //----------------------------------------------------------------------------
    public String getZip() {
        return zip.get();
    }

    //----------------------------------------------------------------------------
    public void setZip(String c)
    {
    	zip.set(c);
    }
    
    //----------------------------------------------------------------------------
    public String getEmail() {
        return email.get();
    }

    //----------------------------------------------------------------------------
    public void setEmail(String mail)
    {
    	email.set(mail);
    }
    
    //----------------------------------------------------------------------------
    public String getDateOfBirth() {
        return dateOfBirth.get();
    }

    //----------------------------------------------------------------------------
    public void setDateOfBirth(String d)
    {
    	dateOfBirth.set(d);
    }
    
    //----------------------------------------------------------------------------
    public String getStatus() {
        return status.get();
    }

    //----------------------------------------------------------------------------
    public void setStatus(String s)
    {
    	status.set(s);
    }
}
