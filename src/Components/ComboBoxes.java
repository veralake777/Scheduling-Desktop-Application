package Components;

import DbDao.DbCityDao;
import DbDao.DbCountryDao;
import DbDao.DbCustomerDao;
import POJO.City;
import POJO.Country;
import POJO.Customer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.util.StringConverter;
import utils.DBUtils;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class ComboBoxes extends ComboBox {
    private ComboBox<Customer> customers = new ComboBox<>();
    private ComboBox<String> countries = new ComboBox<>();
    private ComboBox<String> cities = new ComboBox<>();
    private ComboBox<LocalTime> appointmentTimes = new ComboBox<>();

    // for auto complete
    String filter = "";

    public ComboBoxes() {
    }

    private void customers() throws Exception {
        ObservableList<Customer> list = FXCollections.observableArrayList();
        Stream<Customer> customerStream = new DbCustomerDao(DBUtils.getMySQLDataSource()).getAll();
        customerStream.forEach(list::add);
        customers.setEditable(false);
//        // Create a FilteredList wrapping the ObservableList.
//        FilteredList<String> filteredItems = new FilteredList<>(list, p -> true);
//
//        // Add a listener to the textProperty of the combobox editor. The
//        // listener will simply filter the list every time the input is changed
//        // as long as the user hasn't selected an item in the list.
//        customers.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
//            final TextField editor = customers.getEditor();
//            final String selected = customers.getSelectionModel().getSelectedItem();
//
//            // This needs run on the GUI thread to avoid the error described
//            // here: https://bugs.openjdk.java.net/browse/JDK-8081700.
//            Platform.runLater(() -> {
//                // If the no item in the list is selected or the selected item
//                // isn't equal to the current input, we refilter the list.
//                if (selected == null || !selected.equals(editor.getText())) {
//                    filteredItems.setPredicate(item -> {
//                        // We return true for any items that starts with the
//                        // same letters as the input. We use toUpperCase to
//                        // avoid case sensitivity.
//                        if (item.toUpperCase().startsWith(newValue.toUpperCase())) {
//                            return true;
//                        } else {
//                            return false;
//                        }
//                    });
//                }
//            });
//        });
        customers.setItems(list);
        customers.setOnAction(e-> customers.getSelectionModel().getSelectedItem());
    }

    private void countries() throws Exception {
        ObservableList<String> list = FXCollections.observableArrayList();
        Stream<Country> countryStream = new DbCountryDao((DBUtils.getMySQLDataSource())).getAll();
        countryStream.forEach(c->{
            list.add(c.getCountry());
        });
        countries.setItems(list);
        countries.setOnAction(e->{System.out.println(countries.getSelectionModel().getSelectedItem());});
    }

    private void cities() throws Exception {
        ObservableList<String> list = FXCollections.observableArrayList();
        Stream<City> cityStream = new DbCityDao(DBUtils.getMySQLDataSource()).getAll();
        cityStream.forEach(c-> {
            list.add(c.getCity());
        });
        cities.setItems(list);
        cities.setOnAction(e->{System.out.println(cities.getSelectionModel().getSelectedItem());});
    }

    private void fifteenMinuteAppointmentSlots() throws ParseException {
        // 15 minute increments
        ObservableList<LocalTime> list = FXCollections.observableArrayList();
        int hours = 12;
        int minutes = 60;
        int increment = 15;

        for(int i=6; i< hours; i++) {
            for(int j=0; j< minutes; j+=increment) {
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

    public ComboBox<String> getCountries() throws Exception {
        countries();
        return countries;
    }

    public ComboBox<String> getCities() throws Exception {
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
}
