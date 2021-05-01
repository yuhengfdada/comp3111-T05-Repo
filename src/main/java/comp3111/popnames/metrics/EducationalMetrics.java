package comp3111.popnames.metrics;

/**
 * This class compares the educational levels
 */
public class EducationalMetrics extends Metrics {

    /**
     * Indicators of educational levels
     */
    private enum EducationalLevel {
        ELEMENTARY_SCHOOL, MIDDLE_SCHOOL, HIGH_SCHOOL,
        BACHELOR, MASTER, PHD, NA
    }

    private EducationalLevel self, mate;
    private double selfSig, mateSig;
    private double weight;

    /**
     * Get the detailed score
     * @return the score between 0 and 1 if info is complete, otherwise -1
     */
    @Override
    public double getScore() {
        if (self == EducationalLevel.NA || mate == EducationalLevel.NA) {
            return -1.0;
        }

        int difference = (self.ordinal() - mate.ordinal());
        double selfScore = -difference * selfSig;
        double mateScore = difference * mateSig;

        return (Math.min(selfScore, mateScore) + 5.0) * 0.2 * weight;
    }

    /**
     * Get the metric name
     * @return String
     */
    @Override
    public String getMetricDescription() {
        return "Compatibility of educational level";
    }

    /**
     * Get the name of the metric
     *
     * @return the name
     */
    @Override
    public String getMetricName() {
        return "Education";
    }

    /**
     * Get the explanation of the score
     * @return String
     */
    @Override
    public String getExplanation() {
        if (self == EducationalLevel.NA || mate == EducationalLevel.NA) {
            return "This metric is not available since the information is incomplete.";
        }

        if (Math.max(selfSig, mateSig) < 2.5) {
            return "Educational levels are relatively irrelevant for your relationships.";
        }
        double score = getScore();
        if (score <= 0.33) {
            return "The educational levels of you and your mate do not match the corresponding expectations " +
                    "of each other";
        } else if (score <= 0.67) {
            return "There are discrepancies between your expectations and educational levels";
        } else {
            return "Your educational levels have minimal impact on the compatibility";
        }
    }

    /**
     * Set the significance level of the metrics
     * @param self self's significance level
     * @param mate mate's significance level
     */
    public void setSignificance(double self, double mate) {
        selfSig = self;
        mateSig = mate;
        weight = (self + mate) * 0.5;
    }

    /**
     * Set the educational level
     * @param level the level
     */
    public void setSelfEdu(EducationalLevel level) {
        self = level;
    }

    /**
     * Set the educational level
     * @param index the index of the level in enum
     */
    public void setSelfEdu(int index) {
        if (index < 0 || index >= EducationalLevel.values().length) {
            self = EducationalLevel.NA;
        } else {
            self = EducationalLevel.values()[index];
        }
    }

    /**
     * Set the educational level of the mate
     * @param level the level
     */
    public void setMateEdu(EducationalLevel level) {
        mate = level;
    }

    /**
     * Set the educational level of the mate
     * @param index the level index
     */
    public void setMateEdu(int index) {
        if (index < 0 || index >= EducationalLevel.values().length) {
            mate = EducationalLevel.NA;
        } else {
            mate = EducationalLevel.values()[index];
        }
    }

}
