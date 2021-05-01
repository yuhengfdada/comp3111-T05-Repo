package comp3111.popnames.controllers;

import comp3111.popnames.applications.PopularityReport;
import comp3111.popnames.record.NameAnalyzer.NameQuery;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Locale;

public class PopReportController {

    private enum QueryType {
        OCCURRENCES, RANK
    }

    private static final ObservableList<String> typeList;

    static {
        typeList = FXCollections.observableArrayList("Occurrences", "Ranks");
    }

    private ReportInputController prevController;
    private Stage prevStage;
    private String name, nameCmp;
    private ArrayList<NameQuery> query, queryCmp;

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
    private ChoiceBox<String> barChartType;

    @FXML
    private TextField barChartExtraName;

    @FXML
    private Button barChartAddName;

    @FXML
    private Button barChartReset;

    @FXML
    private Tab lineChartTab;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private ChoiceBox<String> lineChartType;

    @FXML
    private TextField lineChartExtraName;

    @FXML
    private Button lineChartAddName;

    @FXML
    private Button lineChartReset;

    @FXML
    void onBarChartAddName(ActionEvent event) {
        if (!validateInput(barChartExtraName.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Name Input Invalid/Not Found in Database");
            Label text = new Label("Note that the name added for comparison shares the\n" +
                                   "same year range and gender with previous query.\n\n" +
                                   "Please retry with different input.");
            text.setWrapText(true);
            alert.getDialogPane().setContent(text);
            alert.showAndWait();
        } else {
            nameCmp = barChartExtraName.getText().trim();
            lineChartExtraName.setText(nameCmp);
            updateBarChart();
            updateLineChart();
        }
    }

    @FXML
    void onBarChartReset(ActionEvent event) {
        if (nameCmp != null) {
            nameCmp = null;
            queryCmp.clear();
            updateBarChart();
            updateLineChart();
        }
    }

    @FXML
    void onLineChartAddName(ActionEvent event) {
        if (!validateInput(lineChartExtraName.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Name Input Invalid/Not Found in Database");
            Label text = new Label("Note that the name added for comparison shares the\n" +
                                   "same year range and gender with previous query.\n\n" +
                                   "Please retry with different input.");
            text.setWrapText(true);
            alert.getDialogPane().setContent(text);
            alert.showAndWait();
        } else {
            nameCmp = lineChartExtraName.getText().trim();
            barChartExtraName.setText(nameCmp);
            updateBarChart();
            updateLineChart();
        }
    }

    @FXML
    void onLineChartReset(ActionEvent event) {
        onBarChartReset(event);
    }

    @FXML
    public void initialize() {
        PopularityReport report = PopularityReport.getInstance();
        name = report.name();
        query = report.query();

        barChartType.setItems(typeList);
        lineChartType.setItems(typeList);

        initSummary();
        initTableView();
        initBarChart();
        initLineChart();

        lineChartType.getSelectionModel().select(0);
        lineChartType.getSelectionModel().selectedIndexProperty().addListener((new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                updateLineChart();
            }
        }));

        barChartType.getSelectionModel().select(0);
        barChartType.getSelectionModel().selectedIndexProperty().addListener((new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                updateBarChart();
            }
        }));
    }

    private void initSummary() {
        PopularityReport report = PopularityReport.getInstance();
        Text summary = new Text(report.getSummary());
        summary.setFont(new Font(14));
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
        XYChart.Series<String, Number> dataSeries = getDataSeries(query, QueryType.OCCURRENCES, name);
        barChart.getData().add(dataSeries);
        barChart.setTitle("Occurrences/Rank by Year");
        barChart.getXAxis().setLabel("Year");
        barChart.getYAxis().setLabel("Occurrences");
    }

    private void initLineChart() {
        XYChart.Series<String, Number> dataSeries = getDataSeries(query, QueryType.OCCURRENCES, name);
        lineChart.getData().add(dataSeries);
        lineChart.setTitle("Occurrences/Rank by Year");
        lineChart.getXAxis().setLabel("Year");
        lineChart.getYAxis().setLabel("Occurrences");
    }

    private void updateBarChart() {
        QueryType type = QueryType.values()[barChartType.getSelectionModel().getSelectedIndex()];
        barChart.getData().clear();

        XYChart.Series<String, Number> dataSeries = getDataSeries(query, type, name);
        barChart.getData().add(dataSeries);

        if (nameCmp != null && queryCmp != null && !queryCmp.isEmpty()) {
            XYChart.Series<String, Number> cmp = getDataSeries(queryCmp, type, nameCmp);
            barChart.getData().add(cmp);
        }

        String typeStr = "Occurrences";
        if (type == QueryType.RANK) {
            typeStr = "Rank";
        }
        barChart.getYAxis().setLabel(typeStr);
    }

    private void updateLineChart() {
        QueryType type = QueryType.values()[lineChartType.getSelectionModel().getSelectedIndex()];
        lineChart.getData().clear();

        XYChart.Series<String, Number> dataSeries = getDataSeries(query, type, name);
        lineChart.getData().add(dataSeries);

        if (nameCmp != null && queryCmp != null && !queryCmp.isEmpty()) {
            XYChart.Series<String, Number> cmp = getDataSeries(queryCmp, type, nameCmp);
            lineChart.getData().add(cmp);
        }

        String typeStr = "Occurrences";
        if (type == QueryType.RANK) {
            typeStr = "Rank";
        }
        lineChart.getYAxis().setLabel(typeStr);
    }

    private XYChart.Series<String, Number> getDataSeries(ArrayList<NameQuery> query, QueryType type, String name) {
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        dataSeries.setName(name);
        for (NameQuery item : query) {
            dataSeries.getData().add(new XYChart.Data<>(Integer.toString(item.getYear()), getData(type, item)));
        }
        return dataSeries;
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

    private Number getData(QueryType type, NameQuery query) {
        if (type == QueryType.OCCURRENCES) {
            return query.getOccurrence();
        } else {
            return query.getRank();
        }
    }

    private boolean validateInput(String nameInput) {
        String processed = nameInput.trim().toLowerCase(Locale.ROOT);
        if (processed.isEmpty() || hasDigit(processed)) {
            return false;
        }

        queryCmp = PopularityReport.getInstance().getQueryResult(nameInput);
        return !queryCmp.isEmpty();
    }

    private boolean hasDigit(String input) {
        String regex = ".*[0-9].*";
        return input.matches(regex);
    }

}
