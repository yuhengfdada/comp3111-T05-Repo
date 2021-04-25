package comp3111.popnames.applications;

import comp3111.popnames.metrics.*;
import comp3111.popnames.record.ThemeAnalyzer;

import java.util.ArrayList;
import java.util.Arrays;

public class CompatibilityPredictor {

    public enum Algorithm {
        BASIC, DATA_BASED
    }

    public static CompatibilityPredictor instance = new CompatibilityPredictor();
    public final EducationalMetrics eduMetrics;
    public final IncomeMetrics incomeMetrics;
    public final ThemeAnalyzer themeAnalyzer;
    public final AgeMetrics ageMetrics;
    public final KidsMetrics kidsMetrics;
    public final ThemeMetrics themeMetrics;
    public final MeaningMetrics meanMetrics;
    public final ArrayList<Metrics> metrics;
    public final ArrayList<Double> weights;

    private CompatibilityPredictor() {
        algorithm = Algorithm.BASIC;
        eduMetrics = new EducationalMetrics();
        incomeMetrics = new IncomeMetrics();
        ageMetrics = new AgeMetrics();
        kidsMetrics = new KidsMetrics();
        themeMetrics = new ThemeMetrics();
        meanMetrics = new MeaningMetrics();
        themeAnalyzer = new ThemeAnalyzer();

        metrics = new ArrayList<Metrics>(
                Arrays.asList(eduMetrics, incomeMetrics, ageMetrics, kidsMetrics, themeMetrics, meanMetrics)
        );

        weights = new ArrayList<Double>(
                Arrays.asList(0.15, 0.15, 0.15, 0.15, 0.2, 0.2)
        );

        for (int i = 0; i < metrics.size(); ++i) {
            metrics.get(i).setWeight(weights.get(i));
        }
    }

    /**
     * Get the unique instance of the class
     * @return the instance of the class
     */
    public static CompatibilityPredictor getInstance() {
        return instance;
    }

    /**
     * Input personal info
     * @param name the name
     * @param yearOfBirth year of birth
     * @param gender gender
     */
    public void setSelfInfo(String name, int yearOfBirth, char gender) {
        this.selfName = name;
        this.selfYob = yearOfBirth;
        this.selfGender = gender;
    }

    /**
     * Input mate's info
     * @param name mate's name
     * @param gender mate's gender
     */
    public void setMateInfo(String name, char gender) {
        mateName = name;
        mateGender = gender;
    }

    public void algorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public Algorithm algorithm() {
        return algorithm;
    }

    public double predict() {
        if (algorithm == Algorithm.BASIC) {
            return basicPredictor();
        }

        double sum = 0.0;
        for (Metrics metric : metrics) {
            sum += metric.getScore() * metric.weight();
        }
        return sum;
    }

    public double basicPredictor() {
        if (selfName.length() == mateName.length()) {
            return 1.0;
        }
        return 0.0;
    }

    public String getBasicReport() {
        return "Basic algorithm: If the two names have the same length, compatibility score will be 100, otherwise 0.";
    }

    /**
     * Get the theme of the name
     * @param name the name of interest
     * @return the theme if name exists, null otherwise
     */
    public String getTheme(String name) {
        return themeAnalyzer.getNameTheme(name);
    }

    /**
     * Get the meaning of the name
     * @param name the name of interest
     * @return the meaning if name exists, null otherwise
     */
    public String getMeaning(String name) {
        return themeAnalyzer.getNameTheme(name);
    }

    public String selfName;
    public int selfYob;
    public char selfGender;
    public String mateName;
    public int mateYob;
    public char mateGender;
    public Algorithm algorithm;
}
