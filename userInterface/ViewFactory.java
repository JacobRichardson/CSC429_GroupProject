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
		else if(viewName.equals("titleSearch") == true)
		{
			return new SearchBook(model);
		}
		else if(viewName.equals("BookCollectionView") == true)
		{
			return new BookCollectionView2(model);
		}
		else if(viewName.equals("enterPatronView") == true)
		{
			return new EnterPatronView(model);
		}
		/*else if(viewName.equals("WithdrawTransactionView") == true)
		{
			return new WithdrawTransactionView(model);
		}
		else if(viewName.equals("TransferTransactionView") == true)
		{
			return new TransferTransactionView(model);
		}
		else if(viewName.equals("BalanceInquiryTransactionView") == true)
		{
			return new BalanceInquiryTransactionView(model);
		}
		else if(viewName.equals("BalanceInquiryReceipt") == true)
		{
			return new BalanceInquiryReceipt(model);
		}
		else if(viewName.equals("WithdrawReceipt") == true)
		{
			return new WithdrawReceipt(model);
		}
		else if(viewName.equals("DepositReceipt") == true)
		{
			return new DepositReceipt(model);
		}
		else if(viewName.equals("TransferReceipt") == true)
		{
			return new TransferReceipt(model);
		}*/
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
