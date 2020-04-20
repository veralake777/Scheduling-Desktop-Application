package PresentationState.Appointment;

import DAO.AppointmentDao;
import iluwatar.DbDao.DbCustomerDao;
import javafx.beans.InvalidationListener;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import utils.Database.DBUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class UpdateAppointmentActionHandlers {
	public static InvalidationListener updateButtonHandler(UpdateAppointmentPresentationState ps) {
		// update view and save to database
		return observable ->
		{
			int id = ps.appointment.get().getId();
			try {
				int customerId = new DbCustomerDao(DBUtils.getMySQLDataSource()).getById(ps.appointment.get().getCustomerId()).get().getId();
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

				// TIME STUFF

				// start
				String startDate = ps.startDate.getValue() + " " + ps.startTime.getValue() + ":00";
				// validate that startDate < endDate
				Date startToDate = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse(startDate);
				String endDate = ps.endDate.getValue() + " " + ps.endTime.getValue() + ":00";
				Date endToDate = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse(endDate);
				boolean datesPass = startToDate.before(endToDate);
				boolean timesPass= startToDate.getTime() < endToDate.getTime();
				AppointmentDao.updateAppointmentDate("start", String.valueOf(startToDate), id);
				AppointmentDao.updateAppointmentDate("end", String.valueOf(endToDate), id);
//				if(!datesPass || !timesPass) {
//					Alert alert = new Alert(Alert.AlertType.valueOf("ERROR"));
//					alert.setTitle("Date Selection Error");
//					alert.setContentText("Start date and time must be before end date and time.");
//					alert.setResizable(false);
//
//					alert.showAndWait();
//				} else {
//
//				}
			} catch (ClassNotFoundException | ParseException | SQLException | IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
//			//ALERT ask if they want to commit changes to the database. On ok, QueryUtils.commitQuery;
			Alert alert = new Alert(Alert.AlertType.valueOf("CONFIRMATION"));
			alert.setTitle("SQL Prompt");
			alert.setContentText("Would you like to save your changes to the database?");
			alert.setResizable(false);

			Optional<ButtonType> result = alert.showAndWait();
			ButtonType button = result.orElse(ButtonType.CANCEL);

			if (button == ButtonType.OK) {
				System.out.println("Ok pressed");
				try {
					ps.initData(id);
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("canceled");
			}
			alert.showAndWait();
		};
	}

	public static InvalidationListener onIdComboBoxChange(UpdateAppointmentPresentationState ps, UpdateAppointmentController controller) {
		return observable -> {
			try {
				ps.initData(controller.idComboBox.getValue());
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	}

	public static InvalidationListener onCustomerComboBoxChange(UpdateAppointmentPresentationState ps, UpdateAppointmentController controller) {
		return observable -> {
			System.out.println("CUSTOMER CHANGED");
		};
	}

	public static InvalidationListener exitProgram(UpdateAppointmentController controller) {
		return observable -> {
			Stage stage = (Stage) controller.cancelButton.getScene().getWindow();
			stage.close();
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
