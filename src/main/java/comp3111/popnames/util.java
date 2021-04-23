package comp3111.popnames;

public class util {
	public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        int i = Integer.parseInt(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	public static boolean isValidYear(int year) {
		return year >= 1880 && year <= 2019;
	}
	
	public static boolean isValidN(int n) {
		return n >= 1 && n <= 10;
	}
}
