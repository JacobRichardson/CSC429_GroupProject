package userInterface;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import exception.InvalidPrimaryKeyException;
import impresario.IModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
import model.IITCollection;
import model.InventoryItem;
import model.InventoryItemType;
import model.Manager;
import model.Vendor;
import model.VendorInventoryItemType;
import model.VendorSearchCollection;


public class ConfirmItemRemovalView extends View {

	private InventoryItemType iit;
	private Label label = new Label();
	private Button confirm = new Button("Confirm");
	private Button cancel = new Button("Cancel");
	private Button back = new Button("Back");
	
	
	
	public ConfirmItemRemovalView(IModel model) {
		super(model, "ConfirmItemRemovalView");
	
	
	VBox container = new VBox(10);

	container.setPadding(new Insets(15, 5, 5, 5));

	// create a Node (Text) for showing the title
	container.getChildren().add(createTitle());
	
	container.getChildren().add(createFormContents());
	
	getChildren().add(container);
}

private Node createFormContents() {
	GridPane grid = new GridPane();
	grid.setAlignment(Pos.CENTER);
	grid.setHgap(10);
	grid.setVgap(10);
	grid.setPadding(new Insets(25, 25, 25, 25));
	if(!(myModel.getState("Barcode") == null) && myModel.getState("Status").equals("Available")) {
	
	try {
		iit = new InventoryItemType((String)myModel.getState("InventoryItemTypeName"), "");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		int validityDays = Integer.parseInt((String)iit.getState("ValidityDays"));

		LocalDate today = LocalDate.now();
		LocalDate received = LocalDate.parse((String)myModel.getState("DateReceived"));
		System.out.println("received = " + received);
		int diff = (int) ChronoUnit.DAYS.between(received, today);
		System.out.println("diff = " + diff);
		System.out.println("validityDays = " + validityDays);
		if(diff > validityDays) {
			label.setText(myModel.getState("InventoryItemTypeName") + " is/are expired and its status has been updated.");
			confirm.setVisible(false);
			updateItem(false);
		}
		else
			label.setText("Take "+myModel.getState("InventoryItemTypeName")+" Out Of Inventory?");
			
	}
	
	else if(myModel.getState("Barcode") == null) {
		label.setText("No item found with that barcode.");
		confirm.setVisible(false);
	}
	else if(!myModel.getState("Status").equals("Available")) {
		label.setText("Item is unavailable for use.");
		confirm.setVisible(false);
	}


	confirm.setOnAction(new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			processAction(e);
			label.setText("Item taken out of inventory");
			System.out.println("Updated");
		}
	});

	cancel.setOnAction(new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			new Manager();
		}
	});
	
	back.setOnAction(new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			processAction(e);
		}
	});
	

	grid.add(label, 0, 0);	
	grid.add(confirm, 0, 2);
	grid.add(back, 1, 2);
	grid.add(cancel, 2, 2);

	return grid;
}

	private Node createTitle() {
		Text titleText = new Text("       Restaurant Inventory Management         ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 40));
		titleText.setTextAlignment(TextAlignment.CENTER);
	

		return titleText;
	}
	
	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub
		
	}
	
	protected void processAction(Event e) {
		if(e.getSource() == confirm) {
			updateItem(true);
		}
		else if(e.getSource() == back)
			myModel.stateChangeRequest("Back", null);
	}
	
	protected void updateItem(boolean bool) {
		Properties props = new Properties();
		props.setProperty("Barcode", (String)myModel.getState("Barcode"));
		props.setProperty("VendorId", (String)myModel.getState("VendorId"));
		props.setProperty("InventoryItemTypeName", (String)myModel.getState("InventoryItemTypeName"));
		props.setProperty("DateReceived", (String)myModel.getState("DateReceived"));
		if(bool) {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			props.setProperty("DateOfLastUse", (String)dtf.format(LocalDate.now()));
		}
		else {
			props.setProperty("DateOfLastUse", (String)myModel.getState("DateOfLastUse"));
		}
		props.setProperty("Notes", (String)myModel.getState("Notes"));
		if(bool)
			props.setProperty("Status", "Used");
		else
			props.setProperty("Status", "Expired");
		InventoryItem item= new InventoryItem(props);
		try {
			item.update((String)myModel.getState("Barcode"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
