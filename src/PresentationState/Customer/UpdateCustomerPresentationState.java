package PresentationState.Customer;

import DAO.AddressDao;
import DAO.CityDao;
import DAO.CountryDao;
import DAO.CustomerDao;
import DAO.POJO.Address;
import DAO.POJO.City;
import DAO.POJO.Country;
import DAO.POJO.Customer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.SQLException;
import java.text.ParseException;

public class UpdateCustomerPresentationState {
	Customer customerData;

	public StringProperty id  = new SimpleStringProperty();
	public StringProperty name  = new SimpleStringProperty();
	public StringProperty phone = new SimpleStringProperty();
	public StringProperty address = new SimpleStringProperty();
	public StringProperty address2 = new SimpleStringProperty();
	public StringProperty city = new SimpleStringProperty();
	public StringProperty postalCode = new SimpleStringProperty();
	public StringProperty country = new SimpleStringProperty();
	public StringProperty url = new SimpleStringProperty();


	public void initBinding() {
	}

	public void initData(int customerId) throws ParseException, SQLException, ClassNotFoundException {
		// get customerData
		customerData = CustomerDao.getCustomerById(customerId);

		// get addressData
		Address addressData = AddressDao.getAddress(customerData.getAddressId());
		// get cityData
		City cityData = CityDao.getCity(addressData.getCityId());
		// get countryData
		Country countryData = CountryDao.getCountry(cityData.getCountryId());

		// get countryData from cityData
		id.setValue(String.valueOf(customerId));
		name.setValue(customerData.getName());
		address.setValue(addressData.getAddress());
		address2.setValue(addressData.getAddress2());
		phone.setValue(addressData.getPhone());
		city.setValue(cityData.getCity());
		country.setValue(countryData.getCountry());
		phone.setValue(addressData.getPhone());
	}

}
