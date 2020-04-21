package PresentationState.Appointment.AddAppointment;

import javafx.beans.InvalidationListener;
import javafx.stage.Stage;

public class AddAppointmentActionHandlers {
//	public static InvalidationListener addButtonHandler(AddAppointmentPresentationState ps) {
//		// add view and save to database
//		return observable ->
//		{
//			int id = ps.appointment.get().getId();
//			try {
//				int customerId = new DbCustomerDao(DBUtils.getMySQLDataSource()).getById(ps.appointment.get().getCustomerId()).get().getId();
//				AppointmentDao.addAppointmentWithString("customerId", String.valueOf(customerId), id);
//
//				// user
//				AppointmentDao.addAppointmentWithString("userId", ps.userId.getValue(), id);
//
//				// title
//				AppointmentDao.addAppointmentWithString("title", ps.title.getValue(), id);
//
//				// description
//				AppointmentDao.addAppointmentWithString("description", ps.description.getValue(), id);
//
//				// location
//				AppointmentDao.addAppointmentWithString("location", ps.location.getValue(), id);
//
//				// contact info
//				AppointmentDao.addAppointmentWithString("contact", ps.description.getValue(), id);
//
//				// type
//				AppointmentDao.addAppointmentWithString("type", ps.type.getValue(), id);
//
//				// url
//				AppointmentDao.addAppointmentWithString("url", ps.url.getValue(), id);
//
//				// TIME STUFF
//
//				// start
//				String startDate = ps.startDate.getValue() + " " + ps.startTime.getValue() + ":00";
//				// validate that startDate < endDate
//				Date startToDate = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse(startDate);
//				String endDate = ps.endDate.getValue() + " " + ps.endTime.getValue() + ":00";
//				Date endToDate = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse(endDate);
//				boolean datesPass = startToDate.before(endToDate);
//				boolean timesPass= startToDate.getTime() < endToDate.getTime();
//				AppointmentDao.addAppointmentDate("start", String.valueOf(startToDate), id);
//				AppointmentDao.addAppointmentDate("end", String.valueOf(endToDate), id);
////				if(!datesPass || !timesPass) {
////					Alert alert = new Alert(Alert.AlertType.valueOf("ERROR"));
////					alert.setTitle("Date Selection Error");
////					alert.setContentText("Start date and time must be before end date and time.");
////					alert.setResizable(false);
////
////					alert.showAndWait();
////				} else {
////
////				}
//			} catch (ClassNotFoundException | ParseException | SQLException | IOException e) {
//				e.printStackTrace();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
////			//ALERT ask if they want to commit changes to the database. On ok, QueryUtils.commitQuery;
//			Alert alert = new Alert(Alert.AlertType.valueOf("CONFIRMATION"));
//			alert.setTitle("SQL Prompt");
//			alert.setContentText("Would you like to save your changes to the database?");
//			alert.setResizable(false);
//
//			Optional<ButtonType> result = alert.showAndWait();
//			ButtonType button = result.orElse(ButtonType.CANCEL);
//
//			if (button == ButtonType.OK) {
//				System.out.println("Ok pressed");
//				try {
//					ps.initData(id);
//				} catch (ParseException e) {
//					e.printStackTrace();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				} catch (ClassNotFoundException e) {
//					e.printStackTrace();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			} else {
//				System.out.println("canceled");
//			}
//			alert.showAndWait();
//		};
//	}


	public static InvalidationListener onCustomerComboBoxChange(AddAppointmentPresentationState ps, AddAppointmentController controller) {
		return observable -> {
			System.out.println("wtf");
			ps.clear();
		};
	}

	public static InvalidationListener exitProgram(AddAppointmentController controller) {
		return observable -> {
			Stage stage = (Stage) controller.cancelButton.getScene().getWindow();
			stage.close();
		};
	}
}

/**
 *  public static void addAppointment(String addCol, String setColValue, int rowId) throws ClassNotFoundException {
 *         try {
 *             // UPDATE `table_name` SET `column_name` = `new_value' [WHERE condition];
 *             // TODO: check colVal type....if int without single quotes, else with single quotes (like below)
 *             sqlStatement = "UPDATE appointment SET " + addCol + " = '" + setColValue + "' WHERE appointmentId = " + rowId;
 *             DBUtils.startConnection();
 *             QueryUtils.createQuery(sqlStatement);
 *             ResultSet result = QueryUtils.getResult();
 *         } catch (ClassNotFoundException e) {
 *             System.out.println("ApptDao UPDATE CLASS NOT FOUND");
 *             e.getException();
 *         }
 */
