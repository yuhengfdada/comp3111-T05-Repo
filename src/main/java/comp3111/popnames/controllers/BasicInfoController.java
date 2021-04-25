package comp3111.popnames.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import comp3111.popnames.applications.CompatibilityPredictor;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

public class BasicInfoController {

    private CompatibilityPredictor predictor;

    private static final ObservableList<String> genderList;
    private static final ObservableList<String> algorithmList;

    static {
        genderList = FXCollections.observableArrayList("Male", "Female", "Non-binary");
        algorithmList = FXCollections.observableArrayList("Basic", "Data based");
    }

    @FXML
    public void initialize() {
        selfGender.setItems(genderList);
        mateGender.setItems(genderList);
        algorithm.setItems(algorithmList);
    }

    @FXML
    private Button nextBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField selfName;

    @FXML
    private ChoiceBox<String> selfGender;

    @FXML
    private ChoiceBox<String> mateGender;

    @FXML
    private ChoiceBox<String> algorithm;

    @FXML
    private TextField selfYob;

    @FXML
    private TextField mateName;

    @FXML
    void onCancelBtnPressed(ActionEvent event) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onNextBtnPressed(ActionEvent event) throws IOException {
        if (validateInput()) {
            setValues();
        } else {
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        if (algorithm.getSelectionModel().getSelectedIndex() == 0) {
            loader.setLocation(getClass().getResource("/score_predictor_ui/result_ui.fxml"));
        } else {
            loader.setLocation(getClass().getResource("/score_predictor_ui/add_info_ui.fxml"));
        }

        Parent root = (Parent) loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Compatibility Score Predictor");
        stage.setScene(new Scene(root));
        stage.setResizable(false);

        Stage curStage = (Stage) nextBtn.getScene().getWindow();

        if (algorithm.getSelectionModel().getSelectedIndex() == 0) {
            ResultController controller = loader.getController();
            controller.setParent(curStage, this);
        } else {
            AdditionalInfoController controller = loader.getController();
            controller.setParent(curStage, this);
        }

        curStage.hide();
        stage.show();
    }

    private void setValues() {
        CompatibilityPredictor predictor = CompatibilityPredictor.getInstance();
        char sGender = genderToChar(selfGender.getSelectionModel().getSelectedItem());
        char mGender = genderToChar(mateGender.getSelectionModel().getSelectedItem());
        predictor.setSelfInfo(selfName.getText().trim().toLowerCase(Locale.ROOT),
                Integer.parseInt(selfYob.getText()), sGender);
        predictor.setMateInfo(mateName.getText().trim().toLowerCase(Locale.ROOT), mGender);
        predictor.algorithm(
                CompatibilityPredictor.Algorithm.values()[algorithm.getSelectionModel().getSelectedIndex()]);
    }

    private boolean hasDigit(String input) {
        String regex = ".*[0-9].*";
        return input.matches(regex);
    }

    private char genderToChar(String gender) {
        return gender.charAt(0);
    }

    private boolean validateInput() {
        StringBuilder errorMsg = new StringBuilder();
        String sName = selfName.getText().trim();
        if (sName.isEmpty()) {
            errorMsg.append("- Please enter your first name.\n");
        } else if (hasDigit(sName)) {
            errorMsg.append("- Please enter a valid name.\n");
        }

        String mName = mateName.getText().trim();
        if (mName.isEmpty()) {
            errorMsg.append("- Please enter your mate's name.\n");
        } else if (hasDigit(mName)) {
            errorMsg.append("- Please enter a valid name.\n");
        }

        String sGender = selfGender.getSelectionModel().getSelectedItem();
        if (sGender == null) {
            errorMsg.append("- Please select your gender.\n");
        }

        String mGender = mateGender.getSelectionModel().getSelectedItem();
        if (mGender == null) {
            errorMsg.append("- Please select your mate's gender.\n");
        }

        try {
            int sYob = Integer.parseInt(selfYob.getText());
            if (sYob < 1880 || sYob > 2021) {
                errorMsg.append("- Please enter a valid year of birth (1880-2021).\n");
            }
        } catch (NumberFormatException e) {
            errorMsg.append("- Please enter a valid year of birth (1880-2021).\n");
        }

        String algo = algorithm.getSelectionModel().getSelectedItem();
        if (algo == null) {
            errorMsg.append("- Please choose an algorithm to be used.\n");
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

    /**
     * Close the current stage
     */
    public void close() {
        Stage stage = (Stage) nextBtn.getScene().getWindow();
        stage.close();
    }

}

