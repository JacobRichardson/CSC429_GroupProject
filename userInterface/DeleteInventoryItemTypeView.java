package userInterface;

import javafx.event.Event;
import exception.InvalidPrimaryKeyException;
import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.InventoryItemType;
import model.Manager;

public class DeleteInventoryItemTypeView extends View {

	private Label label = new Label();
	private Button yes = new Button("YES");
	private Button no = new Button("NO");
	private Button cancel = new Button("CANCEL");
	protected MessageView statusLog;

	public DeleteInventoryItemTypeView(IModel model) {
		super(model, "DeleteInventoryItemTypeView");

		VBox container = new VBox(10);

		container.setPadding(new Insets(15, 5, 5, 5));

		// create a Node (Text) for showing the title
		container.getChildren().add(createTitle());

		container.getChildren().add(createFormContents());

		container.getChildren().add(createStatusLog("                                            "));

		getChildren().add(container);
	}

	private Node createFormContents() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		label.setText("Do you want to delete this item?");

		yes.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				processAction(e);
			}
		});

		no.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				processAction(e);
			}
		});
		
		cancel.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				processAction(e);
			}
		});
		
		cancel.setAlignment(Pos.CENTER);

		grid.add(label, 1, 0);
		;
		grid.add(yes, 0, 1);
		grid.add(no, 2, 1);
		grid.add(cancel, 2, 5);

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
		if (e.getSource() == yes) {
			InventoryItemType iit;
			try {
				iit = new InventoryItemType(myModel.getState("ItemTypeName").toString());
				iit.delete();
				displayMessage("Success");
			} catch (InvalidPrimaryKeyException e1) {
				// TODO Auto-generated catch block
				// e1.printStackTrace();
			}
		} else {
			new Manager();
		}
	}

	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub

	}

	// --------------------------------------------------------------------------
	protected MessageView createStatusLog(String initialMessage) {
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	/**
	 * Display info message
	 */
	// ----------------------------------------------------------
	public void displayMessage(String message) {
		statusLog.displayMessage(message);
	}

	/**
	 * Clear error message
	 */
	// ----------------------------------------------------------
	public void clearErrorMessage() {
		statusLog.clearErrorMessage();
	}

}