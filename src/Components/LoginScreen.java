package Components;

import DbDao.DbAppointmentDao;
import DbDao.DbUserDao;
import POJO.Appointment;
import POJO.User;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import utils.DBUtils;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.*;
import java.util.stream.Stream;


/**
 * RUBRIC A. Create a log-in form that can determine the user’s location and translate log-in and error control
 * messages (e.g., “The username and password did not match.”) into two languages.
 * <p>
 * Use of ResourceBundle and Locale are used in conjunction to set the text of the Labels and Buttons in two languages:
 * English and French
 */
public class LoginScreen {
    // log user
    User user;
    private final static Logger USER_LOG = Logger.getLogger("userActivityLog.txt");
    GridPane mainGridPane;
    Stage primaryStage;
    Label loginErrorLbl = new Label();

    // RUBRIC A - determine user's location (Internationalization)
    Locale locale = Locale.getDefault();
    ResourceBundle rb = ResourceBundle.getBundle("utils/app", locale);


    // constructor
    public LoginScreen(User user, Stage primaryStage) {
        this.user = user;
        this.primaryStage = primaryStage;
    }

    // build login screen
    private void build() {
        mainGridPane = new GridPane();
        // basic set up of mainGridPane
        mainGridPane.getStylesheets().add("CSS/loginStyle.css");
        mainGridPane.getStyleClass().add("loginMainGridPane");
        int numCols = 2;

        // 2 COLUMNS - 30% width
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100.00 / 3);
            column.setHgrow(Priority.SOMETIMES);
            mainGridPane.getColumnConstraints().add(column);
        }

        // main gridPane
        mainGridPane.setId("mainGridPane");
        mainGridPane.setAlignment(Pos.CENTER);
//        mainGridPane.setPrefSize(800, 1200);

        // COL 1
        VBox leftSide = new VBox(25);
        leftSide.setAlignment(Pos.CENTER_RIGHT);
        leftSide.translateXProperty().setValue(-100);

        Label welcomeLbl = new Label(rb.getString("welcome"));
        welcomeLbl.getStyleClass().add("medium");

        Label companyNameLbl = new Label(rb.getString("companyName"));
        companyNameLbl.getStyleClass().add("large");

        Label appNameLbl = new Label(rb.getString("applicationName"));
        appNameLbl.getStyleClass().add("small");

        leftSide.getChildren().addAll(welcomeLbl, companyNameLbl, appNameLbl);
        mainGridPane.add(leftSide, 0, 0);

        // COL 2
        VBox rightSide = new VBox(25);
        rightSide.setAlignment(Pos.CENTER_LEFT);
        rightSide.translateXProperty().set(50);
        rightSide.translateYProperty().set(55);

        Label accountLoginLbl = new Label(rb.getString("login"));
        accountLoginLbl.getStyleClass().add("medium");

        Label userNameLbl = new Label(rb.getString("userName"));
        userNameLbl.getStyleClass().add("small");

        TextField userNameTxtFld = new TextField();
        userNameTxtFld.setText(rb.getString("enterYourUserName"));
        userNameTxtFld.setId("userNameTxtFld");

        Label passwordLbl = new Label(rb.getString("password"));
        passwordLbl.getStyleClass().add("small");

        PasswordField passwordFld = new PasswordField();
        passwordFld.setText(rb.getString("enterYourPassword"));
        passwordFld.setId("passwordTxtFld");

        if (KeyEvent.KEY_PRESSED.getName().equals("VK_ENTER")) {
            System.out.println("ENTER KEY PRESSED");
        }

        HBox btnHBox = new HBox(25);
        Button loginBtn = new Button(rb.getString("login"));
        loginBtn.setOnAction(e -> {
            try {
                validateUser(e, userNameTxtFld.getText(), passwordFld.getText());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        Button exitBtn = new Button(rb.getString("exit"));
        exitBtn.setOnAction(this::onClickExit);
        btnHBox.getChildren().addAll(loginBtn, exitBtn);
        btnHBox.setAlignment(Pos.BASELINE_RIGHT);

        rightSide.getChildren().addAll(accountLoginLbl, userNameLbl, userNameTxtFld, passwordLbl, passwordFld, btnHBox, loginErrorLbl);
        mainGridPane.add(rightSide, 1, 0);

        // if Enter key is pressed then fire the loginBtn action event
        loginBtn.setDefaultButton(true);
        mainGridPane.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                loginBtn.fire();
                ev.consume();
            }
        });
    }

    public Scene getScene() {
        build();
        return new Scene(mainGridPane);
    }


    private void onClickExit(ActionEvent actionEvent) {
        primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.close();
    }

    // validate user input for login screen
    private void validateUser(ActionEvent actionEvent, String userName, String password) throws Exception {
        LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME).setLevel(Level.FINE);
        DbUserDao dbUserDao = new DbUserDao(DBUtils.getMySQLDataSource());
        Optional<User> user = dbUserDao.getByNameAndPassword(userName, password);

        // RUBRIC J.   Provide the ability to track user activity by recording timestamps for user log-ins in
        // a .txt file. Each new record should be appended to the log file, if the file already exists.

        // disable default handlers
        USER_LOG.setUseParentHandlers(false);

        // set up file handler
        FileHandler userLogFH;
        userLogFH = new FileHandler("userActivityLog.txt", true);
        SimpleFormatter sf = new SimpleFormatter();
        userLogFH.setFormatter(sf);
        USER_LOG.addHandler(userLogFH);

        if (user.isPresent()) {
            GridPane mainView = new MainView(user.get()).getView();
            Scene mainViewScene = new Scene(mainView);

            // RUBRIC H.   Write code to provide an alert if there is an appointment within 15 minutes of the user’s log-in.
            Stream<Appointment> appointmentStream = new DbAppointmentDao(DBUtils.getMySQLDataSource()).getAll();
            AtomicBoolean isAppointmentInLessThanFifteen = new AtomicBoolean(false);
           appointmentStream.forEach(a->{
                    if(a.getStart().toLocalDateTime().isAfter(LocalDateTime.now().minusMinutes(15))
                            && a.getStart().toLocalDateTime().isBefore(LocalDateTime.now())
                            && a.getUserId() == user.get().getId()) {
                        isAppointmentInLessThanFifteen.set(true);
                    }
            });

            if(isAppointmentInLessThanFifteen.get()) {
                // RUBRIC A: determine the user’s location and translate log-in and error control messages
                Alert alert = new Alert(Alert.AlertType.INFORMATION, rb.getString("appointmentInFifteen"));
                alert.showAndWait();
            }

            primaryStage.setScene(mainViewScene);

            // log to userlog.txt
            USER_LOG.setLevel(Level.INFO);
            USER_LOG.log(Level.INFO, "SUCCESSFUL LOGIN:\n  UserName: " + userName + " \n  Password: " + password + "\n  Timestamp: " + LocalDateTime.now().toString());
        } else {
            // log to userlog.txt
            USER_LOG.setLevel(Level.INFO);
            USER_LOG.log(Level.INFO, "\n----------------------------------------\n" +
                    "FAILED LOGIN:\n  UserName: " + userName +
                    " \n  Password: " + password + "\n  Timestamp: " + LocalDateTime.now().toString() +
                    "\n----------------------------------------\n"
            );
            // RUBRIC F: Write exception controls to prevent entering an incorrect username and password
            loginErrorLbl.getStyleClass().add("small");
            loginErrorLbl.setText(rb.getString("loginErrorMessage"));
        }
    }
}
