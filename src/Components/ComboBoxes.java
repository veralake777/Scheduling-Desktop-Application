package Components;

import DbDao.*;
import POJO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import utils.DBUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

public class ComboBoxes extends ComboBox {
    private ComboBox<Customer> customers = new ComboBox<>();
    private ComboBox<Country> countries = new ComboBox<>();
    private ComboBox<City> cities = new ComboBox<>();
    private ComboBox<LocalTime> appointmentTimes = new ComboBox<>();
    private ComboBox<User> consultants = new ComboBox<>();

    public ComboBoxes() {
    }

    private void consultants() throws Exception {
        ObservableList<User> list = FXCollections.observableArrayList();
        Stream<User> userStream = new DbUserDao(DBUtils.getMySQLDataSource()).getAll();
        userStream.forEach(list::add);
        consultants.setEditable(false);
        consultants.setItems(list);
        consultants.setOnAction(e -> consultants.getSelectionModel().getSelectedItem());
    }

    private void customers() throws Exception {
        ObservableList<Customer> list = FXCollections.observableArrayList();
        Stream<Customer> customerStream = new DbCustomerDao(DBUtils.getMySQLDataSource()).getAll();
        customerStream.forEach(list::add);
        customers.setEditable(false);
        customers.setItems(list);
        customers.setOnAction(e -> customers.getSelectionModel().getSelectedItem());
    }

    private void countries() throws Exception {
        ObservableList<Country> list = FXCollections.observableArrayList();
        Stream<Country> countryStream = new DbCountryDao((DBUtils.getMySQLDataSource())).getAll();
        countryStream.forEach(list::add);
        countries.setItems(list);
        countries.setOnAction(e -> countries.getSelectionModel().getSelectedItem());
    }

    private void cities() throws Exception {
        ObservableList<City> list = FXCollections.observableArrayList();
        Stream<City> cityStream = new DbCityDao(DBUtils.getMySQLDataSource()).getAll();
        cityStream.forEach(list::add);
        cities.setItems(list);
        cities.setOnAction(e -> {
           cities.getSelectionModel().getSelectedItem();
        });
    }

    //F.   Write exception controls to prevent each of the following. You may use the same mechanism of exception control more than once, but you must incorporate at least  two different mechanisms of exception control.
    // (a)  scheduling an appointment outside business hours
    // this combobox and the durationTimes are the only ways to set Appointment Times and they are both always within business hours
    private void fifteenMinuteAppointmentSlots(LocalDateTime localDate) throws Exception {
        // 15 minute increments

        // unavailable appointments
        ObservableList<LocalTime> allAppointmentSlots = FXCollections.observableArrayList();
        Stream<Appointment> appointmentStream = new DbAppointmentDao(DBUtils.getMySQLDataSource()).getAll();
        List<LocalDateTime> unavailableAppointmentSlots = FXCollections.observableArrayList();
        ObservableList<LocalTime> availableSlots = FXCollections.observableArrayList();

        // FUTURE FEATURE
//        appointmentStream.forEach(a->{
//            LocalDateTime start = LocalDateTime.of(localDate.toLocalDate(), LocalTime.of(a.getStart().toLocalDateTime().getHour(), a.getStart().toLocalDateTime().getMinute()));
//            LocalDateTime end = LocalDateTime.of(localDate.toLocalDate(), LocalTime.of(a.getEnd().toLocalDateTime().getHour(), a.getEnd().toLocalDateTime().getMinute()));
//            while(start.isBefore(end)) {
//                unavailableAppointmentSlots.add(start);
//                start = start.plusMinutes(15);
//            }
//            unavailableAppointmentSlots.add(start);
//        });

        // all appointment slots
        for (LocalDateTime startTime = LocalDateTime.of(localDate.toLocalDate(), LocalTime.of(8, 0));
             !startTime.isAfter(LocalDateTime.of(localDate.toLocalDate(), LocalTime.of(18, 0)));
             startTime = startTime.plus(Duration.ofMinutes(15))) {
            allAppointmentSlots.add(LocalTime.parse(startTime.toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm"))));
        }

        // FUTURE FEATURE
        // remove unavailable appointments from all appointments
//        for (LocalDateTime available : allAppointmentSlots) {
//            for (LocalDateTime unavailable : unavailableAppointmentSlots) {
//                if (available.equals(unavailable)) {
//                    allAppointmentSlots.remove(unavailable);
//                    break;
//                }
//            }
//        }


        appointmentTimes.setItems(allAppointmentSlots);
    }

    private ComboBox<Integer> durationTimes() {
        ObservableList<Integer> list = FXCollections.observableArrayList();
        list.addAll(15, 30, 45, 60, 75, 90, 105, 120);
        ComboBox<Integer> durations = new ComboBox<>(list);
        return durations;
    }


    public ComboBox<Customer> getCustomers() throws Exception {
        customers();
        return customers;
    }

    public ComboBox<Country> getCountries() throws Exception {
        countries();
        return countries;
    }

    public ComboBox<City> getCities() throws Exception {
        cities();
        return cities;
    }

    public ComboBox<LocalTime> getAppointmentTimes(LocalDateTime localDateTime) throws Exception {
        fifteenMinuteAppointmentSlots(localDateTime);
        return appointmentTimes;
    }

    public ComboBox<Integer> getDurationTimes() {
        return durationTimes();
    }

    public ComboBox<User> getConsultants() throws Exception {
        consultants();
        return consultants;
    }
}
