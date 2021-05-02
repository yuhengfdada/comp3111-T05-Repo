package comp3111.popnames.Task5;

import Task5.Task5_X1_NameAnalyzer;
import org.junit.Test;
import static org.junit.Assert.*;


public class Task5_X1_NameAnalyzerTest {

    @Test
    public void getSummarytest(){
        Task5_X1_NameAnalyzer.getSummary(2000, 'F');
        assertEquals("Emily", Task5_X1_NameAnalyzer.mostPopularName);
        assertEquals(25952, Task5_X1_NameAnalyzer.occurrences);
    }
}
