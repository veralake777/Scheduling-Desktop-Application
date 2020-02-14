package controller;

import DAO.CityDao;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.City;

public class MainScreenController {

    @FXML
    private Label userId;
    @FXML
    private Label userName;

    public MainScreenController() throws Exception {
    }

    @FXML
    public void initialize() throws Exception {
        City testCity = CityDao.getCity("testCity");
        userId.setText(String.valueOf(testCity.getCity()));
        userName.setText(String.valueOf(testCity.getCityId()));
    }
}
