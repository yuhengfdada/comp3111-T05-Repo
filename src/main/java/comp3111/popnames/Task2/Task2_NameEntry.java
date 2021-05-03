package comp3111.popnames.Task2;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Task2_NameEntry {

    private SimpleStringProperty Name;
    private SimpleIntegerProperty Frequency;
    private SimpleIntegerProperty  Occurrences;
    private SimpleStringProperty Percentage;

    public Task2_NameEntry(String fName, int fFrequency, int fOccurrences, String fPercentage){
        this.Name =  new SimpleStringProperty(fName);
        this.Frequency = new SimpleIntegerProperty(fFrequency);
        this.Occurrences = new SimpleIntegerProperty(fOccurrences);
        this.Percentage = new SimpleStringProperty(fPercentage);
    }
    public String getName() {
        return Name.get();
    }

    public void setName(String fName) {
        Name.set(fName);
    }

    public Integer getFrequency() {
        return Frequency.get();
    }

    public void setFrequency(int fFrequency) {
        Frequency.set(fFrequency);
    }

    public Integer getOccurrences() {
        return Occurrences.get();
    }

    public void setOccurrences(Integer fOccurrences) {
        Occurrences.set(fOccurrences);
    }

    public String getPercentage(){
        return Percentage.get();
    }

    public void setPercentage(String fPercentage){
        Percentage.set(fPercentage);
    }
}


