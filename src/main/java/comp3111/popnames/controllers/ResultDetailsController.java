package comp3111.popnames.controllers;

import comp3111.popnames.applications.CompatibilityPredictor;
import comp3111.popnames.metrics.Metrics;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * The controller for rendering the detailed report in task 6.
 */
public class ResultDetailsController {

    @FXML
    private Text score;

    @FXML
    private Button backBtn;

    @FXML
    private TextFlow report;

    @FXML
    void onBackBtnPressed(ActionEvent event) {
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.close();
    }

    /**
     * Initialize the controller.
     */
    @FXML
    public void initialize() {
        CompatibilityPredictor predictor = CompatibilityPredictor.getInstance();
        double rawScore = predictor.predict();
        score.setText(String.valueOf(Math.round(rawScore * 100)));

        if (rawScore < 0.33) {
            score.setFill(Color.RED);
        } else if (rawScore < 0.67) {
            score.setFill(Color.ORANGE);
        } else {
            score.setFill(Color.GREEN);
        }

        setReport();
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
            list.add(new Text(metric.getMetricDescription() + ": "));
            list.add(metric.getFormattedScore());
            list.add(new Text("\n" + metric.getExplanation() + "\n\n"));
        }
    }

}