package userInterface;

import java.util.Enumeration;
import java.util.Vector;

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
import model.ItemCollection;
import model.InventoryItem;
import model.InventoryItemType;

public class ItemCollectionView extends View {

	protected TableView<ItemTableModel> iITTable;
	protected Button backBTN;
	protected Button submitBTN;

	protected MessageView statusLog;

	public ItemCollectionView(IModel wsc) {
		super(wsc, null);
		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 5, 5, 5));
		// create our GUI components, add them to this panel
		container.getChildren().add(createTitle());
		container.getChildren().add(createFormContent());
		// Error message area
		container.getChildren().add(createStatusLog("                                            "));
		getChildren().add(container);

		populateFields();
	}

	// --------------------------------------------------------------------------
	protected void populateFields() {
		getEntryTableModelValues();
	}

	// --------------------------------------------------------------------------
	protected void getEntryTableModelValues() {

		ObservableList<ItemTableModel> tableData = FXCollections.observableArrayList();
		try {
			ItemCollection itemCollection = (ItemCollection) myModel.getState("ItemCollection");
			Vector entryList = (Vector) itemCollection.getState("Item");
			Enumeration entries = entryList.elements();
			while (entries.hasMoreElements() == true) {
				InventoryItem nextItem = (InventoryItem) entries.nextElement();
				System.out.println(nextItem);
				Vector<String> view = nextItem.getEntryListView();
				System.out.println(nextItem);
				// add this list entry to the list
				ItemTableModel nextTableRowData = new ItemTableModel(view);
				tableData.add(nextTableRowData);

			}

			iITTable.setItems(tableData);
		} catch (Exception e) {// SQLException e) {
			// Need to handle this exception
		}
	}

	// Create the title container
	// -------------------------------------------------------------
	private Node createTitle() {
		HBox container = new HBox();
		container.setAlignment(Pos.CENTER);

		Text titleText = new Text(" Restaurant Management System ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 40));
		titleText.setWrappingWidth(300);
		titleText.setTextAlignment(TextAlignment.CENTER);
		
		container.getChildren().add(titleText);

		return container;
	}

	// Create the main form content
	// -------------------------------------------------------------
	private VBox createFormContent() {
		VBox vbox = new VBox(10);

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text prompt = new Text("LIST OF INVENTORY ITEM TYPES");
		prompt.setWrappingWidth(350);
		prompt.setTextAlignment(TextAlignment.CENTER);
		prompt.setFill(Color.BLACK);
		grid.add(prompt, 0, 0, 2, 1);

		iITTable = new TableView<ItemTableModel>();
		iITTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		TableColumn barcodeColumn = new TableColumn("Barcode");
		barcodeColumn.setMinWidth(150);
		barcodeColumn.setCellValueFactory(new PropertyValueFactory<ItemTableModel, String>("Barcode"));

		TableColumn vendorIdColumn = new TableColumn("VendorId");
		vendorIdColumn.setMinWidth(175);
		vendorIdColumn.setCellValueFactory(new PropertyValueFactory<ItemTableModel, String>("VendorId"));

		TableColumn IITNameColumn = new TableColumn("InventoryItemTypeName");
		IITNameColumn.setMinWidth(200);
		IITNameColumn.setCellValueFactory(new PropertyValueFactory<ItemTableModel, String>("InventoryItemTypeName"));

		TableColumn dateRecievedColumn = new TableColumn("DateRecieved");
		dateRecievedColumn.setMinWidth(200);
		dateRecievedColumn.setCellValueFactory(new PropertyValueFactory<ItemTableModel, String>("DateRecieved"));

		TableColumn lastUseColumn = new TableColumn("DateOfLastUse");
		lastUseColumn.setMinWidth(200);
		lastUseColumn.setCellValueFactory(new PropertyValueFactory<ItemTableModel, String>("DateOfLastUse"));

		TableColumn notesColumn = new TableColumn("Notes");
		notesColumn.setMinWidth(200);
		notesColumn.setCellValueFactory(new PropertyValueFactory<ItemTableModel, String>("Notes"));

		TableColumn statusColumn = new TableColumn("Status");
		statusColumn.setMinWidth(100);
		statusColumn.setCellValueFactory(new PropertyValueFactory<ItemTableModel, String>("Status"));

		iITTable.getColumns().addAll(barcodeColumn, vendorIdColumn, IITNameColumn, dateRecievedColumn,
				lastUseColumn, notesColumn, statusColumn);

		iITTable.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() >= 2) {
					processIITSelected();
				}
			}
		});
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(iITTable);

		backBTN = new Button("Back");
		backBTN.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("Back", null);
			}
		});

		submitBTN = new Button("Submit");
		submitBTN.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				processIITSelected();
			}
		});

		HBox btnContainer = new HBox(100);
		btnContainer.setAlignment(Pos.CENTER);
		btnContainer.getChildren().add(submitBTN);
		btnContainer.getChildren().add(backBTN);

		vbox.getChildren().add(grid);
		vbox.getChildren().add(scrollPane);
		vbox.getChildren().add(btnContainer);

		return vbox;
	}

	// --------------------------------------------------------------------------
	public void updateState(String key, Object value) {
	}

	// --------------------------------------------------------------------------
	protected void processIITSelected() {
		ItemTableModel selectedItem =  iITTable.getSelectionModel().getSelectedItem();
		if(selectedItem!=null) {
			String selectedItemTypeName = selectedItem.getBarcode();
			InventoryItemType.setSelectedInventoryItemTypeName(selectedItem.getBarcode());

			/*
			 * For some reason this will not switch over to the vendor selected key. It
			 * won't call the manager stateChangeRequest for some reason. This needs to be
			 * looked into.
			 */
			System.out.println(myModel);
			myModel.stateChangeRequest("itemSelected", selectedItemTypeName);
		}
	}

	// --------------------------------------------------------------------------
	protected MessageView createStatusLog(String initialMessage) {
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	/**
	 * Display info message
	 */
	// ----------------------------------------------------------
	public void displayMessage(String message) {
		statusLog.displayMessage(message);
	}

	/**
	 * Clear error message
	 */
	// ----------------------------------------------------------
	public void clearErrorMessage() {
		statusLog.clearErrorMessage();
	}

	// --------------------------------------------------------------------------
	public void mouseClicked(MouseEvent click) {
		if (click.getClickCount() >= 2) {
			processIITSelected();
		}
	}

}