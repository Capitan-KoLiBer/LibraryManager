package sample.Authentication;

import com.jfoenix.controls.*;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Panel.Panel;
import sample.Utils.Utils;

public class Login {


    public StackPane root_pane;
    private int stage_width = 400;
    private int stage_height = 300;


    private Stage stage;
    private Login login;
    private Register register;
    private Panel panel;
    private boolean isEnglish = Utils.Language.getLanguage() == 1;

    public void initVars(Stage stage , Login login , Register register , Panel panel){
        this.stage = stage;
        this.login = login;
        this.register = register;
        this.panel = panel;
    }

    public void showStage(){
        root_pane = new StackPane();
        root_pane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root_pane);
        scene.setFill(Color.WHITE);
        stage.setResizable(false);
        stage.setWidth(stage_width);
        stage.setHeight(stage_height);
        stage.setTitle(isEnglish ? "Login" : "ورود");
        initView();
        stage.setScene(scene);
        stage.show();
    }

    private void initView(){
        BorderPane border_pane = new BorderPane();
        border_pane.setStyle("-fx-background-image: url('background3.png') ; -fx-background-size: cover ; -fx-background-position: center ; -fx-padding: 20px;");
        ImageView login_top_imageview = new ImageView("book0.png");
        login_top_imageview.setStyle("-fx-effect: dropshadow(gaussian, black, 180, 0.2,0,0)");
        login_top_imageview.setFitWidth(70);
        login_top_imageview.setFitHeight(70);
        border_pane.setTop(login_top_imageview);
        border_pane.setAlignment(login_top_imageview,Pos.CENTER);
        GridPane grid_pane = new GridPane();
        if (!isEnglish){
            grid_pane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        }
        grid_pane.setMinWidth(stage_width);
        grid_pane.setPadding(new Insets(10));
        grid_pane.setVgap(10);
        grid_pane.setHgap(10);
        grid_pane.setAlignment(Pos.TOP_CENTER);
        Label login_username_label = new Label(isEnglish ? "Username : " : "نام کاربری :‌ ");
        login_username_label.setStyle("-fx-font-weight: bold");
        JFXTextField login_username_textfield = new JFXTextField();
        login_username_textfield.setStyle("-fx-font-weight: bold");
        grid_pane.addRow(1,login_username_label,login_username_textfield);
        Label login_password_label = new Label(isEnglish ? "Password : " : "رمز عبور : ");
        login_password_label.setStyle("-fx-font-weight: bold");
        JFXPasswordField login_password_passwordfield = new JFXPasswordField();
        login_password_passwordfield.setStyle("-fx-font-weight: bold");
        grid_pane.addRow(2,login_password_label,login_password_passwordfield);
        JFXButton login_button = new JFXButton(isEnglish ? "Login" : "ورود");
        JFXButton register_button = new JFXButton(isEnglish ? "Register" : "ثبت نام");
        login_button.setStyle("-fx-font-weight: bold;-fx-background-color: #5c6bc0;-fx-text-fill: white");
        register_button.setStyle("-fx-font-weight: bold;-fx-background-color: #26a69a;-fx-text-fill: white");
        login_button.setOnAction(value ->  {
            checkLogin(login_username_textfield.getText(),login_password_passwordfield.getText());
        });
        register_button.setOnAction(value -> {
            registerUser();
        });
        login_button.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.BLACK,45,0,1,1));
        register_button.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.BLACK,45,0,0,1));
        grid_pane.addRow(3,register_button,login_button);
        border_pane.setCenter(grid_pane);
        root_pane.getChildren().add(border_pane);
    }

    private void checkLogin(String username,String password){
        String[] user_data = Utils.Auth.getUser(username);
        if(user_data != null){
            if(user_data[1].equals(password)){
                try {
                    panel.showStage(username,password,user_data[1]);
                } catch (Exception e) {}
            }else{
                Utils.Dialogs.showDialog(isEnglish ? "Error" : "ارور",isEnglish ? "Password is incorrect !" : "رمز عبور اشتباه است !",root_pane);
            }
        }else{
            Utils.Dialogs.showDialog(isEnglish ? "Error" : "ارور",isEnglish ? "Username Not Found !" : "نام کاربری یافت نشد !",root_pane);
        }
    }

    private void registerUser(){
        register.showStage();
    }

}
