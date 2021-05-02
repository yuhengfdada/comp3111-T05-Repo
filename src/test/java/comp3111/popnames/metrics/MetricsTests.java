package comp3111.popnames.metrics;

import comp3111.popnames.applications.CompatibilityPredictor;
import comp3111.popnames.record.NameAnalyzer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

public class MetricsTests {
    @Test
    public void testAgeMetrics() {
        Random randomGenerator = new Random();
        NameAnalyzer analyzer = NameAnalyzer.getInstance();
        CompatibilityPredictor predictor = CompatibilityPredictor.getInstance();

        ArrayList<String> names = new ArrayList<>(Arrays.asList(
                "Emily", "Cody", "may", "James", "ReX", "Nia", "Jeremy", "Hikari", "Alice", "jason", "Emma",
                "Jane", "Tomas", "Desmond", "raymond", "kenneth", "Gibson", "neo", "Elisa"
        ));

        ArrayList<Character> genders = new ArrayList<>(Arrays.asList('M', 'F'));

        for (int i = 0; i < 100; i++) {
            int index = randomGenerator.nextInt(names.size());
            predictor.selfName = names.get(index);
            index = randomGenerator.nextInt(names.size());
            predictor.mateName = names.get(index);

            predictor.selfGender = genders.get(randomGenerator.nextInt(genders.size()));
            predictor.mateGender = genders.get(randomGenerator.nextInt(genders.size()));
            predictor.selfYob = randomGenerator.nextInt(140) + 1880;
            if (randomGenerator.nextInt(2) < 1) {
                predictor.mateYob = randomGenerator.nextInt(140) + 1880;
            }

            AgeMetrics metrics = new AgeMetrics();
            metrics.agePref = AgeMetrics.AgePreference.values()[randomGenerator.nextInt(2)];

            assertTrue(metrics.getScore() <= 1.0 && metrics.getScore() >= -1.0);
            metrics.getMetricName();
            metrics.getMetricDescription();
            metrics.getExplanation();
        }
    }

    @Test
    public void testEduMetrics() {
        Random randomGenerator = new Random();
        NameAnalyzer analyzer = NameAnalyzer.getInstance();
        CompatibilityPredictor predictor = CompatibilityPredictor.getInstance();

        ArrayList<String> names = new ArrayList<>(Arrays.asList(
                "Emily", "Cody", "may", "James", "ReX", "Nia", "Jeremy", "Hikari", "Alice", "jason", "Emma",
                "Jane", "Tomas", "Desmond", "raymond", "kenneth", "Gibson", "neo", "Elisa"
        ));

        ArrayList<Character> genders = new ArrayList<>(Arrays.asList('M', 'F'));

        for (int i = 0; i < 100; i++) {
            int index = randomGenerator.nextInt(names.size());
            predictor.selfName = names.get(index);
            index = randomGenerator.nextInt(names.size());
            predictor.mateName = names.get(index);

            predictor.selfGender = genders.get(randomGenerator.nextInt(genders.size()));
            predictor.mateGender = genders.get(randomGenerator.nextInt(genders.size()));
            predictor.selfYob = randomGenerator.nextInt(140) + 1880;
            if (randomGenerator.nextInt(2) < 1) {
                predictor.mateYob = randomGenerator.nextInt(140) + 1880;
            }

            EducationalMetrics metrics = new EducationalMetrics();
            metrics.setMateEdu(randomGenerator.nextInt(7));
            metrics.setSelfEdu(randomGenerator.nextInt(7));
            metrics.setSignificance(randomGenerator.nextDouble(), randomGenerator.nextDouble());

            assertTrue(metrics.getScore() <= 1.0 && metrics.getScore() >= -1.0);
            metrics.getMetricName();
            metrics.getMetricDescription();
            metrics.getExplanation();
        }
    }

    @Test
    public void testIncomeMetrics() {
        Random randomGenerator = new Random();
        NameAnalyzer analyzer = NameAnalyzer.getInstance();
        CompatibilityPredictor predictor = CompatibilityPredictor.getInstance();

        ArrayList<String> names = new ArrayList<>(Arrays.asList(
                "Emily", "Cody", "may", "James", "ReX", "Nia", "Jeremy", "Hikari", "Alice", "jason", "Emma",
                "Jane", "Tomas", "Desmond", "raymond", "kenneth", "Gibson", "neo", "Elisa"
        ));

        ArrayList<Character> genders = new ArrayList<>(Arrays.asList('M', 'F'));

        for (int i = 0; i < 100; i++) {
            int index = randomGenerator.nextInt(names.size());
            predictor.selfName = names.get(index);
            index = randomGenerator.nextInt(names.size());
            predictor.mateName = names.get(index);

            predictor.selfGender = genders.get(randomGenerator.nextInt(genders.size()));
            predictor.mateGender = genders.get(randomGenerator.nextInt(genders.size()));
            predictor.selfYob = randomGenerator.nextInt(140) + 1880;
            if (randomGenerator.nextInt(2) < 1) {
                predictor.mateYob = randomGenerator.nextInt(140) + 1880;
            }

            IncomeMetrics metrics = new IncomeMetrics();
            metrics.setSelf(randomGenerator.nextInt(4));
            metrics.setMate(randomGenerator.nextInt(4));
            metrics.setSignificance(randomGenerator.nextDouble(), randomGenerator.nextDouble());

            assertTrue(metrics.getScore() <= 1.0 && metrics.getScore() >= -1.0);
            metrics.getMetricName();
            metrics.getMetricDescription();
            metrics.getExplanation();
        }
    }

    @Test
    public void testKidsMetrics() {
        Random randomGenerator = new Random();
        NameAnalyzer analyzer = NameAnalyzer.getInstance();
        CompatibilityPredictor predictor = CompatibilityPredictor.getInstance();

        ArrayList<String> names = new ArrayList<>(Arrays.asList(
                "Emily", "Cody", "may", "James", "ReX", "Nia", "Jeremy", "Hikari", "Alice", "jason", "Emma",
                "Jane", "Tomas", "Desmond", "raymond", "kenneth", "Gibson", "neo", "Elisa"
        ));

        ArrayList<Character> genders = new ArrayList<>(Arrays.asList('M', 'F'));

        for (int i = 0; i < 100; i++) {
            int index = randomGenerator.nextInt(names.size());
            predictor.selfName = names.get(index);
            index = randomGenerator.nextInt(names.size());
            predictor.mateName = names.get(index);

            predictor.selfGender = genders.get(randomGenerator.nextInt(genders.size()));
            predictor.mateGender = genders.get(randomGenerator.nextInt(genders.size()));
            predictor.selfYob = randomGenerator.nextInt(140) + 1880;
            if (randomGenerator.nextInt(2) < 1) {
                predictor.mateYob = randomGenerator.nextInt(140) + 1880;
            }

            KidsMetrics metrics = new KidsMetrics();
            metrics.setSelfKids(randomGenerator.nextInt(4));
            metrics.setMateKids(randomGenerator.nextInt(4));

            assertTrue(metrics.getScore() <= 1.0 && metrics.getScore() >= -1.0);
            metrics.getMetricName();
            metrics.getMetricDescription();
            metrics.getExplanation();
        }
    }

    @Test
    public void testMeaningMetrics() {
        Random randomGenerator = new Random();
        NameAnalyzer analyzer = NameAnalyzer.getInstance();
        CompatibilityPredictor predictor = CompatibilityPredictor.getInstance();

        ArrayList<String> names = new ArrayList<>(Arrays.asList(
                "Emily", "Cody", "may", "James", "ReX", "Nia", "Jeremy", "Hikari", "Alice", "jason", "Emma",
                "Jane", "Tomas", "Desmond", "raymond", "kenneth", "Gibson", "neo", "Elisa"
        ));

        ArrayList<Character> genders = new ArrayList<>(Arrays.asList('M', 'F'));

        for (int i = 0; i < 100; i++) {
            int index = randomGenerator.nextInt(names.size());
            predictor.selfName = names.get(index);
            index = randomGenerator.nextInt(names.size());
            predictor.mateName = names.get(index);

            predictor.selfGender = genders.get(randomGenerator.nextInt(genders.size()));
            predictor.mateGender = genders.get(randomGenerator.nextInt(genders.size()));
            predictor.selfYob = randomGenerator.nextInt(140) + 1880;
            if (randomGenerator.nextInt(2) < 1) {
                predictor.mateYob = randomGenerator.nextInt(140) + 1880;
            }

            MeaningMetrics metrics = new MeaningMetrics();
            metrics.meanSuitability(randomGenerator.nextDouble());

            assertTrue(metrics.getScore() <= 1.0 && metrics.getScore() >= -1.0);
            metrics.getMetricName();
            metrics.getMetricDescription();
            metrics.getExplanation();
        }
    }

    @Test
    public void testThemeMetrics() {
        Random randomGenerator = new Random();
        NameAnalyzer analyzer = NameAnalyzer.getInstance();
        CompatibilityPredictor predictor = CompatibilityPredictor.getInstance();

        ArrayList<String> names = new ArrayList<>(Arrays.asList(
                "Emily", "Cody", "may", "James", "ReX", "Nia", "Jeremy", "Hikari", "Alice", "jason", "Emma",
                "Jane", "Tomas", "Desmond", "raymond", "kenneth", "Gibson", "neo", "Elisa"
        ));

        ArrayList<Character> genders = new ArrayList<>(Arrays.asList('M', 'F'));

        for (int i = 0; i < 100; i++) {
            int index = randomGenerator.nextInt(names.size());
            predictor.selfName = names.get(index);
            index = randomGenerator.nextInt(names.size());
            predictor.mateName = names.get(index);

            predictor.selfGender = genders.get(randomGenerator.nextInt(genders.size()));
            predictor.mateGender = genders.get(randomGenerator.nextInt(genders.size()));
            predictor.selfYob = randomGenerator.nextInt(140) + 1880;
            if (randomGenerator.nextInt(2) < 1) {
                predictor.mateYob = randomGenerator.nextInt(140) + 1880;
            }

            ThemeMetrics metrics = new ThemeMetrics();
            metrics.themeSuitability(randomGenerator.nextDouble());

            assertTrue(metrics.getScore() <= 1.0 && metrics.getScore() >= -1.0);
            metrics.getMetricName();
            metrics.getMetricDescription();
            metrics.getExplanation();
        }
    }

    @Test
    public void testNamePropertyMetrics() {
        Random randomGenerator = new Random();
        NameAnalyzer analyzer = NameAnalyzer.getInstance();
        CompatibilityPredictor predictor = CompatibilityPredictor.getInstance();

        ArrayList<String> names = new ArrayList<>(Arrays.asList(
                "Emily", "Cody", "may", "James", "ReX", "Nia", "Jeremy", "Hikari", "Alice", "jason", "Emma",
                "Jane", "Tomas", "Desmond", "raymond", "kenneth", "Gibson", "neo", "Elisa"
        ));

        ArrayList<Character> genders = new ArrayList<>(Arrays.asList('M', 'F'));

        for (int i = 0; i < 100; i++) {
            int index = randomGenerator.nextInt(names.size());
            predictor.selfName = names.get(index);
            index = randomGenerator.nextInt(names.size());
            predictor.mateName = names.get(index);

            predictor.selfGender = genders.get(randomGenerator.nextInt(genders.size()));
            predictor.mateGender = genders.get(randomGenerator.nextInt(genders.size()));
            predictor.selfYob = randomGenerator.nextInt(140) + 1880;
            if (randomGenerator.nextInt(2) < 1) {
                predictor.mateYob = randomGenerator.nextInt(140) + 1880;
            }

            NamePropertyMetrics metrics = new NamePropertyMetrics();
            metrics.setRarityPref(randomGenerator.nextInt(4));

            assertTrue(metrics.getScore() <= 1.0 && metrics.getScore() >= -1.0);
            metrics.getMetricName();
            metrics.getMetricDescription();
            metrics.getExplanation();
        }
    }
}
