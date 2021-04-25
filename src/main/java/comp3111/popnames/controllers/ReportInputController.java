package comp3111.popnames.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class ReportInputController {

    @FXML
    private TextField startYearTextField;

    @FXML
    private TextField endYearTextField;

    @FXML
    private ChoiceBox<?> genderChoice;

    @FXML
    private TextField nameTextField;

    @FXML
    private Button confirmBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    void onCancelPressed(ActionEvent event) {

    }

    @FXML
    void onConfirmPressed(ActionEvent event) {

    }

}
