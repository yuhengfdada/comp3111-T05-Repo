package comp3111.popnames.record;

import edu.duke.FileResource;
import edu.duke.ResourceException;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.util.HashMap;
import java.util.Locale;

public class ThemeAnalyzer {

    private static class NameTheme {
        public NameTheme(String theme, String meaning, String usage, char gender) {
            this.theme = theme;
            this.meaning = meaning;
            this.usage = usage;
            this.gender = gender;
        }
        public String theme, meaning, usage;
        public char gender;
    }

    private static final String themeDataPath;
    private static final String meaningDataPath;
    private HashMap<String, NameTheme> themes;

    static {
        themeDataPath = "dataset/themes/name_themes.csv";
        meaningDataPath = "dataset/themes/name_with_meanings.csv";
    }

    public ThemeAnalyzer() {
        themes = new HashMap<>();
        initData();
    }

    private static CSVParser getFileParser(String path) {
        try {
            FileResource fr = new FileResource(path);
            return fr.getCSVParser(false);
        } catch (ResourceException e) {
            return null;
        }
    }

    /**
     * Initialize the meanings and themes data for names
     */
    private void initData() {
        CSVParser parser = getFileParser(themeDataPath);
        if (parser != null) {
            for (CSVRecord record : parser) {
                String name = record.get(1).trim().toLowerCase(Locale.ROOT);
                String theme = record.get(0);
                char gender = 'M';
                if (record.get(2).length() > 1) {
                    gender = 'N';
                } else if (record.get(2).equals("f")) {
                    gender = 'F';
                }
                String usage = record.get(3);
                String meaning = record.get(4);

                if (!themes.containsKey(name)) {
                    themes.put(name, new NameTheme(theme, meaning, usage, gender));
                }
            }
        }

        parser = getFileParser(meaningDataPath);
        if (parser != null) {
            for (CSVRecord record : parser) {
                String name = record.get(0).trim().toLowerCase(Locale.ROOT);
                char gender = 'M';
                if (record.get(1).length() > 1) {
                    gender = 'N';
                } else if (record.get(1).equals("f")) {
                    gender = 'F';
                }
                String usage = record.get(2);
                String meaning = record.get(3);

                if (!themes.containsKey(name)) {
                    themes.put(name, new NameTheme(null, meaning, usage, gender));
                }
            }
        }
    }

    /**
     * Get the theme of the name
     * @param name the name of interest
     * @return the theme if the name exists in database, null otherwise
     */
    public String getNameTheme(String name) {
        String processed = name.trim().toLowerCase(Locale.ROOT);
        if (themes.containsKey(name)) {
            return themes.get(name).theme;
        }
        return null;
    }

    /**
     * Get the meaning of the name
     * @param name the name of interest
     * @return the meaning if the name exists in database, null otherwise
     */
    public String getNameMeaning(String name) {
        String processed = name.trim().toLowerCase(Locale.ROOT);
        if (themes.containsKey(name)) {
            return themes.get(name).meaning;
        }
        return null;
    }

    /**
     * Get the commonly associated gender of the name
     * @param name the name of interest
     * @return the gender if name exists in database, '\0' otherwise
     */
    public char getGender(String name) {
        String processed = name.trim().toLowerCase(Locale.ROOT);
        if (themes.containsKey(name)) {
            return themes.get(name).gender;
        }
        return '\0';
    }

    /**
     * Get the range of languages where the name is used
     * @param name the name of interest
     * @return the result if name exists in database, null otherwise
     */
    public String getUsage(String name) {
        String processed = name.trim().toLowerCase(Locale.ROOT);
        if (themes.containsKey(name)) {
            return themes.get(name).usage;
        }
        return null;
    }

}
