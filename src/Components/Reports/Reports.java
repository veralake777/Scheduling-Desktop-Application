package Components.Reports;

import Components.Appointments.AppointmentsTable;
import Components.Calendar.Week;
import POJO.User;
import com.mysql.jdbc.PreparedStatement;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
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
    public GridPane numberOfAppointmentTypesPerMonth() throws Exception {
        int typeCount = 0;
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        gridPane.maxHeight(Double.MAX_VALUE);
        gridPane.maxWidth(Double.MAX_VALUE);


        // get connection
        var connection = DBUtils.getMySQLDataSource().getConnection();

        // get distinct type count and generate row constraints
        PreparedStatement typeCountStatement = (PreparedStatement) connection.prepareStatement("SELECT COUNT(DISTINCT type) FROM appointment");
        ResultSet typeCountRS = typeCountStatement.executeQuery();
        if (typeCountRS.next()) {
            typeCount = typeCountRS.getInt(1);
        }

        for (int i = 1; i <= typeCount + 1; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setMinHeight(75);
            rowConst.setFillHeight(true);
            gridPane.getRowConstraints().add(rowConst);
        }

        PreparedStatement typesStatement = (PreparedStatement) connection.prepareStatement("SELECT type FROM appointment");
        ResultSet typesRS = typesStatement.executeQuery();
        String[] types = new String[typeCount];
        int i = -1;
        while (typesRS.next()) {
            i++;
            String type = typesRS.getString(1);
            types[i] = type;
        }

        // add types to col 0, row i of gridPane
        for (i = 0; i < types.length; i++) {
            Label label = new Label(types[i]);
            GridPane.setHalignment(label, HPos.CENTER);
            gridPane.add(label, 0, i + 1);
        }

        // generate column constraints
        int numCols = 13;
        for (i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / numCols);
            gridPane.getColumnConstraints().add(colConst);
        }

        Label januaryReport = new Label("January");
        gridPane.add(januaryReport, 1, 0);
        GridPane.setHalignment(januaryReport, HPos.CENTER);

        Label februaryReport = new Label("February");
        gridPane.add(februaryReport, 2, 0);
        GridPane.setHalignment(februaryReport, HPos.CENTER);

        Label marchReport = new Label("March");
        gridPane.add(marchReport, 3, 0);
        GridPane.setHalignment(marchReport, HPos.CENTER);

        Label aprilReport = new Label("April");
        gridPane.add(aprilReport, 4, 0);
        GridPane.setHalignment(aprilReport, HPos.CENTER);

        Label mayReport = new Label("May");
        gridPane.add(mayReport, 5, 0);
        GridPane.setHalignment(mayReport, HPos.CENTER);

        Label juneReport = new Label("June");
        gridPane.add(juneReport, 6, 0);
        GridPane.setHalignment(juneReport, HPos.CENTER);

        Label julyReport = new Label("July");
        gridPane.add(julyReport, 7, 0);
        GridPane.setHalignment(julyReport, HPos.CENTER);

        Label augustReport = new Label("August");
        gridPane.add(augustReport, 8, 0);
        GridPane.setHalignment(augustReport, HPos.CENTER);

        Label septemberReport = new Label("September");
        gridPane.add(septemberReport, 9, 0);
        GridPane.setHalignment(septemberReport, HPos.CENTER);

        Label octoberReport = new Label("October");
        gridPane.add(octoberReport, 10, 0);
        GridPane.setHalignment(octoberReport, HPos.CENTER);

        Label novemberReport = new Label("November");
        gridPane.add(novemberReport, 11, 0);
        GridPane.setHalignment(novemberReport, HPos.CENTER);

        Label decemberReport = new Label("December");
        gridPane.add(decemberReport, 12, 0);
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

        return gridPane;
    }

    private void addCountRow(GridPane gridPane, String[] types, ResultSet resultSet, int colIndex) throws SQLException {
        for (int rowIndex = 0; rowIndex < types.length; rowIndex++) {
            if (resultSet.getString(2).equals(types[rowIndex])) {
                Label label = new Label(String.valueOf(resultSet.getInt(3)));
                GridPane.setHalignment(label, HPos.CENTER);
                gridPane.add(label, colIndex, rowIndex + 1);
            }
        }
    }

    // the schedule for each consultant
    public GridPane consultantSchedule() throws Exception {
        LocalDate monday = LocalDate.now().with(DayOfWeek.MONDAY);
        // 2 col gridpane
        GridPane gridPane = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(40);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(60);
        gridPane.getColumnConstraints().addAll(col1, col2);

        // appointment table (left)
        Label title = new Label(user.getUserName() + "'s Current Schedule");
        title.setFont(Font.font("Roboto Bold", FontWeight.BOLD, 30));
        title.setPadding(new Insets(20, 0, 20, 0));
        GridPane.setHalignment(title, HPos.CENTER);

        gridPane.add(title, 0, 0, 2, 1);
        gridPane.add(new AppointmentsTable(user).getAppointmentTableView(), 0, 1);
        gridPane.add(new Week(user).getView(LocalDate.now().with(DayOfWeek.MONDAY)), 1, 1);
        return gridPane;
    }


    // one additional report of your choice
    public void numberOfCustomersPerCountry() {

    }
}
