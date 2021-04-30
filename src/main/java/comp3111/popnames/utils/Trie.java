package comp3111.popnames.utils;

import java.util.*;

/**
 * This implements the trie used for autocompletion
 */
public class Trie {

    static class TrieNode {
        private boolean isWord = false;
        private String name;
        private final HashMap<Character, TrieNode> children;

        public TrieNode() {
            children = new HashMap<>();
        }

        public void setWord() {
            isWord = true;
        }

        public TrieNode findChild(char ch) {
            if (children.containsKey(ch)) {
                return children.get(ch);
            }
            return null;
        }

        public String getName() {
            return name;
        }

        public ArrayList<TrieNode> findAllLeaves() {
            ArrayList<TrieNode> leaves = new ArrayList<>();
            if (isWord) {
                leaves.add(this);
            }
            for (TrieNode node : children.values()) {
                leaves.addAll(node.findAllLeaves());
            }
            return leaves;
        }

        public TrieNode addChild(char ch) {
            TrieNode child = new TrieNode();
            children.put(ch, child);
            return child;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private final TrieNode root;
    private final HashSet<String> names;

    public Trie() {
        root = new TrieNode();
        names = new HashSet<>();
    }

    /**
     * Add a name to the trie
     * @param name the name to be added
     */
    public void addName(String name) {
        int depth = 0;
        TrieNode cur = root;
        while (depth < name.length()) {
            TrieNode next = cur.findChild(name.charAt(depth));
            if (next != null)
                cur = next;
            else
                cur = cur.addChild(name.charAt(depth));
            ++depth;
        }
        cur.setWord();
        cur.setName(name);
        names.add(name);
    }

    private List<String> prefixHelper(String prefix, int depth, TrieNode cur) {
        if (depth == prefix.length()) {
            ArrayList<TrieNode> leaves = cur.findAllLeaves();
            HashSet<String> strings = new HashSet<>();
            for (TrieNode leaf : leaves) {
                strings.add(leaf.name);
            }

            ArrayList<String> list = new ArrayList<>(strings);
            list.sort(Comparator.naturalOrder());
            return list;
        }

        TrieNode child = cur.findChild(prefix.charAt(depth));
        if (child == null) {
            return new ArrayList<>();
        }
        return prefixHelper(prefix, depth + 1, child);
    }

    /**
     * Given an input, return a list of names with the input as prefix
     * @param prefix the prefix
     * @return a List<String> contains the result
     */
    public List<String> findNamesWithPrefix(String prefix) {
        return prefixHelper(prefix, 0, root);
    }

    /**
     * Check whether a word exists in the trie
     * @param str the word of interest
     * @return whether the input is a word in trie
     */
    public boolean nameExists(String str) {
        return names.contains(str);
    }
}
