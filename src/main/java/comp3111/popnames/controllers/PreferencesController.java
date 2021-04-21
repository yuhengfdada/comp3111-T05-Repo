package comp3111.popnames.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class PreferencesController {

    private static final ObservableList<String> agePrefList;

    static {
        agePrefList = FXCollections.observableArrayList("Younger", "Older");
    }

    @FXML
    private ChoiceBox<String> agePref;

    @FXML
    private TextField nameTheme;

    @FXML
    private TextField nameMeaning;

    @FXML
    private Button nextBtn;

    @FXML
    private Button backBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private Slider themeSuitability;

    @FXML
    private Slider meanSuitability;

    @FXML
    public void initialize() {
        agePref.setItems(agePrefList);
    }

    @FXML
    void onBackBtnPressed(ActionEvent event) {

    }

    @FXML
    void onCancelBtnPressed(ActionEvent event) {

    }

    @FXML
    void onNextBtnPressed(ActionEvent event) {

    }

}
