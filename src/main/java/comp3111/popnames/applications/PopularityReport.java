package comp3111.popnames.applications;

import comp3111.popnames.record.NameAnalyzer;
import comp3111.popnames.record.NameAnalyzer.NameQuery;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.IntSummaryStatistics;

/**
 * The class for generating the popularity report for task 3.
 */
public class PopularityReport {

    private int startYear;
    private int endYear;
    private char gender;
    private String name;
    private ArrayList<NameQuery> query;
    private ArrayList<NameQuery> sortedQuery;

    private static final PopularityReport instance = new PopularityReport();

    private PopularityReport() {

    }

    /**
     * Retrieve the unique instance of the class
     * @return the instance
     */
    public static PopularityReport getInstance() {
        return instance;
    }

    /**
     * This comparator sort the queries from high rank to low rank
     */
    private static final Comparator<NameQuery> queryComparator = new Comparator<NameQuery>() {
        @Override
        public int compare(NameQuery o1, NameQuery o2) {
            return o1.rank - o2.rank;
        }
    };

    /**
     * Input parameters and generate report
     * @param start the starting year
     * @param end the ending year
     * @param gender the gender preferred for the name
     * @param name the name of interest
     * @return flag for success
     */
    public boolean generateReport(int start, int end, char gender, String name) {
        startYear = start;
        endYear = end;
        this.gender = gender;
        this.name = name;

        NameAnalyzer analyzer = NameAnalyzer.getInstance();
        query = analyzer.getNameReport(name, gender, startYear, endYear);
        if (query.isEmpty()) {
            return false;
        }

        sortedQuery = new ArrayList<>(query);
        sortedQuery.sort(queryComparator);
        return true;
    }

    /**
     * Generate query result with previous year range and gender but (optionally) different name for comparison
     * @param name the optionally new name for comparison
     * @return the query result
     */
    public ArrayList<NameQuery> getQueryResult(String name) {
        NameAnalyzer analyzer = NameAnalyzer.getInstance();
        return analyzer.getNameReport(name, gender, startYear, endYear);
    }

    /**
     * Get the main part of the summary.
     * @return the summary
     */
    public String getSummary() {
        StringBuilder text = new StringBuilder();
        text.append(String.format("The year when the name %s was most popular is %d at rank %d. ",
                name, sortedQuery.get(0).year, sortedQuery.get(0).rank));
        text.append(String.format("\nIn that year, the number of occurrence is %d, ", sortedQuery.get(0).occurrence));
        text.append(String.format("which represents %s of total %s births in %d.",
                percentageToStr(sortedQuery.get(0).percentage), genderToStr(), sortedQuery.get(0).year));

        NameQuery lastElement = sortedQuery.get(sortedQuery.size() - 1);
        text.append(String.format("\n\nThe year when the name %s was least popular is %d at rank %d. ",
                name, lastElement.year, lastElement.rank));
        text.append(String.format("\nIn that year, the number of occurrence is %d, ", lastElement.occurrence));
        text.append(String.format("which represents %s of total %s births in %d.",
                percentageToStr(lastElement.percentage), genderToStr(), lastElement.year));

        IntSummaryStatistics occurStats = new IntSummaryStatistics();
        IntSummaryStatistics rankStats = new IntSummaryStatistics();
        for (NameQuery query : sortedQuery) {
            occurStats.accept(query.occurrence);
            rankStats.accept(query.rank);
        }

        text.append(String.format("\n\nStatistics for the occurrences of name %s between year %d and %d:",
                name, startYear, endYear));
        text.append(String.format("\nmaximum: %-9d minimum: %-9d mean: %-9.2f sum: %d", occurStats.getMax(),
                occurStats.getMin(), occurStats.getAverage(), occurStats.getSum()));

        text.append(String.format("\n\nStatistics for the ranks of name %s between year %d and %d:",
                name, startYear, endYear));
        text.append(String.format("\nmaximum: %-9d minimum: %-9d mean: %-12.2f", rankStats.getMax(),
                rankStats.getMin(), rankStats.getAverage()));

        String missing = getMissingYears();
        if (missing != null) {
            long yearCount = missing.chars().filter(ch -> ch == ',').count();
            if (yearCount > 6) {
                text.append(String.format("\n\n\nNOTE: Data are missing for %d years.", yearCount));
            } else {
                text.append("\n\n\nNOTE: Data are missing for year ").append(getMissingYears()).append(".");
            }
        }

        return text.toString();
    }

    /**
     * Get the query result
     * @return an ArrayList of NameQuery, empty if name not found
     */
    public ArrayList<NameQuery> query() {
        return query;
    }

    /**
     * Get the queried name
     * @return the name input
     */
    public String name() {
        return name;
    }

    private String genderToStr() {
        if (gender == 'M') {
            return "male";
        } else {
            return "female";
        }
    }

    private static String percentageToStr(double value) {
        if (value < 0.01) {
            return String.format("%.2f\u2030", value * 1000);
        } else {
            return String.format("%.2f%%", value * 100);
        }
    }

    private String getMissingYears() {
        StringBuilder text = new StringBuilder();
        for (int i = startYear; i < query.get(0).year; i++) {
            text.append(String.format("%d, ", i));
        }
        for (int i = 0; i < query.size() - 1; i++) {
            int cur = query.get(i).year;
            int next = query.get(i + 1).year;
            if (cur + 1 < next) {
                for (int j = cur + 1; j < next; j++) {
                    text.append(String.format("%d, ", j));
                }
            }
        }
        for (int i = query.get(query.size() - 1).year + 1; i <= endYear; i++) {
            text.append(String.format("%d, ", i));
        }
        if (text.length() > 0) {
            return text.substring(0, text.length() - 1);
        } else {
            return null;
        }
    }

}
