package comp3111.popnames.record;

import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

public class NameRecordTest {
    @Test
    public void testNameRecordMaxComparator() {
        NameRecord name1 = new NameRecord("name1", 'M', 2021, 3);
        NameRecord name2 = new NameRecord("name2", 'F', 2021, 0);
        NameRecord name3 = new NameRecord("name3", 'F', 2021, 239);
        Comparator<NameRecord> comparator = NameRecord.maxComparator;
        assertTrue(comparator.compare(name1, name2) < 0);
        assertTrue(comparator.compare(name1, name3) > 0);
        assertEquals(0, comparator.compare(name2, name2));
    }

    @Test
    public void testNameRecordCompareTo() {
        NameRecord name1 = new NameRecord("name1", 'M', 2021, 3);
        NameRecord name2 = new NameRecord("name2", 'F', 2021, 0);
        NameRecord name3 = new NameRecord("name3", 'F', 2021, 239);
        assertTrue(name1.compareTo(name2) > 0);
        assertTrue(name2.compareTo(name3) < 0);
        assertEquals(0, name3.compareTo(name3));
        assertEquals(0, name3.compareTo("name"));
    }
}
