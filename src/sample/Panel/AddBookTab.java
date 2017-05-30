package sample.Panel;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.EventHandler;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import sample.Utils.Utils;

public class AddBookTab extends Tab {

    private JFXTextField name_field,author_field,publisher_field;
    private boolean isEnglish = Utils.Language.getLanguage() == 1;

    AddBookTab(StackPane root_pane){
        setText(isEnglish ? "Add Book" : "افزودن کتاب");
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: khaki ; -fx-effect: dropshadow(gaussian, black, 180, 0.1, 0, 0)");
        pane.setMaxWidth(500);
        pane.setMaxHeight(400);
        GridPane grid_pane = new GridPane();
        if(!isEnglish){
            grid_pane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        }
        grid_pane.setHgap(20);
        grid_pane.setVgap(20);
        grid_pane.setAlignment(Pos.CENTER);
        Label name_label = new Label(isEnglish ? "Book Name : " : "نام کتاب :‌ ");
        name_field = new JFXTextField();
        name_label.setStyle("-fx-font-weight: bold;");
        name_field.setStyle("-fx-font-weight: bold;");
        grid_pane.add(name_label,0,0);
        grid_pane.add(name_field,1,0);
        Label author_label = new Label(isEnglish ? "Author Name : " : "نام نویسنده : ");
        author_field = new JFXTextField();
        author_label.setStyle("-fx-font-weight: bold;");
        author_field.setStyle("-fx-font-weight: bold;");
        grid_pane.add(author_label,0,1);
        grid_pane.add(author_field,1,1);
        Label publisher_label = new Label(isEnglish ? "Publisher Name : " : "نام انتشارات");
        publisher_field = new JFXTextField();
        publisher_label.setStyle("-fx-font-weight: bold;");
        publisher_field.setStyle("-fx-font-weight: bold;");
        grid_pane.add(publisher_label,0,2);
        grid_pane.add(publisher_field,1,2);
        JFXButton add_button = new JFXButton(isEnglish ? "Add" : "افزودن");
        add_button.setStyle("-fx-font-weight: bold;-fx-background-color: forestgreen; -fx-text-fill: white; -jfx-button-type: RAISED;-fx-font-weight: bold;");
        grid_pane.add(add_button,0,4);
        add_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                add_book(root_pane , name_field.getText() , author_field.getText() , publisher_field.getText());
            }
        });
        pane.setCenter(grid_pane);
        setContent(pane);
    }

    private void add_book(StackPane root_pane , String book_name , String book_author , String book_publisher){
        boolean is_valid = true;
        if(book_name.length() <= 0 && is_valid){
            Utils.Dialogs.showDialog(isEnglish ? "Error" : "ارور",isEnglish ? "Enter Book Name !" : "نام کتاب را وارد کنید !",root_pane);
            is_valid = false;
        }
        if(book_author.length() <= 0 && is_valid){
            Utils.Dialogs.showDialog(isEnglish ? "Error" : "ارور",isEnglish ? "Enter Author Name !" : "نام نویسنده کتاب را وارد کنید !",root_pane);
            is_valid = false;
        }
        if(book_publisher.length() <= 0 && is_valid){
            Utils.Dialogs.showDialog(isEnglish ? "Error" : "ارور",isEnglish ? "Enter Publisher Name !" : "نام انتشارات کتاب را وارد کنید !",root_pane);
            is_valid = false;
        }
        if(is_valid){
            if(Utils.Book.setBook(book_name , book_author , book_publisher)){
                name_field.setText("");
                publisher_field.setText("");
                author_field.setText("");
                Utils.Dialogs.showDialog(isEnglish ? "Alert" : "توجه",isEnglish ? "Success !" : "عملیات با موفقیت انجام شد !",root_pane);
            }else{
                Utils.Dialogs.showDialog(isEnglish ? "Error" : "ارور",isEnglish ? "An error occurs !" : "متاسفانه مشکلی رخ داد !",root_pane);
            }
        }
    }
}
