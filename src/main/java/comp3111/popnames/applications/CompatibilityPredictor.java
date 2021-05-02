package comp3111.popnames.applications;

import comp3111.popnames.metrics.*;
import comp3111.popnames.record.ThemeAnalyzer;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This is the predictor class for the compatibility score.
 */
public class CompatibilityPredictor {

    /**
     * The algorithm enum class.
     */
    public enum Algorithm {
        /**
         * The basic algorithm.
         */
        BASIC,
        /**
         * The algorithm using the datasets and info input.
         */
        DATA_BASED
    }

    /**
     * The unique instance of the singleton class.
     */
    public static CompatibilityPredictor instance = new CompatibilityPredictor();

    /**
     * The education metrics.
     */
    public final EducationalMetrics eduMetrics;

    /**
     * The income metrics.
     */
    public final IncomeMetrics incomeMetrics;

    /**
     * The theme analyzer.
     */
    public final ThemeAnalyzer themeAnalyzer;

    /**
     * The age metrics.
     */
    public final AgeMetrics ageMetrics;

    /**
     * The kids metrics.
     */
    public final KidsMetrics kidsMetrics;

    /**
     * The theme metrics.
     */
    public final ThemeMetrics themeMetrics;

    /**
     * The meaning metrics.
     */
    public final MeaningMetrics meanMetrics;

    /**
     * The name property metrics.
     */
    public final NamePropertyMetrics propertyMetrics;

    /**
     * The list of metrics.
     */
    public final ArrayList<Metrics> metrics;

    /**
     * The corresponding weights of the metrics.
     */
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
        propertyMetrics = new NamePropertyMetrics();

        metrics = new ArrayList<>(
                Arrays.asList(themeMetrics, meanMetrics, propertyMetrics, ageMetrics, eduMetrics,
                        incomeMetrics, kidsMetrics)
        );

        weights = new ArrayList<>(
                Arrays.asList(0.2, 0.2, 0.2, 0.1, 0.1, 0.1, 0.1)
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

    /**
     * Set the algorithm to be used
     * @param algorithm the algorithm to be used
     */
    public void algorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * Get the algorithm used
     * @return the algorithm
     */
    public Algorithm algorithm() {
        return algorithm;
    }

    /**
     * Predict the compatibility score
     * @return the score
     */
    public double predict() {
        if (algorithm == Algorithm.BASIC) {
            return basicPredictor();
        }

        double sum = 0.0;
        for (Metrics metric : metrics) {
            double score = metric.getScore();
            if (score >= 0.0) {
                sum += score * metric.weight();
            }
        }
        return sum;
    }

    /**
     * Use the basic algorithm to predict
     * @return the prediction
     */
    public double basicPredictor() {
        if (selfName.length() == mateName.length()) {
            return 1.0;
        }
        return 0.0;
    }

    /**
     * Get the report from the basic algorithm
     * @return the report
     */
    public String getBasicReport() {
        return "Basic algorithm:\nIf the two names have the same length, compatibility score will be 100, otherwise 0.";
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

    /**
     * Personal name of the user.
     */
    public String selfName;

    /**
     * User's year of birth.
     */
    public int selfYob;

    /**
     * User's gender.
     */
    public char selfGender;

    /**
     * Mate's name.
     */
    public String mateName;

    /**
     * Mate's year of birth.
     */
    public int mateYob;

    /**
     * Mate's gender.
     */
    public char mateGender;

    /**
     * The algorithm used.
     */
    public Algorithm algorithm;
}
