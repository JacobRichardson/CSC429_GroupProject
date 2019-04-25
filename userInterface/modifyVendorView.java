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
import model.Manager;
import model.Vendor;

public class modifyVendorView extends View {
	
	private TextField vendorTF = new TextField();
	private TextField phoneTF = new TextField();
	
	private Button submitBTN;
	private Button cancelBTN = new Button("Cancel");
	private Button backBTN;
	
	private Label vendorLBL = new Label("Vendor Name");
	private Label phoneLBL= new Label("Phone Number");
	private Label statusLBL= new Label("Status");
	
	private ComboBox statusCB = new ComboBox();
	private Label messageLBL = new Label();
	private String Id;
	
	public modifyVendorView(IModel model) {
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
		Id=""+myModel.getState("Id");
		vendorTF.setText((String) myModel.getState("Name"));
		phoneTF.setText((String) myModel.getState("PhoneNumber"));
		statusCB.getItems().addAll("Active", "Inactive");
		statusCB.setValue(myModel.getState("Status"));
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
    	
    	grid.add(vendorLBL, 0, 0);
    	grid.add(phoneLBL, 0, 1);
    	grid.add(statusLBL, 0, 2);
    	grid.add(messageLBL, 0, 5);
    	
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
    	
    vendorTF.setOnAction(new EventHandler<ActionEvent>() {
		     public void handle(ActionEvent e) {
		      messageLBL.setText("");
    	     }
    });
    
    backBTN=new Button("Back");
    backBTN.setOnAction(new EventHandler<ActionEvent>() {
	     public void handle(ActionEvent e) {
	      messageLBL.setText("");
	     }
    });
    grid.add(backBTN, 0, 4);
    
    phoneTF.setOnAction(new EventHandler<ActionEvent>() {
	     public void handle(ActionEvent e) {

	      messageLBL.setText("");
	     }
});
    	
    	return grid;
	}
	
	protected void processAction(Event e) {
		if(vendorTF.getText().isEmpty() || phoneTF.getText().isEmpty())
			messageLBL.setText("All vendor data must be filled");
		else if(!checkPhone(phoneTF.getText()) || phoneTF.getText().length()!=12)
			messageLBL.setText("Please enter phone number \nin xxx-xxx-xxxx");
			
		else updateVendor();
	}
	
	protected void updateVendor() {
		Properties props = new Properties();
		props.setProperty("Id", Id);
		props.setProperty("Name", vendorTF.getText());
		props.setProperty("PhoneNumber", phoneTF.getText());
		props.setProperty("Status", (String) statusCB.getValue());
		Vendor v= new Vendor(props);
		v.update();
		messageLBL.setText("Vendor updated");
		submitBTN.setVisible(false);
		backBTN.setVisible(false);
		cancelBTN.setText("Back");
	}
	
	public boolean checkPhone(String num) {
		//585-111-1111
		for(int i=0; i<num.length(); i++) {
			if((i==3||i==7) && num.charAt(i)!='-') 
				return false;
			else if(num.charAt(i)<='0' && num.charAt(i)>='9') 
				return false;
		}
		return true;
	}
	
			public void updateState(String key, Object value) {
		// TODO Auto-generated method stub
		
	}
			
			public void mouseClicked(MouseEvent click)
			{
				if(click.getClickCount() >= 1)
				{
					messageLBL.setText("");
				}
			}
}
