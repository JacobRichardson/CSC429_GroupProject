package userInterface;

// system imports
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

import java.util.Vector;
import java.util.Enumeration;

// project imports
import impresario.IModel;
import model.Manager;
import model.InventoryItemType;
import model.IITCollection;
import model.InventoryItem;
import model.ItemCollection;

//==============================================================================
public class ReorderList extends View
{
	protected TableView<ReorderListTableModel> ReorderListTable;
	protected Button doneBTN;
	protected Button submitButton;


	protected MessageView statusLog;


	//--------------------------------------------------------------------------
	public ReorderList(IModel wsc)
	{
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

	//--------------------------------------------------------------------------
	protected void populateFields()
	{
		getEntryTableModelValues();
	}

	//--------------------------------------------------------------------------
	protected void getEntryTableModelValues()
	{	

		ObservableList<ReorderListTableModel> tableData = FXCollections.observableArrayList();
		try {
			IITCollection iITCollection = (IITCollection) myModel.getState("IITCollection");
			Vector entryList = (Vector) iITCollection.getState("InventoryItemType");
			Enumeration entries = entryList.elements();

			while (entries.hasMoreElements() == true) {
				InventoryItemType nextIIT = (InventoryItemType) entries.nextElement();
				Vector<String> view = nextIIT.getEntryListView();
				// add this list entry to the list
				ReorderListTableModel nextTableRowData = new ReorderListTableModel(view);
				tableData.add(nextTableRowData);	
			}
			
			//Variables for process eaching inventory item type name.
			ReorderListTableModel data;
			String query = "";
			ItemCollection itemCollection;
			int numberOfItems;
			int reorderPoint;
			System.out.println(tableData.size());
			//For each table data.
			for(int i=0; i<=tableData.size(); i++) {
				
				//Get the data.
				data = tableData.get(i);
					
				//Get the reorder point.
				reorderPoint = Integer.parseInt(data.getReorderPoint());
				
				//Select the Inventory item where the name equals the row's name and the status is avaiable.
				query = "SELECT * FROM InventoryItem WHERE InventoryItemTypeName = '" 
				+ data.getItemTypeName() + "' AND Status = 'Available'";
				
				//Use the query to get an item collection.
				itemCollection = new ItemCollection(query, "ReorderList");
				
				//Get the number of items found.
				numberOfItems = itemCollection.getNumRecords();
				
				//If the number of items is greater then the reorder point.
				if(numberOfItems > reorderPoint)
				{
					//Remove the item from the data.
					tableData.remove(i);
				}
				
				
				//DEBUG
				 
				// System.out.println("Query: " + query);
				 //System.out.println("NUM RECORDS: " + itemCollection.getNumRecords());
				 //System.out.println("Reorder Point:" + reorderPoint);
				 			
			}
			
			
			
			ReorderListTable.setItems(tableData);
		} catch (Exception e) {// SQLException e) {
			// Need to handle this exception
			e.printStackTrace();
		}
	}

	// Create the title container
	//-------------------------------------------------------------
	private Node createTitle()
	{
		HBox container = new HBox();
		container.setAlignment(Pos.CENTER);	

		Text titleText = new Text(" Restuarant Management System ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 40));
		titleText.setWrappingWidth(300);
		titleText.setTextAlignment(TextAlignment.CENTER);

		container.getChildren().add(titleText);

		return container;
	}

	// Create the main form content
	//-------------------------------------------------------------
	private VBox createFormContent()
	{
		VBox vbox = new VBox(10);

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text prompt = new Text("REORDER LIST");
		prompt.setWrappingWidth(350);
		prompt.setTextAlignment(TextAlignment.CENTER);
		prompt.setFill(Color.BLACK);
		grid.add(prompt, 0, 0, 2, 1);

		ReorderListTable = new TableView<ReorderListTableModel>();
		ReorderListTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		TableColumn ItemTypeName = new TableColumn("ItemTypeName") ;
		ItemTypeName.setMinWidth(900);
		ItemTypeName.setCellValueFactory(
				new PropertyValueFactory<ReorderListTableModel, String>("ItemTypeName"));

		
//		TableColumn reorderPointColumn = new TableColumn("Reorder Point") ;
//		reorderPointColumn.setMinWidth(200);
//		reorderPointColumn.setCellValueFactory(
//				new PropertyValueFactory<ReorderListTableModel, String>("ReorderPoint"));
		
		//TODO: Implement way to display how many there are.
//		
//		TableColumn countColumn = new TableColumn("Count") ;
//		countColumn.setMinWidth(200);
//		countColumn.setCellValueFactory(
//				new PropertyValueFactory<ReorderListTableModel, String>(""));

		//reorderPointColumn, countColumn
		ReorderListTable.getColumns().addAll(ItemTypeName);


		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(ReorderListTable);

		doneBTN = new Button("Confirm");
		doneBTN.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				new Manager();
			}
		});


		HBox btnContainer = new HBox(100);
		btnContainer.setAlignment(Pos.CENTER);
		btnContainer.getChildren().add(doneBTN);

		vbox.getChildren().add(grid);
		vbox.getChildren().add(scrollPane);
		vbox.getChildren().add(btnContainer);

		return vbox;
	}



	//--------------------------------------------------------------------------
	public void updateState(String key, Object value)
	{

	}

	//--------------------------------------------------------------------------
	protected MessageView createStatusLog(String initialMessage)
	{
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}


	/**
	 * Display info message
	 */
	//----------------------------------------------------------
	public void displayMessage(String message)
	{
		statusLog.displayMessage(message);
	}

	/**
	 * Clear error message
	 */
	//----------------------------------------------------------
	public void clearErrorMessage()
	{
		statusLog.clearErrorMessage();
	}
}
