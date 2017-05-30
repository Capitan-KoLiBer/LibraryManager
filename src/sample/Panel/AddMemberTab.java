package sample.Panel;

import com.jfoenix.controls.*;
import javafx.event.EventHandler;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import sample.Utils.Utils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddMemberTab extends Tab {

    private JFXTextField name_field,id_field;
    private JFXDatePicker date_picker;
    private boolean isEnglish = Utils.Language.getLanguage() == 1;

    AddMemberTab(StackPane root_pane){
        setText(isEnglish ? "Add Member" : "افزودن دانشجو");
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: lightblue ; -fx-effect: dropshadow(gaussian, black, 180, 0.1, 0, 0)");
        pane.setMaxWidth(500);
        pane.setMaxHeight(400);
        GridPane grid_pane = new GridPane();
        if(!isEnglish){
            grid_pane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        }
        grid_pane.setHgap(20);
        grid_pane.setVgap(20);
        grid_pane.setAlignment(Pos.CENTER);
        Label name_label = new Label(isEnglish ? "Member Name : " : "نام دانشجو :‌ ");
        name_field = new JFXTextField();
        name_label.setStyle("-fx-font-weight: bold;");
        name_field.setStyle("-fx-font-weight: bold;");
        grid_pane.add(name_label,0,0);
        grid_pane.add(name_field,1,0);
        Label gender_label = new Label(isEnglish ? "Member Gender : " : "جنسیت دانشجو ‌:‌ ");
        JFXComboBox<Label> gender_combobox = new JFXComboBox<>();
        gender_label.setStyle("-fx-font-weight: bold;");
        gender_combobox.setStyle("-fx-font-weight: bold;");
        gender_combobox.getItems().addAll(new Label(isEnglish ? "Male" : "مرد"),new Label(isEnglish ? "Female" : "زن"));
        gender_combobox.getSelectionModel().select(0);
        grid_pane.add(gender_label,0,1);
        grid_pane.add(gender_combobox,1,1);
        Label id_label = new Label(isEnglish ? "Member ID : " : "کد دانشجو : ");
        id_field = new JFXTextField();
        id_label.setStyle("-fx-font-weight: bold;");
        id_field.setStyle("-fx-font-weight: bold;");
        grid_pane.add(id_label,0,2);
        grid_pane.add(id_field,1,2);
        Label date_label = new Label(isEnglish ? "Member Birth Date : " : "تاریخ تولد دانشجو :‌ ");
        date_picker = new JFXDatePicker();
        date_label.setStyle("-fx-font-weight: bold;");
        date_picker.setStyle("-fx-font-weight: bold;");
        date_picker.setDefaultColor(Color.valueOf("#00BCD4"));
        grid_pane.add(date_label,0,3);
        grid_pane.add(date_picker,1,3);
        JFXButton add_button = new JFXButton(isEnglish ? "Add" : "افزودن");
        add_button.setStyle("-fx-font-weight: bold;-fx-background-color: forestgreen; -fx-text-fill: white; -jfx-button-type: RAISED;-fx-font-weight: bold;");
        grid_pane.add(add_button,0,4);
        add_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                add_member(root_pane , name_field.getText() , gender_combobox.getSelectionModel().getSelectedItem().getText() , id_field.getText() , date_picker.valueProperty().getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) );
            }
        });
        pane.setCenter(grid_pane);
        setContent(pane);
    }

    private void add_member(StackPane root_pane , String member_name , String member_gender , String member_id , String member_date){
        boolean is_valid = true;
        if(member_name.length() <= 0 && is_valid){
            Utils.Dialogs.showDialog(isEnglish ? "Error" : "ارور",isEnglish ? "Enter Member Name : " : "نام دانشجو را وارد کنید !",root_pane);
            is_valid = false;
        }
        if(member_gender.length() <= 0 && is_valid){
            Utils.Dialogs.showDialog(isEnglish ? "Error" : "ارور",isEnglish ? "Enter Member Gender : " : "جنسیت دانشجو را وارد کنید !",root_pane);
            is_valid = false;
        }
        if(member_id.length() <= 0 && is_valid){
            Utils.Dialogs.showDialog(isEnglish ? "Error" : "ارور",isEnglish ? "Enter Member ID : " : "شماره دانشجو را وارد کنید !",root_pane);
            is_valid = false;
        }
        if(member_date.length() <= 0 && is_valid){
            Utils.Dialogs.showDialog(isEnglish ? "Error" : "ارور",isEnglish ? "Enter Member Birth Date : " : "تاریخ تولد دانشجو را وارد کنید !",root_pane);
            is_valid = false;
        }
        if(is_valid){
            if(Utils.Member.setMember(member_name,member_gender,member_id,member_date)){
                name_field.setText("");
                id_field.setText("");
                date_picker.valueProperty().setValue(null);
                Utils.Dialogs.showDialog(isEnglish ? "Alert" : "توجه",isEnglish ? "Success !" : "عملیات با موفقیت انجام شد !",root_pane);
            }else{
                Utils.Dialogs.showDialog(isEnglish ? "Error" : "ارور",isEnglish ? "An error occurs !" : "متاسفانه مشکلی رخ داد !",root_pane);
            }
        }
    }

}
