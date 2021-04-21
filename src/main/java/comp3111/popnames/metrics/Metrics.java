package comp3111.popnames.metrics;

/**
 * This interface provides detailed scores and related descriptions
 */
public interface Metrics {
    /**
     * Get the detailed score
     * @return double
     */
    double getScore();

    /**
     * Get the metric name
     * @return String
     */
    String getMetricName();

    /**
     * Get the explanation of the score
     * @return String
     */
    String getExplanation();

    /**
     * Set the significance level of the metrics
     * @param self self's significance level
     * @param mate mate's significance level
     */
    void setSignificance(double self, double mate);
}

