package comp3111.popnames.Task5;

import javafx.scene.input.KeyCode;
import org.testfx.framework.junit.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.Test;

public class ControllerTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ui.fxml"));
        VBox root = (VBox) loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Popular Names");
        stage.show();
    }

    @Test
    public void testTask5_X2Controller() {
        clickOn("#tabApp2");
        clickOn("#Year_of_born");
        write("1880");
        clickOn("#input_Name");
        write("Amy");
        clickOn("#inputGenderOfInterest");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#yourGender");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#agePreference");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#T5_algo2");
    }
}