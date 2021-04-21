package comp3111.popnames.metrics;

public class MiscMetrics implements Metrics {

    public enum kidsPreference {
        TWO_OR_MORE, ONE, NONE, NA
    }

    private kidsPreference selfKids, mateKids;
    private boolean selfEmploy, mateEmploy;

    /**
     * Get the detailed score
     *
     * @return double
     */
    @Override
    public double getScore() {
        return 0;
    }

    /**
     * Get the metric name
     *
     * @return String
     */
    @Override
    public String getMetricName() {
        return null;
    }

    /**
     * Get the explanation of the score
     *
     * @return String
     */
    @Override
    public String getExplanation() {
        return null;
    }

    /**
     * Set the significance level of the metrics
     *
     * @param self self's significance level
     * @param mate mate's significance level
     */
    @Override
    public void setSignificance(double self, double mate) {

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
