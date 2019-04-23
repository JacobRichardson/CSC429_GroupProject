package userInterface;

import java.time.LocalDate;
import java.util.Properties;

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
import model.Vendor;
import model.VendorInventoryItemType;
import model.InventoryItem;

public class EnterBarcodeAndNotesView extends View {

	private Label barcodeLabel = new Label();
	private Label andLabel = new Label();
	private Label notesLabel = new Label();
	private Button submit = new Button("SUBMIT");
	private Button cancel = new Button("Back");
	private TextField barcodeTF = new TextField();
	private TextField notesTF = new TextField();
	
	public EnterBarcodeAndNotesView(IModel model) {
		super(model, "FindIITView");
		
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

		barcodeLabel.setText("Item Barcode : ");
		andLabel.setText("     And");
		notesLabel.setText("Part of Notes : ");

		submit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				processAction(e);
			}
		});

		cancel.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				processAction(e);
			}
		});
		

		grid.add(barcodeLabel, 0, 0);
		grid.add(barcodeTF, 2, 0);
		grid.add(andLabel, 0, 1);
		grid.add(notesLabel, 0, 2);
		grid.add(notesTF, 2, 2);
		grid.add(submit, 0, 4);
		grid.add(cancel, 2, 4);

		return grid;
	}
	
	private Node createTitle() {
		Text titleText = new Text("       Restaurant Inventory Management         ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKGREEN);

		return titleText;
	}

	protected void processAction(Event e) {

		if (e.getSource() == submit) {
			
			//Insert the inventory item into the db.
			Properties props = new Properties();
			
			//Set the properties.
			props.setProperty("Barcode", barcodeTF.getText());
			props.setProperty("VendorId", Vendor.getSelectedVendorId());
			props.setProperty("InventoryItemTypeName", InventoryItemType.getSelectedInventoryItemTypeName());
			props.setProperty("DateReceived",  LocalDate.now().toString());
			props.setProperty("DateOfLastUse",  LocalDate.now().toString());
			props.setProperty("Notes", notesTF.getText());
			props.setProperty("Status", "Available");
			
			//Create the Inventory Item object.
			InventoryItem InventoryItem = new InventoryItem(props);
			
			//Save it into the database.
			InventoryItem.update();
			
			//Clear fields.
			barcodeTF.setText("");
			notesTF.setText("");
			
			//Swtich back to the EIITView.
			myModel.stateChangeRequest("VendorSelected", Vendor.getSelectedVendorId());
			
		} else {
			
			//TODO: IMPLEMENT BACK.
			new Manager();
		}
	}
	
	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub
		
	}

	
}
