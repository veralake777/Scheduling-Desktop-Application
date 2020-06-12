package Components;

import DbDao.DbCityDao;
import DbDao.DbCountryDao;
import DbDao.DbCustomerDao;
import DbDao.DbUserDao;
import POJO.City;
import POJO.Country;
import POJO.Customer;
import POJO.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import utils.DBUtils;

import java.text.ParseException;
import java.time.LocalTime;
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

    private void fifteenMinuteAppointmentSlots() throws ParseException {
        // 15 minute increments
        ObservableList<LocalTime> list = FXCollections.observableArrayList();
        int hours = 12;
        int minutes = 60;
        int increment = 15;

        for (int i = 6; i < hours; i++) {
            for (int j = 0; j < minutes; j += increment) {
                list.add(LocalTime.of(i, j, 0));
//                if(Local.equals("11:45:00")) {
//                    list.add(LocalTime.of(12, 0, 0));
//                }
            }
        }
        appointmentTimes.setItems(list);
    }

    private ComboBox<Integer> durationTimes() {
        ObservableList<Integer> list = FXCollections.observableArrayList();
        list.addAll(15, 30, 45, 60);
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

    public ComboBox<LocalTime> getAppointmentTimes() throws ParseException {
        fifteenMinuteAppointmentSlots();
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
