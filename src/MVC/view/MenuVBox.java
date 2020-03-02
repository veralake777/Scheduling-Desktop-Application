package MVC.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MenuVBox extends VBox {
    VBox menu = new VBox();

    Button home = new Button("HOME");
    Button appointments = new Button("APPOINTMENTS");
    Button customers = new Button("CUSTOMERS");

    public MenuVBox() {
        setMenu();
    }

    private void setMenu() {
        VBox.setMargin(home, new Insets(5, 0, 5, 0));
        VBox.setMargin(appointments, new Insets(5, 0, 5, 0));
        VBox.setMargin(customers, new Insets(5, 0, 5, 0));
        VBox.setVgrow(home, Priority.ALWAYS);
        menu.getChildren().addAll(home, appointments, customers);
    }

    public VBox getMenu() {
        return menu;
    }
}
