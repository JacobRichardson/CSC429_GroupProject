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
import java.util.Properties;

public class FindInventoryItemTypeView extends View {

	private TextField itemTypeTF = new TextField();
	private TextField notesTF = new TextField();
	
	private Button submitBTN;
	private Button cancelBTN;
	
	private Label itemTypeLBL = new Label("Part of ItemTypeName:");
	private Label andLBL = new Label("And/Or");
	private Label notesLBL = new Label("Part of Notes:");
	private Label messageLBL = new Label();
	
	public FindInventoryItemTypeView(IModel model){
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
		itemTypeTF.setText("");
		notesTF.setText("");
		messageLBL.setText("");
	}
	private Node createTitle() {		
		Text titleText = new Text("       Restaurant Inventory Managment         ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKGREEN);
		
	
		return titleText;
	}
	
	private Node createFormContents() {
		GridPane grid = new GridPane();
    	grid.setAlignment(Pos.CENTER);
   		grid.setHgap(10);
    	grid.setVgap(10);
    	grid.setPadding(new Insets(25, 25, 25, 25));
    	
    	grid.add(itemTypeLBL, 0, 0);
    	grid.add(andLBL, 0, 1);
    	grid.add(notesLBL, 0, 2);
    	grid.add(messageLBL, 0, 4);
    	
    	grid.add(itemTypeTF, 1, 0);
    	grid.add(notesTF, 1, 2);
    	
    	submitBTN = new Button("Submit");
    	submitBTN.setOnAction(new EventHandler<ActionEvent>() {
 		     public void handle(ActionEvent e) {
 		     	processAction(e);    
      	     }
  	});
grid.add(submitBTN, 0, 3);
    	
		cancelBTN = new Button("Cancel");
    	cancelBTN.setOnAction(new EventHandler<ActionEvent>() {
		     public void handle(ActionEvent e) {
		     	processAction(e);    
     	     }
 	});
    	grid.add(cancelBTN, 1, 3);
    	return grid;
	}
	protected void processAction(Event e) {
		if (e.getSource()==cancelBTN) {
			populateFields();
			myModel.stateChangeRequest("chooseActionScreen", null);
		}
		else if(itemTypeTF.getText().isEmpty() && notesTF.getText().isEmpty())
			messageLBL.setText("ItemTypeName or Notes must be filled");
		else
			getTypes();
	}
	protected void getTypes(){
		String query = "SELECT * FROM `InventoryItemType` WHERE ItemTypeName LIKE "
				+"'%"+itemTypeTF.getText()+"%' OR Notes LIKE '%"+notesTF.getText()+"%'";
		myModel.stateChangeRequest("IITCollectionView", query);
	}
	public void updateState(String key, Object value) {
		
	}
}
