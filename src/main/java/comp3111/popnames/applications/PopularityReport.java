package comp3111.popnames.applications;

import comp3111.popnames.record.NameAnalyzer;
import comp3111.popnames.record.NameAnalyzer.NameQuery;

import java.util.ArrayList;
import java.util.Comparator;

public class PopularityReport {

    private int startYear;
    private int endYear;
    private char gender;
    private String name;
    private ArrayList<NameQuery> query;
    private ArrayList<NameQuery> sortedQuery;

    private static final Comparator<NameQuery> queryComparator = new Comparator<NameQuery>() {
        @Override
        public int compare(NameQuery o1, NameQuery o2) {
            return o2.rank - o1.rank;
        }
    };

    /**
     * Input parameters and generate report
     * @param start the starting year
     * @param end the ending year
     * @param gender the gender preferred for the name
     * @param name the name of interest
     * @return true for success
     */
    public boolean generateReport(int start, int end, char gender, String name) {
        startYear = start;
        endYear = end;
        this.gender = gender;
        this.name = name;

        NameAnalyzer analyzer = NameAnalyzer.getInstance();
        query = analyzer.getNameReport(name, gender);
        if (query.isEmpty()) {
            return false;
        }

        sortedQuery = new ArrayList<>(query);
        sortedQuery.sort(queryComparator);
        return true;
    }

    /**
     * Get the main part of the summary.
     * @return the summary
     */
    public String getSummary() {
        StringBuilder text = new StringBuilder();
        text.append(String.format("The year when the name %s was most popular is %d at rank %d.",
                name, sortedQuery.get(0).year, sortedQuery.get(0).rank));
        text.append(String.format("In that year, the number of occurrence is %d, ", sortedQuery.get(0).occurrence));
        text.append(String.format("which represents %.2f%% of total %s births in %d.", sortedQuery.get(0).percentage,
                genderToStr(), sortedQuery.get(0).year));
        return text.toString();
    }

    /**
     * Get the query result
     * @return an ArrayList of NameQuery, empty if name not found
     */
    public ArrayList<NameQuery> query() {
        return query;
    }

    private String genderToStr() {
        if (gender == 'M') {
            return "male";
        } else {
            return "female";
        }
    }

}
