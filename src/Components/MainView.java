package Components;

import Components.Calendar.CalendarView;
import Components.Customer.CustomersTable;
import POJO.User;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Screen;

public class MainView {
    Main main;
    GridPane gridPane;

    public MainView(Main main) {
        this.main = main;
    }

    private void build() throws Exception {
        Rectangle2D screenSize = Screen.getPrimary().getBounds();
        gridPane = new GridPane();
        RowConstraints row1 = new RowConstraints();
        row1.setFillHeight(true);
        gridPane.getRowConstraints().add(row1);
        gridPane.setMinHeight(screenSize.getHeight());
        gridPane.add(new MainMenu(main, gridPane).getView(), 0, 0);
        gridPane.add(new CalendarView(main).getView(), 0, 1);
    }
    public GridPane getView() throws Exception {
        build();
        return gridPane;
    }
}
