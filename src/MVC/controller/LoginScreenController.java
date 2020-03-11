package MVC.controller;

import DAO.AppointmentDao;
import DAO.POJO.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import static utils.Database.QueryUtils.createQuery;
import static utils.Database.QueryUtils.getResult;

public class LoginScreenController {
    Stage stage;
    Parent scene;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private TextField userNameTxt;

    public void goToMainScreen(ActionEvent actionEvent) throws IOException {
        Stage stage;
        Parent scene;
        // return to main menu
        // build stage
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();

        // load add part MVC.view
        scene = FXMLLoader.load(getClass().getResource("/MVC/view/mainScreenView.fxml"));

        // add scene to stage
        stage.setScene(new Scene(scene));

        //show stage
        stage.show();
    }

    // validate user input for login screen
    public static boolean validateInput(String selectRow, String fromTable, String whereCol, String isValue) throws SQLException, ClassNotFoundException {

        String query = "SELECT " + selectRow + " FROM " + fromTable;
        createQuery(query);
        try (ResultSet rs = getResult()) {
            while (rs.next()) {
                if (rs.getString(whereCol).equals(isValue)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
        return false;
    }

    @FXML
    private void onActionLogin(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException, ParseException {
        // validate email and password
        // set userName and password
        User credentials = new User(userNameTxt.getText(), passwordTxt.getText());

        //get username and password
        String userName = credentials.getUserName();
        String password = credentials.getPassword();

        // test add
        AppointmentDao.addAppointment(10, 1, 1, "testAddAppt", "test description", "location", "test", "newtest", "test.com", "NOW()", "NOW()", "test", "NOW()", "test");

        // test update
        AppointmentDao.updateAppointmentWithString("title", "testUpdateAppt", 10);


        // validate userName
        boolean validUserName = validateInput("*", "user", "userName", userName);
        // validate password
        boolean validPassword = validateInput("*", "user", "password", password);

        // if userName is true and password is true then switch to main screen
        if (validUserName && validPassword) {
            // switch to main screen
            goToMainScreen(actionEvent);
        } else {
            System.out.println("Invalid Username or Password.");
        }

        // else notify user invalid credentials
    }


    @FXML
    private void onActionExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    public void initialize() {

    }
}
