package comp3111.popnames.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

public class PopReportControllersTest extends ApplicationTest {
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
    public void testPopReport() {
        clickOn("#tabReport3");
        clickOn("#popReportStartBtn");

        write("1880");
        clickOn("#endYearTextField");
        write("2019");
        clickOn("#nameTextField");
        write("Emily");
        clickOn("#genderChoice");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#confirmBtn");

        clickOn("#tableViewTab");
        clickOn("#barChartTab");
        clickOn("#barChartType");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#barChartExtraName");
        write("Alice");
        clickOn("#barChartAddName");
        clickOn("#lineChartTab");
        clickOn("#lineChartType");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#lineChartReset");
    }
}
