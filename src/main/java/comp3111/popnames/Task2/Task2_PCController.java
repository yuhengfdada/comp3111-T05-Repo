package comp3111.popnames.Task2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.*;
import java.io.IOException;
/**
 * The controller for showing pie chart in task 2.
 * @author LUO, Yuqing
 */
public class Task2_PCController {

    @FXML
    private PieChart pie_chart;

    /**
     * Initialize the controller.
     */
    @FXML
    public void initialize(){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for(int i = 0; i < Task2_NameAnalyzer.name_list.size(); i++){
            pieChartData.add(new PieChart.Data(Task2_NameAnalyzer.name_list.get(i).getKey(), Task2_NameAnalyzer.name_list.get(i).getValue()));
        }
        pie_chart.setData(pieChartData);

    }

    public void start() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Task2_pie_chart.fxml"));
        Stage stage = new Stage();
        VBox root = loader.load();
        stage.setTitle("Pie Chart of K-th popular names");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

}
