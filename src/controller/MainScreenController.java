package controller;

import DAO.CustomerDao;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Customer;

public class MainScreenController {

    @FXML
    private Label userId;
    @FXML
    private Label userName;

    public MainScreenController() throws Exception {
    }

    @FXML
    public void initialize() throws Exception {
        Customer testCustomer = CustomerDao.getCustomer(1);
        userId.setText(String.valueOf(testCustomer.getCustomerName()));
        userName.setText(testCustomer.getCustomerName());
    }
}
