package comp3111.popnames.Task2;

import comp3111.popnames.Task2.Task2_NameAnalyzer;
import org.junit.Test;
import static org.junit.Assert.*;

public class Task2NameAnalyzerTest {

    @Test
    public void testgetSummary(){
        Task2_NameAnalyzer.getSummary(2000, 2001, 3, 'F');
        assertEquals("Hannah", Task2_NameAnalyzer.name_list.get(0).getKey());
        assertEquals(20707, (int) Task2_NameAnalyzer.nameRecords.get(Task2_NameAnalyzer.name_list.get(0).getKey()));
        assertEquals("Madison", Task2_NameAnalyzer.name_list.get(1).getKey() );
        assertEquals(19967, (int) Task2_NameAnalyzer.nameRecords.get(Task2_NameAnalyzer.name_list.get(1).getKey()));
        assertEquals(40674, Task2_NameAnalyzer.totalOccurrence);

        String report = Task2_NameAnalyzer.getSummary(2005, 2005, 4 , 'M');
        assertEquals("Matthew", Task2_NameAnalyzer.name_list.get(0).getKey());
        assertEquals(21456, (int) Task2_NameAnalyzer.nameRecords.get(Task2_NameAnalyzer.name_list.get(0).getKey()));
        assertEquals(1,(int)Task2_NameAnalyzer.matchedNames.get("Matthew"));

        Task2_NameAnalyzer.getSummary(2005, 2010, 1 , 'F');
        assertEquals("Emily", Task2_NameAnalyzer.name_list.get(0).getKey());
    }
}
