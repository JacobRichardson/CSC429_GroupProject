package userInterface;

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
import java.util.Properties;

public class ModifyInventoryItemType extends View {

	private TextField itemTypeNameTF = new TextField();
	private TextField unitsTF = new TextField();
	private TextField unitsMeasureTF = new TextField();
	private TextField validityDaysTF = new TextField();
	private TextField reorderPointTF = new TextField();
	private TextField statusTF = new TextField();
	private TextField notesTF = new TextField();
	
	private Label itemTypeNameLBL = new Label("ItemTypeName");
	private Label unitsLBL = new Label("Units");
	private Label unitsMeasureLBL = new Label("Units Measure");
	private Label validityDaysLBL = new Label("Validity Days");
	private Label reorderPointLBL = new Label("Reorder Point");
	private Label statusLBL = new Label("Status");
	private Label notesLBL = new Label("Notes");
	
	private Button submitBTN = new Button("Submit");
	private Button cancelBTN = new Button("Cancel");
	
	public ModifyInventoryItemType(IModel model) {
		super(model, "ModifyInventoryItemType");
	}
}
