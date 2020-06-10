package Components;

import POJO.User;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    /**
     * @LoginScreen
     * pass user data as new User() from Components/LoginScreen to be used globally throughout the application
     */
    private User user;
    private LoginScreen loginScreen;
    @Override
    public void start(Stage primaryStage) throws Exception{
        loginScreen = new LoginScreen(primaryStage);
        // maximized screen
        primaryStage.setMaximized(true);

        // disable can resize screen - this application does not include CSS for different view sizes
        primaryStage.setResizable(false);
//        gridPane.add(new CustomersTableView().getView(),0, 0);
        primaryStage.setTitle("AcmeCO Scheduling Application");
        primaryStage.setScene(loginScreen.getScene());
        primaryStage.show();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) { this.user = user; }

    public static void main(String[] args) {
        launch(args);
    }
}
