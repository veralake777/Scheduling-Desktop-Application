package PresentationState.Customer;

import DAO.AddressDao;
import DAO.CustomerDao;
import javafx.beans.InvalidationListener;

import java.sql.SQLException;
import java.text.ParseException;

public class UpdateCustomerActionHandlers {
	String name;
	String address;
	String address2;
	String postalCode;
	String city;
	String country;
	String phone;

	public static InvalidationListener updateCustomerHandler(UpdateCustomerPresentationState ps) {
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
			ps.city.setValue(ps.city.getValue());
			ps.country.setValue(ps.country.getValue());
			ps.phone.setValue(ps.phone.getValue());


			try {
				ps.customerData.setId(Integer.parseInt(ps.id.getValue()));
				ps.customerData.setName(ps.name.getValue());
				ps.customerData.setAddressId(AddressDao.getAddressId(ps.address.getValue()));
				ps.customerData.setActive(true);

				CustomerDao.updateCustomer(
						ps.customerData.getId(),
						ps.customerData.getName(),
						ps.customerData.getAddressId(),
						ps.customerData.isActive()
				);

			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			};
}

	private void updateId(UpdateCustomerPresentationState ps) throws ParseException, SQLException, ClassNotFoundException {
		CustomerDao.updateCustomer(
				"customerName", ps.name.getValue(), Integer.parseInt(ps.id.getValue()));
	}

	public static InvalidationListener onActionNewCustomer(UpdateCustomerPresentationState ps) {
		return observable -> {
				int nextAvailableId = 0; // next available id - PK
				try {
					nextAvailableId = CustomerDao.getAllCustomers().size() + 1;
				} catch (ClassNotFoundException | SQLException | ParseException e) {
					e.printStackTrace();
				}
			ps.id.setValue(String.valueOf(nextAvailableId));
				ps.name.setValue("John Doe");
				ps.address.setValue("street name");
				ps.address2.setValue("apartment, etc.");
				ps.city.setValue("city");
				ps.country.setValue("country");
				ps.phone.setValue("555-5555");
				//TODO fix add customer - extract to a new button and view for add customer

//			try {
//				CustomerDao.addCustomer(Integer.parseInt(ps.id.getValue()), ps.name.getValue(), ps.customerData.getAddressId(), 1, "2020-03-23 00:00:00", "test","2020-03-23 00:00:00", "test");
//			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
//			}
		};
	}
}
