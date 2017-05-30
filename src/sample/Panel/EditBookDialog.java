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

public class EditBookDialog {

    private boolean isEnglish = Utils.Language.getLanguage() == 1;

    EditBookDialog(String book_name , String book_author , String book_publisher , StackPane root_pane,ShowBooksTab showBooksTab){
        JFXDialog dialog = new JFXDialog();
        GridPane grid_pane = new GridPane();
        grid_pane.setPadding(new Insets(50));
        if (!isEnglish){
            grid_pane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        }
        grid_pane.setHgap(20);
        grid_pane.setVgap(20);
        grid_pane.setAlignment(Pos.CENTER);
        Label name_label = new Label(isEnglish ? "Book Name : " : "نام کتاب :‌ ");
        JFXTextField name_field = new JFXTextField(book_name);
        name_label.setStyle("-fx-font-weight: bold;");
        name_field.setStyle("-fx-font-weight: bold;");
        grid_pane.add(name_label,0,0);
        grid_pane.add(name_field,1,0);
        Label author_label = new Label(isEnglish ? "Author Name : " : "نام نویسنده : ");
        JFXTextField author_field = new JFXTextField(book_author);
        author_label.setStyle("-fx-font-weight: bold;");
        author_field.setStyle("-fx-font-weight: bold;");
        grid_pane.add(author_label,0,1);
        grid_pane.add(author_field,1,1);
        Label publisher_label = new Label(isEnglish ? "Publisher Name : " : "نام انتشارات");
        JFXTextField publisher_field = new JFXTextField(book_publisher);
        publisher_label.setStyle("-fx-font-weight: bold;");
        publisher_field.setStyle("-fx-font-weight: bold;");
        grid_pane.add(publisher_label,0,2);
        grid_pane.add(publisher_field,1,2);
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
                if(name_field.getText().length() <= 0 && is_valid){
                    Utils.Dialogs.showDialog(isEnglish ? "Error" : "ارور",isEnglish ? "Enter Book Name !" : "نام کتاب را وارد کنید !",root_pane);
                    is_valid = false;
                }
                if(author_field.getText().length() <= 0 && is_valid){
                    Utils.Dialogs.showDialog(isEnglish ? "Error" : "ارور",isEnglish ? "Enter Author Name !" : "نام نویسنده کتاب را وارد کنید !",root_pane);
                    is_valid = false;
                }
                if(publisher_field.getText().length() <= 0 && is_valid){
                    Utils.Dialogs.showDialog(isEnglish ? "Error" : "ارور",isEnglish ? "Enter Publisher Name !" : "نام انتشارات کتاب را وارد کنید !",root_pane);
                    is_valid = false;
                }
                if(is_valid){
                    if(Utils.Book.setBook(name_field.getText(),author_field.getText(),publisher_field.getText())){
                        Utils.Dialogs.showDialog(isEnglish ? "Alert" : "توجه",isEnglish ? "Success !" : "عملیات با موفقیت انجام شد !",root_pane);
                    }else{
                        Utils.Dialogs.showDialog(isEnglish ? "Error" : "ارور",isEnglish ? "An error occurs !" : "متاسفانه مشکلی رخ داد !",root_pane);
                    }
                    dialog.close();
                    showBooksTab.loadListView();
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
    }

}
