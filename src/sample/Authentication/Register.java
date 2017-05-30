package sample.Authentication;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Panel.Panel;
import sample.Utils.Utils;

public class Register{

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
        stage.setTitle(isEnglish ? "Register" : "ثبت نام");
        initView();
        stage.setScene(scene);
        stage.show();
    }

    private void initView(){
        BorderPane border_pane = new BorderPane();
        border_pane.setStyle("-fx-background-image: url('background2.jpg') ; -fx-background-size: cover ; -fx-background-position: center ; -fx-padding: 20px;");
        ImageView register_top_imageview = new ImageView("book0.png");
        register_top_imageview.setStyle("-fx-effect: dropshadow(gaussian, black, 180, 0.2,0,0)");
        register_top_imageview.setFitWidth(70);
        register_top_imageview.setFitHeight(70);
        border_pane.setTop(register_top_imageview);
        border_pane.setAlignment(register_top_imageview,Pos.CENTER);
        GridPane grid_pane = new GridPane();
        if(!isEnglish){
            grid_pane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        }
        grid_pane.setMinWidth(stage_width);
        grid_pane.setPadding(new Insets(10));
        grid_pane.setVgap(10);
        grid_pane.setHgap(10);
        grid_pane.setAlignment(Pos.TOP_CENTER);
        Label login_name_label = new Label(isEnglish ? "Name : " : "نام :‌ ");
        login_name_label.setStyle("-fx-font-weight: bold");
        JFXTextField login_name_textfield = new JFXTextField();
        login_name_textfield.setStyle("-fx-font-weight: bold");
        grid_pane.addRow(1,login_name_label,login_name_textfield);
        Label login_username_label = new Label(isEnglish ? "Username : " : "نام کاربری :‌ ");
        login_username_label.setStyle("-fx-font-weight: bold");
        JFXTextField login_username_textfield = new JFXTextField();
        login_username_textfield.setStyle("-fx-font-weight: bold");
        grid_pane.addRow(2,login_username_label,login_username_textfield);
        Label login_password_label = new Label(isEnglish ? "Password : " : "رمز عبور : ");
        login_password_label.setStyle("-fx-font-weight: bold");
        JFXPasswordField login_password_passwordfield = new JFXPasswordField();
        login_password_passwordfield.setStyle("-fx-font-weight: bold");
        grid_pane.addRow(3,login_password_label,login_password_passwordfield);
        JFXButton register_button = new JFXButton(isEnglish ? "Register" : "ثبت نام");
        JFXButton cancel_button = new JFXButton(isEnglish ? "Back" : "بازگشت");
        register_button.setStyle("-fx-font-weight: bold;-fx-background-color: #26a69a;-fx-text-fill: white");
        cancel_button.setStyle("-fx-font-weight: bold;-fx-background-color: #ef5350;-fx-text-fill: white");
        register_button.setOnAction(value -> {
            checkRegister(login_name_textfield.getText(),login_username_textfield.getText(),login_password_passwordfield.getText());
        });
        cancel_button.setOnAction(value -> {
            login.showStage();
        });
        register_button.setEffect(new DropShadow(45, Color.BLACK));
        cancel_button.setEffect(new DropShadow(45, Color.BLACK));
        grid_pane.addRow(4,register_button,cancel_button);
        border_pane.setCenter(grid_pane);
        root_pane.getChildren().add(border_pane);
    }

    private void checkRegister(String name, String username, String password){
        if(name.length() > 0){
            if(username.length() > 0){
                if(password.length() > 0){
                    Utils.Auth.setUser(username,password,name);
                    login.showStage();
                    Utils.Dialogs.showDialog(isEnglish ? "Alert" : "پیام" ,isEnglish ? "Your Account Registered !" : "حساب شما ثبت گردید !",login.root_pane);
                }else{
                    Utils.Dialogs.showDialog(isEnglish ? "Error" : "ارور" ,isEnglish ? "Enter Correct Password !" : "لطفا رمز عبور را صحیح وارد کنید !",root_pane);
                }
            }else{
                Utils.Dialogs.showDialog(isEnglish ? "Error" : "ارور" ,isEnglish ? "Enter Correct Username !" : "لطفا نام کاربری را صحیح وارد کنید !",root_pane);
            }
        }else{
            Utils.Dialogs.showDialog(isEnglish ? "Error" : "ارور" , isEnglish ? "Enter Correct Name !" : "لطفا نام خود را صحیح وارد کنید !",root_pane);
        }
    }
}
