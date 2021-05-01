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
     * @return double
     */
    public abstract double getScore();

    /**
     * Get the metric name
     * @return String
     */
    public abstract String getMetricName();

    /**
     * Get the explanation of the score
     * @return String
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

    protected static <E extends Enum> int getScale(E element) {
        return element.ordinal();
    }

    protected static <E extends Enum> int getDiff(E e1, E e2) {
        return Math.abs(e1.ordinal() - e2.ordinal());
    }

    protected double weight;
}

