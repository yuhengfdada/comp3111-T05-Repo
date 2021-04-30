package comp3111.popnames.controllers;

import comp3111.popnames.applications.PopularityReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ReportInputController {

    private static final ObservableList<String> genderList;

    static {
        genderList = FXCollections.observableArrayList("Male", "Female");
    }

    @FXML
    private TextField startYearTextField;

    @FXML
    private TextField endYearTextField;

    @FXML
    private ChoiceBox<String> genderChoice;

    @FXML
    private TextField nameTextField;

    @FXML
    private Button confirmBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    public void initialize() {
        genderChoice.setItems(genderList);
    }

    @FXML
    void onCancelPressed(ActionEvent event) {
        close();
    }

    @FXML
    void onConfirmPressed(ActionEvent event) throws IOException {
        if (validateInput()) {
            if (!setValues()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Name Entered Not Found");

                Label content = new Label("Sorry, the name you entered does not exist in our database.\n" +
                        "Please retry with a different name.");
                content.setWrapText(true);
                alert.getDialogPane().setContent(content);
                alert.showAndWait();
            } else {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/popularity_report_ui/report_ui.fxml"));
                Parent root = (Parent) loader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Name Popularity Report");
                stage.setScene(new Scene(root));
                stage.setResizable(false);

                Stage curStage = (Stage) confirmBtn.getScene().getWindow();
                PopReportController controller = loader.getController();
                controller.setParent(curStage, this);
                curStage.hide();
                stage.show();
            }
        }
    }

    /**
     * Close the current stage
     */
    public void close() {
        Stage curStage = (Stage) confirmBtn.getScene().getWindow();
        curStage.close();
    }

    private boolean validateInput() {
        StringBuilder errorMsg = new StringBuilder();

        try {
            int startYear = Integer.parseInt(startYearTextField.getText());
            if (startYear < 1880 || startYear > 2019) {
                errorMsg.append("- Please enter a valid starting year between 1880 and 2019.\n");
            }
        } catch (NumberFormatException e) {
            errorMsg.append("- Please enter a valid starting year between 1880 and 2019.\n");
        }

        try {
            int endYear = Integer.parseInt(endYearTextField.getText());
            if (endYear < 1880 || endYear > 2019) {
                errorMsg.append("- Please enter a valid ending year between 1880 and 2019.\n");
            }
        } catch (NumberFormatException e) {
            errorMsg.append("- Please enter a valid ending year between 1880 and 2019.\n");
        }

        String name = nameTextField.getText().trim();
        if (name.length() < 2 || name.length() > 15) {
            errorMsg.append("- Please enter a valid name within 2-15 characters.\n");
        } else if (hasDigit(name)) {
            errorMsg.append("- Please enter a valid name with characters only.\n");
        }

        String gender = genderChoice.getSelectionModel().getSelectedItem();
        if (gender == null) {
            errorMsg.append("- Please choose a gender preference.\n");
        }

        if (errorMsg.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Required Fields Empty/Invalid");

            Label content = new Label(errorMsg.toString());
            content.setWrapText(true);
            alert.getDialogPane().setContent(content);

            alert.showAndWait();
            return false;
        }

        return true;
    }

    private boolean setValues() {
        PopularityReport report = PopularityReport.getInstance();

        int startYear = Integer.parseInt(startYearTextField.getText());
        int endYear = Integer.parseInt(endYearTextField.getText());
        String name = nameTextField.getText().trim();
        char gender = genderChoice.getSelectionModel().getSelectedItem().charAt(0);

        return report.generateReport(startYear, endYear, gender, name);
    }

    private boolean hasDigit(String input) {
        String regex = ".*[0-9].*";
        return input.matches(regex);
    }

}
