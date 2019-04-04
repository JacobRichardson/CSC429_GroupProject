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
		else if(viewName.equals("FindInventoryItemTypeView") == true){
			return new FindInventoryItemTypeView(model);
		}
		else if(viewName.equals("IITCollectionView") == true){
			return new IITCollectionView(model);
		}
		else if(viewName.equals("AddVendor") == true) {
			return new addVendorView(model);
		}
		else if(viewName.equals("AddIIT") == true) {
			return new AddIITView(model);
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
