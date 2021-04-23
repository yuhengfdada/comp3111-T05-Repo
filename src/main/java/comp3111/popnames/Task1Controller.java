package comp3111.popnames;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.scene.control.Label;

public class Task1Controller {
    @FXML
    private Label lb1;
    @FXML
    private Button back;
    @FXML
    private Button rwkk;
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
}