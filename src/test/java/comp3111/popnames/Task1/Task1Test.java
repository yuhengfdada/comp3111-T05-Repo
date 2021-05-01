package comp3111.popnames.Task1;


import static org.junit.Assert.assertEquals;

import org.junit.Test;
public class Task1Test {
	@Test
	public void task1Test() {
		Task1 task1 = new Task1();
		task1.year = 1880;
		task1.topN = 10;
		task1.getSummaryAndStore(task1.year);
		assertEquals(110491, task1.totalBoys);
		assertEquals(90993, task1.totalGirls);
		assertEquals(1058, task1.uniqueBoys);
		assertEquals(942, task1.uniqueGirls);
		assertEquals("James", task1.popNamesListM.get(2).getKey());
		assertEquals("Emma", task1.popNamesListF.get(2).getKey());
	}
}
