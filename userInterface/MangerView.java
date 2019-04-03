package userInterface;

import java.text.NumberFormat;
import java.util.Properties;

import impresario.IModel;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

public class MangerView extends View {
	
	private Button addIventoryItemTypeBTN;
	private Button modifyVendorBTN;
	private Button outOfInventoryBTN;
	private Button modifyIventoryItemTypeBTN;
	private Button addVendorInventoryItemTypeBTN;
	private Button modifyStatusInventoryItemBTN;
	private Button deleteInventoryItemTypeBTN;
	private Button deleteVendorInvetoryItemBTN;
	private Button reorderListBTN;
	private Button addVendorBTN;
	private Button processInvoiceBTN;
	private Button fullInventoryBTN;
	
	private Button doneBTN;

	
	public MangerView(IModel model) {
		super(model, "Manger View");
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
		// TODO Auto-generated method stub
		
	}

	private Node createTitle() {		
		Text titleText = new Text("       Brockport Library          ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKGREEN);
		
	
		return titleText;
	}

	private Node createFormContents() {
		GridPane grid = new GridPane();
    	grid.setAlignment(Pos.CENTER);
   		grid.setHgap(10);
    	grid.setVgap(10);
    	grid.setPadding(new Insets(25, 25, 25, 25));
    	
    	
    	//Initialize Buttons.
    	addIventoryItemTypeBTN = new Button("Add an Inventory Item Type");
    	modifyVendorBTN = new Button("Modify A Vendor");
    	outOfInventoryBTN = new Button("Take an Item out of Inventory");
    	modifyIventoryItemTypeBTN = new Button("Modify an Inventory Item Type");
    	addVendorInventoryItemTypeBTN = new Button("Add a Vendor Inventory Item Type");
    	modifyStatusInventoryItemBTN = new Button("Modify the Status of an Inventory Item");
    	deleteInventoryItemTypeBTN = new Button("Delete an Inventory Item Type");
    	deleteVendorInvetoryItemBTN = new Button("Delete a Vendor Inventory Item Type");
    	reorderListBTN = new Button("Obtain a Reorder list");
    	addVendorBTN = new Button("Add a Vendor");
    	processInvoiceBTN = new Button("Process an Invoice from a Vendor");
    	fullInventoryBTN = new Button("Obtain a full current Inventory");
       	doneBTN = new Button("Done");

       	//Add buttons to grid.
       	grid.add(addIventoryItemTypeBTN,0,0);		 	grid.add(addVendorBTN,1,0);					 grid.add(deleteVendorInvetoryItemBTN,2,0);		grid.add(modifyStatusInventoryItemBTN,3,0);
       	grid.add(modifyIventoryItemTypeBTN,0,1);		grid.add(modifyVendorBTN,1,1);				 grid.add(processInvoiceBTN,2,1);	    		grid.add(reorderListBTN,3,1);
    	grid.add(deleteInventoryItemTypeBTN,0,2);  		grid.add(addVendorInventoryItemTypeBTN,1,2); grid.add(outOfInventoryBTN,2,2);				grid.add(fullInventoryBTN,3,2);
    	grid.add(doneBTN, 0, 4);
    	
       	
       	//Styles
       	addIventoryItemTypeBTN.setMinWidth(250);
    	addIventoryItemTypeBTN.setMinHeight(30);
    	modifyVendorBTN.setMinWidth(250);
    	modifyVendorBTN.setMinHeight(30);
    	outOfInventoryBTN.setMinWidth(250);
    	outOfInventoryBTN.setMinHeight(30);
    	modifyIventoryItemTypeBTN.setMinWidth(250);
    	modifyIventoryItemTypeBTN.setMinHeight(30);
    	addVendorInventoryItemTypeBTN.setMinWidth(250);
    	addVendorInventoryItemTypeBTN.setMinHeight(30);
    	modifyStatusInventoryItemBTN.setMinWidth(250);
    	modifyStatusInventoryItemBTN.setMinHeight(30);
    	deleteInventoryItemTypeBTN.setMinWidth(250);
    	deleteInventoryItemTypeBTN.setMinHeight(30);
    	deleteVendorInvetoryItemBTN.setMinWidth(250);
    	deleteVendorInvetoryItemBTN.setMinHeight(30);
    	reorderListBTN.setMinWidth(250);
    	reorderListBTN.setMinHeight(30);
    	addVendorBTN.setMinWidth(250);
    	addVendorBTN.setMinHeight(30);
    	processInvoiceBTN.setMinWidth(250);
    	processInvoiceBTN.setMinHeight(30);
    	fullInventoryBTN.setMinWidth(250);
    	fullInventoryBTN.setMinHeight(30);
    	
    	
       	//EVENT HANDLERS
    	
    	doneBTN.setOnAction(new EventHandler<ActionEvent>() {
  		     public void handle(ActionEvent e) {
  		     	processAction(e);    
       	     }
  		});
    	
    	addIventoryItemTypeBTN.setOnAction(new EventHandler<ActionEvent>() {
 		     public void handle(ActionEvent e) {
 		     	processAction(e);  
 		     	
      	     }
 		});
    	
    	modifyVendorBTN.setOnAction(new EventHandler<ActionEvent>() {
		     public void handle(ActionEvent e) {
		     	processAction(e);    
     	     }
		});
    	outOfInventoryBTN.setOnAction(new EventHandler<ActionEvent>() {
		     public void handle(ActionEvent e) {
		     	processAction(e);    
     	     }
		});
    	modifyIventoryItemTypeBTN.setOnAction(new EventHandler<ActionEvent>() {
		     public void handle(ActionEvent e) {
		     	processAction(e);    
     	     }
		});
    	modifyStatusInventoryItemBTN.setOnAction(new EventHandler<ActionEvent>() {
		     public void handle(ActionEvent e) {
		     	processAction(e);    
     	     }
		});
    	deleteInventoryItemTypeBTN.setOnAction(new EventHandler<ActionEvent>() {
		     public void handle(ActionEvent e) {
		     	processAction(e);    
     	     }
		});
    	deleteVendorInvetoryItemBTN.setOnAction(new EventHandler<ActionEvent>() {
		     public void handle(ActionEvent e) {
		     	processAction(e);    
     	     }
		});
    	reorderListBTN.setOnAction(new EventHandler<ActionEvent>() {
		     public void handle(ActionEvent e) {
		     	processAction(e);    
     	     }
		});
    	processInvoiceBTN.setOnAction(new EventHandler<ActionEvent>() {
		     public void handle(ActionEvent e) {
		     	processAction(e);    
     	     }
		});
    	fullInventoryBTN.setOnAction(new EventHandler<ActionEvent>() {
		     public void handle(ActionEvent e) {
		     	processAction(e);    
     	     }
		});
    	addVendorInventoryItemTypeBTN.setOnAction(new EventHandler<ActionEvent>() {
		     public void handle(ActionEvent e) {
		     	processAction(e);    
     	     }
		});
    	addVendorBTN.setOnAction(new EventHandler<ActionEvent>() {
		     public void handle(ActionEvent e) {
		     	processAction(e);    
     	     }
		});
    	
		return grid;
	}

	public void processAction(Event e) {
		if(e.getSource() == doneBTN) {
			System.exit(0);
		}
		else if(e.getSource() == addVendorInventoryItemTypeBTN) {
			myModel.stateChangeRequest("AddVIIT", null);
		}
		else if(e.getSource() == modifyVendorBTN)
			myModel.stateChangeRequest("ModifyVendor", null);
		else if(e.getSource() == outOfInventoryBTN)
			myModel.stateChangeRequest("", null);
		else if(e.getSource() == modifyIventoryItemTypeBTN)
			myModel.stateChangeRequest("FindInventoryItemTypeView", null);
		else if(e.getSource() == modifyIventoryItemTypeBTN)
			myModel.stateChangeRequest("", null);
		else if(e.getSource() == modifyIventoryItemTypeBTN)
			myModel.stateChangeRequest("", null);
		else if(e.getSource() == modifyIventoryItemTypeBTN)
			myModel.stateChangeRequest("", null);
		else if(e.getSource() == modifyIventoryItemTypeBTN)
			myModel.stateChangeRequest("", null);
		else if(e.getSource() == modifyIventoryItemTypeBTN)
			myModel.stateChangeRequest("", null);
		else if(e.getSource() == modifyIventoryItemTypeBTN)
			myModel.stateChangeRequest("", null);
		else if(e.getSource() == modifyIventoryItemTypeBTN)
			myModel.stateChangeRequest("", null);
		else if(e.getSource() == modifyIventoryItemTypeBTN)
			myModel.stateChangeRequest("", null);
		else
			System.out.println(e);
	}
	
	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub
		
	}

}
