package comp3111.popnames.controllers;

import comp3111.popnames.predictor.CompatibilityPredictor;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class ResultController {

    private PreferencesController previousController;
    private BasicInfoController basicInfoController;
    private Stage previousStage;

    @FXML
    private Text score;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button backBtn;

    @FXML
    private TextFlow report;

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
    public void initialize() {
        CompatibilityPredictor predictor = CompatibilityPredictor.getInstance();
        score.setText(Integer.toString((int) (predictor.predict() * 100)));
        setReport();
    }

    public void setParent(Stage stage, PreferencesController controller) {
        previousController = controller;
        previousStage = stage;
    }

    public void setParent(Stage stage, BasicInfoController controller) {
        basicInfoController = controller;
        previousStage = stage;
    }

    private void setReport() {
        CompatibilityPredictor predictor = CompatibilityPredictor.getInstance();
        ObservableList<Node> list = report.getChildren();
        if (predictor.algorithm() == CompatibilityPredictor.Algorithm.BASIC) {
            list.add(new Text(predictor.getBasicReport()));
            return;
        }
    }

    private void close() {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
        if (basicInfoController != null) {
            basicInfoController.close();
        } else {
            previousController.close();
        }
    }

}
