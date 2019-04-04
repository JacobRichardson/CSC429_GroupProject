package userInterface;

import exception.InvalidPrimaryKeyException;
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

public class SearchInventoryItemType extends View {

	private TextField nameTF = new TextField();
	private Label nameLBL = new Label("Name");
	
	private Button doneBTN = new Button("Done");
	private Button submitBTN = new Button("Submit");
	
	private MessageView statusLog;
	
	public SearchInventoryItemType(IModel model) {
		super(model, "deleteInventoryItemType");
		
		VBox container = new VBox(10);
		
		container.setPadding(new Insets(15, 5, 5, 5));
		
		// create a Node (Text) for showing the title
		container.getChildren().add(createTitle());
		
		// create a Node (GridPane) for showing data entry fields
		container.getChildren().add(createFormContents());
		container.getChildren().add(createStatusLog("                          "));
	
		getChildren().add(container);
	}
	
	private Node createFormContents() {
		GridPane grid = new GridPane();
    	grid.setAlignment(Pos.CENTER);
   		grid.setHgap(10);
    	grid.setVgap(10);
    	grid.setPadding(new Insets(25, 25, 25, 25));
    	
    	grid.add(nameLBL, 0, 0);
    	grid.add(nameTF, 1, 0);
    	grid.add(submitBTN, 2, 0);
    	
    	submitBTN.setOnAction(new EventHandler<ActionEvent>() {
		     public void handle(ActionEvent e) {
		    	 processAction(e);
		     }
    	});
    	
    	doneBTN.setOnAction(new EventHandler<ActionEvent>() {
		     public void handle(ActionEvent e) {
		     	new Manager();   
    	     }
    	});
    	grid.add(doneBTN, 1, 2);
    	return grid;
	}
	
	private Node createTitle() {		
		Text titleText = new Text("       Restaurant Inventory Managment         ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKGREEN);
		
	
		return titleText;
	}

	protected void processAction(Event e) {
		if (e.getSource() == submitBTN) {
			if (nameTF.getText().isEmpty()) {
				displayMessage("Please enter info.");
			} else {
				enterInventoryItemTypeDetails();
			}
		}
	}
	
	private void enterInventoryItemTypeDetails(){
		String details= "ItemTypeName like '%"+nameTF.getText()+"%'";
		myModel.stateChangeRequest("InventoryItemTypeSelectionScreen", details);
	}
	
	@Override
	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub
		
	}
	
	private MessageView createStatusLog(String initialMessage)
	{
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}
	
	public void displayMessage(String message)
	{
		statusLog.displayMessage(message);
	}
	
	public void displayErrorMessage(String message)
	{
		statusLog.displayErrorMessage(message);
	}
}
