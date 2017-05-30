package sample.Panel;

import com.jfoenix.controls.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Authentication.Login;
import sample.Authentication.Register;
import sample.Utils.Utils;

public class Panel {

    private StackPane root_pane;
    private int stage_width = 800;
    private int stage_height = 600;
    private Stage stage;
    private Login login;
    private Register register;
    private Panel panel;
    private String title;
    private boolean isEnglish = Utils.Language.getLanguage() == 1;

    public void initVars(Stage stage , Login login , Register register , Panel panel){
        this.stage = stage;
        this.login = login;
        this.register = register;
        this.panel = panel;
    }

    public void showStage(String username,String password, String name){
        this.title = title;
        root_pane = new StackPane();
        root_pane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root_pane);
        scene.setFill(Color.WHITE);
        stage.setResizable(true);
        stage.setWidth(stage_width);
        stage.setHeight(stage_height);
        stage.setTitle(isEnglish ? "Admin Panel" : "پنل مدیریت");
        initView();
        stage.setScene(scene);
        stage.show();
        Utils.Dialogs.showDialog(isEnglish ? "Alert" : "پیام",isEnglish ? "Welcome " : "خوش آمدید "+ " "+name,root_pane);
    }

    private void initView(){
        VBox flow_pane = new VBox();
        flow_pane.setAlignment(Pos.TOP_CENTER);
        JFXTabPane tabPane = new JFXTabPane();
        tabPane.setStyle("-fx-background-image: url('background1.jpg') ; -fx-background-size: cover ; -fx-background-position: center;");
        tabPane.setPrefSize(stage.getWidth(), stage.getHeight());
        AddMemberTab addMemberTab = new AddMemberTab(root_pane);
        AddBookTab addBookTab = new AddBookTab(root_pane);
        ShowMembersTab showMembersTab = new ShowMembersTab(root_pane);
        ShowBooksTab showBooksTab = new ShowBooksTab(root_pane);
        tabPane.getTabs().addAll(addMemberTab , addBookTab , showMembersTab , showBooksTab);
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observableValue, Tab tab, Tab t1) {
                if(t1.equals(showBooksTab)){
                    showBooksTab.loadListView();
                }else if(t1.equals(showMembersTab)){
                    showMembersTab.loadListView();
                }
            }
        });

        ToolBar toolbar = new ToolBar();
        toolbar.setStyle("-fx-background-color: #00BCD4;");
        toolbar.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.BLACK,45,0,0,1));
        toolbar.setPrefHeight(50);
        JFXButton settings = new JFXButton(isEnglish ? "Settings" : "تنظیمات",new ImageView("settings.png"));
        settings.setStyle("-fx-start-margin: 10px;-fx-background-color: orange;-jfx-button-type: RAISED;-fx-text-fill: white;-fx-font-weight: bold");
        toolbar.getItems().add(settings);
        if(!isEnglish){
            toolbar.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        }
        flow_pane.getChildren().add(toolbar);
        flow_pane.getChildren().add(tabPane);

        settings.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                SettingsDialog settingsDialog = new SettingsDialog();
                settingsDialog.show(root_pane);
            }
        });

        root_pane.getChildren().add(flow_pane);
    }

}
