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
		statusCB.getItems().addAll("Availible", "Returned to Vendor", "Expired", "Damaged", "Lost");
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
		if (unitsTF.getText().isEmpty() || unitMeasureTF.getText().isEmpty() || validityDaysTF.getText().isEmpty()
				|| reorderPointTF.getText().isEmpty() || notesTF.getText().isEmpty())
			messageLBL.setText("All Item Type data must be filled");
		// Then check to see if it is the submit button.
		else if (!isInteger(unitsTF.getText()))
			messageLBL.setText("Please enter a Integer \nnumber for units");
		else if(e.getSource()==backBTN)
			myModel.stateChangeRequest("BackMIIT", null);
		else if (e.getSource() == submitBTN)
			modifyInventoryItemType();
	}

	protected void modifyInventoryItemType() {

		// Process modifing the inventory item type.

		// New properties object.
		Properties props = new Properties();

		// Set the values.
		
		props.setProperty("Status", (String) statusCB.getValue());

		InventoryItem i = new InventoryItem(props);

		i.update();

		messageLBL.setText("Inventory Item Type Updated!");
		submitBTN.setVisible(false);
		backBTN.setVisible(false);
		cancelBTN.setText("Back");
	}

	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub

	}

	private boolean isInteger(String s) {
		try {
			if (Integer.parseInt(s) < 0) {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}

		return true;
	}

	private boolean isDouble(String s) {
		try {
			if (Double.parseDouble(s) < 0) {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}

		return true;
	}

}