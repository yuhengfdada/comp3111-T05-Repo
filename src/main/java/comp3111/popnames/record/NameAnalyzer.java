package comp3111.popnames.record;

import comp3111.popnames.record.NameRecord;
import edu.duke.FileResource;
import edu.duke.ResourceException;
import javafx.util.Pair;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class NameAnalyzer {
    public static class YearRecord {
        public YearRecord() {
            maleTotalOccur = 0;
            femaleTotalOccur = 0;
        }

        private final HashMap<String, NameRecord> maleNameMap = new HashMap<>();
        private final HashMap<String, NameRecord> femaleNameMap = new HashMap<>();
        private final ArrayList<NameRecord> maleNames = new ArrayList<>();
        private final ArrayList<NameRecord> femaleNames = new ArrayList<>();
        private int maleTotalOccur;
        private int femaleTotalOccur;
    }

    public static class NameQuery {
        int occurrence;
        int rank;
        int year;
        double percentage;
    }

    public NameAnalyzer(int start, int end) {
        startYear = start;
        endYear = end;
    }

    private final HashMap<Integer, YearRecord> yearRecords = new HashMap<>();
    private final int startYear;
    private final int endYear;

    public CSVParser getFileParser(int year) {
        if (year < startYear || year > endYear) {
            return null;
        }

        try {
            FileResource fr = new FileResource(String.format("dataset/yob%s.csv", year));
            return fr.getCSVParser(false);
        } catch (ResourceException e) {
            return null;
        }
    }

    public void analyzeYear(int year) {
        CSVParser parser = getFileParser(year);
        if (parser == null) {
            return;
        }
        YearRecord yearRecord = new YearRecord();

        for (CSVRecord record : parser) {
            String name = record.get(0);
            char gender = 'M';
            if (record.get(1).equals("F")) {
                gender = 'F';
            }
            int occurrence = Integer.parseInt(record.get(2));

            NameRecord nameRecord = new NameRecord(name, gender, year, occurrence);
            if (gender == 'M') {
                yearRecord.maleNames.add(nameRecord);
                yearRecord.maleTotalOccur += occurrence;
            } else {
                yearRecord.femaleNames.add(nameRecord);
                yearRecord.femaleTotalOccur += occurrence;
            }
        }

        yearRecord.maleNames.sort(NameRecord.maxComparator);
        yearRecord.femaleNames.sort(NameRecord.maxComparator);
        for (int i = 0; i < yearRecord.maleNames.size(); i += 1) {
            yearRecord.maleNames.get(i).rank(i + 1);
        }
        for (int i = 0; i < yearRecord.femaleNames.size(); i += 1) {
            yearRecord.femaleNames.get(i).rank(i + 1);
        }

        for (NameRecord name : yearRecord.maleNames) {
            yearRecord.maleNameMap.put(name.name(), name);
        }
        for (NameRecord name : yearRecord.femaleNames) {
            yearRecord.femaleNameMap.put(name.name(), name);
        }

        yearRecords.put(year, yearRecord);
    }

    public void analyzeData() {
        for (int i = startYear; i <= endYear; i += 1) {
            analyzeYear(i);
        }
    }

    public ArrayList<NameQuery> getNameReport(String name, char gender) {
        ArrayList<NameQuery> list = new ArrayList<>();
        for (int i = startYear; i <= endYear; i += 1) {
            if (!yearRecords.containsKey(i)) {
                continue;
            }

            YearRecord yearRecord = yearRecords.get(i);

            if (gender == 'M' && yearRecord.maleNameMap.containsKey(name)) {
                NameQuery query = new NameQuery();
                NameRecord record = yearRecord.maleNameMap.get(name);
                query.occurrence = record.occurrence();
                query.rank = record.rank();
                query.year = i;
                query.percentage = (double) query.occurrence / (double) yearRecord.maleTotalOccur;
                list.add(query);
            }

            if (gender == 'F' && yearRecord.femaleNameMap.containsKey(name)) {
                NameQuery query = new NameQuery();
                NameRecord record = yearRecord.femaleNameMap.get(name);
                query.occurrence = record.occurrence();
                query.rank = record.rank();
                query.year = i;
                query.percentage = (double) query.occurrence / (double) yearRecord.femaleTotalOccur;
                list.add(query);
            }
        }
        return list;
    }

}
