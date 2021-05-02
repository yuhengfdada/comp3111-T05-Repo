package Task5;

import edu.duke.FileResource;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Task5_X1_NameAnalyzer {

    public static String mostPopularName;
    public static int occurrences;
    public static CSVParser getFileParser(int year) {
        FileResource fr = new FileResource(String.format("dataset/yob%s.csv", year));
        return fr.getCSVParser(false);
    }

    public static String getSummary(int yearOfBorn,char gender) {
        mostPopularName = "";
        occurrences = 0;
        for (CSVRecord rec : getFileParser(yearOfBorn)) {
            if(rec.get(1).charAt(0) == gender){
                mostPopularName = rec.get(0);
                occurrences = Integer.parseInt(rec.get(2));
                break;
            }
        }
        String sGender;
        String sGender_2;
        if(gender == 'F'){
            sGender = "female";
            sGender_2 = "girl";
        }
        else {
            sGender = "male";
            sGender_2 = "boy";
        }
        String oReport = "The most popular name of " + sGender + " in your year of born is " + mostPopularName + " with occurrence of " + occurrences + ".\n";

        oReport += "The chance of meeting and becoming soulmate with a " + sGender_2 + " named " + mostPopularName + " is the highest.";

        return oReport;
    }

}
