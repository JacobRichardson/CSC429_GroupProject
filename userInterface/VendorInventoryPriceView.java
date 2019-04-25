package userInterface;

//Imports
import java.util.Properties;
import impresario.IModel;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.InventoryItemType;
import model.Manager;
import model.Vendor;
import model.VendorInventoryItemType;

import java.time.LocalDate;

public class VendorInventoryPriceView  extends View 
{
	
	private GridPane grid;
	private TextField priceTF;
	private Button submitBTN;
	private Button cancelBTN;
	private Button backBTN;
	private Label priceLBL;
	private Label messageLBL;
	
	// For showing error message
	protected MessageView statusLog;
	
	public VendorInventoryPriceView(IModel model) {
		super(model, "EnterBookView");
		
		VBox container = new VBox(10);

		container.setPadding(new Insets(15, 5, 5, 5));

		// create a Node (Text) for showing the title
		container.getChildren().add(createTitle());

		// create a Node (GridPane) for showing data entry fields
		container.getChildren().add(createFormContents());
		getChildren().add(container);

		populateFields();

		// STEP 0: Be sure you tell your model what keys you are interested in
		myModel.subscribe("LoginError", this);

	}
	
	private void populateFields() {
		priceTF.setText("");
		messageLBL.setText("");
	}
	
	private Node createTitle() {		
		Text titleText = new Text("       Restaurant Inventory Management         ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 40));
		titleText.setTextAlignment(TextAlignment.CENTER);
		
		
	
		return titleText;
	}
	
	private Node createFormContents() {
		
		//CREATE GUI COMPONETS
		
		grid = new GridPane();
		priceTF = new TextField();
		priceLBL = new Label("Price");
		messageLBL = new Label();
		cancelBTN = new Button("Cancel");
	  	submitBTN = new Button("Submit");
	  	backBTN= new Button("Back");
		
		//ADD COMPONTES TO GRID.
		grid.add(priceLBL, 0, 0);				grid.add(priceTF, 1, 0);
		grid.add(submitBTN, 0, 1);				grid.add(cancelBTN, 1, 1);
		grid.add(backBTN, 0, 2);
		grid.add(messageLBL, 0, 4, 2, 1);
		
		//STYLE GRID.
		
    	grid.setAlignment(Pos.CENTER);
   		grid.setHgap(10);
    	grid.setVgap(10);
    	grid.setPadding(new Insets(25, 25, 25, 25));
    	
    	//BUTTON HANDLERS.
    	
    	//Call process action.
    	submitBTN.setOnAction(new EventHandler<ActionEvent>() {
 		     public void handle(ActionEvent e) {
 		     	processAction(e);    
      	     }
    	});
  
    	//Call process action.
    	cancelBTN.setOnAction(new EventHandler<ActionEvent>() {
		     public void handle(ActionEvent e) {
		    	 new Manager();   
     	     }
    	});
    	
    	backBTN.setOnAction(new EventHandler<ActionEvent>() {
		     public void handle(ActionEvent e) {
		     	processAction(e);    
     	     }
   	});
    
    	//Return the grid.
    	return grid;
	}
	
	
	//Method to process all actions.
	protected void processAction(Event e) {
		System.out.println(myModel);
		//If it is the cancel button request a cancel state change request.
		if(e.getSource() == cancelBTN) {
			myModel.stateChangeRequest("cancel", null);
		}
		else if(e.getSource() == backBTN)
			myModel.stateChangeRequest("BackPrice", null);
		//If the text field is emtpy.
		else if(priceTF.getText().isEmpty()) {
			//Focus on the text field
			priceTF.requestFocus();
			//Display message to enter price
			messageLBL.setText("Please enter price.");
		}
		//Check to make sure the number is positive.
		else if(!isDouble(priceTF.getText())) {
			priceTF.requestFocus();
			messageLBL.setText("Please enter a positive decimal number for the price.");
		}
		else {
			//Proces the vendorIventoryItemType.
			
			//Get the data.
			String vendorId = Vendor.getSelectedVendorId();
			//Make sure to set the inventoryItemTypeName in the inventoryItemTypeCollection view.
			String inventoryItemTypeName = InventoryItemType.getSelectedInventoryItemTypeName();
			String price = priceTF.getText();
			String dateOfLastUpdate = LocalDate.now().toString();
			
			//Proces the vendor inventory item type.
			processVendorInventoryItemType(vendorId, inventoryItemTypeName, price, dateOfLastUpdate);
			
			//Clear the price box.
			priceTF.setText("");
			
			//Display message.
			 messageLBL.setText("Vendor Inventory Item Type has been entered!");
			 submitBTN.setVisible(false);
			 cancelBTN.setText("Done");
			 backBTN.setVisible(false);
		}
				
	}
	
	//This creates a properties object and inserts it into the database.
	private void processVendorInventoryItemType(String vendorId, String inventoryItemTypeName, String price, String dateOfLastUpdate) {
		
		//Make sure to convery to the right data type of necessary (String to double and so on).
		
		//New properties object.
		Properties props = new Properties();
		
		//System.out.println("VendorId: " + vendorId);
		
		//Set the properties.
		props.setProperty("VendorId", vendorId);
		props.setProperty("InventoryItemTypeName", inventoryItemTypeName);
		props.setProperty("VendorPrice", price);
		props.setProperty("DateOfLastUpdate", dateOfLastUpdate);
		
		//Create the Vendor Inventory Price object.
		VendorInventoryItemType vip = new VendorInventoryItemType(props);
		
		//Save into the database.
		vip.update();
		
	}
	
	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub
		
	}
	
	public void displayMessage(String message)
	{
		statusLog.displayMessage(message);
	}

	
	//Method to test is a string is a double.
	private boolean isDouble (String s) {
		try {
			if(Double.parseDouble(s) < 0) {
				return false;
			}
		}
		catch(NumberFormatException e) {
			return false;
		}
		catch(NullPointerException e) {
			return false;
		}
		
		return true;
	}
			
			
}