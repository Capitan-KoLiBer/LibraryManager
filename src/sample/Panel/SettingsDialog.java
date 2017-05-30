package sample.Panel;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import sample.Utils.Utils;

/**
 * Created by koliber on 5/15/17.
 */
public class SettingsDialog extends JFXDialog{

    private boolean isEnglish = Utils.Language.getLanguage() == 1;

    SettingsDialog(){
        GridPane dialog_pane = new GridPane();
        dialog_pane.setVgap(10);
        dialog_pane.setHgap(10);
        if(!isEnglish){
            dialog_pane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        }
        dialog_pane.setPadding(new Insets(10));
        Label title_label = new Label(isEnglish ? "Settings" : "تنظیمات");
        title_label.setStyle("-fx-font-weight: bold;-fx-font-size: 15");
        Label language_label = new Label(isEnglish ? "Language : " : "زبان :‌ ");
        JFXComboBox<Label> language_combobox = new JFXComboBox<>();
        language_combobox.getItems().addAll(new Label("فارسی"),new Label("English"));
        language_combobox.getSelectionModel().select(Utils.Language.getLanguage());
        language_combobox.valueProperty().addListener(new ChangeListener<Label>() {
            @Override
            public void changed(ObservableValue<? extends Label> observableValue, Label label, Label t1) {
                if(t1.getText().equals("English")){
                    Utils.Language.setLanguage(1);
                }else{
                    Utils.Language.setLanguage(0);
                }
            }
        });
        JFXButton ok_button = new JFXButton(isEnglish ? "Close" : "بستن");
        ok_button.setStyle("-fx-font-weight: bold;-fx-background-color: #29b6f6 ; -jfx-button-type: RAISED;-fx-text-fill: white;");
        ok_button.setOnAction(value -> {
            close();
        });


        dialog_pane.add(title_label,0,0);
        dialog_pane.add(language_label,0,1);
        dialog_pane.add(language_combobox,1,1);
        dialog_pane.add(ok_button,0,2);
        setPadding(new Insets(10));
        setContent(dialog_pane);
    }
}
