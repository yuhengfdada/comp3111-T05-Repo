package comp3111.popnames.Task2;

import javafx.scene.input.KeyCode;
import org.testfx.framework.junit.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.Test;

public class ControllerTest extends ApplicationTest{
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
    public void testTask2_BCController() {
        clickOn("#tabReport2");
        clickOn("#input_starting_year");
        write("1880");
        clickOn("#input_ending_year");
        write("1890");
        clickOn("#input_ranking");
        write("5");
        clickOn("#inputGender");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#show_summary");
        clickOn("#task2_bar_chart");
    }

    @Test
    public void testTask2_DTController(){
        clickOn("#tabReport2");
        clickOn("#input_starting_year");
        write("1880");
        clickOn("#input_ending_year");
        write("1890");
        clickOn("#input_ranking");
        write("5");
        clickOn("#inputGender");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#task2_data_table");
    }

    @Test
    public void testTask2_PCController(){
        clickOn("#tabReport2");
        clickOn("#input_starting_year");
        write("1880");
        clickOn("#input_ending_year");
        write("1890");
        clickOn("#input_ranking");
        write("5");
        clickOn("#inputGender");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#task2_pie_chart");
    }


}
