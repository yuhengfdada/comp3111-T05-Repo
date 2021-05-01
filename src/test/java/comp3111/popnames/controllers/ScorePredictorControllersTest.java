package comp3111.popnames.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.TextMatchers;

public class ScorePredictorControllersTest extends ApplicationTest {

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
    public void testBasicPredictor() {
        clickOn("#tabApp3");
        clickOn("#scorePredStartBtn");
        clickOn("#cancelBtn");
        clickOn("#scorePredStartBtn");

        clickOn("#selfName");
        write("Rex");

        clickOn("#mateName");
        write("Hikari");

        clickOn("#selfGender");
        type(KeyCode.ENTER);

        clickOn("#mateGender");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        clickOn("#selfYob");
        write("2018");

        clickOn("#algorithm");
        type(KeyCode.ENTER);

        clickOn("#nextBtn");
        clickOn("#backBtn");
        clickOn("#nextBtn");

        FxAssert.verifyThat("#score", TextMatchers.hasText("0"));

        clickOn("#backBtn");
        clickOn("#cancelBtn");
    }

    @Test
    public void testDataBasedPredictor() {
        clickOn("#tabApp3");
        clickOn("#scorePredStartBtn");

        clickOn("#selfName");
        write("Cody");
        clickOn("#mateName");
        write("May");
        clickOn("#selfGender");
        type(KeyCode.ENTER);
        clickOn("#mateGender");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#selfYob");
        write("2018");
        clickOn("#algorithm");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#nextBtn");

        clickOn("#selfEduLevel");
        type(KeyCode.ENTER);
        clickOn("#mateEduLevel");
        type(KeyCode.ENTER);
        clickOn("#mateIncomeLevel");
        type(KeyCode.ENTER);
        clickOn("#selfKids");
        type(KeyCode.ENTER);
        clickOn("#mateKids");
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        clickOn("#mateYob");
        write("2019");
        clickOn("#nextBtn");

        clickOn("#agePref");
        type(KeyCode.ENTER);
        clickOn("#nextBtn");

        clickOn("#backBtn");
        clickOn("#nextBtn");
        FxAssert.verifyThat("#score", TextMatchers.hasText("33"));
        clickOn("#showDetailsBtn");
        FxAssert.verifyThat("#score", TextMatchers.hasText("33"));
        clickOn("#backBtn");
        clickOn("#cancelBtn");
    }

}
