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
