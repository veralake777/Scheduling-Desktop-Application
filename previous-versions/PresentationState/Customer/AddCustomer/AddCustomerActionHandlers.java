package PresentationState.Customer.AddCustomer;

import DAO.CustomerDao;
import javafx.beans.InvalidationListener;

import java.sql.SQLException;
import java.text.ParseException;

public class AddCustomerActionHandlers {
	public static InvalidationListener updateCustomerHandler(AddCustomerPresentationState ps) {
		/**
		 * B.   Provide the ability to add, update, and delete customer records
		 * in the database, including name, address, and phone number.
		 *
		 * @link DAO/CustomerDAO
		 */
		String id = ps.id.getValue();
		return observable -> {
			ps.id.setValue(ps.id.getValue());
			ps.name.setValue(ps.name.getValue());
			ps.address.setValue(ps.address.getValue());
			ps.address2.setValue(ps.address2.getValue());
			ps.postalCode.setValue(ps.postalCode.getValue());
//			ps.cityTypeList.get(ps.cityTypeList.);
//			ps.countryTypeList.setValue(ps.country.getValue());
			ps.phone.setValue(ps.phone.getValue());


			ps.customerData.setId(Integer.parseInt(ps.id.getValue()));
			ps.customerData.setName(ps.name.getValue());
//				ps.customerData.setAddressId(AddressDao.getAddressId(ps.address.getValue()));
			ps.customerData.setAddressId(1);
			ps.customerData.setActive(true);


			CustomerDao.updateCustomer(
					ps.customerData.getId(),
					ps.customerData.getName(),
					ps.customerData.getAddressId(),
					ps.customerData.isActive()
			);

		};
}

	private void updateId(AddCustomerPresentationState ps) throws ParseException, SQLException, ClassNotFoundException {
		CustomerDao.updateCustomer(
				"customerName", ps.name.getValue(), Integer.parseInt(ps.id.getValue()));
	}

	// FIXME nullpointer
	public static InvalidationListener onActionAddCustomer(AddCustomerPresentationState ps) {
		return observable -> {
			ps.id.setValue(ps.id.getValue());
			ps.name.setValue(ps.name.getValue());
			ps.address.setValue(ps.address.getValue());
			ps.address2.setValue(ps.address2.getValue());
			ps.postalCode.setValue(ps.postalCode.getValue());
//			ps.cityTypeList.get(ps.cityTypeList.);
//			ps.countryTypeList.setValue(ps.country.getValue());

			ps.phone.setValue(ps.phone.getValue());
			ps.customerData.setId(Integer.parseInt(ps.id.getValue()));
			ps.customerData.setName(ps.name.getValue());
//				ps.customerData.setAddressId(AddressDao.getAddressId(ps.address.getValue()));
			ps.customerData.setAddressId(1);
			ps.customerData.setActive(true);
			try {


				CustomerDao.addCustomer(
						ps.customerData.getName(),
						ps.customerData.getAddressId(),
						1,
						ps.customerData.getCreateDate().toString(),
						ps.customerData.getCreatedBy(),
						ps.customerData.getLastUpdate().toString(),
						ps.customerData.getLastUpdateBy()
				);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		};
	}
}
