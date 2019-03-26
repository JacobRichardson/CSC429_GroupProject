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

public class LibrarianView extends View {
	private Button insertNewBookBTN;
	private Button insertNewPatronBTN;
	private Button searchBooksBTN;
	private Button searchPatronBTN;
	private Button doneBTN;

	public LibrarianView(IModel model) {
		super(model, "Librarian View");
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
		// TODO Auto-generated method stub
		
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
    	
    	//Insert new book

    	insertNewBookBTN= new Button("Insert a new book");
    	insertNewBookBTN.setOnAction(new EventHandler<ActionEvent>() {

  		     @Override
  		     public void handle(ActionEvent e) {
  		     	processAction(e);    
       	     }
   	});
    	grid.add(insertNewBookBTN,0,0);
    	
    	//Insert new patron
    	insertNewPatronBTN=new Button("Insert a new patron");
    	insertNewPatronBTN.setOnAction(new EventHandler<ActionEvent>() {

  		     @Override
  		     public void handle(ActionEvent e) {
  		     	processAction(e);    
       	     }
   	});
    	grid.add(insertNewPatronBTN, 0, 1);
    	
    	//Search for book
    	searchBooksBTN=new Button("Search for a book");
    	searchBooksBTN.setOnAction(new EventHandler<ActionEvent>() {

  		     @Override
  		     public void handle(ActionEvent e) {
  		     	processAction(e);    
       	     }
   	});
    	grid.add(searchBooksBTN, 1, 0);
    	
    	//Search for patron
    	searchPatronBTN=new Button("Search for a patron");
    	searchPatronBTN.setOnAction(new EventHandler<ActionEvent>() {

  		     @Override
  		     public void handle(ActionEvent e) {
  		     	processAction(e);    
       	     }
   	});
    	grid.add(searchPatronBTN, 1, 1);
    	
    	//Done
    	doneBTN=new Button("Done");
    	doneBTN.setOnAction(new EventHandler<ActionEvent>() {

  		     @Override
  		     public void handle(ActionEvent e) {
  		     	processAction(e);    
       	     }
   	});
    	grid.add(doneBTN, 1, 3);
    	
		return grid;
	}

	public void processAction(Event e) {
		if(e.getSource()==doneBTN) {
			System.exit(0);
		}
		else if(e.getSource()==searchPatronBTN) {
			myModel.stateChangeRequest("searchPatron", null);
		}
		else if(e.getSource()==searchBooksBTN)
			myModel.stateChangeRequest("titleSearch", null);
		else if(e.getSource()==insertNewBookBTN)
			myModel.stateChangeRequest("enterBookView", null);
		else if(e.getSource()==insertNewPatronBTN)
			myModel.stateChangeRequest("enterPatronView", null);
		else
			System.out.println(e);
	}
	
	@Override
	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub
		
	}

}
