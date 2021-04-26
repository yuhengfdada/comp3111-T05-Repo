package comp3111.popnames.controllers;

import comp3111.popnames.applications.PopularityReport;
import comp3111.popnames.record.NameAnalyzer.NameQuery;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.util.ArrayList;

public class PopReportController {

    private ReportInputController prevController;
    private Stage prevStage;

    @FXML
    private Tab summaryTab;

    @FXML
    private TextFlow summaryTextFlow;

    @FXML
    private Tab tableViewTab;

    @FXML
    private TableView<NameQuery> tableView;

    @FXML
    private Tab barChartTab;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private Tab lineChartTab;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    public void initialize() {
        initSummary();
        initTableView();
        initBarChart();
        initLineChart();
    }

    private void initSummary() {
        PopularityReport report = PopularityReport.getInstance();
        Text summary = new Text(report.getSummary());
        summary.setFont(new Font(13));
        summaryTextFlow.getChildren().add(summary);
    }

    private void initTableView() {
        TableColumn<NameQuery, Integer> columnYear = new TableColumn<>("Year");
        columnYear.setCellValueFactory(new PropertyValueFactory<>("year"));

        TableColumn<NameQuery, Integer> columnRank = new TableColumn<>("Rank");
        columnRank.setCellValueFactory(new PropertyValueFactory<>("rank"));

        TableColumn<NameQuery, Integer> columnOccur = new TableColumn<>("Occurrences");
        columnOccur.setCellValueFactory(new PropertyValueFactory<>("occurrence"));

        TableColumn<NameQuery, String> columnPercentage = new TableColumn<>("Percentage");
        columnPercentage.setCellValueFactory(cellData -> cellData.getValue().percentageProperty());

        tableView.getColumns().add(columnYear);
        tableView.getColumns().add(columnRank);
        tableView.getColumns().add(columnOccur);
        tableView.getColumns().add(columnPercentage);

        PopularityReport report = PopularityReport.getInstance();
        ArrayList<NameQuery> query = report.query();
        for (NameQuery item : query) {
            tableView.getItems().add(item);
        }

        tableView.setPlaceholder(new Label("No records to display"));
    }

    private void initBarChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Year");
        yAxis.setLabel("Occurrences");

        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        PopularityReport report = PopularityReport.getInstance();
        ArrayList<NameQuery> query = report.query();
        dataSeries.setName(String.format("Occurrences of the Name \"%s\"", report.name()));
        for (NameQuery item : query) {
            dataSeries.getData().add(new XYChart.Data<>(Integer.toString(item.getYear()), item.getOccurrence()));
        }
        barChart.getData().add(dataSeries);
        barChart.setTitle("Occurrences by Year");
    }

    private void initLineChart() {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Year");
        yAxis.setLabel("Occurrences");

        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        PopularityReport report = PopularityReport.getInstance();
        ArrayList<NameQuery> query = report.query();
        dataSeries.setName(String.format("Occurrences of the Name \"%s\"", report.name()));
        for (NameQuery item : query) {
            dataSeries.getData().add(new XYChart.Data<>(Integer.toString(item.getYear()), item.getOccurrence()));
        }
        lineChart.getData().add(dataSeries);
        lineChart.setTitle("Occurrences by Year");
    }

    /**
     * Set the previous stage and controller
     * @param stage the previous stage
     * @param controller the previous controller
     */
    public void setParent(Stage stage, ReportInputController controller) {
        prevStage = stage;
        prevController = controller;
    }

}
