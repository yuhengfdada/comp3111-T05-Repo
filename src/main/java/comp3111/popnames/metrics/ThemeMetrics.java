package comp3111.popnames.metrics;

/**
 * The metrics of the compatibility of the underlying themes of the names.
 */
public class ThemeMetrics extends Metrics {

    private double themeSuitability;

    /**
     * Get the detailed score
     *
     * @return the score between 0 and 1 if info is complete, otherwise -1
     */
    @Override
    public double getScore() {
        return themeSuitability;
    }

    /**
     * Get the metric name
     *
     * @return String
     */
    @Override
    public String getMetricDescription() {
        return "Compatibility of the themes behind names";
    }

    /**
     * Get the name of the metric
     *
     * @return the name
     */
    @Override
    public String getMetricName() {
        return "Theme";
    }

    /**
     * Get the explanation of the score
     *
     * @return String
     */
    @Override
    public String getExplanation() {
        if (themeSuitability <= 0.33) {
            return "The themes behind you and your mate's name are not compatible";
        } else if (themeSuitability <= 0.67) {
            return "The themes behind you and your mate's name are somewhat compatible";
        } else {
            return "The themes behind you and your mate's name are very compatible";
        }
    }

    /**
     * Set the suitability of the theme.
     * @param value The value.
     */
    public void themeSuitability(double value) {
        themeSuitability = value;
    }

}
