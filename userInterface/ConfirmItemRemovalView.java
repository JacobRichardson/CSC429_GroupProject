package userInterface;


import java.util.Enumeration;
import java.util.Vector;

import exception.InvalidPrimaryKeyException;
import impresario.IModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.IITCollection;
import model.InventoryItemType;
import model.Manager;
import model.Vendor;
import model.VendorInventoryItemType;
import model.VendorSearchCollection;


//NOT
//FINISHED




public class ConfirmItemRemovalView extends View {

	private Label label = new Label();
	private Button confirm = new Button("CONFIRM");
	private Button cancel = new Button("CANCEL");
	
	
	
	public ConfirmItemRemovalView(IModel model) {
		super(model, "ConfirmItemRemovalView");
	
	
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

	label.setText("Take This Item Out Of Inventory?");

/*
	confirm.setOnAction(new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			processAction(e);
		}
	});
*/
	cancel.setOnAction(new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			new Manager();
		}
	});
	

	grid.add(label, 0, 0);	
	grid.add(confirm, 0, 2);
	grid.add(cancel, 2, 2);

	return grid;
}

private Node createTitle() {
	Text titleText = new Text("       Restaurant Inventory Management         ");
	titleText.setFont(Font.font("Arial", FontWeight.BOLD, 40));
	titleText.setTextAlignment(TextAlignment.CENTER);
	

	return titleText;
}

/*
protected void processAction(Event e) {
	if(barcodeTF.getText().length() != 9) {
		messageLBL.setText("Improper or missing barcode");
	}
	else
		confirmItemRemoval();
}

protected void confirmItemRemoval() {
	myModel.stateChangeRequest("EnterItemView", barcodeTF.getText());
}

*/
		
		
			
		
		
	
	

	
	
	
	
	
	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub
		
	}
}
