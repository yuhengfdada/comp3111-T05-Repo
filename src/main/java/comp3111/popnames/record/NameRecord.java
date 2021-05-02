package comp3111.popnames.record;

import java.util.Comparator;

/**
 * The class for storing the name records.
 */
public class NameRecord implements Comparable {
    /**
     * Construct a NameRecord.
     * @param name The name.
     * @param gender The gender.
     * @param year The year.
     * @param occurrence The occurrence.
     */
    public NameRecord(String name, char gender, int year, int occurrence) {
        this.name = name;
        this.gender = gender;
        this.year = year;
        this.occurrence = occurrence;
    }

    /**
     * compareTo for the Comparable.
     * @param o The other NameRecord.
     * @return The difference of the occurrence.
     */
    public int compareTo(Object o) {
        if (o.getClass() != NameRecord.class) {
            return 0;
        }
        NameRecord other = (NameRecord) o;
        return this.occurrence - other.occurrence;
    }

    /**
     * Get the rank.
     * @param rank The rank.
     */
    public void rank(int rank) {
        this.rank = rank;
    }

    /**
     * Get the year.
     * @return The year.
     */
    public int year() {
        return this.year;
    }

    /**
     * Get the occurrence.
     * @return The occurrence.
     */
    public int occurrence() {
        return this.occurrence;
    }

    /**
     * Get the rank.
     * @return The rank.
     */
    public int rank() {
        return this.rank;
    }

    /**
     * Get the gender.
     * @return The gender.
     */
    public char gender() {
        return this.gender;
    }

    /**
     * Get the name.
     * @return The name.
     */
    public String name() {
        return this.name;
    }

    /**
     * The comparator which sorts the recording according to the descending order of occurrences.
     */
    public static final Comparator<NameRecord> occurrenceComparator = new Comparator<NameRecord>() {
        @Override
        public int compare(NameRecord o1, NameRecord o2) {
            return o2.occurrence - o1.occurrence;
        }
    };

    /**
     * The comparator which sorts the recording according to the descending order of ranks.
     */
    public static final Comparator<NameRecord> rankComparator = new Comparator<NameRecord>() {
        @Override
        public int compare(NameRecord o1, NameRecord o2) {
            return o2.rank - o1.rank;
        }
    };

    private String name;
    private char gender;
    private int year;
    private int occurrence;
    private int rank;
}
