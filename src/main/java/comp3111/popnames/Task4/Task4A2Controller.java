package comp3111.popnames.Task4;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

import comp3111.popnames.Task1.Task1;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Task4A2Controller {


    @FXML
    private Label t4a2_rationale;

    @FXML
    private Label t4a2_title;

    @FXML
    private Label t4a2_title1;

    @FXML
    private Label t4a2_rationale1;
    
    @FXML
    private Button back;

    @FXML
    void goBack(ActionEvent event) throws IOException {
    	Parent task1Parent = FXMLLoader.load(getClass().getResource("/ui.fxml"));
    	Scene task1Scene = new Scene(task1Parent);
    	
    	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(task1Scene);
    	window.show();
    }
    
    public void appear(Stage stage) {
    	stage.setTitle("Name Recommendation");
    	Task4A2 task4 = (Task4A2)stage.getUserData();
    	task4.getMostPopNameAndStore();
    	t4a2_title.setText(String.format("The recommended boy's name: %s",task4.selectedPopBoyString));
    	t4a2_rationale.setText(task4.summaryB);
    	t4a2_title1.setText(String.format("The recommended girl's name: %s",task4.selectedPopGirlString));
    	t4a2_rationale1.setText(task4.summaryG);
    }

}