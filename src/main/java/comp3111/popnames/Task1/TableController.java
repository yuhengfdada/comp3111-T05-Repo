package comp3111.popnames.Task1;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class TableController {

    @FXML
    private TableView<Task1> table;
    @FXML
    private TableColumn<Task1, String> nameColumn;
    @FXML
    private TableColumn<Task1, Integer> occColumn;
    @FXML
    private TableColumn<Task1, String> percentageColumn;
    
    @FXML
    private Button back;

    private Task1 task1;
    
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
    
    public void appearTable(Stage stage) {
    	task1 = (Task1)stage.getUserData();
    	
    	// lb1.setText(task1.getSummaryAndStore(task1.getYear()));
    }
}
