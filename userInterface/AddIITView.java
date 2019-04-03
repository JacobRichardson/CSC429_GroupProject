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
import model.Vendor;

public class AddIITView extends View {
	//TextFields
	private TextField typeNameTF = new TextField();
	private TextField unitsTF = new TextField();
	private TextField unitMeasureTF = new TextField();
	private TextField validityDaysTF = new TextField();
	private TextField reorderPointTF = new TextField();
	private TextField notesTF = new TextField();
	private TextField statusTF = new TextField();
	//Buttons
	private Button submitBTN;
	private Button cancelBTN = new Button("Cancel");
	//Labels
	private Label typeNameLBL = new Label("Item Type Name");
	private Label unitsLBL= new Label("Units");
	private Label unitMeasureLBL= new Label("Unit Measure");
	private Label validityDaysLBL= new Label("Validity Days");
	private Label reorderPointLBL= new Label("Reorder Point");
	private Label notesLBL= new Label("Notes");
	private Label statusLBL= new Label("Status");
	private Label messageLBL = new Label("");
	//Constructor
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
	//Fill fields on screen
	private void populateFields() {
		System.out.println(myModel.getState("Name"));
		typeNameTF.setText("");
		unitsTF.setText("");
		unitMeasureTF.setText("");
		validityDaysTF.setText("");
		reorderPointTF.setText("");
		notesTF.setText("");
		statusTF.setText("");
	}
	//Make the title of the screen
	private Node createTitle() {		
		Text titleText = new Text("       Restaurant Inventory Managment         ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKGREEN);
		
	
		return titleText;
	}
	//Place objects on the screen
	private Node createFormContents() {
		GridPane grid = new GridPane();
    	grid.setAlignment(Pos.CENTER);
   		grid.setHgap(10);
    	grid.setVgap(10);
    	grid.setPadding(new Insets(25, 25, 25, 25));
    	
    	grid.add(vendorLBL, 0, 0);
    	grid.add(phoneLBL, 0, 1);
    	grid.add(statusLBL, 0, 2);
    	grid.add(messageLBL, 0, 3);
    	
    	grid.add(vendorTF, 1, 0);
    	grid.add(phoneTF, 1, 1);
    	grid.add(statusCB, 1, 2);
    	
    	submitBTN = new Button("Submit");
    	submitBTN.setOnAction(new EventHandler<ActionEvent>() {
 		     public void handle(ActionEvent e) {
 		     	processAction(e);    
      	     }
  	});
    	grid.add(submitBTN, 0, 3);
    	
    	cancelBTN.setOnAction(new EventHandler<ActionEvent>() {
		     public void handle(ActionEvent e) {
		     	new Manager();   
     	     }
 	});
    	grid.add(cancelBTN, 1, 3);
    	return grid;
	}
	//Process action from submit button
	protected void processAction(Event e) {
		if(e.getSource()==submitBTN)
			updateVendor();
		else if(typeNameTF.getText().isEmpty() || unitsTF.getText().isEmpty() || unitMeasureTF.getText().isEmpty() || validityDaysTF.getText().isEmpty() || reorderPointTF.getText().isEmpty() || notesTF.getText().isEmpty() || statusTF.getText().isEmpty())
			messageLBL.setText("All Item Type data must be filled");
		else
			myModel.stateChangeRequest("chooseActionScreen", null);
	}
	
	protected void updateVendor() {
		
	}
	
			public void updateState(String key, Object value) {
		// TODO Auto-generated method stub
		
	}
}