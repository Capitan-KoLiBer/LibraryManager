package sample.Panel;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXListView;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import sample.Utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by koliber on 5/15/17.
 */
public class ShowMembersTab extends Tab{

    private JFXListView<GridPane> list_view;
    private StackPane root_pane;
    private boolean isEnglish = Utils.Language.getLanguage() == 1;

    ShowMembersTab(StackPane root_pane){
        this.root_pane = root_pane;
        setText(isEnglish ? "Show Members" : "نمایش دانشجویان");
        list_view = new JFXListView<>();
        list_view.setBackground(new Background(new BackgroundFill(Paint.valueOf("#00000000"), CornerRadii.EMPTY, Insets.EMPTY)));
        list_view.setVerticalGap(10d);
        list_view.setPadding(new Insets(50));
        list_view.setExpanded(true);
        list_view.depthProperty().set(1);
        setContent(list_view);
    }

    public void loadListView(){
        try{
            ArrayList<HashMap<String,String>> members_list = Utils.Member.getMembers();
            // KEYS -> MEMBER_NAME , MEMBER_GENDER , MEMBER_ID , MEMBER_DATE
            list_view.getItems().clear();
            for(int i = 0; i < members_list.size(); i++){
                GridPane grid_pane = new GridPane();
                if (!isEnglish){
                    grid_pane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                }
                ImageView member_image = new ImageView(Utils.Image.getMemberImage(members_list.get(i).get("MEMBER_GENDER").equals("مرد") || members_list.get(i).get("MEMBER_GENDER").equals("Male")));
                grid_pane.add(member_image,0,0);
                GridPane info_pane = new GridPane();
                info_pane.setHgap(10);
                info_pane.setVgap(10);
                info_pane.setPadding(new Insets(5));
                Label member_name_label = new Label(isEnglish ? "Member Name : " : "نام دانشجو :‌ ");
                Label member_id_label = new Label(isEnglish ? "Member ID : " : "شماره دانشجو :‌ ");
                Label member_date_label = new Label(isEnglish ? "Member Birth Date : " : "تاریخ تولد دانشجو :‌ ");
                Label member_name_value_label = new Label(members_list.get(i).get("MEMBER_NAME"));
                Label member_id_value_label = new Label(members_list.get(i).get("MEMBER_ID"));
                Label member_date_value_label = new Label(members_list.get(i).get("MEMBER_DATE"));
                member_name_label.setStyle("-fx-font-weight: bold");
                member_id_label.setStyle("-fx-font-weight: bold");
                member_date_label.setStyle("-fx-font-weight: bold");
                member_name_value_label.setStyle("-fx-font-weight: bold");
                member_id_value_label.setStyle("-fx-font-weight: bold");
                member_date_value_label.setStyle("-fx-font-weight: bold");
                info_pane.add(member_name_label,0,0);
                info_pane.add(member_name_value_label,1,0);
                info_pane.add(member_id_label,0,1);
                info_pane.add(member_id_value_label,1,1);
                info_pane.add(member_date_label,0,2);
                info_pane.add(member_date_value_label,1,2);
                grid_pane.add(info_pane,1,0);
                GridPane button_pane = new GridPane();
                button_pane.setVgap(10);
                button_pane.setHgap(10);
                JFXButton delete_button = new JFXButton("",new ImageView("delete.png"));
                JFXButton edit_button = new JFXButton("",new ImageView("account-edit.png"));
                JFXButton chart_button = new JFXButton("",new ImageView("chart-areaspline.png"));
                delete_button.setStyle("-fx-start-margin: 10px;-fx-background-color: red;-jfx-button-type: RAISED;-fx-text-fill: white");
                edit_button.setStyle("-fx-start-margin: 10px;-fx-background-color: cornflowerblue;-jfx-button-type: RAISED;-fx-text-fill: white");
                chart_button.setStyle("-fx-start-margin: 10px;-fx-background-color: forestgreen;-jfx-button-type: RAISED;-fx-text-fill: white");
                button_pane.add(delete_button,0,0);
                button_pane.add(edit_button,1,0);
                button_pane.add(chart_button,2,0);
                ColumnConstraints column1 = new ColumnConstraints(member_image.getImage().getWidth());
                ColumnConstraints column2 = new ColumnConstraints();
                column2.setHgrow(Priority.ALWAYS);
                ColumnConstraints column3 = new ColumnConstraints(150);
                grid_pane.getColumnConstraints().addAll(column1, column2,column3);
                button_pane.setAlignment(Pos.CENTER_LEFT);
                grid_pane.add(button_pane,2,0);
                list_view.getItems().add(grid_pane);
                delete_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        deleteMember(member_id_value_label.getText());
                    }
                });
                int finalI = i;
                edit_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        editMember(member_name_value_label.getText() , members_list.get(finalI).get("MEMBER_GENDER") , member_id_value_label.getText() , member_date_value_label.getText());
                    }
                });
                chart_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        showChart(member_name_value_label.getText() , member_id_value_label.getText() , member_date_value_label.getText());
                    }
                });
            }
        }catch (Exception ignored){
            System.out.println(ignored.getMessage());
        }
    }

    private void deleteMember(String member_id){
        final boolean[] isConfirm = {false};
        JFXDialog message_dialog = new JFXDialog();
        GridPane dialog_pane = new GridPane();
        if (!isEnglish){
            dialog_pane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        }
        dialog_pane.setPadding(new Insets(10));
        dialog_pane.setVgap(10);
        dialog_pane.setHgap(10);
        Label title_label = new Label(isEnglish ? "Alert" : "هشدار");
        Label text_label = new Label(isEnglish ? "Are you sure to delete the member ?" : "آیا دانشجوی مورد نظر حذف شود ؟");
        title_label.setStyle("-fx-font-weight: bold;-fx-font-size: 15");
        text_label.setStyle("-fx-font-weight: bold;-fx-font-size: 12");
        JFXButton ok_button = new JFXButton(isEnglish ? "Yes" : "بله");
        JFXButton no_button = new JFXButton(isEnglish ? "No" : "خیر");
        ok_button.setStyle("-fx-font-weight: bold;-fx-background-color: #29b6f6 ; -jfx-button-type: RAISED;-fx-text-fill: white");
        no_button.setStyle("-fx-font-weight: bold;-fx-background-color: red ; -jfx-button-type: RAISED;-fx-text-fill: white");
        ok_button.setOnAction(value -> {
            isConfirm[0] = true;
            message_dialog.close();
        });
        no_button.setOnAction(value -> {
            message_dialog.close();
        });
        dialog_pane.add(title_label,0,0);
        dialog_pane.add(text_label,0,1);
        dialog_pane.add(ok_button,0,2);
        dialog_pane.add(no_button,1,2);
        message_dialog.setPadding(new Insets(10));
        message_dialog.setOnDialogClosed(jfxDialogEvent -> {
            if(isConfirm[0]){
                // delete member
                Utils.Member.deleteMember(member_id);
                loadListView();
            }
        });
        message_dialog.setContent(dialog_pane);
        message_dialog.show(root_pane);
    }

    private void editMember(String member_name , String member_gender , String member_id , String member_date){
        new EditMemberDialog(member_name,member_gender,member_id,member_date,root_pane,this);
    }

    private void showChart(String member_name , String member_id , String member_date){
        new MemberChartDialog(member_name,member_id,member_date,root_pane);
    }

}
