package comp3111.popnames.metrics;

public class MeaningMetrics extends Metrics {

    private double meanSuitability;

    /**
     * Get the detailed score
     *
     * @return double
     */
    @Override
    public double getScore() {
        return meanSuitability * 0.2;
    }

    /**
     * Get the metric name
     *
     * @return String
     */
    @Override
    public String getMetricName() {
        return "Compatibility of the meanings behind names";
    }

    /**
     * Get the explanation of the score
     *
     * @return String
     */
    @Override
    public String getExplanation() {
        if (meanSuitability < 0.33) {
            return "The meanings behind you and your mate's name are not compatible";
        } else if (meanSuitability < 0.66) {
            return "The meanings behind you and your mate's name are somewhat compatible";
        } else {
            return "The meanings behind you and your mate's name are very compatible";
        }
    }

    public void meanSuitability(double value) {
        meanSuitability = value;
    }
}
