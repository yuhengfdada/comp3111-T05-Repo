package comp3111.popnames.metrics;

/**
 * This class compares the educational levels
 */
public class EducationalMetrics implements Metrics {

    enum EducationalLevel {
        ELEMENTARY_SCHOOL, MIDDLE_SCHOOL, HIGH_SCHOOL,
        BACHELOR, MASTER, PHD
    }

    private EducationalLevel self, mate;
    private double selfSig, mateSig;
    private double weight;

    /**
     * Get the detailed score
     * @return double
     */
    @Override
    public double getScore() {
        return Math.abs(self.ordinal() - mate.ordinal()) * weight;
    }

    /**
     * Get the metric name
     * @return String
     */
    @Override
    public String getMetricName() {
        return "Educational Level";
    }

    /**
     * Get the explanation of the score
     * @return String
     */
    @Override
    public String getExplanation() {
        if (weight < 0.2) {
            return "The differences in educational level are basically irrelevant for your relationships.";
        }
        double diff = Math.abs(self.ordinal() - mate.ordinal());
        if (diff < 0.5) {
            return "Your educational levels are relatively the same, so the impact on compatibility is minimal.";
        }
        return "There is difference in your educational levels.";
    }

    /**
     * Set the significance level of the metrics
     *
     * @param self self's significance level
     * @param mate mate's significance level
     */
    @Override
    public void setSignificance(double self, double mate) {
        selfSig = self;
        mateSig = mate;
        weight = (self + mate) * 0.5;
    }

}
