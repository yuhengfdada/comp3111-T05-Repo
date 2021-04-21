package comp3111.popnames.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import comp3111.popnames.predictor.CompatibilityPredictor;

public class basicInfoController {

    private CompatibilityPredictor predictor;

    @FXML
    private Button nextBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField selfName;

    @FXML
    private ChoiceBox<?> selfGender;

    @FXML
    private TextField selfYob;

    @FXML
    private TextField mateName;

    @FXML
    void onCancelBtnPressed(ActionEvent event) {

    }

    @FXML
    void onNextBtnPressed(ActionEvent event) {

    }

}

