package comp3111.popnames.Task1;

public class TableEntry {
	String name;
	int occ;
	String percentage;
	
	
	public TableEntry(String n, int o, String p) {
		name = n;
		occ = o;
		percentage = p;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public void setOcc(int n) {
		occ = n;
	}
	
	public void setPercentage(String n) {
		percentage = n;
	}
	
	public String getName() {
		return name;
	}
	
	public int getOcc() {
		return occ;
	}
	
	public String getPercentage() {
		return percentage;
	}
	
}
