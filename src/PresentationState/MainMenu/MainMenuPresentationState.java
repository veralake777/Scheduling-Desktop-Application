package PresentationState.MainMenu;

import DAO.AddressDao;
import DAO.AppointmentDao;
import DAO.CustomerDao;
import DAO.POJO.Address;
import DAO.POJO.Appointment;
import DAO.POJO.Customer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import utils.DateTime.DateTimeUtils;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;

public class MainMenuPresentationState {
	public StringProperty nextAppointmentHeader  = new SimpleStringProperty();
	public StringProperty date  = new SimpleStringProperty();
	public StringProperty time = new SimpleStringProperty();
	public StringProperty customerName = new SimpleStringProperty();
	public StringProperty customerPhone = new SimpleStringProperty();

	public void initBinding() {
	}

	public void initData(int appointmentId) throws ParseException, SQLException, ClassNotFoundException {
		// get appointment data
		Appointment appointmentData = null;
		try {
			appointmentData = AppointmentDao.getAppointment(appointmentId);
		} catch (ClassNotFoundException | SQLException | ParseException e) {
			e.printStackTrace();
		}

		// get customer data
		Customer customerData = null;
		try {
			customerData = CustomerDao.getCustomerById(appointmentData.getCustomerId());
		} catch (ClassNotFoundException | SQLException | ParseException e) {
			e.printStackTrace();
		}

		// get customer contact info
		Address addressData = null;
		try {
			assert customerData != null;
			addressData = AddressDao.getAddress(customerData.getAddressId());
		} catch (ClassNotFoundException | SQLException | ParseException e) {
			e.printStackTrace();
		}

		nextAppointmentHeader.setValue("Next Appointment");
		date.setValue(String.valueOf(appointmentData.getStart().get(Calendar.DATE)));
		time.setValue(DateTimeUtils.getHoursAndMinutes(appointmentData.getStart()));
		customerName.setValue(customerData.getName());
		assert addressData != null;
		customerPhone.setValue(addressData.getPhone());

	}
}
