package controller;

import DAO.UserDao;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.User;

public class MainScreenController {

    @FXML
    private Label userId;
    @FXML
    private Label userName;

    public MainScreenController() throws Exception {
    }

    @FXML
    public void initialize() throws Exception {
        User testUser = UserDao.getUser("test");
        userId.setText(String.valueOf(testUser.getUserId()));
        userName.setText(testUser.getUserName());
    }
}
