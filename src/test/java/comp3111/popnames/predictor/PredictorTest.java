package comp3111.popnames.predictor;

import org.junit.Test;

import static org.junit.Assert.*;

public class PredictorTest {
    @Test
    public void testBasicPredictor() {
        CompatibilityPredictor predictor = new CompatibilityPredictor();
        predictor.setSelfInfo("Rex", 425, 'M');
        predictor.setMateInfo("Nia", 'F');
        predictor.algorithm(CompatibilityPredictor.Algorithm.BASIC);
        assertEquals(1.0, predictor.predict(), 1e-3);

        predictor.setMateInfo("Homura", 'F');
        assertEquals(0.0, predictor.predict(), 1e-3);
    }
}
