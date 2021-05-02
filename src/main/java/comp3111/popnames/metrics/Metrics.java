package comp3111.popnames.metrics;

import javafx.scene.text.Text;
import javafx.scene.paint.Color;

import java.awt.*;

/**
 * This interface provides detailed scores and related descriptions
 */
public abstract class Metrics {
    /**
     * Get the detailed score
     * @return the score between 0 and 1 if info is complete, otherwise -1
     */
    public abstract double getScore();

    /**
     * Get the description of the metric
     * @return the description
     */
    public abstract String getMetricDescription();

    /**
     * Get the name of the metric
     * @return the name
     */
    public abstract String getMetricName();

    /**
     * Get the explanation of the score
     * @return the explanation
     */
    public abstract String getExplanation();

    /**
     * Set the weight of the metric
     * @param weight the weight
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Get the weight of the metric
     * @return the weight
     */
    public double weight() {
        return weight;
    }

    /**
     * Get a formatted text of the score
     * @return the formatted text
     */
    public Text getFormattedScore() {
        String totalWeight = String.format("%.2f", weight * 100);
        double rawScore = getScore();
        String score = String.format("%.2f", rawScore * weight * 100);

        if (rawScore < 0.0) {
            Text text = new Text("N/A");
            text.setFill(Color.RED);
            return text;
        }

        Text text = new Text(score + "/" + totalWeight);
        if (rawScore < 0.33) {
            text.setFill(Color.RED);
        } else if (rawScore < 0.67) {
            text.setFill(Color.ORANGE);
        } else {
            text.setFill(Color.GREEN);
        }
        return text;
    }

    /**
     * Get the ordinal of a enum variable.
     * @param element the enum variable
     * @param <E> the enum class
     * @return the ordinal
     */
    protected static <E extends Enum> int getScale(E element) {
        return element.ordinal();
    }

    /**
     * Get the difference of the ordinals of two enum variables.
     * @param e1 The first variable.
     * @param e2 The second variable.
     * @param <E> The enum class.
     * @return The difference.
     */
    protected static <E extends Enum> int getDiff(E e1, E e2) {
        return Math.abs(e1.ordinal() - e2.ordinal());
    }

    /**
     * The weight of the metrics.
     */
    protected double weight;
}

