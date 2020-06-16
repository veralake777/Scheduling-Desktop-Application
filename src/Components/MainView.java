package Components;

import Components.Calendar.CalendarView;
import POJO.User;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;

public class MainView {
    User user;
    GridPane gridPane;

    public MainView(User user) {
        this.user = user;
    }

    private void build() throws Exception {
        // screen size
        gridPane = new GridPane();

        // row constraints
        RowConstraints row1 = new RowConstraints();
        row1.setFillHeight(true);
        RowConstraints row2 = new RowConstraints();
        row2.setFillHeight(true);
        gridPane.getRowConstraints().addAll(row1, row2);

        // column restraints
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(35);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(65);
        gridPane.getColumnConstraints().addAll(col1);

        HBox mainMenu = new MainMenu(user, gridPane).getView();
        GridPane calendarView = new CalendarView(user).getView();
        gridPane.add(mainMenu, 0, 0, 2, 1);
        gridPane.add(calendarView, 0, 1);
    }

    public GridPane getView() throws Exception {
        build();
        return gridPane;
    }
}
