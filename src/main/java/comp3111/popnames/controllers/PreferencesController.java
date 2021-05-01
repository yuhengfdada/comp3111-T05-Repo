package comp3111.popnames.controllers;

import comp3111.popnames.metrics.AgeMetrics;
import comp3111.popnames.applications.CompatibilityPredictor;
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

/**
 * The controller for inputting the preferences in task 6.
 */
public class PreferencesController {

    private static final ObservableList<String> agePrefList, rarityPrefList;
    private Stage previousStage;
    private AdditionalInfoController previousController;

    static {
        agePrefList = FXCollections.observableArrayList("Younger", "Older");
        rarityPrefList = FXCollections.observableArrayList("Popular", "Mediocre", "Rare", "No Preference");
    }

    @FXML
    private ChoiceBox<String> agePref;

    @FXML
    private ChoiceBox<String> rarityPref;

    @FXML
    private TextArea nameTheme;

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

    /**
     * Initialize the controller.
     */
    @FXML
    public void initialize() {
        agePref.setItems(agePrefList);
        rarityPref.setItems(rarityPrefList);
        rarityPref.getSelectionModel().select(3);
        CompatibilityPredictor predictor = CompatibilityPredictor.getInstance();

        String promptText = " You can base your choice on subjective feelings about the name.";

        StringBuilder theme = new StringBuilder();
        theme.append("The meanings or associations behind your name: ");
        String selfTheme = predictor.themeAnalyzer.getNameTheme(predictor.selfName);
        theme.append(Objects.requireNonNullElse(selfTheme,
                "\nSorry, the theme of your name does not exist in our database." + promptText));

        theme.append("\n\nThe meanings or associations behind your mate's name: ");
        String mateTheme = predictor.themeAnalyzer.getNameTheme(predictor.mateName);
        theme.append(Objects.requireNonNullElse(mateTheme,
                "\nSorry, the theme of your mate's name does not exist in our database." + promptText));

        nameTheme.setText(theme.toString());

        StringBuilder meaning = new StringBuilder();
        meaning.append("The detailed meanings behind your name:\n");
        String selfMeaning = predictor.themeAnalyzer.getNameFullMeaning(predictor.selfName);
        meaning.append(Objects.requireNonNullElse(selfMeaning,
                "Sorry, the meaning of your name does not exist in our database." + promptText));

        meaning.append("\n\nThe detailed meanings behind your mate's name:\n");
        String mateMeaning = predictor.themeAnalyzer.getNameFullMeaning(predictor.mateName);
        meaning.append(Objects.requireNonNullElse(mateMeaning,
                "Sorry, the meaning of your mate's name does not exist in our database." + promptText));

        nameMeaning.setText(meaning.toString());
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
        if (!validateInput()) {
            return;
        }
        setValues();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/score_predictor_ui/result_ui.fxml"));
        Parent root = loader.load();
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

    private boolean validateInput() {
        StringBuilder errorMsg = new StringBuilder();
        String agePreference = agePref.getSelectionModel().getSelectedItem();
        if (agePreference == null) {
            errorMsg.append("- Please specify your age preference.\n");
        }

        if (errorMsg.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Required Fields Empty/Invalid");
            alert.setContentText(errorMsg.toString());

            alert.showAndWait();
            return false;
        }
        return true;
    }

    private void setValues() {
        CompatibilityPredictor predictor = CompatibilityPredictor.getInstance();
        predictor.themeMetrics.themeSuitability(themeSuitability.getValue() * 0.2);
        predictor.meanMetrics.meanSuitability(meanSuitability.getValue() * 0.2);
        predictor.ageMetrics.agePref =
                AgeMetrics.AgePreference.values()[agePref.getSelectionModel().getSelectedIndex()];
        predictor.propertyMetrics.setRarityPref(rarityPref.getSelectionModel().getSelectedIndex());
        predictor.propertyMetrics.setDirty();
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
