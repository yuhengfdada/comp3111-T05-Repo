package Task2;

import edu.duke.FileResource;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.Iterator;

public class Task2_NameAnalyzer {

    public static HashMap<String, Integer> nameRecords;
    public static HashMap<String, Integer> matchedNames;
    public static ArrayList<HashMap.Entry<String, Integer>> name_list;
    public static int totalOccurrence = 0;

    public static CSVParser getFileParser(int year) {
        FileResource fr = new FileResource(String.format("dataset/yob%s.csv", year));
        return fr.getCSVParser(false);
    }

    public static String getSummary(int startYear,int endYear, int popularityRanking, char gender) {
        nameRecords = new HashMap<>();
        matchedNames = new HashMap<>();
        totalOccurrence = 0;

        for (int year = startYear; year <= endYear; year++) {
            int currentRanking = 0;
            for (CSVRecord rec : getFileParser(year)) {
                if(rec.get(1).charAt(0) == gender){
                    currentRanking++;
                    if(currentRanking == popularityRanking){
                        totalOccurrence += Integer.parseInt(rec.get(2));
                        if(nameRecords.containsKey(rec.get(0))){
                            int tempNum = nameRecords.get(rec.get(0));
                            nameRecords.replace(rec.get(0), tempNum + Integer.parseInt(rec.get(2)));
                        }
                        else {
                            nameRecords.put(rec.get(0), Integer.parseInt(rec.get(2)));
                        }
                        if(matchedNames.containsKey(rec.get(0))){
                            int tempNum = matchedNames.get(rec.get(0));
                            matchedNames.replace(rec.get(0), tempNum + 1);
                        }
                        else matchedNames.put(rec.get(0), 1);
                    }
                }
            }
        }

        name_list = new ArrayList(matchedNames.entrySet());
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
    String s_Gender = "";
    String s_Gender_2 = "";
    if(gender == 'F'){
        s_Gender = "girls";
        s_Gender_2 = "female";
    }else {
        s_Gender = "boys";
        s_Gender_2 = "male";
    }
    String forRanking = "";
    if(popularityRanking == 1){
        forRanking = "1st";
    }
    else if (popularityRanking == 2){
        forRanking = "2nd";
    }
    else if (popularityRanking == 3){
        forRanking = "3rd";
    }
    else{
        forRanking = String.format("%d-th", popularityRanking);
    }
    double percentage = (double)nameRecords.get(name_list.get(0).getKey()) / (double)totalOccurrence  * 100;

    String oReport = name_list.get(0).getKey()+" has hold the " + forRanking+String.format(" rank most often for a total of %d times,\n",name_list.get(0).getValue());

    oReport += String.format("among names registered for baby %s born in the period from %d to %d.\n", s_Gender, startYear, endYear);

    oReport += String.format("The total number of occurrences of %s is %d, \n",name_list.get(0).getKey(), nameRecords.get(name_list.get(0).getKey()));

    oReport += String.format("which represents %.2f%% of total %s births at the ",percentage,s_Gender_2) + forRanking;

    oReport += String.format(" rank in the period from %d to %d.",startYear, endYear);


    return oReport;
    }

}