package comp3111.popnames.Task4;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
public class Task4A2Test {
	@Test
	public void task4A2Test() {
		Task4A2 task4 = new Task4A2();
		task4.setMomYear(1999);
		task4.setDadYear(2000);
		task4.setDadName("John");
		task4.setMomName("Mary");
		task4.getMostPopNameAndStore();
		assertEquals("Cody", task4.selectedPopBoyString);
		assertEquals("Rose", task4.selectedPopGirlString);
	}
}
