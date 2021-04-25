package comp3111.popnames.Task1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Task1Controller{
    @FXML
    private Label lb1;
    @FXML
    private Button back;
    @FXML
    private Button viewTableBut;

    @FXML
    private Button viewBCBut;

    @FXML
    private Button viewPCBut;
    
    private Task1 task1;
    
    @FXML
    void goBack(ActionEvent event) throws IOException {
    	Parent task1Parent = FXMLLoader.load(getClass().getResource("/ui.fxml"));
    	Scene task1Scene = new Scene(task1Parent);
    	
    	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(task1Scene);
    	window.show();
    }

    @FXML
    void kanyixia(ActionEvent event) {
    	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    	Task1 task1 = (Task1)window.getUserData();
    	lb1.setText(String.valueOf(task1.getYear()));
    }
    
    @FXML
    void viewBC(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/task1_BC.fxml"));
    	Parent task1Parent = (Parent)loader.load();
    	Scene task1Scene = new Scene(task1Parent);
    	BCController controller = (BCController)loader.getController();
    	
    	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setUserData(task1);
    	window.setScene(task1Scene);
    	window.show();
    	controller.appearBC(window);
    }

    @FXML
    void viewPC(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/task1_PC.fxml"));
    	Parent task1Parent = (Parent)loader.load();
    	Scene task1Scene = new Scene(task1Parent);
    	PCController controller = (PCController)loader.getController();
    	
    	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setUserData(task1);
    	window.setScene(task1Scene);
    	window.show();
    	controller.appearPC(window);
    }

    @FXML
    void viewTable(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/task1_table.fxml"));
    	Parent task1Parent = (Parent)loader.load();
    	Scene task1Scene = new Scene(task1Parent);
    	TableController controller = (TableController)loader.getController();
    	
    	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setUserData(task1);
    	controller.appearTable(window);
    	window.setScene(task1Scene);
    	window.show();
    }
    
    public void appearSummary(Stage stage) {
    	stage.setTitle("Most Popular Names");
    	task1 = (Task1)stage.getUserData();
    	task1.getSummaryAndStore(task1.getYear());
    	lb1.setText(task1.summary);
    }
    
    public void showStoredSummary(Stage stage) {
    	stage.setTitle("Most Popular Names");
    	task1 = (Task1)stage.getUserData();
    	lb1.setText(task1.summary);
    }
    

}