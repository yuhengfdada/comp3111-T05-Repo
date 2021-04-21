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

    private String selfName;
    private int selfYob;
    private char selfGender;
    private String mateName;
    private int mateYob;
    private char mateGender;
    private AgePreference agePreference;
    private Algorithm algorithm;
}
