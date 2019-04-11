package userInterface;

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

public class EnterItemBarcodeView extends View {
	
	private Label barcodeLBL = new Label("Barcode: ");
	private TextField barcodeTF = new TextField("");
	private Button submitBTN = new Button("Submit");
	private Button cancelBTN = new Button("Cancel");
	private Label messageLBL = new Label("");
	
	public EnterItemBarcodeView(IModel model) {
		super(model, "EnterItemBarcodeView");
		
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
		barcodeTF.setText("");
		messageLBL.setText("");
	}
	
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
			grid.add(barcodeLBL, 0, 0);
			grid.add(barcodeTF, 0, 1);
			grid.add(submitBTN, 1, 0);
			grid.add(cancelBTN, 1, 1);
			
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
		
		protected void processAction(Event e) {
			if(barcodeTF.getText().length()<9 || barcodeTF.getText().length()>9) {
				messageLBL.setText("Improper or missing barcode");
			}
			else
				confirmItemRemoval();
		}
		protected void confirmItemRemoval() {
			
		}
	
}
