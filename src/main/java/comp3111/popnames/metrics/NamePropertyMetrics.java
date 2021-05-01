package comp3111.popnames.metrics;

import comp3111.popnames.applications.CompatibilityPredictor;
import comp3111.popnames.record.NameAnalyzer;
import comp3111.popnames.record.NameAnalyzer.NameQuery;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.Objects;

/**
 * This measures the compatibility of the underlying traits of the two names.
 * For instance, the rarity and the most popular time periods.
 */
public class NamePropertyMetrics extends Metrics {

    enum Rarity {
        POPULAR, MEDIOCRE, RARE, NA
    }

    private static final int QUERY_RANGE = 10;
    private boolean isDirty = false;

    String selfName, mateName;
    double score, yearScore, rankScore, prefScore;
    String explanation;
    Rarity rarityPref = Rarity.NA;

    double selfMeanYear, mateMeanYear;
    double selfMeanRank, mateMeanRank;

    /**
     * Get the detailed score
     *
     * @return the score between 0 and 1 if info is complete, otherwise -1
     */
    @Override
    public double getScore() {
        CompatibilityPredictor predictor = CompatibilityPredictor.getInstance();
        if (selfName == null || mateName == null) {
            selfName = predictor.selfName;
            mateName = predictor.mateName;
            update();
            return score;
        } else if (selfName.equals(predictor.selfName) && mateName.equals(predictor.mateName) && !isDirty) {
            // Use the cached score
            return score;
        } else {
            update();
            return score;
        }
    }

    /**
     * Get the description of the metric
     *
     * @return the description
     */
    @Override
    public String getMetricDescription() {
        return "The similarity of the underlying traits of the names";
    }

    /**
     * Get the name of the metric
     *
     * @return the name
     */
    @Override
    public String getMetricName() {
        return "Similarity";
    }

    /**
     * Get the explanation of the score
     *
     * @return the explanation
     */
    @Override
    public String getExplanation() {
        CompatibilityPredictor predictor = CompatibilityPredictor.getInstance();
        if (selfName == null || mateName == null) {
            selfName = predictor.selfName;
            mateName = predictor.mateName;
            update();
            return Objects.requireNonNullElse(explanation,
                    "This metric is not available since the names input are not in our database.");
        } else if (selfName.equals(predictor.selfName) && mateName.equals(predictor.mateName) && !isDirty) {
            // Use the cached score
            return Objects.requireNonNullElse(explanation,
                    "This metric is not available since the names input are not in our database.");
        } else {
            update();
            return Objects.requireNonNullElse(explanation,
                    "This metric is not available since the names input are not in our database.");
        }
    }

    /**
     * Update the score and the explanation
     */
    private void update() {
        NameAnalyzer analyzer = NameAnalyzer.getInstance();
        CompatibilityPredictor predictor = CompatibilityPredictor.getInstance();

        ArrayList<NameQuery> selfQuery = analyzer.getNameReport(selfName, predictor.selfGender, 1880, 2019);
        if (selfQuery.isEmpty()) {
            score = -1.0;
            explanation = null;
            return;
        }

        ArrayList<NameQuery> mateQuery = analyzer.getNameReport(mateName, predictor.mateGender, 1880, 2019);
        if (mateQuery.isEmpty()) {
            score = -1.0;
            explanation = null;
            return;
        }

        updateScore(selfQuery, mateQuery);
        updateExplanation();
        isDirty = false;
    }

    private void updateScore(ArrayList<NameQuery> selfQuery, ArrayList<NameQuery> mateQuery) {
        selfQuery.sort(NameQuery.occurrenceComparator);
        mateQuery.sort(NameQuery.occurrenceComparator);

        IntSummaryStatistics selfYearStats = new IntSummaryStatistics();
        IntSummaryStatistics mateYearStats = new IntSummaryStatistics();

        for (int i = 0; i < Math.min(QUERY_RANGE, selfQuery.size()); i++) {
            selfYearStats.accept(selfQuery.get(i).getYear());
        }

        for (int i = 0; i < Math.min(QUERY_RANGE, mateQuery.size()); i++) {
            mateYearStats.accept(mateQuery.get(i).getYear());
        }

        IntSummaryStatistics selfRankStats = new IntSummaryStatistics();
        IntSummaryStatistics mateRankStats = new IntSummaryStatistics();

        for (NameQuery query : selfQuery) {
            selfRankStats.accept(query.getRank());
        }

        for (NameQuery query : mateQuery) {
            mateRankStats.accept(query.getRank());
        }

        selfMeanYear = selfYearStats.getAverage();
        mateMeanYear = mateYearStats.getAverage();
        selfMeanRank = selfRankStats.getAverage();
        mateMeanRank = mateRankStats.getAverage();

        double yearDiff = Math.abs(selfMeanYear - mateMeanYear);

        yearScore = Math.max(0.0, 1.0 - yearDiff * 0.01);
        rankScore = 1.0 - Math.abs(getRankLevel(selfMeanRank) - getRankLevel(mateMeanRank)) * 0.25;
        prefScore = -1.0;
        if (rarityPref == Rarity.POPULAR) {
            if (mateMeanRank <= 100) {
                prefScore = 1.0;
            } else {
                prefScore = 0.5 * 100.0 / mateMeanRank;
            }
        } else if (rarityPref == Rarity.MEDIOCRE) {
            if (mateMeanRank > 100 && mateMeanRank <= 2000) {
                prefScore = 1.0;
            } else {
                prefScore = 0.5 * 900.0 / Math.abs(mateMeanRank - 1000);
            }
        } else if (rarityPref == Rarity.RARE) {
            if (mateMeanRank > 2000) {
                prefScore = 1.0;
            } else {
                prefScore = (1.0 - 1.0 / mateMeanRank) * 0.5;
            }
        }

        if (prefScore < 0.0) {
            score = (yearScore + rankScore) * 0.5;
        } else {
            score = (yearScore + rankScore) * 0.3 + prefScore * 0.4;
        }
    }

    private void updateExplanation() {
        StringBuilder text = new StringBuilder();
        if (yearScore <= 0.33) {
            text.append("The popular year ranges for your names are significantly different: ");
        } else if (yearScore <= 0.67) {
            text.append("The popular year ranges for your names are different: ");
        } else {
            text.append("The popular year ranges for your names are close: ");
        }

        int startYear = (int) Math.min(selfMeanYear, mateMeanYear);
        int endYear = (int) Math.max(selfMeanYear, mateMeanYear);
        text.append(String.format("%d - %d.\n", startYear, endYear));

        if (rankScore <= 0.33) {
            text.append("The popularity for your names are significantly different: ");
        } else if (rankScore < 0.8) {
            text.append("The popularity for your names are different: ");
        } else {
            text.append("The popularity for your names are close: ");
        }

        text.append(String.format("your name is %s and your mate's name is %s.\n", rankToStr(selfMeanRank),
                rankToStr(mateMeanRank)));

        if (prefScore < 0.0) {
            text.append("The rarity preference metric is not available due to incomplete input.");
        } else if (prefScore <= 0.33) {
            text.append("The rarity of your mate's name is significantly inconsistent with you preference.");
        } else if (prefScore <= 0.60) {
            text.append("The rarity of your mate's name is inconsistent with you preference.");
        } else if (prefScore <= 0.80) {
            text.append("The rarity of your mate's name is relatively consistent with you preference.");
        } else {
            text.append("The rarity of your mate's name is very consistent with you preference.");
        }

        explanation = text.toString();
    }

    /**
     * Set the rarity preference by index
     * @param index the index of the enum element
     */
    public void setRarityPref(int index) {
        if (index < 0 || index >= Rarity.values().length) {
            rarityPref = Rarity.NA;
        } else {
            rarityPref = Rarity.values()[index];
        }
    }

    private String rankToStr(double rank) {
        if (rank <= 100) {
            return "popular";
        } else if (rank <= 2000) {
            return "mediocre";
        } else {
            return "rare";
        }
    }

    private int getRankLevel(double rank) {
        if (rank <= 100) {
            return 0;
        } else if (rank <= 500) {
            return 1;
        } else if (rank <= 2000) {
            return 2;
        } else if (rank <= 10000) {
            return 3;
        } else {
            return 4;
        }
    }

    /**
     * Notify the class that properties have been changed.
     */
    public void setDirty() {
        isDirty = true;
    }
}
