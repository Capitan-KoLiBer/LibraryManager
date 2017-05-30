package sample.Panel;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import sample.Utils.Utils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EditMemberDialog {

    private boolean isEnglish = Utils.Language.getLanguage() == 1;

    EditMemberDialog(String member_name , String member_gender , String member_id , String member_date , StackPane root_pane , ShowMembersTab showMembersTab){
        try{
            JFXDialog dialog = new JFXDialog();
            GridPane grid_pane = new GridPane();
            grid_pane.setPadding(new Insets(50));
            if (!isEnglish){
                grid_pane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            }
            grid_pane.setHgap(20);
            grid_pane.setVgap(20);
            grid_pane.setAlignment(Pos.CENTER);
            Label name_label = new Label(isEnglish ? "Member Name : " : "نام دانشجو :‌ ");
            JFXTextField name_field = new JFXTextField(member_name);
            name_label.setStyle("-fx-font-weight: bold;");
            name_field.setStyle("-fx-font-weight: bold;");
            grid_pane.add(name_label,0,0);
            grid_pane.add(name_field,1,0);
            Label id_label = new Label(isEnglish ? "Member ID : " : "کد دانشجو : ");
            Label id_field = new Label(member_id);
            id_label.setStyle("-fx-font-weight: bold;");
            id_field.setStyle("-fx-font-weight: bold;");
            grid_pane.add(id_label,0,1);
            grid_pane.add(id_field,1,1);
            Label date_label = new Label(isEnglish ? "Member Birth Date : " : "تاریخ تولد دانشجو :‌ ");
            JFXDatePicker date_picker = new JFXDatePicker(LocalDate.of( Integer.parseInt(member_date.split("/")[0]) , Integer.parseInt(member_date.split("/")[1]) , Integer.parseInt(member_date.split("/")[2]) ));
            date_label.setStyle("-fx-font-weight: bold;");
            date_picker.setStyle("-fx-font-weight: bold;");
            grid_pane.add(date_label,0,2);
            grid_pane.add(date_picker,1,2);
            JFXButton save_button = new JFXButton(isEnglish ? "Save" : "ذخیره");
            JFXButton cancel_button = new JFXButton(isEnglish ? "Cancel" : "انصراف");
            save_button.setStyle("-fx-font-weight: bold;-fx-background-color: forestgreen; -fx-text-fill: white; -jfx-button-type: RAISED;-fx-font-weight: bold;");
            cancel_button.setStyle("-fx-font-weight: bold;-fx-background-color: orange; -fx-text-fill: white; -jfx-button-type: RAISED;-fx-font-weight: bold;");
            grid_pane.add(save_button,0,4);
            grid_pane.add(cancel_button,1,4);
            save_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    boolean is_valid = true;
                    String date = date_picker.valueProperty().getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
                    if(name_field.getText().length() <= 0 && is_valid){
                        Utils.Dialogs.showDialog(isEnglish ? "Error" : "ارور",isEnglish ? "Enter Member Name : " : "نام دانشجو را وارد کنید !",root_pane);
                        is_valid = false;
                    }
                    if(id_field.getText().length() <= 0 && is_valid){
                        Utils.Dialogs.showDialog(isEnglish ? "Error" : "ارور",isEnglish ? "Enter Member ID : " : "شماره دانشجو را وارد کنید !",root_pane);
                        is_valid = false;
                    }
                    if(date.length() <= 0 && is_valid){
                        Utils.Dialogs.showDialog(isEnglish ? "Error" : "ارور",isEnglish ? "Enter Member Birth Date : " : "تاریخ تولد دانشجو را وارد کنید !",root_pane);
                        is_valid = false;
                    }
                    if(is_valid){
                        if(Utils.Member.setMember(name_field.getText(),member_gender,id_field.getText(),date)){
                            Utils.Dialogs.showDialog(isEnglish ? "Alert" : "توجه",isEnglish ? "Success !" : "عملیات با موفقیت انجام شد !",root_pane);
                        }else{
                            Utils.Dialogs.showDialog(isEnglish ? "Error" : "ارور",isEnglish ? "An error occurs !" : "متاسفانه مشکلی رخ داد !",root_pane);
                        }
                        dialog.close();
                        showMembersTab.loadListView();
                    }
                }
            });
            cancel_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    dialog.close();
                }
            });
            dialog.setContent(grid_pane);
            dialog.show(root_pane);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
