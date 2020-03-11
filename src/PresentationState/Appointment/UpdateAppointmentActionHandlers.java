package PresentationState.Appointment;

import DAO.AppointmentDao;
import DAO.CustomerDao;
import javafx.beans.InvalidationListener;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Objects;

public class UpdateAppointmentActionHandlers {
	public static InvalidationListener updateButtonHandler(UpdateAppointmentPresentationState ps) {
		// update view and save to database
		return observable ->

		{
			int id = ps.appointment.getAppointmentId();
			try {
				// customer - catch (ParseException e) {
				//				e.printStackTrace();
				//			} catch (SQLException e) {
				int customerId = Objects.requireNonNull(CustomerDao.getCustomerByName(ps.customer.getValue())).getId();
				AppointmentDao.updateAppointmentWithString("customerId", String.valueOf(customerId), id);

				// user
				AppointmentDao.updateAppointmentWithString("userId", ps.userId.getValue(), id);

				// title
				AppointmentDao.updateAppointmentWithString("title", ps.title.getValue(), id);

				// description
				AppointmentDao.updateAppointmentWithString("description", ps.description.getValue(), id);

				// location
				AppointmentDao.updateAppointmentWithString("location", ps.location.getValue(), id);

				// contact info
				AppointmentDao.updateAppointmentWithString("contact", ps.description.getValue(), id);

				// type
				AppointmentDao.updateAppointmentWithString("type", ps.type.getValue(), id);

				// url
				AppointmentDao.updateAppointmentWithString("url", ps.url.getValue(), id);

				// start
				// TODO fix me
//				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//				LocalDate date = ps.startDate.getValue();
//				String dateStr = format.format(ps.startDate.getValue());
//				AppointmentDao.updateAppointmentWithDate("start", dateStr  + " " + ps.startTime.getValue(), id);
//				AppointmentDao.updateAppointment("start", String.valueOf(ps.startDate.getValue()), id);


				// end

				// createDate = NOW()

				// createdBy = currentUser

				// lastUpdate = prevValue

				// lastUpdateBy = prevUser
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		};
	}
}

/**
 *  public static void updateAppointment(String updateCol, String setColValue, int rowId) throws ClassNotFoundException {
 *         try {
 *             // UPDATE `table_name` SET `column_name` = `new_value' [WHERE condition];
 *             // TODO: check colVal type....if int without single quotes, else with single quotes (like below)
 *             sqlStatement = "UPDATE appointment SET " + updateCol + " = '" + setColValue + "' WHERE appointmentId = " + rowId;
 *             DBUtils.startConnection();
 *             QueryUtils.createQuery(sqlStatement);
 *             ResultSet result = QueryUtils.getResult();
 *         } catch (ClassNotFoundException e) {
 *             System.out.println("ApptDao UPDATE CLASS NOT FOUND");
 *             e.getException();
 *         }
 */
