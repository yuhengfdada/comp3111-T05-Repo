package comp3111.popnames.record;

import comp3111.popnames.utils.Trie;
import edu.duke.FileResource;
import edu.duke.ResourceException;
import javafx.beans.property.SimpleStringProperty;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.util.*;

/**
 * The analyzer for the name database
 */
public class NameAnalyzer {

    /**
     * This contains the name records within each year
     */
    public static class YearRecord {
        /**
         * The constructor of the class.
         */
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
        /**
         * The occurence of the name.
         */
        public int occurrence;

        /**
         * The rank of the name.
         */
        public int rank;

        /**
         * The year of the name.
         */
        public int year;

        /**
         * The percentage of total birth of the given gender in the given year.
         */
        public double percentage;
        private final SimpleStringProperty percentageProperty = new SimpleStringProperty();

        /**
         * Get the occurrence of the name in one year
         * @return the occurrence
         */
        public int getOccurrence() {
            return occurrence;
        }

        /**
         * Get the rank of the name in one year
         * @return the rank
         */
        public int getRank() {
            return rank;
        }

        /**
         * Get the year for the query
         * @return the year
         */
        public int getYear() {
            return year;
        }

        /**
         * Get the formatted percentage info of the name
         * @return the formatted SimpleStringProperty for TableView
         */
        public SimpleStringProperty percentageProperty() {
            percentageProperty.set(String.format("%.2f%%", percentage * 100.0));
            return percentageProperty;
        }

        /**
         * Comparator according to the occurrence of the name in descending order
         */
        public static final Comparator<NameQuery> occurrenceComparator = (o1, o2) -> o2.occurrence - o1.occurrence;
    }

    private static final NameAnalyzer instance = new NameAnalyzer(1880, 2019);

    /**
     * The trie used for autocompletion
     */
    public final Trie trie = new Trie();

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
    private final HashSet<String> nameSet = new HashSet<>();
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
            nameSet.add(name);
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

        for (String name : nameSet) {
            trie.addName(name);
        }
    }

    /**
     * Retrieve a name report about occurrence, rank and percentage
     * @param name the name of interest
     * @param gender the gender preferred
     * @param startYear the starting year of query range
     * @param endYear the ending year of query range
     * @return an ArrayList of NameQuery, empty if name is not found
     */
    public ArrayList<NameQuery> getNameReport(String name, char gender, int startYear, int endYear) {
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
