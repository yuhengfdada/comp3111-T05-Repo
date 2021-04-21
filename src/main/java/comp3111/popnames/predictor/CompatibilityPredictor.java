package comp3111.popnames.predictor;

import comp3111.popnames.metrics.*;

public class CompatibilityPredictor {
    enum AgePreference {
        YOUNGER, OLDER
    }

    enum Algorithm {
        BASIC, DATA_BASED
    }

    public static CompatibilityPredictor instance = new CompatibilityPredictor();
    public final EducationalMetrics eduMetrics;
    public final IncomeMetrics incomeMetrics;
    public final MiscMetrics miscMetrics;

    private CompatibilityPredictor() {
        algorithm = Algorithm.BASIC;
        eduMetrics = new EducationalMetrics();
        incomeMetrics = new IncomeMetrics();
        miscMetrics = new MiscMetrics();
    }

    public static CompatibilityPredictor getInstance() {
        return instance;
    }

    public void setSelfInfo(String name, int yearOfBirth, char gender) {
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.gender = gender;
    }

    public void setMateInfo(String name, char gender) {
        nameMate = name;
        genderMate = gender;
    }

    public void algorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public double predict() {
        if (algorithm == Algorithm.BASIC) {
            return basicPredictor();
        }
        return 0.0;
    }

    public double basicPredictor() {
        if (name.length() == nameMate.length()) {
            return 1.0;
        }
        return 0.0;
    }

    private String name;
    private int yearOfBirth;
    private char gender;
    private String nameMate;
    private char genderMate;
    private AgePreference agePreference;
    private Algorithm algorithm;
}
