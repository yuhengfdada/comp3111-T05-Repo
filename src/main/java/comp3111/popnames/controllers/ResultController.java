package comp3111.popnames.controllers;

import comp3111.popnames.metrics.Metrics;
import comp3111.popnames.predictor.CompatibilityPredictor;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

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
        double rawScore = predictor.predict();
        score.setText(Integer.toString((int) (rawScore * 100)));

        if (rawScore < 0.33) {
            score.setFill(Color.RED);
        } else if (rawScore < 0.67) {
            score.setFill(Color.ORANGE);
        } else {
            score.setFill(Color.GREEN);
        }

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
        } else {
            assembleReport();
        }
    }

    private void assembleReport() {
        CompatibilityPredictor predictor = CompatibilityPredictor.getInstance();
        ObservableList<Node> list = report.getChildren();

        for (Metrics metric : predictor.metrics) {
            list.add(new Text(metric.getMetricName() + ": "));
            list.add(metric.getFormattedScore());
            list.add(new Text("\n" + metric.getExplanation() + "\n\n"));
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
