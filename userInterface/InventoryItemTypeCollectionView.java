package userInterface;

import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
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

public class InventoryItemTypeCollectionView extends View {

	protected TableView<InventoryItemTypeModel> tableOfInventoryItemType;
	protected Button cancelButton;
	protected Button submitButton;
	
	public InventoryItemTypeCollectionView(IModel model) {
		super(model, "InventoryItemTypeCollectionView");
		
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 5, 5, 5));
		
		// create our GUI components, add them to this panel
		container.getChildren().add(createTitle());
		container.getChildren().add(createFormContent());
	}

	private Node createTitle()
	{
		HBox container = new HBox();
		container.setAlignment(Pos.CENTER);	

		Text titleText = new Text(" Restuarant Management System ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setWrappingWidth(300);
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKGREEN);
		container.getChildren().add(titleText);
		
		return container;
	}
	
	private VBox createFormContent()
	{
		VBox vbox = new VBox(10);

		GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
       	grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Text prompt = new Text("LIST OF INVENTORY ITEM TYPE");
        prompt.setWrappingWidth(350);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        tableOfInventoryItemType = new TableView<InventoryItemTypeModel>();
        tableOfInventoryItemType.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	
		TableColumn ItemTypeNameColumn = new TableColumn("ItemTypeName") ;
		ItemTypeNameColumn.setMinWidth(50);
		ItemTypeNameColumn.setCellValueFactory(
	                new PropertyValueFactory<vendorTableModel, String>("ItemTypeName"));
		
		TableColumn UnitsColumn = new TableColumn("Units") ;
		UnitsColumn.setMinWidth(125);
		UnitsColumn.setCellValueFactory(
	                new PropertyValueFactory<vendorTableModel, String>("Units"));
		  
		TableColumn UnitMeasureColumn = new TableColumn("UnitMeasure") ;
		UnitMeasureColumn.setMinWidth(100);
		UnitMeasureColumn.setCellValueFactory(
	                new PropertyValueFactory<vendorTableModel, String>("UnitMeasure"));
		
		TableColumn ValidityDaysColumn = new TableColumn("ValidityDays") ;
		ValidityDaysColumn.setMinWidth(50);
		ValidityDaysColumn.setCellValueFactory(
	                new PropertyValueFactory<vendorTableModel, String>("ValidityDays"));
		
		TableColumn ReorderPointColumn = new TableColumn("ReorderPoint") ;
		ReorderPointColumn.setMinWidth(50);
		ReorderPointColumn.setCellValueFactory(
	                new PropertyValueFactory<vendorTableModel, String>("ReorderPoint"));
		
		TableColumn NotesColumn = new TableColumn("Notes") ;
		NotesColumn.setMinWidth(50);
		NotesColumn.setCellValueFactory(
	                new PropertyValueFactory<vendorTableModel, String>("Notes"));
		
		TableColumn ValidityDaysColumn = new TableColumn("ValidityDays") ;
		ValidityDaysColumn.setMinWidth(50);
		ValidityDaysColumn.setCellValueFactory(
	                new PropertyValueFactory<vendorTableModel, String>("ValidityDays"));



		tableOfVendors.getColumns().addAll(IdColumn, nameColumn, phoneNumberColumn, statusColumn);

		tableOfVendors.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event)
			{
				if (event.isPrimaryButtonDown() && event.getClickCount() >=2 ){
					processVendorSelected();
				}
			}
		});
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPrefSize(115, 150);
		scrollPane.setContent(tableOfVendors);

		cancelButton = new Button("Cancel");
 		cancelButton.setOnAction(new EventHandler<ActionEvent>() {
       		     public void handle(ActionEvent e) {
					/**
					 * Process the Cancel button.
					 * The ultimate result of this action is that the transaction will tell the teller to
					 * to switch to the transaction choice view. BUT THAT IS NOT THIS VIEW'S CONCERN.
					 * It simply tells its model (controller) that the transaction was canceled, and leaves it
					 * to the model to decide to tell the teller to do the switch back.
			 		*/
					//----------------------------------------------------------
       		     	clearErrorMessage();
       		     	new model.Manager();
            	  }
        	});

 		submitButton = new Button("Submit");
 		submitButton.setOnAction(new EventHandler<ActionEvent>() {
       		     public void handle(ActionEvent e) {
					processVendorSelected();
            	  }
        	});
 		
		HBox btnContainer = new HBox(100);
		btnContainer.setAlignment(Pos.CENTER);
		btnContainer.getChildren().add(submitButton);
		btnContainer.getChildren().add(cancelButton);
		
		vbox.getChildren().add(grid);
		vbox.getChildren().add(scrollPane);
		vbox.getChildren().add(btnContainer);
	
		return vbox;
	}
	
	@Override
	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub
		
	}

	
}
