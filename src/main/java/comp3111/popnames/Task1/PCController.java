package comp3111.popnames.Task1;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.chart.PieChart;

public class PCController {
    @FXML
    private Button back;
    @FXML
    private PieChart pieChart;
    @FXML
    private PieChart pieChart1;
    
	private Task1 task1;
	
	public void appearPC(Stage stage) {
    	stage.setTitle("Pie Chart Summary");
    	task1 = (Task1)stage.getUserData();
    	
    	pieChart.setTitle(String.format("Top %d Names in %d (Male)", task1.topN, task1.year));
    	pieChart1.setTitle(String.format("Top %d Names in %d (Female)", task1.topN, task1.year));
    	
    	ObservableList<PieChart.Data> pieChartDataM = FXCollections.observableArrayList();
    	ObservableList<PieChart.Data> pieChartDataF = FXCollections.observableArrayList();
    	
    	for (Pair<String, Integer> pair : task1.popNamesListM) {
    		pieChartDataM.add(new PieChart.Data(pair.getKey(), pair.getValue()));
    	}
    	for (Pair<String, Integer> pair : task1.popNamesListF) {
    		pieChartDataF.add(new PieChart.Data(pair.getKey(), pair.getValue()));
    	}
    	
    	pieChart.setData(pieChartDataM);
    	pieChart1.setData(pieChartDataF);
    	
	}
	
	
    @FXML
    void goBack(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/task1_1.fxml"));
    	Parent task1Parent = (Parent)loader.load();
    	Scene task1Scene = new Scene(task1Parent);
    	Task1Controller controller = (Task1Controller)loader.getController();
    	
    	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setUserData(task1);
    	controller.showStoredSummary(window);
    	window.setScene(task1Scene);
    	window.show();
    }
}
