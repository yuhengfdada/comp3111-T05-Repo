package comp3111.popnames.metrics;

/**
 * The metrics on the compatibility of the underlying meanings of the names.
 */
public class MeaningMetrics extends Metrics {

    private double meanSuitability;

    /**
     * Get the detailed score
     *
     * @return the score between 0 and 1 if info is complete, otherwise -1
     */
    @Override
    public double getScore() {
        return meanSuitability;
    }

    /**
     * Get the metric name
     *
     * @return String
     */
    @Override
    public String getMetricDescription() {
        return "Compatibility of the meanings behind names";
    }

    /**
     * Get the name of the metric
     *
     * @return the name
     */
    @Override
    public String getMetricName() {
        return "Meaning";
    }

    /**
     * Get the explanation of the score
     *
     * @return String
     */
    @Override
    public String getExplanation() {
        if (meanSuitability <= 0.33) {
            return "The meanings behind you and your mate's name are not compatible";
        } else if (meanSuitability <= 0.67) {
            return "The meanings behind you and your mate's name are somewhat compatible";
        } else {
            return "The meanings behind you and your mate's name are very compatible";
        }
    }

    /**
     * Set the suitability of the meanings.
     * @param value The value.
     */
    public void meanSuitability(double value) {
        meanSuitability = value;
    }
}
