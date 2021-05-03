package comp3111.popnames.Task2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.chart.XYChart;
import java.io.IOException;

public class Task2_BCController {

    @FXML
    private BarChart<String, Number> bar_chart;

    @FXML
    private CategoryAxis Names;

    @FXML
    private NumberAxis Occurrences;


    @FXML
    public void initialize() {
        XYChart.Series<String, Number> series1 = new XYChart.Series();

        for(int i = 0; i < Task2_NameAnalyzer.name_list.size(); i++){
            series1.getData().add(new XYChart.Data(Task2_NameAnalyzer.name_list.get(i).getKey(), Task2_NameAnalyzer.nameRecords.get(Task2_NameAnalyzer.name_list.get(i).getKey())));
        }
        series1.setName("Popular Names");
        bar_chart.getData().add(series1);

    }

    public void start() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Task2_bar_chart.fxml"));
        Stage stage = new Stage();
        VBox root = loader.load();
        stage.setTitle("Bar Chart of K-th popular names");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
}


