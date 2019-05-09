package userInterface;

import java.sql.SQLException;
//imports
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
import model.Manager;
import model.InventoryItem;

public class ModifyIIView extends View {
	// TextFields

	private ComboBox statusCB = new ComboBox();
	// Buttons
	private Button submitBTN = new Button("Submit");
	private Button cancelBTN = new Button("Cancel");
	private Button backBTN = new Button("Back");
	// Labels
	private Label statusLBL = new Label("Status");
	private Label messageLBL = new Label("");

	// Constructor
	public ModifyIIView(IModel model) {
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

	// Fill fields on screen
	private void populateFields() {
		// typeNameLBL =new Label((String) myModel.getState("ItemTypeName"));
		statusCB.getItems().addAll("Available", "Returned to Vendor", "Expired", "Damaged", "Lost");
		statusCB.setValue(myModel.getState("Status"));
	}

	// Make the title of the screen
	private Node createTitle() {
		Text titleText = new Text("       Restaurant Inventory Management         ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 40));
		titleText.setTextAlignment(TextAlignment.CENTER);
		

		return titleText;
	}

	// Place objects on the screen
	private Node createFormContents() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		// Add the components to the grid.

		// Add the labels.
		// grid.add(typeNameLBL, 0, 0);
		
		grid.add(statusLBL, 0, 6);

		// Add the text fields.
		
		grid.add(statusCB, 1, 6);

		// Add the button.
		grid.add(submitBTN, 0, 7);
		grid.add(backBTN, 0, 8);
		grid.add(cancelBTN, 1, 7);

		// Add message label.
		grid.add(messageLBL, 0, 9, 2, 1);

		// Event handlers.

		submitBTN.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				processAction(e);
			}
		});
		
		backBTN.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				
			}
		});

		cancelBTN.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				new Manager();
			}
		});

		return grid;
	}

	// Process action from submit button
	protected void processAction(Event e) {
		modifyItem((String)statusCB.getValue());
	}

	protected void modifyItem(String s) {
		Properties props=new Properties();
		props.setProperty("Barcode", (String)myModel.getState("Barcode"));
		props.setProperty("VendorId", (String)myModel.getState("VendorId"));
		props.setProperty("InventoryItemTypeName", (String)myModel.getState("InventoryItemTypeName"));
		props.setProperty("DateReceived", (String)myModel.getState("DateReceived"));
		props.setProperty("DateOfLastUse", (String)myModel.getState("DateOfLastUse"));
		props.setProperty("Notes", (String)myModel.getState("Notes"));
		props.setProperty("Status", s);
		InventoryItem i=new InventoryItem(props);
		try {
			i.update((String)i.getState("Barcode"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub

	}
}