package comp3111.popnames.controllers;

import comp3111.popnames.applications.CompatibilityPredictor;
import comp3111.popnames.metrics.Metrics;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The controller for rendering the result page in task 6.
 */
public class ResultController {

    private PreferencesController previousController;
    private BasicInfoController basicInfoController;
    private Stage previousStage;

    @FXML
    private Text score;

    @FXML
    private Button showDetailsBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button backBtn;

    @FXML
    private StackedBarChart<Number, String> metricsBarChart;

    @FXML
    private StackedBarChart<Number, String> scoresBarChart;

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
    void onShowDetailsPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/score_predictor_ui/result_details_ui.fxml"));
        Parent root = (Parent) loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Compatibility Score Predictor");
        stage.setScene(new Scene(root));
        stage.setResizable(false);

        stage.show();
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

        initScoresBarChart();
        initMetricsChart();
    }

    /**
     * Set the previous stage and controller
     * @param stage previous stage
     * @param controller previous controller
     */
    public void setParent(Stage stage, PreferencesController controller) {
        previousController = controller;
        previousStage = stage;
    }

    /**
     * Set the previous stage and controller
     * @param stage previous stage
     * @param controller previous controller
     */
    public void setParent(Stage stage, BasicInfoController controller) {
        basicInfoController = controller;
        previousStage = stage;
    }

    private void initMetricsChart() {
        final CategoryAxis yAxis = (CategoryAxis) metricsBarChart.getYAxis();
        final NumberAxis xAxis = (NumberAxis) metricsBarChart.getXAxis();
        yAxis.setCategories(FXCollections.observableArrayList("Metrics"));
        int unfinished = 0, low = 0, medium = 0, high = 0;

        CompatibilityPredictor predictor = CompatibilityPredictor.getInstance();
        for (Metrics metric : predictor.metrics) {
            double score = metric.getScore();
            if (score >= 0.0) {
                if (score <= 0.33) {
                    low++;
                } else if (score <= 0.67) {
                    medium++;
                } else {
                    high++;
                }
            } else {
                unfinished++;
            }
        }

        final XYChart.Series<Number, String> seriesLow = new XYChart.Series<>();
        seriesLow.setName("Low Score");
        seriesLow.getData().add(new XYChart.Data<>(low, "Metrics"));

        final XYChart.Series<Number, String> seriesMedium = new XYChart.Series<>();
        seriesMedium.setName("Medium Score");
        seriesMedium.getData().add(new XYChart.Data<>(medium, "Metrics"));

        final XYChart.Series<Number, String> seriesHigh = new XYChart.Series<>();
        seriesHigh.setName("High Score");
        seriesHigh.getData().add(new XYChart.Data<>(high, "Metrics"));

        final XYChart.Series<Number, String> seriesIncomplete = new XYChart.Series<>();
        seriesIncomplete.setName("Incomplete");
        seriesIncomplete.getData().add(new XYChart.Data<>(unfinished, "Metrics"));

        metricsBarChart.getData().addAll(seriesLow, seriesMedium, seriesHigh, seriesIncomplete);
    }

    private void initScoresBarChart() {
        final CategoryAxis yAxis = (CategoryAxis) scoresBarChart.getYAxis();
        final NumberAxis xAxis = (NumberAxis) scoresBarChart.getXAxis();
        yAxis.setCategories(FXCollections.observableArrayList("Scores"));
        CompatibilityPredictor predictor = CompatibilityPredictor.getInstance();

        for (Metrics metric : predictor.metrics) {
            final XYChart.Series<Number, String> series = new XYChart.Series<>();
            double score = metric.getScore();
            if (score >= 0.0) {
                series.setName(metric.getMetricName());
                series.getData().add(new XYChart.Data<>(metric.getScore() * metric.weight() * 100, "Scores"));
                scoresBarChart.getData().add(series);
            }
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
