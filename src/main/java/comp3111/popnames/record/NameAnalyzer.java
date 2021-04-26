package comp3111.popnames.record;

import edu.duke.FileResource;
import edu.duke.ResourceException;
import javafx.beans.property.SimpleStringProperty;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * The analyzer for the name database
 */
public class NameAnalyzer {

    /**
     * This contains the name records within each year
     */
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

    /**
     * This is used to convey queried information
     */
    public static class NameQuery {
        public int occurrence;
        public int rank;
        public int year;
        public double percentage;
        private final SimpleStringProperty percentageProperty = new SimpleStringProperty();

        public int getOccurrence() {
            return occurrence;
        }

        public int getRank() {
            return rank;
        }

        public int getYear() {
            return year;
        }

        public SimpleStringProperty percentageProperty() {
            percentageProperty.set(String.format("%.2f%%", percentage * 100.0));
            return percentageProperty;
        }
    }

    private static final NameAnalyzer instance = new NameAnalyzer(1880, 2019);

    /**
     * Get the unique instance of the class
     * @return the instance
     */
    public static NameAnalyzer getInstance() {
        return instance;
    }

    private NameAnalyzer(int start, int end) {
        startYear = start;
        endYear = end;
        analyzeData();
    }

    private final HashMap<Integer, YearRecord> yearRecords = new HashMap<>();
    private final int startYear;
    private final int endYear;

    /**
     * Get the csv file parser for a specific year
     * @param year year of interest
     * @return the parser if file exists, null otherwise
     */
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

    private void analyzeYear(int year) {
        CSVParser parser = getFileParser(year);
        if (parser == null) {
            return;
        }
        YearRecord yearRecord = new YearRecord();

        for (CSVRecord record : parser) {
            String name = record.get(0).toLowerCase(Locale.ROOT);
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

        yearRecord.maleNames.sort(NameRecord.occurrenceComparator);
        yearRecord.femaleNames.sort(NameRecord.occurrenceComparator);
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

    private void analyzeData() {
        for (int i = startYear; i <= endYear; i += 1) {
            analyzeYear(i);
        }
    }

    /**
     * Retrieve a name report about occurrence, rank and percentage
     * @param name the name of interest
     * @param gender the gender preferred
     * @return an ArrayList of NameQuery, empty if name is not found
     */
    public ArrayList<NameQuery> getNameReport(String name, char gender) {
        String processedName = name.toLowerCase(Locale.ROOT);
        ArrayList<NameQuery> list = new ArrayList<>();
        for (int i = startYear; i <= endYear; i += 1) {
            if (!yearRecords.containsKey(i)) {
                continue;
            }

            YearRecord yearRecord = yearRecords.get(i);

            if (gender == 'M' && yearRecord.maleNameMap.containsKey(processedName)) {
                NameQuery query = new NameQuery();
                NameRecord record = yearRecord.maleNameMap.get(processedName);
                query.occurrence = record.occurrence();
                query.rank = record.rank();
                query.year = i;
                query.percentage = (double) query.occurrence / (double) yearRecord.maleTotalOccur;
                list.add(query);
            }

            if (gender == 'F' && yearRecord.femaleNameMap.containsKey(processedName)) {
                NameQuery query = new NameQuery();
                NameRecord record = yearRecord.femaleNameMap.get(processedName);
                query.occurrence = record.occurrence();
                query.rank = record.rank();
                query.year = i;
                query.percentage = (double) query.occurrence / (double) yearRecord.femaleTotalOccur;
                list.add(query);
            }
        }
        return list;
    }

    /**
     * Get the sorted NameRecord ArrayList with respect to occurrence
     * @param name the name of interest
     * @return the sorted ArrayList
     */
    public ArrayList<NameRecord> getSortedRecords(String name) {
        String processedName = name.toLowerCase(Locale.ROOT);
        ArrayList<NameRecord> records = new ArrayList<>();
        for (YearRecord year : yearRecords.values()) {
            if (year.femaleNameMap.containsKey(processedName)) {
                records.add(year.femaleNameMap.get(processedName));
            } else if (year.maleNameMap.containsKey(processedName)) {
                records.add(year.maleNameMap.get(processedName));
            }
        }
        records.sort(NameRecord.occurrenceComparator);
        return records;
    }

}
