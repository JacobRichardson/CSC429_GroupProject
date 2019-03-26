package userInterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

//==============================================================================
public class BookTableModel2
{
	private final SimpleStringProperty bookID;
	private final SimpleStringProperty author;
	private final SimpleStringProperty title;
	private final SimpleStringProperty pubYear;
	private final SimpleStringProperty status;
	
	//----------------------------------------------------------------------------
	public BookTableModel2(Vector<String> accountData)
	{
		bookID =  new SimpleStringProperty(accountData.elementAt(0));
		author =  new SimpleStringProperty(accountData.elementAt(1));
		title =  new SimpleStringProperty(accountData.elementAt(2));
		pubYear =  new SimpleStringProperty(accountData.elementAt(3));
		status =  new SimpleStringProperty(accountData.elementAt(4));
	}

	//----------------------------------------------------------------------------
	public String getBookId() {
        return bookID.get();
    }

	//----------------------------------------------------------------------------
    public void setBookId(String number) {
    	bookID.set(number);
    }

    //----------------------------------------------------------------------------
    public String getAuthor() {
        return author.get();
    }

    //----------------------------------------------------------------------------
    public void setAuthor(String aType) {
        author.set(aType);
    }

    //----------------------------------------------------------------------------
    public String getTitle() {
        return title.get();
    }

    //----------------------------------------------------------------------------
    public void setTitle(String bal) {
        title.set(bal);
    }
    
    //----------------------------------------------------------------------------
    public String getPubYear() {
        return pubYear.get();
    }

    //----------------------------------------------------------------------------
    public void setPubYear(String charge)
    {
    	pubYear.set(charge);
    }
    
    public String getStatus() {
        return status.get();
    }

    //----------------------------------------------------------------------------
    public void setStatus(String state)
    {
    	status.set(state);
    }
}
