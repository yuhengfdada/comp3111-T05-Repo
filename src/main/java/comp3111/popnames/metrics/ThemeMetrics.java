package comp3111.popnames.metrics;

public class ThemeMetrics extends Metrics {

    private double themeSuitability;

    /**
     * Get the detailed score
     *
     * @return double
     */
    @Override
    public double getScore() {
        return themeSuitability * 0.2;
    }

    /**
     * Get the metric name
     *
     * @return String
     */
    @Override
    public String getMetricName() {
        return "Compatibility of the themes behind names";
    }

    /**
     * Get the explanation of the score
     *
     * @return String
     */
    @Override
    public String getExplanation() {
        if (themeSuitability < 0.33) {
            return "The themes behind you and your mate's name are not compatible";
        } else if (themeSuitability < 0.66) {
            return "The themes behind you and your mate's name are somewhat compatible";
        } else {
            return "The themes behind you and your mate's name are very compatible";
        }
    }

    public void themeSuitability(double value) {
        themeSuitability = value;
    }

}
