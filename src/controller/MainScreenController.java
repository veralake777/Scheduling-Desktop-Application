package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.User;

import static DAO.UserDao.getRow;

public class MainScreenController {

    @FXML
    private Label userId;
    @FXML
    private Label userName;

    public MainScreenController() throws Exception {
    }

    @FXML
    public void initialize() throws Exception {
        User testUser = getRow("testtest");
        userId.setText(String.valueOf(testUser.getUserId()));
        userName.setText(testUser.getUserName());
    }
}
