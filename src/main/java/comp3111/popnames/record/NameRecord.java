package comp3111.popnames.record;

import java.util.Comparator;

public class NameRecord implements Comparable {
    public NameRecord(String name, char gender, int year, int occurrence) {
        this.name = name;
        this.gender = gender;
        this.year = year;
        this.occurrence = occurrence;
    }

    public int compareTo(Object o) {
        if (o.getClass() != NameRecord.class) {
            return 0;
        }
        NameRecord other = (NameRecord) o;
        return this.occurrence - other.occurrence;
    }

    public void rank(int rank) {
        this.rank = rank;
    }

    public int year() {
        return this.year;
    }

    public int occurrence() {
        return this.occurrence;
    }

    public int rank() {
        return this.rank;
    }

    public char gender() {
        return this.gender;
    }

    public String name() {
        return this.name;
    }

    public static final Comparator<NameRecord> occurrenceComparator = new Comparator<NameRecord>() {
        @Override
        public int compare(NameRecord o1, NameRecord o2) {
            return o2.occurrence - o1.occurrence;
        }
    };

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
