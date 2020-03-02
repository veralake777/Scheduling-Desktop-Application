package MVC.view;

import DAO.CustomerDao;
import DAO.POJO.Customer;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import utils.Database.DBUtils;
import utils.Database.QueryUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class CustomerTableView {

    public static TableView<Customer> getCustomerTableView(TableView<Customer> customerTableView) throws ParseException, SQLException, ClassNotFoundException {

        TableColumn<Customer, Integer> customerId = new TableColumn<>("ID");
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        TableColumn<Customer, String> customerName = new TableColumn<>("Name");
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        TableColumn<Customer, String> address = new TableColumn<>("Address");
        address.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Customer, String> city = new TableColumn<>("City");
        city.setCellValueFactory(new PropertyValueFactory<>("city"));

        TableColumn<Customer, String> country = new TableColumn<>("Country");
        country.setCellValueFactory(new PropertyValueFactory<>("country"));

        // CRUD buttons
        TableColumn deleteBtnCol = new TableColumn();
        String style = "-fx-table-cell-border-color: transparent; -fx-background-color;";
        // TODO get button to delete from DB too
        // TODO not a todo but a note - lambdas are here
        deleteBtnCol.setCellFactory(ActionButtonTableCell.forTableColumn("Delete", (Customer c) -> {
            customerTableView.getItems().remove(c);
            return c;
        }));

        TableColumn updateBtnCol = new TableColumn();
        updateBtnCol.setStyle("-fx-table-cell-border-color: transparent;");
        updateBtnCol.setCellFactory(ActionButtonTableCell.forTableColumn("Update", (Customer c) -> {
            // TODO update dynamicView with selected customer data
            return c;
        }));

        TableColumn detailsBtnCol = new TableColumn();
        detailsBtnCol.setStyle("-fx-table-cell-border-color: transparent;");
        detailsBtnCol.setCellFactory(ActionButtonTableCell.<Customer>forTableColumn("Details", (Customer c) -> {
            // TODO update dynamicView with selected customer data
            return c;
        }));



        customerTableView.setEditable(true);
        customerTableView.setItems(CustomerDao.getAllCustomers());

        customerTableView.getColumns().addAll(customerId, customerName, address, city, country, deleteBtnCol, updateBtnCol, detailsBtnCol);

        return customerTableView;
    }

    private static ResultSet getResultSet(String query) throws ClassNotFoundException {
        DBUtils.startConnection();
        QueryUtils.createQuery(query);
        return QueryUtils.getResult();
    };
}
