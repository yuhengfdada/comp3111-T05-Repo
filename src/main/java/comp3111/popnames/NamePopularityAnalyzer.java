package comp3111.popnames;

import edu.duke.FileResource;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.Iterator;

public class NamePopularityAnalyzer {

    public static CSVParser getFileParser(int year) {
        FileResource fr = new FileResource(String.format("dataset/yob%s.csv", year));
        return fr.getCSVParser(false);
    }

    public void getSummary(int startYear,int endYear, int popularityRanking, char gender) {
        String oReport = "";
        int totalOccurrence = 0;
        HashMap<String, Integer> nameRecords = new HashMap<>();
        HashMap<String, Integer> matchedNames = new HashMap<>();

        for (int year = startYear; year <= endYear; year++) {
            int currentRanking = 0;
            for (CSVRecord rec : getFileParser(year)) {
                if(rec.get(1).charAt(0) == gender){
                    currentRanking++;
                    totalOccurrence += Integer.parseInt(rec.get(2));
                    if(nameRecords.containsKey(rec.get(0))){
                        int tempNum = nameRecords.get(rec.get(0));
                        nameRecords.put(rec.get(0), tempNum + Integer.parseInt(rec.get(2)));
                    }
                    else {
                        nameRecords.put(rec.get(0), Integer.parseInt(rec.get(2)));
                    }
                    if(currentRanking == popularityRanking){
                        if(matchedNames.containsKey(rec.get(0))){
                            int tempNum = nameRecords.get(rec.get(0));
                            matchedNames.put(rec.get(0), tempNum + 1);
                        }
                    }
                }
            }
        }

        ArrayList<HashMap.Entry<Integer,String>> list = new ArrayList(nameRecords.entrySet());
        Collections.sort(list, Comparator.comparing(HashMap.Entry::getValue));

        Iterator<HashMap.Entry<Integer,String>> iterator = list.iterator();
        int i = 0;
        String popularName = "";
        int popularNameOccurrence = 0;
        for(HashMap.Entry<Integer,String> m : list){
            i++;
            if(i == popularityRanking){
                popularName = m.getValue();
                popularNameOccurrence = m.getKey();
            }
        }

        System.out.println("popularName is " + popularName + "with occurrence " + popularNameOccurrence);
    }

}