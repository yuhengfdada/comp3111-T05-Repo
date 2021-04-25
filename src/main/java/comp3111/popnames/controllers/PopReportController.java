package comp3111.popnames.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.text.TextFlow;

public class PopReportController {

    @FXML
    private Tab summaryTab;

    @FXML
    private TextFlow summaryTextFlow;

    @FXML
    private Tab tableViewTab;

    @FXML
    private TableView<?> tableView;

    @FXML
    private Tab barChartTab;

    @FXML
    private BarChart<?, ?> barChart;

    @FXML
    private Tab lineChartTab;

    @FXML
    private LineChart<?, ?> lineChart;

}
