package MVC.controller;

import DAO.CustomerDao;
import DAO.POJO.Customer;
import MVC.view.CustomerTableView;
import MVC.view.MenuVBox;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.sql.SQLException;
import java.text.ParseException;

public class CustomerViewController {
    // style GridPane
    public GridPane gridPane;
    private void buildGridPane() throws ParseException, SQLException, ClassNotFoundException {
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(10);
        col1.setHgrow(Priority.ALWAYS);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        col2.setPercentWidth(37);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setHgrow(Priority.ALWAYS);
        col3.setPercentWidth(53);
        gridPane.getColumnConstraints().setAll(col1, col2, col3);

        // col1
        gridPane.getChildren().set(0, new MenuVBox().getMenu());
        gridPane.getChildren().get(0).setStyle("" +
                "-fx-background-color: WHITE;" +
                "-fx-padding: 20 10 10 20"
        );

        // col2
        buildCustomerTableView();

        // col23
        onLoadDynamicView();
        gridPane.getChildren().get(2).setStyle("-fx-background-color: WHITE;");
    }

    // Customer Table View
    public TableView<Customer> customerTableView;
    private void buildCustomerTableView() throws ParseException, SQLException, ClassNotFoundException {
        customerTableView.getStylesheets().add("tableView.css");
        try {
            customerTableView.setItems(CustomerDao.getAllCustomers());
        } catch (ClassNotFoundException | SQLException | ParseException e) {
            e.printStackTrace();
        }
        CustomerTableView.getCustomerTableView(customerTableView);
    }

    // Dynamic CRUD view for Customers
        // switches on action - view, add, update, delete
    public BorderPane dynamicView;

    private void onLoadDynamicView() {
        dynamicView.setCenter(new Label("Select a Customer"));
    }

    // add customer view
    private BorderPane addCustomer() {
        return dynamicView;
    }

    // view selected customer
    private BorderPane viewSelectedCustomer() {
        return dynamicView;
    }

    // update customer view
    private BorderPane updateCustomer() {
        return dynamicView;
    }

    private BorderPane deleteCustomer() {
        return dynamicView;
    }

    private BorderPane updateCRUDView() {
        return dynamicView;
    }

    public CustomerViewController() throws ParseException, SQLException, ClassNotFoundException {
    }

    @FXML
    public void initialize() throws Exception {
        buildGridPane();
    }
}
