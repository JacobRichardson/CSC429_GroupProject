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
	private Label messageLBL = new Label();
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
		grid.add(messageLBL, 0, 3, 3, 3);

		return grid;
	}
	
	private Node createTitle() {
		Text titleText = new Text("       Restaurant Inventory Management         ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 40));
		titleText.setTextAlignment(TextAlignment.CENTER);
		

		return titleText;
	}

	protected void processAction(Event e) {
		if (e.getSource() == submit) {
				
			//Get values.
			String vendorId = Vendor.getSelectedVendorId();
			String itemTypeName = nameTF.getText();
			
			try {
				
				//Create the VIIT.
				VendorInventoryItemType viit = new VendorInventoryItemType(vendorId, itemTypeName);
				
				//Make sure it is not null.
				if(viit != null) {
				
					//Set the item type name.
					InventoryItemType.setSelectedInventoryItemTypeName(itemTypeName);
					
					//Reset the items.
					nameTF.setText("");
					messageLBL.setText("");
					
					//Proceed to next screen for notes and barcode.
					myModel.stateChangeRequest("EnterBarcodeNotes", itemTypeName);
				}
			} catch (InvalidPrimaryKeyException e1) {
				
				//Print error for entering wrong inventory item type name.
				messageLBL.setText("Please enter a valid Inventory Item Type Name");
			}
		} else {
			new Manager();
		}
	}
	
	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub
		
	}
}
