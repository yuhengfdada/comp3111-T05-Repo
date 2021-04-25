package comp3111.popnames.Task1;

import java.io.IOException;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class BCController {
    @FXML
    private AnchorPane aPane;
    @FXML
    private Button back;
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private BarChart<String, Number> barChart1;
    
	private Task1 task1;
	
	public void appearBC(Stage stage) {
    	stage.setTitle("Bar Chart Summary");
    	task1 = (Task1)stage.getUserData();
 
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName(String.valueOf(task1.year));
        for (Pair<String, Integer> pair : task1.popNamesListM) {
        	series1.getData().add(new XYChart.Data<>(pair.getKey(), pair.getValue()));
        }
        barChart.getData().add(series1);
        
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName(String.valueOf(task1.year));
        for (Pair<String, Integer> pair : task1.popNamesListF) {
        	series2.getData().add(new XYChart.Data<>(pair.getKey(), pair.getValue()));
        }
        barChart1.getData().add(series2);
	}
	
	
	
	
    @FXML
    void goBack(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/task1_1.fxml"));
    	Parent task1Parent = (Parent)loader.load();
    	Scene task1Scene = new Scene(task1Parent);
    	Task1Controller controller = (Task1Controller)loader.getController();
    	
    	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setUserData(task1);
    	controller.appearSummary(window);
    	window.setScene(task1Scene);
    	window.show();
    }
}
