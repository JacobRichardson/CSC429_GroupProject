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
import model.InventoryItemType;
import model.Vendor;
import model.VendorSearchCollection;

public class IITCollectionView extends View {

	protected TableView<IITTableModel> iITTable;
	protected Button cancelBTN;
	protected Button submitBTN;

	protected MessageView statusLog;

	public IITCollectionView(IModel wsc) {
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

		ObservableList<IITTableModel> tableData = FXCollections.observableArrayList();
		try {
			IITCollection iITCollection = (IITCollection) myModel.getState("IITCollection");
			Vector entryList = (Vector) iITCollection.getState("InventoryItemType");
			Enumeration entries = entryList.elements();

			while (entries.hasMoreElements() == true) {
				InventoryItemType nextIIT = (InventoryItemType) entries.nextElement();
				Vector<String> view = nextIIT.getEntryListView();
				// add this list entry to the list
				IITTableModel nextTableRowData = new IITTableModel(view);
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
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setWrappingWidth(300);
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKGREEN);
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

		iITTable = new TableView<IITTableModel>();
		iITTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		TableColumn itemTypeNameColumn = new TableColumn("ItemTypeName");
		itemTypeNameColumn.setMinWidth(50);
		itemTypeNameColumn.setCellValueFactory(new PropertyValueFactory<IITTableModel, String>("ItemTypeName"));

		TableColumn unitsColumn = new TableColumn("Units");
		unitsColumn.setMinWidth(125);
		unitsColumn.setCellValueFactory(new PropertyValueFactory<IITTableModel, String>("Units"));

		TableColumn unitMeasureColumn = new TableColumn("UnitMeasure");
		unitMeasureColumn.setMinWidth(100);
		unitMeasureColumn.setCellValueFactory(new PropertyValueFactory<IITTableModel, String>("UnitMeasure"));

		TableColumn validityDaysColumn = new TableColumn("ValidityDays");
		validityDaysColumn.setMinWidth(100);
		validityDaysColumn.setCellValueFactory(new PropertyValueFactory<IITTableModel, String>("ValidityDays"));

		TableColumn reorderPointColumn = new TableColumn("ReorderPoint");
		reorderPointColumn.setMinWidth(100);
		reorderPointColumn.setCellValueFactory(new PropertyValueFactory<IITTableModel, String>("ReorderPoint"));

		TableColumn notesColumn = new TableColumn("Notes");
		notesColumn.setMinWidth(100);
		notesColumn.setCellValueFactory(new PropertyValueFactory<IITTableModel, String>("Notes"));

		TableColumn statusColumn = new TableColumn("Status");
		statusColumn.setMinWidth(50);
		statusColumn.setCellValueFactory(new PropertyValueFactory<IITTableModel, String>("Status"));

		iITTable.getColumns().addAll(itemTypeNameColumn, unitsColumn, unitMeasureColumn, validityDaysColumn,
				reorderPointColumn, notesColumn, statusColumn);

		iITTable.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() >= 2) {
					processIITSelected();
				}
			}
		});
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPrefSize(115, 150);
		scrollPane.setContent(iITTable);

		cancelBTN = new Button("Cancel");
		cancelBTN.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				/**
				 * Process the Cancel button. The ultimate result of this action is that the
				 * transaction will tell the teller to to switch to the transaction choice view.
				 * BUT THAT IS NOT THIS VIEW'S CONCERN. It simply tells its model (controller)
				 * that the transaction was canceled, and leaves it to the model to decide to
				 * tell the teller to do the switch back.
				 */
				// ----------------------------------------------------------
				clearErrorMessage();
				new model.Manager();
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
		btnContainer.getChildren().add(cancelBTN);

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
		IITTableModel selectedItem = iITTable.getSelectionModel().getSelectedItem();

		if (selectedItem != null) {
			String selectedItemTypeName = selectedItem.getItemTypeName();
			InventoryItemType.setSelectedInventoryItemTypeName(selectedItem.getItemTypeName());

			/*
			 * For some reason this will not switch over to the vendor selected key. It
			 * won't call the manager stateChangeRequest for some reason. This needs to be
			 * looked into.
			 */
			System.out.println(myModel);
			myModel.stateChangeRequest("iitSelected", selectedItemTypeName);
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