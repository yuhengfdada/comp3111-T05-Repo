package comp3111.popnames.Task4;


import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import comp3111.popnames.AnalyzeNames;
import edu.duke.FileResource;
import javafx.util.Pair;

public class Task4A2 {
	private int momYear = 0;
	private int dadYear = 0;

	private String dadName;
	private String momName;
	int dadSum;
	int momSum;
	
	String selectedPopBoyString;
	int selectedPopBoyOcc;
	int selectedPopBoySum;
	int selectedPopBoyDiff;
	String selectedPopGirlString;
	int selectedPopGirlOcc;
	int selectedPopGirlSum;
	int selectedPopGirlDiff;
	
	int totalBoys = 0;
	int totalGirls = 0;
	int uniqueBoys = 0;
	int uniqueGirls = 0;
	
	String summaryB;
	String summaryG;
	
	public static CSVParser getFileParser(int year) {
	     FileResource fr = new FileResource(String.format("dataset/yob%s.csv", year));
	     return fr.getCSVParser(false);
	}
	
	public void getMostPopNameAndStore() {
		dadSum = sumString(getDadName());
		momSum = sumString(getMomName());
		int minM = Integer.MAX_VALUE;
		int minF = Integer.MAX_VALUE;
		
		for (CSVRecord rec : getFileParser(dadYear)) {
			int numBorn = Integer.parseInt(rec.get(2));
			if (rec.get(1).equals("M")) {
				totalBoys += numBorn;
				uniqueBoys++;
				if (rec.get(0).equals(dadName))	continue;
				int strSum = sumString(rec.get(0));
				if (Math.abs(dadSum-strSum) < minM) {
					selectedPopBoyString = rec.get(0);
					selectedPopBoyOcc = numBorn;
					selectedPopBoySum = strSum;
					selectedPopBoyDiff = Math.abs(dadSum-strSum);
					minM = selectedPopBoyDiff;
				}
			}
		}
		
		for (CSVRecord rec : getFileParser(momYear)) {
			int numBorn = Integer.parseInt(rec.get(2));
			if (rec.get(1).equals("F")) {
				totalGirls += numBorn;
				uniqueGirls++;
				if (rec.get(0).equals(momName))	continue;
				int strSum = sumString(rec.get(0));
				if (Math.abs(momSum-strSum) < minF) {
					selectedPopGirlString = rec.get(0);
					selectedPopGirlOcc = numBorn;
					selectedPopGirlSum = strSum;
					selectedPopGirlDiff = Math.abs(momSum-strSum);
					minF = selectedPopGirlDiff;
				}
			}
		}
		summaryB = String.format("%s is the most appropriate name found by the algorithm with the number of occurrences of %d, "
				+ "which represents %.3f%% of total male births in dad's year of birth, %d.\n"
				+ "The sum of all characters in the dad's name %s is %d.\n"
				+ "The sum of all characters in name %s is %d, which is the closest to that of the dad's name with difference %d.", 
				selectedPopBoyString,  selectedPopBoyOcc, selectedPopBoyOcc / (double)totalBoys, dadYear,
				getDadName(), dadSum, selectedPopBoyString, selectedPopBoySum, selectedPopBoyDiff);
		summaryG = String.format("%s is the most appropriate name found by the algorithm with the number of occurrences of %d, "
				+ "which represents %.3f%% of total female births in mom's year of birth, %d.\n"
				+ "The sum of all characters in the mom's name %s is %d.\n"
				+ "The sum of all characters in name %s is %d, which is the closest to that of the mom's name with difference %d.", 
				selectedPopGirlString,  selectedPopGirlOcc, selectedPopGirlOcc / (double)totalGirls, momYear,
				getMomName(), momSum, selectedPopGirlString, selectedPopGirlSum, selectedPopGirlDiff);
	}

	public int getMomYear() {
		return momYear;
	}

	public void setMomYear(int momYear) {
		this.momYear = momYear;
	}

	public int getDadYear() {
		return dadYear;
	}

	public void setDadYear(int dadYear) {
		this.dadYear = dadYear;
	}
	
	
	
	private int sumString(String str) {
		int sum = 0;
		for (int i = 0; i < str.length(); i++) {
			sum += str.charAt(i);
		}
		return sum;
	}

	public String getMomName() {
		return momName;
	}

	public void setMomName(String momName) {
		this.momName = momName;
	}

	public String getDadName() {
		return dadName;
	}

	public void setDadName(String dadName) {
		this.dadName = dadName;
	}
}
