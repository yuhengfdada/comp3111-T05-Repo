package comp3111.popnames.Task5;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * The controller for showing result of algorithm 2 in task 5.
 * @author LUO, Yuqing
 */
public class Task5_X2Controller {

    @FXML
    private TextArea text_area;

    /**
     * Initialize the controller.
     */
    public void initialize(){
        text_area.setText(Task5_X2_NameAnalyzer.oReport);
    }

    public void start() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/T5X2_Controller.fxml"));
        Stage stage = new Stage();
        VBox root = loader.load();
        stage.setTitle("Details of recommended soulmate");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

}
