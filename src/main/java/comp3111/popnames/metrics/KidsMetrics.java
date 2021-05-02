package comp3111.popnames.metrics;

/**
 * The metrics for compatibility on kids preferences.
 */
public class KidsMetrics extends Metrics {

    /**
     * The enum class of kids preferences.
     */
    private enum kidsPreference {
        TWO_OR_MORE, ONE, NONE, NA
    }

    private kidsPreference selfKids, mateKids;

    /**
     * Get the detailed score
     *
     * @return the score between 0 and 1 if info is complete, otherwise -1
     */
    @Override
    public double getScore() {
        if (selfKids == kidsPreference.NA || mateKids == kidsPreference.NA) {
            return -1.0;
        }
        return 1 - getDiff(selfKids, mateKids) * 0.5;
    }

    /**
     * Get the metric name
     *
     * @return String
     */
    @Override
    public String getMetricDescription() {
        return "Compatibility of the attitudes towards kid raising";
    }

    /**
     * Get the name of the metric
     *
     * @return the name
     */
    @Override
    public String getMetricName() {
        return "Kids";
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

    /**
     * Set the kids preference of the user.
     * @param index The ordinal of the enum.
     */
    public void setSelfKids(int index) {
        if (index < 0 || index >= kidsPreference.values().length) {
            selfKids = kidsPreference.NA;
        } else {
            selfKids = kidsPreference.values()[index];
        }
    }

    /**
     * Set the kids preference of the mate.
     * @param index The ordinal of the enum.
     */
    public void setMateKids(int index) {
        if (index < 0 || index >= kidsPreference.values().length) {
            mateKids = kidsPreference.NA;
        } else {
            mateKids = kidsPreference.values()[index];
        }
    }

}
