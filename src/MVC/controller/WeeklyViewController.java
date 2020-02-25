package MVC.controller;

import DAO.AppointmentDao;
import DAO.POJO.Appointment;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.text.ParseException;

public class WeeklyViewController {
    @FXML
    public TableView tableView;

    @FXML
    public void initialize() throws ParseException, SQLException, ClassNotFoundException {
        createAndConfigureWeek();
    }

    private void createAndConfigureWeek() throws ParseException, SQLException, ClassNotFoundException {
        Appointment appt = null;

        // Create Columns
        TableColumn day1 = new TableColumn<>("Day1");
        day1.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        TableColumn day2 = new TableColumn<>("Day2");
        day2.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        TableColumn day3 = new TableColumn<>("Day3");
        day3.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        TableColumn day4 = new TableColumn<>("Day4");
        day4.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        TableColumn day5 = new TableColumn<>("Day5");
        day5.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        TableColumn day6 = new TableColumn<>("Day6");
        day6.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        TableColumn day7 = new TableColumn<>("Day7");
        day7.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));


        tableView.getColumns().addAll(day1, day2, day3, day4, day5, day6, day7);

        // equal width columns
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        try {
            appt = AppointmentDao.getAppointment(1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        tableView.getItems().addAll(appt, appt);
    }

    // get appointments in each week
    private ObservableList<Appointment> getWeeklyAppointments() throws ParseException, SQLException, ClassNotFoundException {
        //TODO figure out how to access month, year, etc throughout application for consistency
//        AppointmentDao.getAppointmentsWithinMonth();
        return AppointmentDao.getAllAppointments();
    }

    // get appointments for each day

    // set daily appointments

}
