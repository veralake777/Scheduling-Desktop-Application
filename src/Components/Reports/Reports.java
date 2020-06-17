package Components.Reports;

import Components.Calendar.Week;
import Components.ComboBoxes;
import POJO.User;
import com.mysql.jdbc.PreparedStatement;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import utils.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class Reports {
    /**
     * I.   Provide the ability to generate each  of the following reports:
     * <p>
     * •   number of appointment types by month
     * <p>
     * •   the schedule for each consultant
     * <p>
     * •   one additional report of your choice
     **/
    User user;

    public Reports(User user) {
        this.user = user;
    }

    // number of appointment types by month
    private GridPane numberOfAppointmentTypesPerMonth() throws Exception {
        int typeCount = 0;
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        gridPane.maxHeight(Double.MAX_VALUE);
        gridPane.maxWidth(Double.MAX_VALUE);
        gridPane.setStyle("-fx-background-color: WHITE;");


        // get connection
        var connection = DBUtils.getMySQLDataSource().getConnection();

        // get distinct type count and generate row constraints
        PreparedStatement typeCountStatement = (PreparedStatement) connection.prepareStatement("SELECT COUNT(DISTINCT type) FROM appointment");
        ResultSet typeCountRS = typeCountStatement.executeQuery();
        if (typeCountRS.next()) {
            typeCount = typeCountRS.getInt(1);
        }

        // build row constraints
        for (int i = 1; i <= typeCount + 2; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setMinHeight(40);
            rowConst.setFillHeight(true);
            gridPane.getRowConstraints().add(rowConst);
        }

        PreparedStatement typesStatement = (PreparedStatement) connection.prepareStatement("SELECT DISTINCT type FROM appointment");
        ResultSet typesRS = typesStatement.executeQuery();
        String[] types = new String[typeCount];
        int i = 0;
        while (typesRS.next()) {
            String type = typesRS.getString(1);
            types[i] = type;
            i++;
        }

        // add types to col 0, row i + 2 of gridPane
        for (i = 0; i < types.length; i++) {
            Label label = new Label(types[i]);
            label.setFont(Font.font("Roboto Bold", FontWeight.BOLD, 14));
            GridPane.setHalignment(label, HPos.CENTER);
            gridPane.add(label, 0, i + 2);
        }

        // generate column constraints
        int numCols = 13;
        for (i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            if(i==0) {
                colConst.setMinWidth(100);
            } else {
                colConst.setPercentWidth(100.0 / numCols - 1);
            }
            gridPane.getColumnConstraints().add(colConst);
        }

        Label januaryReport = new Label("JAN");
        januaryReport.setFont(Font.font("Roboto Bold", FontWeight.BOLD, 14));
        gridPane.add(januaryReport, 1, 1);
        GridPane.setHalignment(januaryReport, HPos.CENTER);

        Label februaryReport = new Label("FEB");
        februaryReport.setFont(Font.font("Roboto Bold", FontWeight.BOLD, 14));
        gridPane.add(februaryReport, 2, 1);
        GridPane.setHalignment(februaryReport, HPos.CENTER);

        Label marchReport = new Label("MAR");
        marchReport.setFont(Font.font("Roboto Bold", FontWeight.BOLD, 14));
        gridPane.add(marchReport, 3, 1);
        GridPane.setHalignment(marchReport, HPos.CENTER);

        Label aprilReport = new Label("APR");
        aprilReport.setFont(Font.font("Roboto Bold", FontWeight.BOLD, 14));
        gridPane.add(aprilReport, 4, 1);
        GridPane.setHalignment(aprilReport, HPos.CENTER);

        Label mayReport = new Label("MAY");
        mayReport.setFont(Font.font("Roboto Bold", FontWeight.BOLD, 14));
        gridPane.add(mayReport, 5, 1);
        GridPane.setHalignment(mayReport, HPos.CENTER);

        Label juneReport = new Label("JUN");
        juneReport.setFont(Font.font("Roboto Bold", FontWeight.BOLD, 14));
        gridPane.add(juneReport, 6, 1);
        GridPane.setHalignment(juneReport, HPos.CENTER);

        Label julyReport = new Label("JUL");
        julyReport.setFont(Font.font("Roboto Bold", FontWeight.BOLD, 14));
        gridPane.add(julyReport, 7, 1);
        GridPane.setHalignment(julyReport, HPos.CENTER);

        Label augustReport = new Label("AUG");
        augustReport.setFont(Font.font("Roboto Bold", FontWeight.BOLD, 14));
        gridPane.add(augustReport, 8, 1);
        GridPane.setHalignment(augustReport, HPos.CENTER);

        Label septemberReport = new Label("SEP");
        septemberReport.setFont(Font.font("Roboto Bold", FontWeight.BOLD, 14));
        gridPane.add(septemberReport, 9, 1);
        GridPane.setHalignment(septemberReport, HPos.CENTER);

        Label octoberReport = new Label("OCT");
        octoberReport.setFont(Font.font("Roboto Bold", FontWeight.BOLD, 14));
        gridPane.add(octoberReport, 10, 1);
        GridPane.setHalignment(octoberReport, HPos.CENTER);

        Label novemberReport = new Label("NOV");
        novemberReport.setFont(Font.font("Roboto Bold", FontWeight.BOLD, 14));
        gridPane.add(novemberReport, 11, 1);
        GridPane.setHalignment(novemberReport, HPos.CENTER);

        Label decemberReport = new Label("DEC");
        decemberReport.setFont(Font.font("Roboto Bold", FontWeight.BOLD, 14));
        gridPane.add(decemberReport, 12, 1);
        GridPane.setHalignment(decemberReport, HPos.CENTER);


        PreparedStatement statement = (PreparedStatement) connection.prepareStatement("SELECT MONTH(start), type, COUNT(*) as count " +
                "FROM appointment GROUP BY MONTH(start), type ORDER BY MONTH(start)");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            // row index in gridpane matches month value
            int colIndex = 0;
            switch (resultSet.getInt(1)) {
                case 1:
                    colIndex = 1;
                    // january label
                    addCountRow(gridPane, types, resultSet, colIndex);
                    break;
                case 2:
                    colIndex = 2;
                    // code block
                    addCountRow(gridPane, types, resultSet, colIndex);
                    break;
                case 3:
                    colIndex = 3;
                    // code block
                    addCountRow(gridPane, types, resultSet, colIndex);
                    break;
                case 4:
                    colIndex = 4;
                    // code block
                    addCountRow(gridPane, types, resultSet, colIndex);
                    break;
                case 5:
                    colIndex = 5;
                    // code block
                    addCountRow(gridPane, types, resultSet, colIndex);
                    break;
                case 6:
                    colIndex = 6;
                    // code block
                    addCountRow(gridPane, types, resultSet, colIndex);
                    break;
                case 7:
                    colIndex = 7;
                    // code block
                    addCountRow(gridPane, types, resultSet, colIndex);
                    break;
                case 8:
                    colIndex = 8;
                    // code block
                    addCountRow(gridPane, types, resultSet, colIndex);
                    break;
                case 9:
                    colIndex = 9;
                    // code block
                    addCountRow(gridPane, types, resultSet, colIndex);
                    break;
                case 10:
                    colIndex = 10;
                    // code block
                    addCountRow(gridPane, types, resultSet, colIndex);
                    break;
                case 11:
                    colIndex = 11;
                    // code block
                    addCountRow(gridPane, types, resultSet, colIndex);
                    break;
                case 12:
                    colIndex = 12;
                    // code block
                    addCountRow(gridPane, types, resultSet, colIndex);
                    break;
                default:
                    return null;
            }
        }
        Label title = new Label("Appointment Type Count for ALL users");
        title.setStyle("-fx-background-color: radial-gradient(radius 75%, #ebaa5d, #38909b);" +
                "-fx-padding:10 150;");
        title.setFont(Font.font("Roboto Bold", FontWeight.BOLD, 14));
        gridPane.add(title, 0, 0, gridPane.getColumnCount(), 1);
        GridPane.setHalignment(title, HPos.CENTER);
        return gridPane;
    }

    private void addCountRow(GridPane gridPane, String[] types, ResultSet resultSet, int colIndex) throws SQLException {
        for (int rowIndex = 0; rowIndex < types.length; rowIndex++) {
            if (resultSet.getString(2).equals(types[rowIndex])) {
                Label label = new Label(String.valueOf(resultSet.getInt(3)));
                label.setFont(Font.font("Roboto Light", FontWeight.MEDIUM, 12));
                GridPane.setHalignment(label, HPos.CENTER);
                gridPane.add(label, colIndex, rowIndex + 2);
            }
        }
    }

    // the schedule for each consultant
    private GridPane consultantSchedule() throws Exception {
        Week week = new Week(user);
        LocalDate monday = LocalDate.now().with(DayOfWeek.MONDAY);
        // 2 col gridpane
        GridPane gridPane = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(40);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(60);
        gridPane.getColumnConstraints().addAll(col1, col2);

        // appointment table (left)

        HBox hBox = new HBox(2);
        Label title = new Label("'s Current Schedule");
        ComboBox<User> consultants = new ComboBoxes().getConsultants();
        consultants.getSelectionModel().select(user);
        consultants.setMinHeight(30);
        consultants.setStyle("-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, white;" +
                "    -fx-background-insets: 0 0 -1 0, 0, 1, 2;" +
                "    -fx-background-radius: 3px, 3px, 2px, 1px;" +
                "-fx-font-size: 20;");
        consultants.setEditable(true);
        consultants.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
                    user = consultants.getSelectionModel().getSelectedItem();
                    try {
                        week.getView(user, monday);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
        title.setFont(Font.font("Roboto Bold", FontWeight.BOLD, 30));
        hBox.getChildren().addAll(consultants, title);

        gridPane.add(hBox, 1, 0);
        gridPane.add(week.getView(monday), 1, 1);
        return gridPane;
    }


    // one additional report of your choice
    private AnchorPane numberOfCustomersPerCountry() {
        Label title = new Label("productivity");
        AnchorPane anchorPane = new AnchorPane(title);
        anchorPane.setMinHeight(300);
        anchorPane.setStyle("-fx-background-color: WHITE;");
        return anchorPane;
    }

    public GridPane getView() throws Exception {
        GridPane gridPane = new GridPane();
        ColumnConstraints columnConstraints1 = new ColumnConstraints();
        columnConstraints1.setPercentWidth(40);
        ColumnConstraints columnConstraints2 = new ColumnConstraints();
        columnConstraints1.setPercentWidth(60);
        gridPane.getColumnConstraints().addAll(columnConstraints2, columnConstraints1);

        // Reports (left)
        Label reportTitle = new Label("REPORTS");
        reportTitle.setFont(Font.font("Roboto Bold", FontWeight.BOLD, 30));
        gridPane.add(reportTitle, 0, 0);
        GridPane numberOfAppointmentTypesPerMonth = numberOfAppointmentTypesPerMonth();
        assert numberOfAppointmentTypesPerMonth != null;
        gridPane.add(new ScrollPane(numberOfAppointmentTypesPerMonth), 0, 1);
        gridPane.add(numberOfCustomersPerCountry(), 0, 2);


        // consultant schedule with combobox (right)
        GridPane consultantSchedule = consultantSchedule();
        consultantSchedule.getColumnConstraints().clear();
        consultantSchedule.setPadding(new Insets(0, 0, 0, 10));
        gridPane.add(consultantSchedule, 1, 1, 1, 2);

        return gridPane;
    }
}
