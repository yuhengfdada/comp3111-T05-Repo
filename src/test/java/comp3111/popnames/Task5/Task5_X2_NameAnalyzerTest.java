package comp3111.popnames.Task5;

import Task5.Task5_X2_NameAnalyzer;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class Task5_X2_NameAnalyzerTest {

    @Test
    public void checkNametest(){
        assertEquals(false, Task5_X2_NameAnalyzer.checkName(2000, "NotvalidName", 'F'));
        assertEquals(true, Task5_X2_NameAnalyzer.checkName(2000, "Jessica", 'F'));
    }

    @Test
    public void getNametest(){
        Task5_X2_NameAnalyzer.getName(2000, "Madison", 'F', 'F', 'Y');
        assertEquals("Emily", Task5_X2_NameAnalyzer.name_list.get(0).getKey());
        assertEquals("Hannah",Task5_X2_NameAnalyzer.matchedName);
        assertEquals(3,Task5_X2_NameAnalyzer.ownNameRanking);
        assertEquals(3,Task5_X2_NameAnalyzer.matchedNameRanking);
        Task5_X2_NameAnalyzer.getName(2000, "Emily", 'F', 'F', 'Y');
        assertEquals("Madison", Task5_X2_NameAnalyzer.matchedName);
        assertEquals(1,Task5_X2_NameAnalyzer.ownNameRanking);
        assertEquals(2,Task5_X2_NameAnalyzer.matchedNameRanking);
        Task5_X2_NameAnalyzer.getName(1880, "Mary", 'F', 'F', 'O');
        assertEquals(2604, Task5_X2_NameAnalyzer.matchedNameOccurrence);
    }
}
