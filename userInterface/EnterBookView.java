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
import model.BookCatalog;

public class EnterBookView extends View {
	
	private TextField authorTF = new TextField();
	private TextField titleTF = new TextField();
	private TextField pubYearTF = new TextField();
	private ComboBox statusCB = new ComboBox();
	
	private Button submitBTN;
	private Button goBackBTN = new Button("Go Back");
	
	private Label authorLBL = new Label("Author");
	private Label titleLBL= new Label("Title");
	private Label pubYearLBL = new Label("Publication Year");
	private Label statusLBL= new Label("Status");
	private Label messageLBL = new Label();
	
	public EnterBookView(IModel model) {
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
		authorTF.setText("");
		titleTF.setText("");
		pubYearTF.setText("");
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
    	
    	grid.add(authorLBL, 0, 0);
    	grid.add(titleLBL, 1, 0);
    	grid.add(pubYearLBL, 2, 0);
    	grid.add(statusLBL, 3, 0);
    	grid.add(messageLBL, 0, 4);
    	
    	grid.add(authorTF, 0, 1);
    	grid.add(titleTF, 1, 1);
    	grid.add(pubYearTF, 2, 1);
    	statusCB.getItems().addAll("Avaliable", "Unavaible");
    	grid.add(statusCB, 3, 1);
    	
    	submitBTN = new Button("Submit");
    	submitBTN.setOnAction(new EventHandler<ActionEvent>() {

 		     @Override
 		     public void handle(ActionEvent e) {
 		     	processAction(e);    
      	     }
  	});
    	grid.add(submitBTN, 3, 2);
    	
    	goBackBTN.setOnAction(new EventHandler<ActionEvent>() {

		     @Override
		     public void handle(ActionEvent e) {
		     	processAction(e);    
     	     }
 	});
    	grid.add(goBackBTN, 3, 3);
    	return grid;
	}
	
	protected void processAction(Event e) {
		if(e.getSource() == goBackBTN)
			myModel.stateChangeRequest("LibrarianView", null);
		else if(authorTF.getText().isEmpty() || titleTF.getText().isEmpty() || pubYearTF.getText().isEmpty())
			messageLBL.setText("Please enter info.");
		else {
			int year=Integer.parseInt(pubYearTF.getText());
			if(year<1800 || year>2018)
				messageLBL.setText("Please make sure the year is\n between 1800 and 2018");
			else
				enterBook();
		}
	}
	private void enterBook(){
		Properties props = new Properties();
		props.put("author", authorTF.getText());
		props.put("title",titleTF.getText());
		props.put("pubYear",pubYearTF.getText());
		props.put("status",statusCB.getValue());
		Properties schema = new Properties();
		schema.put("TableName", "books");
		try {
			new BookCatalog(props);
			populateFields();
			messageLBL.setText("Book entered");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	@Override
	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub
		
	}
}
