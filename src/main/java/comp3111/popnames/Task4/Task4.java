package comp3111.popnames.Task4;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import comp3111.popnames.AnalyzeNames;
import edu.duke.FileResource;
import javafx.util.Pair;
/**
 * This class contains all information needed for task4, algorithm 1.
 * @author syh
 *
 */
public class Task4 {
	private int momYear = 0;
	private int dadYear = 0;

	String mostPopBoyString;
	int mostPopBoyOcc;
	String mostPopGirlString;
	int mostPopGirlOcc;
	
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
		for (CSVRecord rec : getFileParser(dadYear)) {
			int numBorn = Integer.parseInt(rec.get(2));
			if (rec.get(1).equals("M")) {
				totalBoys += numBorn;
				uniqueBoys++;
				mostPopBoyString = rec.get(0);
				mostPopBoyOcc = numBorn;
				break;
			}
		}
		for (CSVRecord rec : getFileParser(momYear)) {
			int numBorn = Integer.parseInt(rec.get(2));
			if (rec.get(1).equals("F")) {
				totalGirls += numBorn;
				uniqueGirls++;
				mostPopGirlString = rec.get(0);
				mostPopGirlOcc = numBorn;
				break;
			}
		}
		summaryB = String.format("%s is the most popular name with the number of occurrences of %d, which represents %.3f%% of total male births in dad's year of birth, %d.", mostPopBoyString,  mostPopBoyOcc, mostPopBoyOcc / (double)totalBoys, dadYear);
		summaryG = String.format("%s is the most popular name with the number of occurrences of %d, which represents %.3f%% of total female births in mom's year of birth, %d.", mostPopGirlString,  mostPopGirlOcc, mostPopGirlOcc / (double)totalGirls, momYear);
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
}
