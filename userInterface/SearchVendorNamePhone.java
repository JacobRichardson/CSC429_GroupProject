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

public class SearchVendorNamePhone extends View {
	
	private TextField vendorTF = new TextField();
	private TextField phoneTF = new TextField();
	
	private Button submitBTN;
	private Button cancelBTN = new Button("Cancel");
	
	private Label vendorLBL = new Label("Vendor");
	private Label phoneLBL= new Label("Phone Number");
	private Label messageLBL = new Label();
	
	public SearchVendorNamePhone(IModel model) {
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
	
	private void populateFields() {
		vendorTF.setText("");
		phoneTF.setText("");
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
    	
    	grid.add(vendorLBL, 0, 0);
    	grid.add(phoneLBL, 0, 1);
    	grid.add(messageLBL, 0, 4);
    	
    	grid.add(vendorTF, 1, 0);
    	grid.add(phoneTF, 1, 1);
    	
    	submitBTN = new Button("Submit");
    	submitBTN.setOnAction(new EventHandler<ActionEvent>() {
 		     public void handle(ActionEvent e) {
 		     	processAction(e);    
      	     }
  	});
    	grid.add(submitBTN, 0, 2);
    	
    	cancelBTN.setOnAction(new EventHandler<ActionEvent>() {
		     public void handle(ActionEvent e) {
		     	processAction(e);    
     	     }
 	});
    	grid.add(cancelBTN, 1, 2);
    	return grid;
	}
	
	protected void processAction(Event e) {
		if(e.getSource() == cancelBTN)
			myModel.stateChangeRequest("cancel", null);
		else if(vendorTF.getText().isEmpty() || phoneTF.getText().isEmpty())
			messageLBL.setText("Please enter info.");
			else
				enterVendorDetails();
	}
	private void enterVendorDetails(){
		String details= "Name like '%"+vendorTF.getText()+"%' and PhoneNumber Like '%"+phoneTF.getText()+"%'";
		myModel.stateChangeRequest("VendorSelectionScreen", details);
	}
			public void updateState(String key, Object value) {
		// TODO Auto-generated method stub
		
	}
}
