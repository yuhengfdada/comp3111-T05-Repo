package comp3111.popnames.util;

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
	
	public static boolean isAlpha(String name) {
	    char[] chars = name.toCharArray();

	    for (char c : chars) {
	        if(!Character.isLetter(c)) {
	            return false;
	        }
	    }
	    return true;
	}
	
	public static String cap(String str) {
		return str.substring(0,1).toUpperCase() + str.substring(1);
	}
}
