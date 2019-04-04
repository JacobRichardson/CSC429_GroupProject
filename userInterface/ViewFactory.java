package userInterface;

import impresario.IModel;

//==============================================================================
public class ViewFactory {

	public static View createView(String viewName, IModel model)
	{
		if(viewName.equals("ManagerView") == true)
		{
			return new MangerView(model);
		}
		else if(viewName.equals("searchVendor") == true)
		{
			return new SearchVendorNamePhone(model);
		}
		else if(viewName.equals("vendorCollection") == true)
		{
			return new vendorCollectionView(model);
		}
		else if(viewName.equals("vendorModify") == true) {
			return new modifyVendorView(model);
		}
		else if(viewName.equals("VendorInventoryPrice") == true) {
			return new VendorInventoryPriceView(model);
		}
		else if (viewName.equals("searchInventoryItemType")) {
			return new SearchInventoryItemType(model);
		}
		else if (viewName.equals("InventoryItemTypeCollection")) {
			return new InventoryItemTypeCollectionView(model);
		}
		else
			return null;
	}


	/*
	public static Vector createVectorView(String viewName, IModel model)
	{
		if(viewName.equals("SOME VIEW NAME") == true)
		{
			//return [A NEW VECTOR VIEW OF THAT NAME TYPE]
		}
		else
			return null;
	}
	*/

}
