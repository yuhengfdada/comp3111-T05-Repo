package comp3111.popnames.Task4;
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
    public void testTask4Controller() {
    	clickOn("#tabApp1");
    	clickOn("#t4_momYOB");
    	write("1880");
    	clickOn("#t4_dadYOB");
    	write("2010");
    	clickOn("#t4_generate");
    	
    	clickOn("#back");
    	
    	clickOn("#tabApp1");
    	clickOn("#t4_a2");
    	clickOn("#t4_momYOB2");
    	write("1880");
    	clickOn("#t4_dadYOB2");
    	write("2010");
    	clickOn("#t4_momName");
    	write("Luna");
    	clickOn("#t4_dadName");
    	write("Solar");
    	clickOn("#t4_generate2");
    	
    	clickOn("#back");
    }
    
    @Test
    public void errorTest() {
    	clickOn("#tabApp1");
    	clickOn("#t4_generate");
    	
    	clickOn("#t4_momYOB");
    	write("1800");
    	clickOn("#t4_dadYOB");
    	write("1800");
    	clickOn("#t4_generate");
    	
    	clickOn("#t4_a2");
    	clickOn("#t4_generate2");
    	
    	clickOn("#t4_momYOB2");
    	write("1800");
    	clickOn("#t4_dadYOB2");
    	write("1800");
    	clickOn("#t4_momName");
    	write("Luna");
    	clickOn("#t4_dadName");
    	write("Solar");
    	clickOn("#t4_generate2");
    	
    }
    
    @Test
    public void errorTest2() {
    	clickOn("#tabApp1");
    	clickOn("#t4_a2");
    	clickOn("#t4_momYOB2");
    	write("1880");
    	clickOn("#t4_dadYOB2");
    	write("1880");
    	clickOn("#t4_momName");
    	write("@@");
    	clickOn("#t4_dadName");
    	write("Solar");
    	clickOn("#t4_generate2");
    }
}