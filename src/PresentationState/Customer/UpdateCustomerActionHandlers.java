package PresentationState.Customer;

import javafx.beans.InvalidationListener;

public class UpdateCustomerActionHandlers {

	public static InvalidationListener updateCustomerHandler(UpdateCustomerPresentationState ps) {
		return observable -> ps.name.setValue("Hello " + ps.name.getValue());
	}
}
