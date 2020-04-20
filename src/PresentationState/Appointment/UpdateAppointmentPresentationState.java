package PresentationState.Appointment;

import DAO.CustomerDao;
import DAO.POJO.Customer;
import iluwatar.DbDao.DbAppointmentDao;
import iluwatar.DbDao.DbCustomerDao;
import iluwatar.POJO.Appointment;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.Database.DBUtils;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * PresentationState cannot only hold real data but also meta data used for the presentation.
 * For example it could hold information about validation results, mandatory, enabled or disabled fields etc.
 * depending on certain rules. The PresentationState then will contain the result of applying these rules.
 */

public class UpdateAppointmentPresentationState {
	//get appointment information
	Optional<Appointment> appointment;
	Customer customer;

	public final StringProperty id = new SimpleStringProperty();
	public final StringProperty customerName = new SimpleStringProperty();
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

	public ObservableList<Integer> ids = FXCollections.observableArrayList();
	public ObservableList<String> customerNames = FXCollections.observableArrayList();

	public void initBinding() {
		/**
		 * The purpose of initBinding() is to bind internal fields of PresentationState to each other
		 * (e.g. customerName automatically changes when appointmentId changes).
		 */
		startDate.addListener((observable, oldValue, newValue) -> {
//			startDate.getValue();
//			datePicker.getEditor().getText();
			System.out.println(oldValue);
			System.out.println(newValue);
		});


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
	public void initData(int appointmentId) throws Exception {
		if(ids.isEmpty()) {
			setIds();
		}

		if(customerNames.isEmpty()) {
			setCustomerNames();
		}
		appointment = new DbAppointmentDao(DBUtils.getMySQLDataSource()).getById(appointmentId);
		// get customerName
		assert appointment != null;
		customer = CustomerDao.getCustomerById(appointment.get().getCustomerId());

		id.setValue(String.valueOf(appointment.get().getCustomerId()));

		assert customer != null;
		this.customerName.setValue(String.valueOf(customer.getName()));
		userId.setValue(String.valueOf(appointment.get().getUserId()));
		customerContact.setValue(String.valueOf(appointment.get().getContact()));
		title.setValue(String.valueOf(appointment.get().getTitle()));
		description.setValue(String.valueOf(appointment.get().getDescription()));
		location.setValue(String.valueOf(appointment.get().getLocation()));
		url.setValue(String.valueOf(appointment.get().getUrl()));

		// DateTime elements
		// format YYYY/MM/DD

//		Calendar startCal = DateTimeUtils.stringToCalendar(appointment.get().getStart());
//		Calendar startCal = appointment.get().getStart();
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		String dateStr = format.format(startCal.getTime());
		startDate.setValue( appointment.get().getStart());

//		Calendar endCal =  DateTimeUtils.stringToCalendar(appointment.get().getEnd());
//		dateStr = format.format(endCal.getTime());
		endDate.setValue(appointment.get().getEnd());
		// format HH:mm
//		startTime.setValue(String.valueOf(startCal.getTime()));
//		endTime.setValue(String.valueOf(endCal.getTime()));

	}


	private void newAppointment() {
		customerName.setValue("");
		customerContact.setValue("");
		userId.setValue("1");
		title.setValue("");
		description.setValue("");
		location.setValue("");
		type.setValue("");
		url.setValue("");


		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		startTime.setValue(dtf.format(now));
		endTime.setValue(dtf.format(now.plusHours(1)));

		dtf = DateTimeFormatter.ofPattern("YYYY-mm-dd");

		startDate.setValue(dtf.format(now));
		endDate.setValue(dtf.format(now));
	}

	private void setIds() throws Exception {
		Stream<iluwatar.POJO.Appointment> appointmentStream = new DbAppointmentDao(DBUtils.getMySQLDataSource()).getAll();
		appointmentStream.forEach(a->{
			ids.add(a.getId());
		});
	}

	private void setCustomerNames() throws Exception {
		Stream<iluwatar.POJO.Customer> customerStream = new DbCustomerDao((DBUtils.getMySQLDataSource())).getAll();
		customerStream.forEach(c->{
			customerNames.add(c.getCustomerName());
		});
	}
}
