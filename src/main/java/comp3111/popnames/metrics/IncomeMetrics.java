package comp3111.popnames.metrics;

public class IncomeMetrics implements Metrics {

    enum IncomeLevel {
        LOWEST_25, MIDDLE_50, TOP_25
    }

    private IncomeLevel self, mate;
    private double selfSig, mateSig;

    /**
     * Get the detailed score
     *
     * @return double
     */
    @Override
    public double getScore() {
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
    public String getMetricName() {
        return "Income Level";
    }

    /**
     * Get the explanation of the score
     *
     * @return String
     */
    @Override
    public String getExplanation() {
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
     *
     * @param self self's significance level
     * @param mate mate's significance level
     */
    @Override
    public void setSignificance(double self, double mate) {
        selfSig = self;
        mateSig = mate;
    }
}
