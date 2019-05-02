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
	
	
	private Label addLBL;
	private Label modifyLBL;
	private Label deleteLBL;
	private Label otherLBL;
	
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
		Text titleText = new Text("       Restaurant Management System      ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 40));
		titleText.setTextAlignment(TextAlignment.CENTER);	
		return titleText;
	}

	private Node createFormContents() {
		GridPane grid = new GridPane();

    	grid.setAlignment(Pos.CENTER);
   		grid.setHgap(50);
    	grid.setVgap(10);
    	grid.setPadding(new Insets(25, 25, 25, 25));
    	
    	
    	//Initialize Components.
    	addIventoryItemTypeBTN = new Button("Inventory Item Type");
    	modifyVendorBTN = new Button("Vendor");
    	outOfInventoryBTN = new Button("Take an Item Out of Inventory");
    	modifyIventoryItemTypeBTN = new Button("Inventory Item Type");
    	addVendorInventoryItemTypeBTN = new Button("Vendor Inventory Item Type");
    	modifyStatusInventoryItemBTN = new Button("Status of an Inventory Item");
    	deleteInventoryItemTypeBTN = new Button("Inventory Item Type");
    	deleteVendorInvetoryItemBTN = new Button("Vendor Inventory Item Type");
    	reorderListBTN = new Button("Reorder List");
    	addVendorBTN = new Button("Vendor");
    	processInvoiceBTN = new Button("Process Invoice");
    	fullInventoryBTN = new Button("Current Inventory");
       	doneBTN = new Button("Done");//Delete this when done
       	

       	addLBL = new Label("Add");
       	modifyLBL = new Label("Modify");
       	deleteLBL = new Label("Delete");
       	otherLBL = new Label("Other");
       	
       	//Add components to grid.
       	grid.add(addLBL,0,0);							grid.add(modifyLBL,1,0);
       	grid.add(addIventoryItemTypeBTN,0,1);		 	grid.add(modifyIventoryItemTypeBTN,1,1);					
       	grid.add(addVendorBTN,0,2);						grid.add(modifyVendorBTN,1,2);				 
    	grid.add(addVendorInventoryItemTypeBTN,0,3);  	grid.add(modifyStatusInventoryItemBTN,1,3); 
    	
    	grid.add(deleteLBL,0,4);						grid.add(otherLBL,1,4);	
    	grid.add(deleteInventoryItemTypeBTN,0,5);		grid.add(fullInventoryBTN,1,5);
    	grid.add(deleteVendorInvetoryItemBTN,0,6);		grid.add(outOfInventoryBTN,1,6);
    													grid.add(processInvoiceBTN,1,7);
     													grid.add(reorderListBTN,1,8);
    	
    	grid.add(doneBTN, 0, 9);
    	
    	
    	
    	addLBL.setFont(Font.font("Arial", FontWeight.BOLD, 20));
    	modifyLBL.setFont(Font.font("Arial", FontWeight.BOLD, 20));
    	deleteLBL.setFont(Font.font("Arial", FontWeight.BOLD, 20));
    	otherLBL.setFont(Font.font("Arial", FontWeight.BOLD, 20));
    	
    	addLBL.setMaxWidth(Double.MAX_VALUE);
    	addLBL.setAlignment(Pos.CENTER);
    	modifyLBL.setMaxWidth(Double.MAX_VALUE);
    	modifyLBL.setAlignment(Pos.CENTER);
    	deleteLBL.setMaxWidth(Double.MAX_VALUE);
    	deleteLBL.setAlignment(Pos.CENTER);
    	otherLBL.setMaxWidth(Double.MAX_VALUE);
    	otherLBL.setAlignment(Pos.CENTER);
    	
    	//Apply styles
    	doneBTN.getStyleClass().add("red");
    	
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
			myModel.stateChangeRequest("EnterItemBarcodeView", null);
		else if(e.getSource() == modifyIventoryItemTypeBTN)
			myModel.stateChangeRequest("ModifyIIT", null);
		else if(e.getSource() == deleteInventoryItemTypeBTN)
			myModel.stateChangeRequest("DeleteIIT", null);
		else if(e.getSource() == modifyIventoryItemTypeBTN)
			myModel.stateChangeRequest("FindInventoryItemTypeView", null);
		else if(e.getSource() == addVendorBTN)
			myModel.stateChangeRequest("AddVendor", null);
		else if(e.getSource() == addIventoryItemTypeBTN)
			myModel.stateChangeRequest("AddIIT", null);
		else if(e.getSource() == deleteVendorInvetoryItemBTN)
			myModel.stateChangeRequest("deleteVIIT", null);
		else if(e.getSource() == processInvoiceBTN)
			myModel.stateChangeRequest("processInvoice", null);
		else if(e.getSource() == modifyStatusInventoryItemBTN)
			myModel.stateChangeRequest("modifyItem", null);
		else if(e.getSource() == reorderListBTN)
			myModel.stateChangeRequest("ReorderList", null);
		else if(e.getSource() == modifyIventoryItemTypeBTN)
			myModel.stateChangeRequest("", null);
		else if(e.getSource() == modifyIventoryItemTypeBTN)
			myModel.stateChangeRequest("", null);
		else if(e.getSource() == fullInventoryBTN)
			myModel.stateChangeRequest("ViewInventory", null);
		else
			System.out.println(e);
	}
	
	
	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub
		
	}

}
