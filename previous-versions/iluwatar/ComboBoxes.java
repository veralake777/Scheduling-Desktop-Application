package iluwatar;

import iluwatar.DbDao.DbCityDao;
import iluwatar.DbDao.DbCountryDao;
import iluwatar.DbDao.DbCustomerDao;
import iluwatar.POJO.City;
import iluwatar.POJO.Country;
import iluwatar.POJO.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import utils.Database.DBUtils;

import java.text.ParseException;
import java.util.stream.Stream;

public class ComboBoxes {
    private ComboBox<String> customers = new ComboBox<>();
    private ComboBox<String> countries = new ComboBox<>();
    private ComboBox<String> cities = new ComboBox<>();
    private ComboBox<String> appointmentTimes = new ComboBox<>();

    public ComboBoxes() {
    }

    private void buildCustomers() throws Exception {
        ObservableList<String> list = FXCollections.observableArrayList();
        Stream<Customer> customerStream = new DbCustomerDao((DBUtils.getMySQLDataSource())).getAll();
        customerStream.forEach(c->{
            list.add(c.getCustomerName());
        });
        customers.setItems(list);
        customers.setOnAction(e->{System.out.println(customers.getSelectionModel().getSelectedItem());});
    }

    private void buildCountries() throws Exception {
        ObservableList<String> list = FXCollections.observableArrayList();
        Stream<Country> countryStream = new DbCountryDao((DBUtils.getMySQLDataSource())).getAll();
        countryStream.forEach(c->{
            list.add(c.getCountry());
        });
        countries.setItems(list);
        countries.setOnAction(e->{System.out.println(countries.getSelectionModel().getSelectedItem());});
    }

    private void buildCities() throws Exception {
        ObservableList<String> list = FXCollections.observableArrayList();
        Stream<City> cityStream = new DbCityDao(DBUtils.getMySQLDataSource()).getAll();
        cityStream.forEach(c-> {
            list.add(c.getCity());
        });
        cities.setItems(list);
        cities.setOnAction(e->{System.out.println(cities.getSelectionModel().getSelectedItem());});
    }

    private void buildAppointmentTimes() throws ParseException {
        // 15 minute increments
        ObservableList<String> list = FXCollections.observableArrayList();
        int hours = 12;
        int minutes = 60;
        int increment = 15;

        for(int i=0; i< hours; i++) {
            for(int j=0; j< minutes; j+=increment) {
                String time = i + ":" + j + ":00";
                if(j==0) {
                   time = i + ":00:00";
                }
                list.add(String.valueOf(time));
                if(time.equals("11:45:00")) {
                    list.add("12:00:00");
                }
            }
        }
        appointmentTimes.setItems(list);
        appointmentTimes.setOnAction(e->{System.out.println(appointmentTimes.getSelectionModel().getSelectedItem());});
    }


    public ComboBox<String> getCustomers() throws Exception {
        buildCustomers();
        return customers;
    }

    public ComboBox<String> getCountries() throws Exception {
        buildCountries();
        return countries;
    }

    public ComboBox<String> getCities() throws Exception {
        buildCities();
        return cities;
    }

    public ComboBox<String> getAppointmentTimes() throws ParseException {
        buildAppointmentTimes();
        return appointmentTimes;
    }
}
