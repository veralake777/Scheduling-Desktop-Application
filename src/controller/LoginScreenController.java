package controller;

import DAO.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import static DAO.UserDao.updateUser;
import static utils.Queries.createQuery;
import static utils.Queries.getResult;

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
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();

        // load add part view
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();

        // load add part view
        scene = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));

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
            }} catch (SQLException e) {
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

        // test update
        updateUser("userName", "test", 1);

        // test add
        UserDao.addUser(2, "test2", "test2", 1, "NOW()", "test", "NOW()", "test");


        // validate userName
        boolean validUserName = validateInput("*", "user", "userName", userName);
        // validate password
        boolean validPassword = validateInput("*", "user", "password", password);

        // if userName is true and password is true then switch to main screen
        if(validUserName && validPassword) {
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


//    Font.loadFont(getClass().getResourceAsStream([fontpath...]), [Some font-size, for example 12]);
}
