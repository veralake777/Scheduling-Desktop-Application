package PresentationState.Customer.AddCustomer;

import DAO.CityDao;
import DAO.CountryDao;
import DAO.CustomerDao;
import DAO.POJO.City;
import DAO.POJO.Country;
import DAO.POJO.Customer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.text.ParseException;

public class AddCustomerPresentationState {
	Customer customerData;

	public StringProperty id  = new SimpleStringProperty();
	public StringProperty name  = new SimpleStringProperty();
	public StringProperty phone = new SimpleStringProperty();
	public StringProperty address = new SimpleStringProperty();
	public StringProperty address2 = new SimpleStringProperty();
	public StringProperty postalCode = new SimpleStringProperty();
	public StringProperty url = new SimpleStringProperty();

	// combo boxes
	public ObservableList<City> cityTypeList = FXCollections.observableArrayList();
	public ObservableList<Country> countryTypeList = FXCollections.observableArrayList();

	public void initBinding() {
	}

	public void initData() throws ParseException, SQLException, ClassNotFoundException {
		// get next available PK
		int nextPrimaryKey = CustomerDao.getAllCustomers().size() + 1;
//		// get addressData
//		Address addressData = AddressDao.getAddress(customerData.getAddressId());
//		// get cityData
//		City cityData = CityDao.getCity(addressData.getCityId());
//		// get countryData
//		Country countryData = CountryDao.getCountry(cityData.getCountryId());

		id.setValue(String.valueOf(nextPrimaryKey));
		name.setValue("John Doe");
		address.setValue("street name");
		address2.setValue("apartment, etc.");
		cityTypeList.addAll(CityDao.getAllcities());
		countryTypeList.addAll(CountryDao.getAllCountries());
		phone.setValue("555-5555");
		//TODO fix add customer - extract to a new button and view for add customer

		// get countryData from cityData
//		id.setValue(String.valueOf(nextPrimaryKey));
//		name.setValue(customerData.getName());
//		address.setValue(addressData.getAddress());
//		address2.setValue(addressData.getAddress2());
//		phone.setValue(addressData.getPhone());
//		city.setValue(cityData.getCity());
//		country.setValue(countryData.getCountry());
//		phone.setValue(addressData.getPhone());
	}

}
