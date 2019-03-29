package userInterface;

import java.text.NumberFormat;
import java.util.Properties;

import impresario.IModel;
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
import userInterface.MessageView;

public class SearchBook extends View {
	
	private Button submitBTN;
	private Button cancelBTN;
	private TextField title;
	private Text statusLog;

	public SearchBook(IModel model) {
		super(model, "SearchPatron");
		
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
		populateFields();
	}

	private Node createTitle() {		
		Text titleText = new Text("       Brockport Library          ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKGREEN);
		
	
		return titleText;
	}
	
	private void populateFields() {
		// TODO Auto-generated method stub
		title.setText("");
	}

	private Node createFormContents() {
		GridPane grid = new GridPane();
    	grid.setAlignment(Pos.CENTER);
   		grid.setHgap(10);
    	grid.setVgap(10);
    	grid.setPadding(new Insets(25, 25, 25, 25));
    	
    	Label input = new Label("Enter part of title:");
    	grid.add(input,0,0);
    	
    	//title textfield
    	title= new TextField();
    	grid.add(title, 0, 1);
    	
    	//Cancel button
    	cancelBTN=new Button("Cancel");
    	cancelBTN.setOnAction(new EventHandler<ActionEvent>() {
 		     public void handle(ActionEvent e) {
 		     	processAction(e);    
      	     }
  	});
    	grid.add(cancelBTN, 1, 0);
    	
    	//Submit button
    	submitBTN= new Button("Submit");
    	submitBTN.setOnAction(new EventHandler<ActionEvent>() {
 		     public void handle(ActionEvent e) {
 		     	processAction(e);    
      	     }
  	});
    	grid.add(submitBTN, 1, 1);
    	
    	statusLog= new Text("");
    	grid.add(statusLog, 0, 3);
    	
		return grid;
	}


	protected void processAction(ActionEvent e) {
		if(e.getSource()==cancelBTN)
			myModel.stateChangeRequest("LibrarianView", null);
		else{
			if(title.getText().length()==0) {
				statusLog.setFont(Font.font("Arial", FontWeight.BOLD, 20));
				statusLog.setText("No title entered.");
			}
			else {
				String titleCode=title.getText();
				proccesstitle(titleCode);
				}
			}
		
	}

	private void proccesstitle(String titleCode) {
		myModel.stateChangeRequest("title", titleCode);
	}

	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub
		
	}

}
