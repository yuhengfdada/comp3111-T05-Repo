package comp3111.popnames.Task1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import edu.duke.FileResource;
import javafx.util.Pair;

public class Task1 {
	int year = 0;
	int topN = 0;
	public void setYear(int i) {year = i;}
	public void setTopN(int i) {topN = i;}
	public int getYear() {return year;}
	public int getTopN() {return topN;}
	
	
	int totalBirths = 0;
	int totalBoys = 0;
	int totalGirls = 0;
	int totalNames = 0;
	int uniqueBoys = 0;
	int uniqueGirls = 0;
	
	List<Pair<String, Integer>> popNamesListM = new ArrayList<>();
	List<Pair<String, Integer>> popNamesListF = new ArrayList<>();
	public static CSVParser getFileParser(int year) {
	     FileResource fr = new FileResource(String.format("dataset/yob%s.csv", year));
	     return fr.getCSVParser(false);
	}
	 
		
	public String getSummaryAndStore(int year) {
		String oReport = "";	
		int topNCounterM = topN;
		int topNCounterF = topN;
		oReport = String.format("Summary (Year of %d):\n", year);
		for (CSVRecord rec : getFileParser(year)) {
			int numBorn = Integer.parseInt(rec.get(2));
			totalBirths += numBorn;
			totalNames += 1;
			if (rec.get(1).equals("M")) {
				totalBoys += numBorn;
				uniqueBoys++;
				if (topNCounterM != 0) {
					popNamesListM.add(new Pair<String, Integer>(rec.get(0), numBorn));
					topNCounterM -= 1;
				}
			}
			else {
				totalGirls += numBorn;
				uniqueGirls++;
				if (topNCounterF != 0) {
					popNamesListF.add(new Pair<String, Integer>(rec.get(0), numBorn));
					topNCounterF -= 1;
				}
			}
		}
		oReport += String.format("%s is the most popular name with the number of occurrences of %d, which represents %.2f%% of total male births in %d.", popNamesListM.get(0).getKey(),  popNamesListM.get(0).getValue(), popNamesListM.get(0).getValue() / (double)totalBoys, year);
		oReport += String.format("\n%s is the most popular name with the number of occurrences of %d, which represents %.2f%% of total female births in %d.", popNamesListF.get(0).getKey(),  popNamesListF.get(0).getValue(), popNamesListF.get(0).getValue() / (double)totalGirls, year);
		return oReport;
	}
}
