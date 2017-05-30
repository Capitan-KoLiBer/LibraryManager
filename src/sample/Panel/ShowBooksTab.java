package sample.Panel;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXListView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import sample.Utils.Utils;
import java.util.ArrayList;
import java.util.HashMap;

public class ShowBooksTab extends Tab {

    private JFXListView<GridPane> list_view;
    private StackPane root_pane;
    private boolean isEnglish = Utils.Language.getLanguage() == 1;

    ShowBooksTab(StackPane root_pane){
        this.root_pane = root_pane;
        setText(isEnglish ? "Show Books" : "نمایش کتاب ها");
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
            ArrayList<HashMap<String,String>> books_list = Utils.Book.getBooks();
            list_view.getItems().clear();
            for(int i = 0; i < books_list.size(); i++){
                GridPane grid_pane = new GridPane();
                if(!isEnglish){
                    grid_pane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                }
                ImageView book_image = new ImageView(Utils.Image.getRandomBookImage());
                grid_pane.add(book_image,0,0);
                GridPane info_pane = new GridPane();
                info_pane.setHgap(10);
                info_pane.setVgap(10);
                info_pane.setPadding(new Insets(5));
                Label book_name_label = new Label(isEnglish ? "Book Name : " : "نام کتاب :‌ ");
                Label book_author_label = new Label(isEnglish ? "Author Name : " : "نام نویسنده :‌ ");
                Label book_publisher_label = new Label(isEnglish ? "Publisher Name : " : "نام ناشر :‌ ");
                Label book_name_value_label = new Label(books_list.get(i).get("BOOK_NAME"));
                Label book_author_value_label = new Label(books_list.get(i).get("BOOK_AUTHOR"));
                Label book_publisher_value_label = new Label(books_list.get(i).get("BOOK_PUBLISHER"));
                book_name_label.setStyle("-fx-font-weight: bold");
                book_author_label.setStyle("-fx-font-weight: bold");
                book_publisher_label.setStyle("-fx-font-weight: bold");
                book_name_value_label.setStyle("-fx-font-weight: bold");
                book_author_value_label.setStyle("-fx-font-weight: bold");
                book_publisher_value_label.setStyle("-fx-font-weight: bold");
                info_pane.add(book_name_label,0,0);
                info_pane.add(book_name_value_label,1,0);
                info_pane.add(book_author_label,0,1);
                info_pane.add(book_author_value_label,1,1);
                info_pane.add(book_publisher_label,0,2);
                info_pane.add(book_publisher_value_label,1,2);
                grid_pane.add(info_pane,1,0);
                GridPane button_pane = new GridPane();
                button_pane.setVgap(10);
                button_pane.setHgap(10);
                JFXButton delete_button = new JFXButton("",new ImageView("delete.png"));
                JFXButton edit_button = new JFXButton("",new ImageView("table-edit.png"));
                JFXButton chart_button = new JFXButton("",new ImageView("chart-areaspline.png"));
                JFXButton serve_button;
                boolean isReserved = !books_list.get(i).get("BOOK_RESERVED").equals("F");
                if(isReserved){
                    serve_button = new JFXButton("",new ImageView("response.png"));
                    serve_button.setId("RES");
                }else{
                    serve_button = new JFXButton("",new ImageView("request.png"));
                    serve_button.setId("REQ");
                }
                delete_button.setStyle("-fx-start-margin: 10px;-fx-background-color: red;-jfx-button-type: RAISED;-fx-text-fill: white");
                edit_button.setStyle("-fx-start-margin: 10px;-fx-background-color: cornflowerblue;-jfx-button-type: RAISED;-fx-text-fill: white");
                chart_button.setStyle("-fx-start-margin: 10px;-fx-background-color: forestgreen;-jfx-button-type: RAISED;-fx-text-fill: white");
                serve_button.setStyle("-fx-start-margin: 10px;-fx-background-color: orange;-jfx-button-type: RAISED;-fx-text-fill: white");
                button_pane.add(delete_button,0,0);
                button_pane.add(edit_button,1,0);
                button_pane.add(chart_button,0,1);
                button_pane.add(serve_button,1,1);
                ColumnConstraints column1 = new ColumnConstraints(book_image.getImage().getWidth());
                ColumnConstraints column2 = new ColumnConstraints();
                column2.setHgrow(Priority.ALWAYS);
                ColumnConstraints column3 = new ColumnConstraints(100);
                grid_pane.getColumnConstraints().addAll(column1, column2,column3);
                button_pane.setAlignment(Pos.CENTER_LEFT);
                grid_pane.add(button_pane,2,0);
                list_view.getItems().add(grid_pane);
                delete_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        deleteBook(book_name_value_label.getText());
                    }
                });
                edit_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        editBook(book_name_value_label.getText() , book_author_value_label.getText() , book_publisher_value_label.getText());
                    }
                });
                chart_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        showChart(book_name_value_label.getText() , book_author_value_label.getText() , book_publisher_value_label.getText());
                    }
                });
                serve_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        serveBook(book_name_value_label.getText() , book_author_value_label.getText() , book_publisher_value_label.getText() , serve_button.getId().equals("REQ"));
                    }
                });
            }
        }catch (Exception ignored){
            System.out.println(ignored.getMessage());
        }
    }

    private void deleteBook(String book_name){
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
        Label text_label = new Label(isEnglish ? "Are you sure to delete the book ?" : "آیا کتاب مورد نظر حذف شود ؟");
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
                Utils.Book.deleteBook(book_name);
                loadListView();
            }
        });
        message_dialog.setContent(dialog_pane);
        message_dialog.show(root_pane);
    }

    private void editBook(String book_name , String book_author , String book_publisher){
        new EditBookDialog(book_name,book_author,book_publisher,root_pane,this);
    }

    private void showChart(String book_name , String book_author , String book_publisher){
        new BookChartDialog(book_name,book_author,book_publisher,root_pane);
    }

    private void serveBook(String book_name , String book_author , String book_publisher , boolean isRequest){
        new ServeBookDialog(book_name,book_author,book_publisher,isRequest,root_pane,this);
    }

}
