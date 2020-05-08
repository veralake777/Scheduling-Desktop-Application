package iluwatar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

class TimeComboBox extends ComboBox<String> {
    private ObservableList<String> times = FXCollections.observableArrayList();

    public TimeComboBox() {
        buildTimes();
        TimeComboBox.super.setItems(times);
    }

    private void buildTimes() {
        int hours = 12;
        int minutes = 60;
        for(int i=0; i<hours; i++) {
            // increment by 15 minutes
            for(int j=0; j<minutes; j += 15) {
                times.add(i + ":" + j + ":00");
            }
        }
    }
}
