package comp3111.popnames.Task5;

import edu.duke.FileResource;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.util.*;

public class Task5_X2_NameAnalyzer {

    public static HashMap<String, Integer> nameRecords;
    public static ArrayList<HashMap.Entry<String, Integer>> name_list;
    public static String ownName;
    public static int ownNameRanking;
    public static int ownNameOccurrence;
    public static String matchedName;
    public static int matchedNameRanking;
    public static int matchedNameOccurrence;
    public static String oReport;

    public static CSVParser getFileParser(int year) {
        FileResource fr = new FileResource(String.format("dataset/yob%s.csv", year));
        return fr.getCSVParser(false);
    }

    public static boolean checkName(int yearOfBorn ,String name, char gender) {
        boolean findName = false;
        for (CSVRecord rec : getFileParser(yearOfBorn)) {
            if (rec.get(1).charAt(0) == gender) {
                if (rec.get(0).equals(name)) {
                    findName = true;
                    break;
                }
            }
        }
        return findName;
    }

    public static void getName(int yearOfBorn ,String name, char gender, char genderOfInterest, char agePreference){
        ownNameRanking = 0;
        ownName = name;
        nameRecords = new HashMap<>();
        for (CSVRecord rec : getFileParser(yearOfBorn)) {
            if (rec.get(1).charAt(0) == gender) {
                ownNameRanking++;
                if (rec.get(0).equals(name)) {
                    ownNameOccurrence = Integer.parseInt(rec.get(2));
                    break;
                }
            }
        }

        if(agePreference == 'Y') {
            for (int i = 0; i < 3 && yearOfBorn + i <= 2019; i++) {
                int endYear = i + yearOfBorn;
                for (CSVRecord rec : getFileParser(endYear)) {
                    if (rec.get(1).charAt(0) == genderOfInterest) {
                        if (nameRecords.containsKey(rec.get(0))) {
                            int tempNum = nameRecords.get(rec.get(0));
                            nameRecords.replace(rec.get(0), tempNum + Integer.parseInt(rec.get(2)));
                        } else {
                            nameRecords.put(rec.get(0), Integer.parseInt(rec.get(2)));
                        }
                    }
                }
            }
        }else{
            for (int i = 0; i < 3 && yearOfBorn - i >= 1880; i++) {
                int endYear = yearOfBorn - i;
                for (CSVRecord rec : getFileParser(endYear)) {
                    if (rec.get(1).charAt(0) == genderOfInterest) {
                        if (nameRecords.containsKey(rec.get(0))) {
                            int tempNum = nameRecords.get(rec.get(0));
                            nameRecords.replace(rec.get(0), tempNum + Integer.parseInt(rec.get(2)));
                        } else {
                            nameRecords.put(rec.get(0), Integer.parseInt(rec.get(2)));
                        }
                    }
                }
            }
        }

        name_list = new ArrayList(nameRecords.entrySet());
        Collections.sort(name_list, new Comparator<HashMap.Entry<String, Integer>>()
        {
            public int compare(HashMap.Entry<String, Integer> o1, HashMap.Entry<String, Integer> o2){
                if(o2.getValue() > o1.getValue())
                    return 1;
                if(o2.getValue() < o1.getValue())
                    return -1;
                else{
                    if(o2.getKey().compareTo(o1.getKey()) < 0)
                        return 1;
                    else
                        return -1;
                }
            }
        });
        matchedName= "";
        String forReport = "";
        if(name_list.get(ownNameRanking - 1).getKey().equals(name)){
            if(ownNameRanking == 1){
                matchedName = name_list.get(ownNameRanking).getKey();
                matchedNameOccurrence = name_list.get(ownNameRanking).getValue();
                matchedNameRanking = ownNameRanking+1;
            }
            else {
                matchedName = name_list.get(ownNameRanking - 2).getKey();
                matchedNameOccurrence = name_list.get(ownNameRanking - 2).getValue();
                matchedNameRanking = ownNameRanking-1;
            }
            forReport = matchedName + "has a popularity ranking of " + matchedNameRanking + ".\n" + "It has the closest popularity ranking to your name except for your own name.\n";
        }
        else {
            matchedName = name_list.get(ownNameRanking - 1).getKey();
            matchedNameOccurrence = name_list.get(ownNameRanking - 1).getValue();
            matchedNameRanking = ownNameRanking;
            forReport = matchedName + "has a popularity ranking of " + matchedNameRanking + ".\n" + "It has the same popularity ranking as your name.\n";
        }

        oReport = "The name recommended is " + matchedName +" .\n";
        oReport += "The popularity ranking of your name in the year you are born is " + ownNameRanking + " .\n";
        oReport += "The name " + matchedName + " is selected according to your preference on gender and age.\n";
        oReport += "Three years' data near your born-year is selected (if applicable) and sorted according to total occurrences.\n";
        oReport += forReport;
    }

}
