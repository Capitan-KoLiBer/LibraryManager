package sample.Panel;

import com.jfoenix.controls.JFXButton;
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

import javax.swing.*;

public class ServeBookDialog {

    private boolean isEnglish = Utils.Language.getLanguage() == 1;

    ServeBookDialog(String book_name , String book_author , String book_publisher , boolean isRequest , StackPane root_pane , ShowBooksTab showBooksTab){
        if(isRequest){
            JFXDialog dialog = new JFXDialog();
            GridPane grid_pane = new GridPane();
            grid_pane.setPadding(new Insets(50));
            if (!isEnglish){
                grid_pane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            }
            grid_pane.setHgap(20);
            grid_pane.setVgap(20);
            grid_pane.setAlignment(Pos.CENTER);
            Label book_name_label = new Label(isEnglish ? "Book Name : " : "نام کتاب :‌ ");
            Label book_name_value_label = new Label(book_name);
            book_name_label.setStyle("-fx-font-weight: bold;");
            book_name_value_label.setStyle("-fx-font-weight: bold;");
            grid_pane.add(book_name_label,0,0);
            grid_pane.add(book_name_value_label,1,0);
            Label book_author_label = new Label(isEnglish ? "Author Name : " : "نام نویسنده : ");
            Label book_author_value_label = new Label(book_author);
            book_author_label.setStyle("-fx-font-weight: bold;");
            book_author_value_label.setStyle("-fx-font-weight: bold;");
            grid_pane.add(book_author_label,0,1);
            grid_pane.add(book_author_value_label,1,1);
            Label book_publisher_label = new Label(isEnglish ? "Publisher Name : " : "نام انتشارات");
            Label book_publisher_value_label = new Label(book_publisher);
            book_publisher_label.setStyle("-fx-font-weight: bold;");
            book_publisher_value_label.setStyle("-fx-font-weight: bold;");
            grid_pane.add(book_publisher_label,0,2);
            grid_pane.add(book_publisher_value_label,1,2);
            Label member_id_label = new Label(isEnglish ? "Member ID : " : "کد دانشجو : ");
            JFXTextField member_id_field = new JFXTextField();
            member_id_label.setStyle("-fx-font-weight: bold;");
            member_id_field.setStyle("-fx-font-weight: bold;");
            grid_pane.add(member_id_label,0,3);
            grid_pane.add(member_id_field,1,3);
            JFXButton save_button = new JFXButton(isEnglish ? "Save" : "ذخیره");
            JFXButton cancel_button = new JFXButton(isEnglish ? "Cancel" : "انصراف");
            save_button.setStyle("-fx-font-weight: bold;-fx-background-color: forestgreen; -fx-text-fill: white; -jfx-button-type: RAISED;-fx-font-weight: bold;");
            cancel_button.setStyle("-fx-font-weight: bold;-fx-background-color: orange; -fx-text-fill: white; -jfx-button-type: RAISED;-fx-font-weight: bold;");
            grid_pane.add(save_button,0,4);
            grid_pane.add(cancel_button,1,4);
            save_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if(member_id_field.getText().length() > 0){
                        if(Utils.Member.isMember(member_id_field.getText())){
                            if(Utils.Book.serveBook(book_name,isRequest,member_id_field.getText())){
                                Utils.Dialogs.showDialog(isEnglish ? "Alert" : "توجه",isEnglish ? "Success !" : "عملیات با موفقیت انجام شد !",root_pane);
                            }else{
                                Utils.Dialogs.showDialog(isEnglish ? "Error" : "ارور",isEnglish ? "An error occurs !" : "متاسفانه مشکلی رخ داد !",root_pane);
                            }
                            dialog.close();
                            showBooksTab.loadListView();
                        }else{
                            Utils.Dialogs.showDialog(isEnglish ? "Error" : "ارور",isEnglish ? "Member ID Not Found !" : "شماره مورد نظر یافت نشد !",root_pane);
                        }
                    }else{
                        Utils.Dialogs.showDialog(isEnglish ? "Error" : "ارور",isEnglish ? "Enter Member ID : " : "شماره دانشجو را وارد کنید !",root_pane);
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
        }else{
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
            Label text_label = new Label(isEnglish ? "Are you sure the book was returned ?" : "آیا کتاب مورد نظر پس آورده شد ؟");
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
                    // delete book
                    Utils.Book.serveBook(book_name,false,null);
                    showBooksTab.loadListView();
                }
            });
            message_dialog.setContent(dialog_pane);
            message_dialog.show(root_pane);
        }
    }

}
