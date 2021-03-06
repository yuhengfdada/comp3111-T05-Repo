package comp3111.popnames.metrics;

/**
 * The metrics for income factors.
 */
public class IncomeMetrics extends Metrics {

    /**
     * The enum class for income levels.
     */
    private enum IncomeLevel {
        LOWEST_25, MIDDLE_50, TOP_25, NA
    }

    private IncomeLevel self, mate;
    private double selfSig, mateSig;

    /**
     * Get the detailed score
     *
     * @return the score between 0 and 1 if info is complete, otherwise -1
     */
    @Override
    public double getScore() {
        if (self == IncomeLevel.NA || mate == IncomeLevel.NA) {
            return -1.0;
        }

        double score = (IncomeLevel.TOP_25.ordinal() - self.ordinal()) * mateSig
            + (IncomeLevel.TOP_25.ordinal() - mate.ordinal()) * selfSig;
        return 1.0 - score * 0.25;
    }

    /**
     * Get the metric name
     *
     * @return String
     */
    @Override
    public String getMetricDescription() {
        return "Compatibility of income level";
    }

    /**
     * Get the name of the metric
     *
     * @return the name
     */
    @Override
    public String getMetricName() {
        return "Income";
    }

    /**
     * Get the explanation of the score
     *
     * @return String
     */
    @Override
    public String getExplanation() {
        if (self == IncomeLevel.NA || mate == IncomeLevel.NA) {
            return "This metric is not available since the information is incomplete.";
        }

        double score = getScore();
        if (score > 0.8) {
            return "Income level has minimal impact on your relationships.";
        }

        if (selfSig + mateSig > 1.0) {
            String head = "Income level is significant for your relationships.";
            if (score < 0.5) {
                return head + " And the income situation from your perspective is not ideal.";
            }
            return head + " But the income situation is relatively satisfactory for you two.";
        }

        return "Income level has limited impact on your relationships.";
    }

    /**
     * Set the significance level of the metrics
     * @param self self's significance level
     * @param mate mate's significance level
     */
    public void setSignificance(double self, double mate) {
        selfSig = self;
        mateSig = mate;
    }

    /**
     * Set the self income level.
     * @param index the ordinal of the enum element.
     */
    public void setSelf(int index) {
        if (index < 0 || index >= IncomeLevel.values().length) {
            self = IncomeLevel.NA;
        } else {
            self = IncomeLevel.values()[index];
        }
    }

    /**
     * Set the income level of the mate.
     * @param index The ordinal of the enum variable.
     */
    public void setMate(int index) {
        if (index < 0 || index >= IncomeLevel.values().length) {
            mate = IncomeLevel.NA;
        } else {
            mate = IncomeLevel.values()[index];
        }
    }

}
