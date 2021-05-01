/**
 * Building on the sample skeleton for 'ui.fxml' COntroller Class generated by SceneBuilder 
 */
package comp3111.popnames;


import com.sun.javafx.scene.control.skin.Utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import javafx.stage.Stage;
import javafx.scene.Node;
import comp3111.popnames.Task1.*;
import comp3111.popnames.Task4.*;
import comp3111.popnames.util.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller{

    @FXML
    private Tab tabTaskZero;

    @FXML
    private TextField textfieldNameF;

    @FXML
    private TextField textfieldYear;

    @FXML
    private Button buttonRankM;

    @FXML
    private TextField textfieldNameM;

    @FXML
    private TextField t1_year;

    @FXML
    private TextField t1_name;
    
    @FXML
    private Button buttonRankF;

    @FXML
    private Button buttonTopM;

    @FXML
    private Button buttonTopF;

    @FXML
    private Button buttonSummary;
    
    @FXML
    private Tab tabReport1;

    @FXML
    private ToggleGroup T1;

    @FXML
    private Tab tabReport2;

    @FXML
    private ToggleGroup T11;

    @FXML
    private Tab tabReport3;

    @FXML
    private ToggleGroup T111;

    @FXML
    private Tab tabApp1;

    @FXML
    private Tab tabApp2;

    @FXML
    private Tab tabApp3;

    @FXML
    private TextArea textAreaConsole;

    @FXML
    private Button scorePredStartBtn;

    @FXML
    private Button popReportStartBtn;
    
    @FXML
    private Button t1_generate;
    
    @FXML
    private TextField t4_momYOB;

    @FXML
    private TextField t4_dadYOB;

    @FXML
    private Button t4_generate;

    @FXML
    private TextField t4_momYOB2;

    @FXML
    private TextField t4_dadYOB2;

    @FXML
    private Button t4_generate2;

    @FXML
    private TextField t4_momName;

    @FXML
    private TextField t4_dadName;

    /**
     *  Task Zero
     *  To be triggered by the "Summary" button on the Task Zero Tab 
     *  
     */
    @FXML
    void doSummary() {
    	int year = Integer.parseInt(textfieldYear.getText());
    	String oReport = AnalyzeNames.getSummary(year);
    	textAreaConsole.setText(oReport);
    }

  
    /**
     *  Task Zero
     *  To be triggered by the "Rank (female)" button on the Task Zero Tab
     *  
     */
    @FXML
    void doRankF() {
    	String oReport = "";
    	String iNameF = textfieldNameF.getText();
    	int iYear = Integer.parseInt(textfieldYear.getText());
    	int oRank = AnalyzeNames.getRank(iYear, iNameF, "F");
    	if (oRank == -1)
    		oReport = String.format("The name %s (female) has not been ranked in the year %d.\n", iNameF, iYear);
    	else
    		oReport = String.format("Rank of %s (female) in year %d is #%d.\n", iNameF, iYear, oRank);
    	textAreaConsole.setText(oReport);
    }

  
    /**
     *  Task Zero
     *  To be triggered by the "Rank (male)" button on the Task Zero Tab
     *  
     */
    @FXML
    void doRankM() {
    	String oReport = "";
    	String iNameM = textfieldNameM.getText();
    	int iYear = Integer.parseInt(textfieldYear.getText());
    	int oRank = AnalyzeNames.getRank(iYear, iNameM, "M");
    	if (oRank == -1)
    		oReport = String.format("The name %s (male) has not been ranked in the year %d.\n", iNameM, iYear);
    	else
    		oReport = String.format("Rank of %s (male) in year %d is #%d.\n", iNameM, iYear, oRank);
    	textAreaConsole.setText(oReport);
    }


    /**
     *  Task Zero
     *  To be triggered by the "Top 5 (female)" button on the Task Zero Tab
     *  
     */
    @FXML
    void doTopF() {
    	String oReport = "";
    	final int topN = 5;
    	int iYear = Integer.parseInt(textfieldYear.getText());
    	oReport = String.format("Top %d most popular names (female) in the year %d:\n", topN, iYear);
    	for (int i=1; i<=topN; i++)
    		oReport += String.format("#%d: %s\n", i, AnalyzeNames.getName(iYear, i, "F"));
    	textAreaConsole.setText(oReport);
    }


    /**
     *  Task Zero
     *  To be triggered by the "Top 5 (male)" button on the Task Zero Tab
     *  
     */
    @FXML
    void doTopM() {
    	String oReport = "";
    	final int topN = 5;
    	int iYear = Integer.parseInt(textfieldYear.getText());
    	oReport = String.format("Top %d most popular names (male) in the year %d:\n", topN, iYear);
    	for (int i=1; i<=topN; i++)
    		oReport += String.format("#%d: %s\n", i, AnalyzeNames.getName(iYear, i, "M"));
    	textAreaConsole.setText(oReport);
    }

    
    @FXML
    void t1_generate_action(ActionEvent event) throws IOException {
    	Task1 task1 = new Task1();
    	if (util.isNumeric(t1_year.getText()) && util.isNumeric(t1_name.getText())) {
    		int year = Integer.parseInt(t1_year.getText());
    		int topN = Integer.parseInt(t1_name.getText());
    		if (util.isValidN(topN) && util.isValidYear(year)) {
    			task1.setYear(year);
    			task1.setTopN(topN);
    		}
    		else {
    			textAreaConsole.setText("Input is not in range!");
    			return;
			}
    	}
    	else {
    		textAreaConsole.setText("Input is not numeric");
    		return;
		}

    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/task1_1.fxml"));
    	Parent task1Parent = (Parent)loader.load();
    	Scene task1Scene = new Scene(task1Parent);
    	Task1Controller controller = (Task1Controller)loader.getController();
    	
    	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setUserData(task1);
    	controller.appearSummary(window);
    	window.setScene(task1Scene);
    	window.show();
    }
   
    @FXML
    void t4_generate_action(ActionEvent event) throws IOException {
    	Task4 task4 = new Task4();
    	if (util.isNumeric(t4_momYOB.getText()) && util.isNumeric(t4_dadYOB.getText())) {
    		int momYear = Integer.parseInt(t4_momYOB.getText());
    		int dadYear = Integer.parseInt(t4_dadYOB.getText());
    		if (util.isValidYear(momYear) && util.isValidYear(dadYear)) {
    			task4.setMomYear(momYear);
    			task4.setDadYear(dadYear);
    		}
    		else {
    			textAreaConsole.setText("Input is not in range!");
    			return;
			}
    	}
    	else {
    		textAreaConsole.setText("Input is not numeric");
    		return;
		}

    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/task4_a1.fxml"));
    	Parent task4Parent = (Parent)loader.load();
    	Scene task4Scene = new Scene(task4Parent);
    	Task4A1Controller controller = (Task4A1Controller)loader.getController();
    	
    	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setUserData(task4);
    	controller.appear(window);
    	window.setScene(task4Scene);
    	window.show();
    }
    
    @FXML
    void t4_generate_action_2(ActionEvent event) throws IOException {
    	Task4A2 task4A2 = new Task4A2();
    	if (util.isNumeric(t4_momYOB2.getText()) && util.isNumeric(t4_dadYOB2.getText())) {
    		int momYear = Integer.parseInt(t4_momYOB2.getText());
    		int dadYear = Integer.parseInt(t4_dadYOB2.getText());
    		if (util.isValidYear(momYear) && util.isValidYear(dadYear)) {
    			task4A2.setMomYear(momYear);
    			task4A2.setDadYear(dadYear);
    		}
    		else {
    			textAreaConsole.setText("Year input is not in range!");
    			return;
			}
    	}
    	else {
    		textAreaConsole.setText("Year input is not numeric");
    		return;
		}
    	
    	if (util.isAlpha(t4_momName.getText()) && util.isAlpha(t4_dadName.getText())) {
    		String momNameString = util.cap(t4_momName.getText());
    		String dadNameString = util.cap(t4_dadName.getText());
    		task4A2.setMomName(momNameString);
    		task4A2.setDadName(dadNameString);

    	} else {
    		textAreaConsole.setText("If you're not Elon Musk, please consider getting a full-alphabet name.");
			return;
    	}

    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/task4_a2.fxml"));
    	Parent task4Parent = (Parent)loader.load();
    	Scene task4Scene = new Scene(task4Parent);
    	Task4A2Controller controller = (Task4A2Controller)loader.getController();
    	
    	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setUserData(task4A2);
    	controller.appear(window);
    	window.setScene(task4Scene);
    	window.show();
    }
    

    @FXML
    void onPredStart(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/score_predictor_ui/basic_info_ui.fxml"));
        Parent root = (Parent) loader.load();
        Stage stage = new Stage();
        // basicInfoController controller = (basicInfoController) loader.getController();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Compatibility Score Predictor");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void onPopReportStart(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/popularity_report_ui/input_ui.fxml"));
        Parent root = (Parent) loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Name Popularity Report");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
}

