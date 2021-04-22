package comp3111.popnames.controllers;

import comp3111.popnames.metrics.EducationalMetrics.EducationalLevel;
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

public class AdditionalInfoController {

    private static final ObservableList<String> eduLevelList;
    private static final ObservableList<String> incomeLevelList;
    private static final ObservableList<String> kidsPreferenceList;
    private Stage previousStage;
    private BasicInfoController previousController;

    static {
        eduLevelList = FXCollections.observableArrayList("Elementary school or below", "Middle school",
                "High school", "Bachelor's degree", "Master's degree", "PhD or above", "Unknown/Not Applicable");
        incomeLevelList = FXCollections.observableArrayList("Lowest 25%", "Middle 50%", "Top 25%",
                "Unknown/Not Applicable");
        kidsPreferenceList = FXCollections.observableArrayList("Two or more kids", "One kid", "No kid",
                "Unknown/Not Applicable");
    }

    @FXML
    private Slider selfEduSig;

    @FXML
    private ChoiceBox<String> selfEduLevel;

    @FXML
    private ChoiceBox<String> mateEduLevel;

    @FXML
    private Slider mateEduSig;

    @FXML
    private ChoiceBox<String> selfIncomeLevel;

    @FXML
    private ChoiceBox<String> mateIncomeLevel;

    @FXML
    private Slider selfIncomeSig;

    @FXML
    private Slider mateIncomeSig;

    @FXML
    private ChoiceBox<String> selfKids;

    @FXML
    private ChoiceBox<String> mateKids;

    @FXML
    private Button nextBtn;

    @FXML
    private Button backBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField mateYob;

    @FXML
    public void initialize() {
        selfEduLevel.setItems(eduLevelList);
        mateEduLevel.setItems(eduLevelList);
        selfIncomeLevel.setItems(incomeLevelList);
        mateIncomeLevel.setItems(incomeLevelList);
        selfKids.setItems(kidsPreferenceList);
        mateKids.setItems(kidsPreferenceList);

        selfEduLevel.getSelectionModel().select("Unknown/Not Applicable");
        mateEduLevel.getSelectionModel().select("Unknown/Not Applicable");
        selfIncomeLevel.getSelectionModel().select("Unknown/Not Applicable");
        mateIncomeLevel.getSelectionModel().select("Unknown/Not Applicable");
        selfKids.getSelectionModel().select("Unknown/Not Applicable");
        mateKids.getSelectionModel().select("Unknown/Not Applicable");
    }

    @FXML
    void onBackBtnPressed(ActionEvent event) {
        previousStage.show();
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onCancelBtnPressed(ActionEvent event) {
        close();
    }

    @FXML
    void onNextBtnPressed(ActionEvent event) throws IOException {
        if (validateInput()) {
            setValues();
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/score_predictor_ui/preferences_ui.fxml"));
        Parent root = (Parent) loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Compatibility Score Predictor");
        stage.setScene(new Scene(root));
        stage.setResizable(false);

        Stage curStage = (Stage) nextBtn.getScene().getWindow();
        PreferencesController controller = loader.getController();
        controller.setParent(curStage, this);
        curStage.hide();
        stage.show();
    }

    private void setValues() {
        CompatibilityPredictor predictor = CompatibilityPredictor.getInstance();
        predictor.eduMetrics.setSelfEdu(selfEduLevel.getSelectionModel().getSelectedIndex());
        predictor.eduMetrics.setMateEdu(mateEduLevel.getSelectionModel().getSelectedIndex());
        predictor.incomeMetrics.setSelf(selfIncomeLevel.getSelectionModel().getSelectedIndex());
        predictor.incomeMetrics.setMate(mateIncomeLevel.getSelectionModel().getSelectedIndex());
        predictor.miscMetrics.setSelfKids(selfKids.getSelectionModel().getSelectedIndex());
        predictor.miscMetrics.setMateKids(mateKids.getSelectionModel().getSelectedIndex());

        if (!mateYob.getText().trim().isEmpty()) {
            predictor.setMateYob(Integer.parseInt(mateYob.getText()));
        }
    }

    private boolean validateInput() {
        String mYob = mateYob.getText().trim();
        StringBuilder errorMsg = new StringBuilder();
        if (!mYob.isEmpty()) {
            try {
                int year = Integer.parseInt(mYob);
                if (year < 1880 || year > 2021) {
                    errorMsg.append("- Please enter a valid year.\n");
                }
            } catch (NumberFormatException e) {
                errorMsg.append("- Please enter a valid year.\n");
            }
        }

        if (errorMsg.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Input Values Invalid");
            alert.setContentText(errorMsg.toString());

            alert.showAndWait();
            return false;
        }

        return true;
    }

    /**
     * Set the previous stage and controller
     * @param stage previous stage
     * @param controller previous controller
     */
    public void setParent(Stage stage, BasicInfoController controller) {
        previousStage = stage;
        previousController = controller;
    }

    /**
     * Close the controller and the previous controller
     */
    public void close() {
        Stage stage = (Stage) nextBtn.getScene().getWindow();
        stage.close();
        previousController.close();
    }

}

