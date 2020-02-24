package MVC.controller;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

public class WeeklyViewController {
    public class TableRow {
        private ObservableValue<String> city;
        private ObservableValue<String> country;
        private ObservableValue<String> start;
        private ObservableValue<String> detail;

        public TableRow(ObservableValue<String> city, ObservableValue<String> country, ObservableValue<String> start,ObservableValue<String> detail) {
            this.city = city;
            this.country = country;
            this.start = start;
            this.detail = detail;
        }

        public ObservableValue<String> getCity() {
            return city;
        }

        public void setCity(ObservableValue<String> city) {
            this.city = city;
        }

        public ObservableValue<String> getCountry() {
            return country;
        }

        public void setCountry(ObservableValue<String> country) {
            this.country = country;
        }

        public ObservableValue<String> getDetail() {
            return detail;
        }

        public void setDetail(ObservableValue<String> detail) {
            this.detail = detail;
        }

        public ObservableValue<String> getStart() {
            return start;
        }

        public void setStart(ObservableValue<String> start) {
            this.start = start;
        }
    }

    @FXML
    public TableView<TableRow> tableView;

    private TableColumn<TableRow, String> colCity;

    private TableColumn<TableRow, String> colCountry;

    private TableColumn<TableRow, String> colStart;

    private Label[] dayLabels = new Label[7];

    @FXML
    public void initialize() throws Exception {
        createAndConfigureWeek();
    }


    private void createAndConfigureWeek() throws ParseException, SQLException, ClassNotFoundException {
//        tableView.setItems(AppointmentDao.getAllAppointments());

    }

    private void showDaysOfWeek() {
        DateFormatSymbols dfs = new DateFormatSymbols(Locale.getDefault());
        String[] dayNames = dfs.getWeekdays();

        // jlblDay[0], jlblDay[1], ..., jlblDay[6] for day names
        for (int i = 0; i < 7; i++) {
//            dayLabels[i].setTextAlignment(TextAlignment.CENTER);
            dayLabels[i].setText(dayNames[i + 1]);
        }
    }
}
