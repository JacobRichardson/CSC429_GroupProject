package userInterface;

import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.InventoryItemType;
import model.Manager;
import model.Vendor;
import model.VendorInventoryItemType;
import java.util.*;

import exception.InvalidPrimaryKeyException;

public class EnterIITView extends View {

	private Label label = new Label();
	private Button submit = new Button("SUBMIT");
	private Button cancel = new Button("CANCEL");
	private TextField nameTF = new TextField();
	
	public EnterIITView(IModel model) {
		super(model, "EnterIITView");
		
		VBox container = new VBox(10);

		container.setPadding(new Insets(15, 5, 5, 5));

		// create a Node (Text) for showing the title
		container.getChildren().add(createTitle());
		
		container.getChildren().add(createFormContents());
		
		getChildren().add(container);
	}

	private Node createFormContents() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		label.setText("Inventory Item Type Name : ");

		submit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				processAction(e);
			}
		});

		cancel.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				processAction(e);
			}
		});
		

		grid.add(label, 0, 0);
		grid.add(nameTF, 2, 0);
		grid.add(submit, 0, 2);
		grid.add(cancel, 2, 2);

		return grid;
	}
	
	private Node createTitle() {
		Text titleText = new Text("       Restaurant Inventory Management         ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKGREEN);

		return titleText;
	}

	protected void processAction(Event e) {
		if (e.getSource() == submit) {
				
			System.out.println("SUBMIT CLICKED!");
			//SEARCH FOR VENDOR INVENTORY ITEM TYPE TO MAKE SURE THEY CAN SUPPLY THAT ITEM TYPE.
			
			//Get values.
			String vendorId = Vendor.getSelectedVendorId();
			String itemTypeName = nameTF.getText();
			
			try {
				
				VendorInventoryItemType viit = new VendorInventoryItemType(vendorId, itemTypeName);
				
				//TODO: Check length not null to see if 1 was found.
				if(viit != null) {
					
					System.out.print("MY MODEL: " + myModel);
					
					InventoryItemType.setSelectedInventoryItemTypeName(itemTypeName);
					
					//Proceed to next screen for notes and barcode.
					myModel.stateChangeRequest("EnterBarcodeNotes", itemTypeName);
				}
				else {
					//Print an error message saying the VIIT was not found.
				}
				
			} catch (InvalidPrimaryKeyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				
				//Print error for entering wrong inventory item type name.
			}
			
			

			
			
		} else {
			new Manager();
		}
	}
	
	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub
		
	}
}
