package comp3111.popnames.Task4;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class Task4Test {
	@Test
	public void task4Test() {
		Task4 task4 = new Task4();
		task4.setMomYear(1880);
		task4.setDadYear(2010);
		task4.getMostPopNameAndStore();
		assertEquals("Mary", task4.mostPopGirlString);
		assertEquals(7065, task4.mostPopGirlOcc);
		assertEquals("Jacob", task4.mostPopBoyString);
		assertEquals(22082, task4.mostPopBoyOcc);
	}
}
