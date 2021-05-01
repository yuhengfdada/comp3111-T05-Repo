package comp3111.popnames.Task1;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TableEntryTest {
	@Test
	public void createEntryTest() {
		TableEntry tableEntry = new TableEntry("John", 20, "1%");
		tableEntry.setName("Henry");
		tableEntry.setOcc(111);
		tableEntry.setPercentage("22%");
		assertEquals("Henry", tableEntry.getName());
		assertEquals(111, tableEntry.getOcc());
		assertEquals("22%", tableEntry.getPercentage());
	}
}
