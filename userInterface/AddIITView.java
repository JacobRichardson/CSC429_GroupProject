package userInterface;

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
import model.InventoryItemType;

public class AddIITView extends View {
	// TextFields
	private TextField typeNameTF = new TextField();
	private TextField unitsTF = new TextField();
	private TextField unitMeasureTF = new TextField();
	private TextField validityDaysTF = new TextField();
	private TextField reorderPointTF = new TextField();
	private TextField notesTF = new TextField();
	private ComboBox statusCB = new ComboBox();
	// Buttons
	private Button submitBTN = new Button("Submit");
	private Button cancelBTN = new Button("Cancel");
	// Labels
	private Label typeNameLBL = new Label("Item Type Name");
	private Label unitsLBL = new Label("Units");
	private Label unitMeasureLBL = new Label("Unit Measure");
	private Label validityDaysLBL = new Label("Validity Days");
	private Label reorderPointLBL = new Label("Reorder Point");
	private Label notesLBL = new Label("Notes");
	private Label statusLBL = new Label("Status");
	private Label messageLBL = new Label("");

	// Constructor
	public AddIITView(IModel model) {
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
		typeNameTF.setText("");
		unitsTF.setText("");
		unitMeasureTF.setText("");
		validityDaysTF.setText("");
		reorderPointTF.setText("");
		notesTF.setText("");
		statusCB.getItems().addAll("Active", "Inactive");
		statusCB.setValue("Active");
	}

	// Make the title of the screen
	private Node createTitle() {
		Text titleText = new Text("       Restaurant Inventory Management         ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKGREEN);

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
		grid.add(typeNameLBL, 0, 0);
		grid.add(unitsLBL, 0, 1);
		grid.add(unitMeasureLBL, 0, 2);
		grid.add(validityDaysLBL, 0, 3);
		grid.add(reorderPointLBL, 0, 4);
		grid.add(notesLBL, 0, 5);
		grid.add(statusLBL, 0, 6);

		// Add the text fields.
		grid.add(typeNameTF, 1, 0);
		grid.add(unitsTF, 1, 1);
		grid.add(unitMeasureTF, 1, 2);
		grid.add(validityDaysTF, 1, 3);
		grid.add(reorderPointTF, 1, 4);
		grid.add(notesTF, 1, 5);
		grid.add(statusCB, 1, 6);

		// Add the button.
		grid.add(submitBTN, 0, 7);
		grid.add(cancelBTN, 1, 7);

		// Add the message label.
		grid.add(messageLBL, 0, 8, 2, 1);

		// Event handlers.

		submitBTN.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				processAction(e);
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

		// First check to see if the fields are empty.
		if (typeNameTF.getText().isEmpty() || unitsTF.getText().isEmpty() || unitMeasureTF.getText().isEmpty()
				|| validityDaysTF.getText().isEmpty() || reorderPointTF.getText().isEmpty()
				|| notesTF.getText().isEmpty())
			messageLBL.setText("All Item Type data must be filled");
		else if(!isInt(unitsTF.getText())) {
			messageLBL.setText("Units must be an integer.");
		}
		else if(!isInt(validityDaysTF.getText())) {
			messageLBL.setText("Validity days must be an integer.");
		}
		else if(!isInt(reorderPointTF.getText())) {
			messageLBL.setText("Reorder point must be an integer.");
		}
		// Then check to see if it is the submit button.
		else if (e.getSource() == submitBTN)
			addInventoryItemType();
	}

	protected void addInventoryItemType() {

		// Process adding the inventory item type.

		// New properties object.
		Properties props = new Properties();

		System.out.println(typeNameTF.getText());
		System.out.println(unitsTF.getText());
		System.out.println(unitMeasureTF.getText());
		System.out.println(validityDaysTF.getText());
		System.out.println(reorderPointTF.getText());
		System.out.println(notesTF.getText());
		System.out.println(statusCB.getValue());

		// Set the values.
		props.setProperty("ItemTypeName", typeNameTF.getText());
		props.setProperty("Units", unitsTF.getText());
		props.setProperty("UnitMeasure", unitMeasureTF.getText());
		props.setProperty("ValidityDays", validityDaysTF.getText());
		props.setProperty("ReorderPoint", reorderPointTF.getText());
		props.setProperty("Notes", notesTF.getText());
		props.setProperty("Status", (String) statusCB.getValue());

		// Create the inventory item type.
		InventoryItemType iit = new InventoryItemType(props);

		// Save it into the database.
		iit.update();


		// Display message on GUI.
		messageLBL.setText("Inventory Item Type added.");
	}

	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub

	}
	
	//Method to test is a string is a int.
	private boolean isInt (String s) {
		try {
			if(Integer.parseInt(s) < 0) {
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