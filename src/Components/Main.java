package Components;

import POJO.User;
import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
    /**
     * @LoginScreen pass user data as new User() from Components/LoginScreen to be used globally throughout the application
     */
    private User user;
    private LoginScreen loginScreen;

    @Override
    public void start(Stage primaryStage) throws Exception {
        loginScreen = new LoginScreen(user, primaryStage);
        // maximized screen
        primaryStage.setWidth(Screen.getPrimary().getBounds().getWidth());
        primaryStage.setHeight(Screen.getPrimary().getBounds().getHeight());
        primaryStage.setTitle("AcmeCO Scheduling Application");
        primaryStage.setScene(loginScreen.getScene());
        primaryStage.show();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
