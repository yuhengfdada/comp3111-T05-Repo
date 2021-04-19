package comp3111.popnames.predictor;

public class CompatibilityPredictor {
    enum AgePreference {
        YOUNGER, OLDER;
    }

    enum Algorithm {
        BASIC, DATA_BASED;
    }

    CompatibilityPredictor() {
        algorithm = Algorithm.BASIC;
    }

    void setSelfInfo(String name, int yearOfBirth, char gender) {
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.gender = gender;
    }

    void setMateInfo(String name, char gender) {
        nameMate = name;
        genderMate = gender;
    }

    void algorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    double predict() {
        if (algorithm == Algorithm.BASIC) {
            return basicPredictor();
        }
        return 0.0;
    }

    double basicPredictor() {
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
