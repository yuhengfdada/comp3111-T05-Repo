package comp3111.popnames.Task1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Pair;

public class TableController {

    @FXML
    private TableView<TableEntry> tableM;
    @FXML
    private TableView<TableEntry> tableF;
    
    @FXML
    private Label titleM;
    @FXML
    private Label titleF;
    
    @FXML
    private TableColumn<TableEntry, String> nameColumn;
    @FXML
    private TableColumn<TableEntry, Integer> occColumn;
    @FXML
    private TableColumn<TableEntry, String> percentageColumn;
    
    @FXML
    private TableColumn<TableEntry, String> nameColumn1;
    @FXML
    private TableColumn<TableEntry, Integer> occColumn1;
    @FXML
    private TableColumn<TableEntry, String> percentageColumn1;
    
    @FXML
    private Button back;

    private Task1 task1;
    
    
    @FXML
    void goBack(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/task1_1.fxml"));
    	Parent task1Parent = (Parent)loader.load();
    	Scene task1Scene = new Scene(task1Parent);
    	Task1Controller controller = (Task1Controller)loader.getController();
    	
    	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setUserData(task1);
    	controller.showStoredSummary(window);
    	window.setScene(task1Scene);
    	window.show();
    }
    
    public void appearTable(Stage stage) {
    	stage.setTitle("Table Summary");
    	task1 = (Task1)stage.getUserData();
    	// set titles
    	titleM.setText(String.format("Top %d Names (male) in %d", task1.topN, task1.year));
    	titleF.setText(String.format("Top %d Names (female) in %d", task1.topN, task1.year));
    	// turn into table entries
    	List<TableEntry> entriesM = new ArrayList<>();
    	List<TableEntry> entriesF = new ArrayList<>();
    	for (Pair<String, Integer> pair : task1.popNamesListM) {
    		String percString = String.format("%.3f%%", (pair.getValue() / (double)task1.totalBoys));
    		TableEntry temp = new TableEntry(pair.getKey(), pair.getValue(), percString);
    		entriesM.add(temp);
    	}
    	for (Pair<String, Integer> pair : task1.popNamesListF) {
    		String percString = String.format("%.3f%%", (pair.getValue() / (double)task1.totalGirls));
    		TableEntry temp = new TableEntry(pair.getKey(), pair.getValue(), percString);
    		entriesF.add(temp);
    	}
    	ObservableList<TableEntry> ls1=FXCollections.observableArrayList();
    	ObservableList<TableEntry> ls2=FXCollections.observableArrayList();
    	for (TableEntry tEntry : entriesM) {
    		ls1.add(tEntry);
    	}
    	tableM.setItems(ls1);
    	nameColumn.setCellValueFactory(new PropertyValueFactory<TableEntry, String>("name"));
    	occColumn.setCellValueFactory(new PropertyValueFactory<TableEntry, Integer>("occ"));
    	percentageColumn.setCellValueFactory(new PropertyValueFactory<TableEntry, String>("percentage"));
    	for (TableEntry tEntry : entriesF) {
    		ls2.add(tEntry);
    	}
    	tableF.setItems(ls2);
    	nameColumn1.setCellValueFactory(new PropertyValueFactory<TableEntry, String>("name"));
    	occColumn1.setCellValueFactory(new PropertyValueFactory<TableEntry, Integer>("occ"));
    	percentageColumn1.setCellValueFactory(new PropertyValueFactory<TableEntry, String>("percentage"));
    	
    }
}
