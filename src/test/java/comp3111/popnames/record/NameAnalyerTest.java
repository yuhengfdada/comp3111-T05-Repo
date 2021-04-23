package comp3111.popnames.record;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class NameAnalyerTest {
    @Test
    public void testGetFileParser() throws IOException {
        NameAnalyzer analyzer = NameAnalyzer.getInstance();
        CSVParser parser1 = analyzer.getFileParser(0);
        CSVParser parser2 = analyzer.getFileParser(1890);
        List<CSVRecord> records = parser2.getRecords();

        assertNull(parser1);
        assertEquals(records.get(1514).get(0), "Tony");
        assertEquals(records.get(1).get(1), "F");
        assertEquals(records.get(0).get(2), "12078");
    }

    @Test
    public void testGetNameReport() {
        NameAnalyzer analyzer = NameAnalyzer.getInstance();
        ArrayList<NameAnalyzer.NameQuery> query1 = analyzer.getNameReport("Emma", 'F');
        ArrayList<NameAnalyzer.NameQuery> query2 = analyzer.getNameReport("emma", 'F');
        ArrayList<NameAnalyzer.NameQuery> query3 = analyzer.getNameReport("Zyran", 'M');
        ArrayList<NameAnalyzer.NameQuery> query4 = analyzer.getNameReport("Zyran", 'U');

        assertEquals(140, query1.size());
        assertEquals(0, query2.size());
        assertEquals(7, query3.size());
        assertEquals(0, query4.size());

        assertEquals(1880, query1.get(0).year);
        assertEquals(2303, query1.get(2).occurrence);
        assertEquals(3, query1.get(1).rank);
    }
}
