package comp3111.popnames.Task1;
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
    public void testTask1Controller() {
    	clickOn("#tabReport1");
    	clickOn("#t1_year");
    	write("1880");
    	clickOn("#t1_name");
    	write("10");
    	clickOn("#t1_generate");
    	
    	clickOn("#viewTableBut");
    	clickOn("#back");
    	clickOn("#viewBCBut");
    	clickOn("#back");
    	clickOn("#viewPCBut");
    	clickOn("#back");
    	
    	clickOn("#back");
    }
    
    @Test
    public void errorTest() {
    	clickOn("#tabReport1");
    	clickOn("#t1_generate");
    	
    	clickOn("#t1_year");
    	write("1800");
    	clickOn("#t1_name");
    	write("1800");
    	clickOn("#t1_generate");
    }
}
