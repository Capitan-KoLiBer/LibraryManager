package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.Authentication.Login;
import sample.Authentication.Register;
import sample.Panel.Panel;
import sample.Utils.Utils;

public class Main extends Application{

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Utils.Initializator.initFileSystem();
        Login login = new Login();
        Register register = new Register();
        Panel panel = new Panel();
        login.initVars(stage,login,register,panel);
        register.initVars(stage,login,register,panel);
        panel.initVars(stage,login,register,panel);
        login.showStage();
    }
}
