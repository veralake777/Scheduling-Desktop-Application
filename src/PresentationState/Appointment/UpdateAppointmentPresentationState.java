package PresentationState.Appointment;

import DAO.AppointmentDao;
import DAO.CustomerDao;
import DAO.POJO.Appointment;
import DAO.POJO.Customer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import utils.DateTime.DateTimeUtils;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;

/**
 * PresentationState cannot only hold real data but also meta data used for the presentation.
 * For example it could hold information about validation results, mandatory, enabled or disabled fields etc.
 * depending on certain rules. The PresentationState then will contain the result of applying these rules.
 */

public class UpdateAppointmentPresentationState {
	//get appointment information
	Appointment appointment;

	public final StringProperty id = new SimpleStringProperty();
	public final StringProperty customer = new SimpleStringProperty();
	public final StringProperty customerContact = new SimpleStringProperty();

	public final StringProperty userId = new SimpleStringProperty();
	public final StringProperty title = new SimpleStringProperty();
	public final StringProperty description = new SimpleStringProperty();
	public final StringProperty location = new SimpleStringProperty();
	public final StringProperty type = new SimpleStringProperty();
	public final StringProperty url = new SimpleStringProperty();
	public final StringProperty startTime = new SimpleStringProperty();
	public final StringProperty startDate = new SimpleStringProperty();
	public final StringProperty endDate = new SimpleStringProperty();
	public final StringProperty endTime = new SimpleStringProperty();

	public void initBinding() {
		/**
		 * The purpose of initBinding() is to bind internal fields of PresentationState to each other
		 * (e.g. customerName automatically changes when appointmentId changes).
		 */

		id.addListener((observable, oldValue, newValue) -> {
			try {
				if(!id.getValue().equals("")){
					// TODO check of appt id exists
					try {
						initData(Integer.parseInt(id.getValue()));
					} catch (ParseException | SQLException | ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			} catch (NumberFormatException | NullPointerException e) {
				System.out.println("NUMBER IS WRONG OR NULL VALUE");
//				e.printStackTrace();
			}
		});
		// TODO bind application id to its other data members
	}

	/**
	 * USE - initialize the presentation state
	 *
	 * @param appointmentId - passed from GUIBinder bindAndInitialize
	 * @throws ParseException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 *
	 */
	public void initData(int appointmentId) throws ParseException, SQLException, ClassNotFoundException {
		appointment = AppointmentDao.getAppointment(appointmentId);
		// get customerName
		assert appointment != null;
		Customer cust = CustomerDao.getCustomer(appointment.getCustomerId());

		id.setValue(String.valueOf(appointment.getAppointmentId()));

		assert cust != null;
		customer.setValue(String.valueOf(cust.getName()));
		userId.setValue(String.valueOf(appointment.getUserId()));
		customerContact.setValue(String.valueOf(appointment.getContact()));
		title.setValue(String.valueOf(appointment.getTitle()));
		description.setValue(String.valueOf(appointment.getDescription()));
		location.setValue(String.valueOf(appointment.getLocation()));
		url.setValue(String.valueOf(appointment.getUrl()));

		// DateTime elements
		// format YYYY/MM/DD
		startDate.setValue(String.valueOf(appointment.getStart().get(Calendar.DATE)));
		endDate.setValue(String.valueOf(appointment.getEnd().get(Calendar.DATE)));
		// format HH:mm
		startTime.setValue(DateTimeUtils.getHoursAndMinutes(appointment.getStart()));
		endTime.setValue(DateTimeUtils.getHoursAndMinutes(appointment.getEnd()));

	}
}
