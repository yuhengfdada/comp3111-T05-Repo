package comp3111.popnames.predictor;

import comp3111.popnames.metrics.*;
import comp3111.popnames.record.ThemeAnalyzer;

public class CompatibilityPredictor {
    enum AgePreference {
        YOUNGER, OLDER
    }

    public enum Algorithm {
        BASIC, DATA_BASED
    }

    public static CompatibilityPredictor instance = new CompatibilityPredictor();
    public final EducationalMetrics eduMetrics;
    public final IncomeMetrics incomeMetrics;
    public final MiscMetrics miscMetrics;
    public final ThemeAnalyzer themeAnalyzer;

    private CompatibilityPredictor() {
        algorithm = Algorithm.BASIC;
        eduMetrics = new EducationalMetrics();
        incomeMetrics = new IncomeMetrics();
        miscMetrics = new MiscMetrics();
        themeAnalyzer = new ThemeAnalyzer();
    }

    public static CompatibilityPredictor getInstance() {
        return instance;
    }

    public void setSelfInfo(String name, int yearOfBirth, char gender) {
        this.selfName = name;
        this.selfYob = yearOfBirth;
        this.selfGender = gender;
    }

    public void setMateInfo(String name, char gender) {
        mateName = name;
        mateGender = gender;
    }

    public void setMateYob(int yearOfBirth) {
        mateYob = yearOfBirth;
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
        return 0.0;
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

    /**
     * Accessor of mate's name
     * @return mate's name
     */
    public String mateName() {
        return mateName;
    }

    private String selfName;
    private int selfYob;
    private char selfGender;
    private String mateName;
    private int mateYob;
    private char mateGender;
    private AgePreference agePreference;
    private Algorithm algorithm;
}
