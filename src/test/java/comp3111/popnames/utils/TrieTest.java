package comp3111.popnames.utils;

import comp3111.popnames.utils.Trie;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class TrieTest {

    @Test
    public void testAddName() {
        Trie trie = new Trie();
        trie.addName("May");
        trie.addName("Mary");
        trie.addName("Margret");
        trie.addName("May");
        assertTrue(trie.nameExists("May"));
        assertTrue(trie.nameExists("Mary"));
        assertTrue(trie.nameExists("Margret"));
    }

    @Test
    public void testFindPrefix() {
        Trie trie = new Trie();
        trie.addName("May");
        trie.addName("Mary");
        trie.addName("May");
        trie.addName("Margret");
        trie.addName("Michael");
        trie.addName("Mike");
        trie.addName("may");
        trie.addName("Joe");
        List<String> expectedList1 = new ArrayList<>(Arrays.asList("Margret", "Mary", "May"));
        List<String> expectedList2 = new ArrayList<>(Arrays.asList("Margret", "Mary"));
        assertEquals(expectedList1, trie.findNamesWithPrefix("Ma"));
        assertEquals(expectedList2, trie.findNamesWithPrefix("Mar"));
        assertTrue(trie.findNamesWithPrefix("j").isEmpty());
    }
}
