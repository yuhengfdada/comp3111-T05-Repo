package comp3111.popnames.Task2;

import org.junit.Test;
import comp3111.popnames.Task2.Task2_NameEntry;
import static org.junit.Assert.*;

public class Task2NameEntryTest {
    @Test
    public void Namestest(){
        Task2_NameEntry newName = new Task2_NameEntry("Amy", 12, 188899, "25.6%");
        assertEquals("Amy", newName.getName());
        assertEquals(12, (int)newName.getFrequency());
        assertEquals(188899, (int)newName.getOccurrences());
        assertEquals("25.6%",newName.getPercentage());
        newName.setFrequency(1);
        assertEquals(1, (int)newName.getFrequency());
        newName.setName("Bob");
        assertEquals("Bob", newName.getName());
        newName.setOccurrences(20);
        assertEquals(20, (int)newName.getOccurrences());
        newName.setPercentage("27.8%");
        assertEquals("27.8%", newName.getPercentage());
    }
}
