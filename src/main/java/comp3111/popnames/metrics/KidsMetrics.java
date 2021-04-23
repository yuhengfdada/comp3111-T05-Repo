package comp3111.popnames.metrics;

public class KidsMetrics extends Metrics {

    public enum kidsPreference {
        TWO_OR_MORE, ONE, NONE, NA
    }

    private kidsPreference selfKids, mateKids;

    /**
     * Get the detailed score
     *
     * @return double
     */
    @Override
    public double getScore() {
        if (selfKids == kidsPreference.NA || mateKids == kidsPreference.NA) {
            return 1.0;
        }
        return 1 - getDiff(selfKids, mateKids) * 0.5;
    }

    /**
     * Get the metric name
     *
     * @return String
     */
    @Override
    public String getMetricName() {
        return "Compatibility of the attitudes towards kid raising";
    }

    /**
     * Get the explanation of the score
     *
     * @return String
     */
    @Override
    public String getExplanation() {
        if (mateKids == kidsPreference.NA || selfKids == kidsPreference.NA) {
            return "This metric is not available since the info is incomplete.";
        }
        int diff = getDiff(mateKids, selfKids);
        switch (diff) {
            case 0:
                return "Your attitudes towards raising kids coincide.";
            case 1:
                return "Your attitudes towards raising kids are different";
            case 2:
                return "You and your mate have great controversy in the choice of raising kids.";
            default:
                return "";
        }
    }

    public void setSelfKids(int index) {
        if (index < 0 || index >= kidsPreference.values().length) {
            selfKids = kidsPreference.NA;
        } else {
            selfKids = kidsPreference.values()[index];
        }
    }

    public void setMateKids(int index) {
        if (index < 0 || index >= kidsPreference.values().length) {
            mateKids = kidsPreference.NA;
        } else {
            mateKids = kidsPreference.values()[index];
        }
    }

}
