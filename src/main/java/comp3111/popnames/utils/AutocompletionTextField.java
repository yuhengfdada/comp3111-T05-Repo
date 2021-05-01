package comp3111.popnames.utils;

import comp3111.popnames.record.NameAnalyzer;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * This extends TextField and offers autocompletion for names based on trie.
 * Reference: https://gist.github.com/floralvikings/10290131
 */
public class AutocompletionTextField extends TextField {
    /**
     * The popup used to select an entry.
     */
    private final ContextMenu entriesPopup;

    /**
     * Create an AutocompletionTextField
     */
    public AutocompletionTextField() {
        super();
        entriesPopup = new ContextMenu();
        textProperty().addListener((observable, oldValue, newValue) -> {
            if (getText().length() == 0) {
                entriesPopup.hide();
            } else {
                NameAnalyzer analyzer = NameAnalyzer.getInstance();
                List<String> searchResult = analyzer.trie.findNamesWithPrefix(getText().toLowerCase(Locale.ROOT));
                if (searchResult.size() > 0) {
                    populatePopup(searchResult);
                    if (!entriesPopup.isShowing()) {
                        entriesPopup.show(AutocompletionTextField.this, Side.BOTTOM, 0, 0);
                    }
                } else {
                    entriesPopup.hide();
                }
            }
        });

        focusedProperty().addListener((observable, oldValue, newValue) -> entriesPopup.hide());
    }

    /**
     * Populate the entry set with the given search results. Display is limited to 10 entries, for performance.
     *
     * @param searchResult The set of matching strings.
     */
    private void populatePopup(List<String> searchResult) {
        List<CustomMenuItem> menuItems = new LinkedList<>();
        int maxEntries = 10;
        int count = Math.min(searchResult.size(), maxEntries);
        for (int i = 0; i < count; i++) {
            final String result = searchResult.get(i).substring(0, 1).toUpperCase()
                    + searchResult.get(i).substring(1);
            Label entryLabel = new Label(result);
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            item.setOnAction(actionEvent -> {
                setText(result);
                entriesPopup.hide();
            });
            menuItems.add(item);
        }
        entriesPopup.getItems().clear();
        entriesPopup.getItems().addAll(menuItems);
    }

}
