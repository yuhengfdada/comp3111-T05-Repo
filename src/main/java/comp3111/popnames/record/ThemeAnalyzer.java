package comp3111.popnames.record;

import edu.duke.FileResource;
import edu.duke.ResourceException;
import javafx.util.Pair;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThemeAnalyzer {

    private static class NameTheme {
        public NameTheme(String theme, String meaning, String usage, char gender) {
            this.theme = theme;
            this.meaning = meaning;
            this.usage = usage;
            this.gender = gender;
        }
        public String theme, meaning, usage, rootMeaning;
        public char gender;
    }

    private static final String themeDataPath;
    private static final String meaningDataPath;
    private static final int maxDepth = 5;
    private final HashMap<String, NameTheme> themes;

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

        setRootMeaning();
    }

    private void setRootMeaning() {
        for (NameTheme name : themes.values()) {
            if (name.rootMeaning != null) {
                continue;
            }
            findRoot(name, 0);
        }
    }

    private NameTheme findRoot(NameTheme cur, int depth) {
        Pattern pattern = Pattern.compile("[A-Z]{2,}");
        Matcher matcher = pattern.matcher(cur.meaning);
        if (matcher.find() && depth < maxDepth) {
            String name = matcher.group(0).toLowerCase(Locale.ROOT);
            if (themes.containsKey(name)) {
                NameTheme root = findRoot(themes.get(name), depth + 1);
                if (root == cur) {
                    return cur;
                }
                cur.rootMeaning = root.meaning; // path compression
                return root;
            }
        }
        return cur;
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
     * Get the root meaning of the name
     * @param name the name of interest
     * @return the root meaning if name exists in database, null otherwise
     */
    public String getNameRootMeaning(String name) {
        String processed = name.trim().toLowerCase(Locale.ROOT);
        if (themes.containsKey(name)) {
            return themes.get(name).rootMeaning;
        }
        return null;
    }

    /**
     * Get the full meaning of the name, including the direct and root meaning
     * @param name the name of interest
     * @return the full meaning if name exists in database, null otherwise
     */
    public String getNameFullMeaning(String name) {
        String meaning = getNameMeaning(name);
        if (meaning == null) {
            return null;
        }
        String rootMeaning = getNameRootMeaning(name);
        if (rootMeaning == null) {
            return meaning;
        } else {
            return meaning + "\n" + rootMeaning;
        }
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
