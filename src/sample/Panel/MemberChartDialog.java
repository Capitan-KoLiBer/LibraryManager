package sample.Panel;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import sample.Utils.Utils;

import java.util.Arrays;
import java.util.HashMap;

public class MemberChartDialog {

    private boolean isEnglish = Utils.Language.getLanguage() == 1;

    MemberChartDialog(String member_name , String member_id , String member_date , StackPane root_pane){
        try{
            JFXDialog dialog = new JFXDialog();
            HBox box = new HBox();
            HashMap<String , String > member_history = Utils.Member.getMember(member_id);
            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();
            BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
            xAxis.setLabel(isEnglish ? "Reserve Date" : "تاریخ بردن کتاب");
            yAxis.setLabel(isEnglish ? "Days Count" : "تعداد روز ها");
            barChart.setTitle(isEnglish ? "Reserved Books History Graph" : "نمودار تاریخ کتاب های گرفته شده توسط دانشجو");
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(isEnglish ? "Date" : "تاریخ");
            for(String start_date : member_history.keySet()){
                String book_name = member_history.get(start_date).split("\t")[0];
                String days_count = member_history.get(start_date).split("\t")[1];
                series.getData().add(new XYChart.Data<>(start_date+"\n"+book_name, Integer.parseInt(days_count)));
            }
            barChart.getData().add(series);
            box.getChildren().add(barChart);
            JFXButton close_button = new JFXButton(isEnglish ? "Close" : "بستن");
            close_button.setStyle("-fx-font-weight: bold;-fx-background-color: cornflowerblue; -fx-text-fill: white; -jfx-button-type: RAISED;");
            close_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    dialog.close();
                }
            });
            box.getChildren().add(close_button);
            dialog.setContent(box);
            dialog.show(root_pane);
        }catch (Exception ignored){}
    }

}
