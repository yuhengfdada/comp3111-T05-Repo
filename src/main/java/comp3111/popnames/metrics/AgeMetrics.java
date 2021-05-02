package comp3111.popnames.metrics;

import comp3111.popnames.applications.CompatibilityPredictor;
import comp3111.popnames.record.NameAnalyzer;
import comp3111.popnames.record.NameRecord;

import java.util.ArrayList;

/**
 * The metrics of compatibility on ages.
 */
public class AgeMetrics extends Metrics {

    /**
     * The enum class for age preferences.
     */
    public enum AgePreference {
        /**
         * Younger.
         */
        YOUNGER,
        /**
         * Older.
         */
        OLDER
    }

    /**
     * The age preference.
     */
    public AgePreference agePref;

    /**
     * Get the detailed score
     *
     * @return the score between 0 and 1 if info is complete, otherwise -1
     */
    @Override
    public double getScore() {
        NameAnalyzer analyzer = NameAnalyzer.getInstance();
        CompatibilityPredictor predictor = CompatibilityPredictor.getInstance();

        if (predictor.mateYob != -1) {
            if (predictor.mateYob <= predictor.selfYob && agePref == AgePreference.OLDER) {
                return 1.0;
            } else if (predictor.mateYob >= predictor.selfYob && agePref == AgePreference.YOUNGER) {
                return 1.0;
            }
            return 0.0;
        }

        ArrayList<NameRecord> mateRecords = analyzer.getSortedRecords(predictor.mateName);
        if (mateRecords.isEmpty()) {
            return 1.0;
        }
        int year = mateRecords.get(0).year();
        if (year <= predictor.selfYob && agePref == AgePreference.OLDER) {
            return 1.0;
        } else if (year >= predictor.selfYob && agePref == AgePreference.YOUNGER) {
            return 1.0;
        }
        return 0.0;
    }

    /**
     * Get the metric name
     *
     * @return String
     */
    @Override
    public String getMetricDescription() {
        return "Compatibility of the ages of the pair";
    }

    /**
     * Get the name of the metric
     *
     * @return the name
     */
    @Override
    public String getMetricName() {
        return "Age";
    }

    /**
     * Get the explanation of the score
     *
     * @return String
     */
    @Override
    public String getExplanation() {
        NameAnalyzer analyzer = NameAnalyzer.getInstance();
        CompatibilityPredictor predictor = CompatibilityPredictor.getInstance();

        if (predictor.mateYob != -1) {
            if (predictor.mateYob < predictor.selfYob && agePref == AgePreference.OLDER) {
                return "The age of your mate is consistent with your age preference.";
            } else if (predictor.mateYob > predictor.selfYob && agePref == AgePreference.YOUNGER) {
                return "The age of your mate is consistent with your age preference.";
            }
            return "The age of your mate is inconsistent with your age preference.";
        }

        ArrayList<NameRecord> mateRecords = analyzer.getSortedRecords(predictor.mateName);
        if (mateRecords.isEmpty()) {
            return "This metric is not available since the relevant information is incomplete.";
        }
        int year = mateRecords.get(0).year();
        String text = "According to data, the name of your mate is most likely belong to "
                + Integer.toString(year / 10 * 10) + "s.\n";

        if (year < predictor.selfYob && agePref == AgePreference.OLDER) {
            return text + "It is consistent with your age preference.";
        } else if (year > predictor.selfYob && agePref == AgePreference.YOUNGER) {
            return text + "It is consistent with your age preference.";
        }
        return text + "It is inconsistent with your age preference.";
    }
}
