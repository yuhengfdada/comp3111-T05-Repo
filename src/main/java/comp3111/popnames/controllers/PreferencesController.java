package comp3111.popnames.controllers;

import comp3111.popnames.predictor.CompatibilityPredictor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class PreferencesController {

    private static final ObservableList<String> agePrefList;
    private String theme, meaning;
    private Stage previousStage;
    private AdditionalInfoController previousController;

    static {
        agePrefList = FXCollections.observableArrayList("Younger", "Older");
    }

    @FXML
    private ChoiceBox<String> agePref;

    @FXML
    private TextField nameTheme;

    @FXML
    private TextArea nameMeaning;

    @FXML
    private Button nextBtn;

    @FXML
    private Button backBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private Slider themeSuitability;

    @FXML
    private Slider meanSuitability;

    @FXML
    public void initialize() {
        agePref.setItems(agePrefList);
        CompatibilityPredictor predictor = CompatibilityPredictor.getInstance();
        theme = predictor.themeAnalyzer.getNameTheme(predictor.mateName());
        nameTheme.setText(Objects.requireNonNullElse(theme,
                "Sorry, the theme of your mate's name does not exist in our database."));

        meaning = predictor.themeAnalyzer.getNameMeaning(predictor.mateName());
        nameMeaning.setText(Objects.requireNonNullElse(meaning,
                "Sorry, the meaning of your mate's name does not exist in our database."));
    }

    @FXML
    void onBackBtnPressed(ActionEvent event) {
        previousStage.show();
        Stage curStage = (Stage) backBtn.getScene().getWindow();
        curStage.close();
    }

    @FXML
    void onCancelBtnPressed(ActionEvent event) {
        close();
    }

    @FXML
    void onNextBtnPressed(ActionEvent event) throws IOException {
        setValues();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/score_predictor_ui/result_ui.fxml"));
        Parent root = (Parent) loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Compatibility Score Predictor");
        stage.setScene(new Scene(root));
        stage.setResizable(false);

        Stage curStage = (Stage) nextBtn.getScene().getWindow();
        ResultController controller = loader.getController();
        controller.setParent(curStage, this);
        curStage.hide();
        stage.show();
    }

    private void setValues() {
        CompatibilityPredictor predictor = CompatibilityPredictor.getInstance();
        predictor.miscMetrics.themeSuitability(themeSuitability.getValue());
        predictor.miscMetrics.meanSuitability(meanSuitability.getValue());
    }

    /**
     * Set the previous stage and controller
     * @param stage the previous stage
     * @param controller the previous controller
     */
    public void setParent(Stage stage, AdditionalInfoController controller) {
        previousStage = stage;
        previousController = controller;
    }

    /**
     * Close the current and previous stage
     */
    public void close() {
        Stage stage = (Stage) nextBtn.getScene().getWindow();
        stage.close();
        previousController.close();
    }

}
