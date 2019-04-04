import exception.InvalidPrimaryKeyException;
import model.InventoryItemType;

public class tmp {

	public static void main(String[] args) {

		try {
			InventoryItemType test = new InventoryItemType("Guinness");
			test.delete();
		} catch (InvalidPrimaryKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
