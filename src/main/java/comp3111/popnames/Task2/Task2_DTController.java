package comp3111.popnames.Task2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The controller for showing data table in task 2.
 * @author LUO, Yuqing
 */
public class Task2_DTController {

    @FXML
    private TableView<Task2_NameEntry> Table = new TableView<Task2_NameEntry>();

    private final ObservableList<Task2_NameEntry> data = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Task2_NameEntry, String> name ;

    @FXML
    private TableColumn<Task2_NameEntry, Integer> frequency;

    @FXML
    private TableColumn<Task2_NameEntry, Integer> occurrences;

    @FXML
    private TableColumn<Task2_NameEntry, String> percentage;

    /**
     * Initialize the controller.
     */
    @FXML
    public void initialize() {
        name.setCellValueFactory(new PropertyValueFactory<Task2_NameEntry, String>("Name"));
        frequency.setCellValueFactory(new PropertyValueFactory<Task2_NameEntry, Integer>("Frequency"));
        occurrences.setCellValueFactory(new PropertyValueFactory<Task2_NameEntry, Integer>("Occurrences"));
        percentage.setCellValueFactory(new PropertyValueFactory<Task2_NameEntry, String>("Percentage"));
        int total_frequency = 0;
        int total_occurrence = 0;
        for(int i = 0; i < Task2_NameAnalyzer.name_list.size(); i++){
            total_frequency += Task2_NameAnalyzer.name_list.get(i).getValue();
            total_occurrence += Task2_NameAnalyzer.nameRecords.get(Task2_NameAnalyzer.name_list.get(i).getKey());
            data.add(new Task2_NameEntry(Task2_NameAnalyzer.name_list.get(i).getKey(), Task2_NameAnalyzer.name_list.get(i).getValue(),
                    Task2_NameAnalyzer.nameRecords.get(Task2_NameAnalyzer.name_list.get(i).getKey()),
                    String.format("%.2f%%",(double) Task2_NameAnalyzer.nameRecords.get(Task2_NameAnalyzer.name_list.get(i).getKey()) / (double) Task2_NameAnalyzer.totalOccurrence * 100)));
        }

        data.add(new Task2_NameEntry("Total", total_frequency, total_occurrence, "100.00%"));
        Table.setEditable(true);
        //stage.show();
        Table.setItems(data);
        //stage.show();

        Table.setEditable(true);
        Table.setItems(data);
    }

    public void start() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Task2_data_table.fxml"));
        Stage stage = new Stage();
        VBox root = loader.load();
        stage.setTitle("Data Table of K-th popular names");
        stage.setScene(new Scene(root));
        stage.setResizable(false);

        stage.show();

    }


}
