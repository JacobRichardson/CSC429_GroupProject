package userInterface;

import java.util.Properties;

import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.BookCatalog;
import model.Vendor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

public class EnterPatronView extends View {
	
	private TextField nameTF = new TextField();
	private TextField addressTF = new TextField();
	private TextField cityTF = new TextField();
	private TextField stateCodeTF = new TextField();
	private TextField zipTF = new TextField();
	private TextField emailTF = new TextField();
	private TextField dateOfBirthTF = new TextField();
	private ComboBox statusCB = new ComboBox();
	
	private Button submitBTN;
	private Button goBackBTN = new Button("Go Back");
	
	private Label nameLBL = new Label("Name");
	private Label addressLBL= new Label("Address");
	private Label cityLBL = new Label("City");
	private Label stateCodeLBL= new Label("State Code");
	private Label zipLBL= new Label("ZIP");
	private Label emailLBL= new Label("Email");
	private Label dateOfBirthLBL= new Label("Date of Birth (YYYY-MM-DD)");
	private Label statusLBL= new Label("Status");
	private Label messageLBL = new Label();
	
	public EnterPatronView(IModel model) {
		
		super(model, "EnterPatronView");
		
		VBox container = new VBox(10);

		container.setPadding(new Insets(15, 5, 5, 5));

		// create a Node (Text) for showing the title
		container.getChildren().add(createTitle());

		// create a Node (GridPane) for showing data entry fields
		container.getChildren().add(createFormContents());
		getChildren().add(container);

		populateFields();

		// STEP 0: Be sure you tell your model what keys you are interested in
		//myModel.subscribe("LoginError", this);

	}
	private void populateFields() {
		nameTF.setText("");
		addressTF.setText("");
		cityTF.setText("");
		stateCodeTF.setText("");
		zipTF.setText("");
		emailTF.setText("");
		dateOfBirthTF.setText("");
		statusCB.getItems().addAll("Active", "In Active");
		messageLBL.setText("");
	}
	
	private Node createTitle() {		
		Text titleText = new Text("       Brockport Library          ");
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
    	
    	grid.add(nameLBL, 0, 0);
    	grid.add(addressLBL, 0, 1);
    	grid.add(cityLBL, 0, 2);
    	grid.add(stateCodeLBL, 0, 3);
    	grid.add(zipLBL, 0, 4);
    	grid.add(emailLBL, 0, 5);
    	grid.add(dateOfBirthLBL, 0, 6);
    	grid.add(statusLBL, 0, 7);
    	
    	grid.add(nameTF, 1, 0);
    	grid.add(addressTF, 1, 1);
    	grid.add(cityTF, 1, 2);
    	grid.add(stateCodeTF, 1, 3);
    	grid.add(zipTF, 1, 4);
    	grid.add(emailTF, 1, 5);
    	grid.add(dateOfBirthTF, 1, 6);
    	grid.add(statusCB, 1, 7);
    	
    	
    	submitBTN = new Button("Submit");
    	submitBTN.setOnAction(new EventHandler<ActionEvent>() {

 		     @Override
 		     public void handle(ActionEvent e) {
 		     	processAction(e);    
      	     }
  	});
    	grid.add(submitBTN, 1, 8);
    	
    	goBackBTN.setOnAction(new EventHandler<ActionEvent>() {

		     @Override
		     public void handle(ActionEvent e) {
		     	processAction(e);    
     	     }
 	});
    	grid.add(goBackBTN, 0, 8);
    	grid.add(messageLBL, 0, 9);
    	return grid;
	}
	
	protected void processAction(Event e) {
		
		if(e.getSource() == goBackBTN)
			myModel.stateChangeRequest("LibrarianView", null);
		
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-mm-dd");
		try {
			Date date = sd.parse(dateOfBirthTF.getText());
		} catch(ParseException ex) {
			messageLBL.setText("Date is not in correct format");
			return;
		}
		int year=Integer.parseInt(dateOfBirthTF.getText().substring(0,4));
		if((year<1917 || year>=2000)) {
			messageLBL.setText("Please enter a year\nbetween 1917 and 2000");
		}
		else if(nameTF.getText().isEmpty() || addressTF.getText().isEmpty() || cityTF.getText().isEmpty() || stateCodeTF.getText().isEmpty()
				|| zipTF.getText().isEmpty() || emailTF.getText().isEmpty())
			messageLBL.setText("Please enter info.");
		else if(stateCodeTF.getLength() != 2)
			messageLBL.setText("Please enter 2 digit state code.");
		else if(zipTF.getLength() != 5)
			messageLBL.setText("Please enter 5 digit ZIP code.");
		else
			enterPatron();
	}
	private void enterPatron(){
		Properties props = new Properties();
		props.put("name", nameTF.getText());
		props.put("address",addressTF.getText());
		props.put("city",cityTF.getText());
		props.put("stateCode",stateCodeTF.getText());
		props.put("zip",zipTF.getText());
		props.put("email",emailTF.getText());
		props.put("dateOfBirth", dateOfBirthTF.getText());
		props.put("status",statusCB.getValue());
		Properties schema = new Properties();
		schema.put("TableName", "patron");
		try {
			new Vendor(props).updateStateInDatabase();
			populateFields();
			messageLBL.setText("Patron entered");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub
		
	}
}
