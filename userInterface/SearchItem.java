package userInterface;

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

import java.util.Properties;

public class SearchItem extends View {

	private TextField barcodeTF = new TextField();
	private TextField itemNameTF = new TextField();

	private Button submitBTN;
	private Button cancelBTN;

	private Label barcodeLBL = new Label("Part of Barcode:");
	private Label andLBL = new Label("And/Or");
	private Label ItemNameLBL = new Label("Item type name:");
	private Label messageLBL = new Label();

	public SearchItem(IModel model){
		super(model, "FindInventoryItemTypeView");

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
		itemNameTF.setText("");
		messageLBL.setText("");
	}
	private Node createTitle() {		
		Text titleText = new Text("       Restaurant Inventory Management         ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 40));
		titleText.setTextAlignment(TextAlignment.CENTER);



		return titleText;
	}

	private Node createFormContents() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		grid.add(barcodeLBL, 0, 0);
		grid.add(andLBL, 0, 1);
		grid.add(ItemNameLBL, 0, 2);
		grid.add(messageLBL, 0, 4);

		grid.add(barcodeTF, 1, 0);
		grid.add(itemNameTF, 1, 2);

		submitBTN = new Button("Submit");
		submitBTN.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				processAction(e);    
			}
		});
		grid.add(submitBTN, 0, 3);

		cancelBTN = new Button("Back");
		cancelBTN.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				new Manager();   
			}
		});
		grid.add(cancelBTN, 1, 3);
		return grid;
	}
	protected void processAction(Event e) {
		if(barcodeTF.getText().isEmpty() && itemNameTF.getText().isEmpty())
			messageLBL.setText("Barcode or Item type name must be filled");
		else if(isInteger(barcodeTF.getText()) || barcodeTF.getText().isEmpty()) {
			getTypes();
		}
		else
			messageLBL.setText("Barcode must be an integer");
	}
	protected void getTypes(){
		//If the item name tf is empty
		if(itemNameTF.getText().isEmpty()) {
			String q="SELECT * FROM InventoryItem where Barcode like '%"+barcodeTF.getText()+"%'";
			myModel.stateChangeRequest("SearchItemCollection",q);
		}
		//If the barcode tf is empty
		else if(barcodeTF.getText().isEmpty()) {
			String q="SELECT * FROM InventoryItem where InventoryItemTypeName like '%"+itemNameTF.getText()+"%'";
			myModel.stateChangeRequest("SearchItemCollection",q);
		}
		//If both tf contain data
		else {
			String q="SELECT * FROM InventoryItem where Barcode like '%"+barcodeTF.getText()+"%' and InventoryItemTypeName like '%"+itemNameTF.getText()+"%'";
			myModel.stateChangeRequest("SearchItemCollection",q);
		}
	}
	
	public boolean isInteger(String s) {
		try {  
		    Integer.parseInt(s);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
	}
	
	public void updateState(String key, Object value) {

	}
}