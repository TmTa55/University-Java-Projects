import java.util.*;
import java.io.*;

public class Trie
{

    private SuffixTrieNode root = new SuffixTrieNode();

    /**
     * Inserts a string into the trie and returns the last node that was
     * inserted.
     *
     * @param str The string to insert into the trie
     * @return The last node that was inserted into the trie
     */
    public SuffixTrieNode insert(String str)
    {
        String[] Lstr = str.split("");
        SuffixTrieNode parent = root;
        for (String s : Lstr)
        {
            SuffixTrieNode child = new SuffixTrieNode();
            //check if current node has child with label of the next character
            if (parent.getChild(s) != null)
                parent = parent.getChild(s);

            else
            {
                parent.addChild(s, child);
                parent = child;
            }
        }
        parent.wordEnd();
        parent.addWord(str);
        return parent;
    }

    /**
     * Search for a particular prefix in the trie, and return the final node in
     * the path from root to the end of the string, i.e. the node corresponding
     * to the final character. getNode() differs from get() in that getNode()
     * searches for any prefix starting from the root, and returns the node
     * corresponding to the final character of the prefix, whereas get() will
     * search for a whole word only and will return null if it finds the pattern
     * in the trie, but not as a whole word.  A "whole word" is a path in the
     * trie that has an ending node that is a terminal node.
     *
     * @param str The string to search for
     * @return the final node in the path from root to the end of the prefix, or
     * null if prefix is not found
     */
    public SuffixTrieNode getNode(String str)
    {
        String[] Lstr = str.split("");
        SuffixTrieNode parent = root;
        for (int i = 0; i < Lstr.length; i++)
        {
            if (parent.getChild(Lstr[i]) != null)
            {
                parent = parent.getChild(Lstr[i]);
                if ((i == Lstr.length-1))
                    return parent;
            }
            else
                return null;

        }
        return null;
    }

    /**
     * Searches for a word in the trie, and returns the final node in the search
     * sequence from the root, i.e. the node corresponding to the final
     * character in the word.
     *
     * getNode() differs from get() in that getNode() searches for any prefix
     * starting from the root, and returns the node corresponding to the final
     * character of the prefix, whereas get() will search for a whole word only
     * and will return null if it finds the pattern in the trie, but not as a
     * whole word. A "whole word" is a path in the
     * trie that has an ending node that is a terminal node.
     *
     * @param str The word to search for
     * @return The node corresponding to the final character in the word, or
     * null if word is not found
     */
    public SuffixTrieNode get(String str)
    {
        SuffixTrieNode parent = getNode(str);
        if (parent == null)
            return null;
        if (parent.isTerminal())
            return parent;
        return null;
    }

    /**
     * Retrieve from the trie an alphabetically sorted list of all words
     * beginning with a particular prefix.
     *
     * @param prefix The prefix with which all words start.
     * @return The list of words beginning with the prefix, or an empty list if
     * the prefix was not found.
     */
    public List<String> getAlphabeticalListWithPrefix(String prefix)
    {
        SuffixTrieNode parent = getNode(prefix);
        List<String> fWords = new ArrayList<>();
        if (parent == null)
            return Collections.singletonList("");

        else
            recurseNodes(parent,fWords,prefix);

        Collections.sort(fWords);
        return fWords;
    }
    public void recurseNodes(SuffixTrieNode node, List<String> words, String incom)
    {
        if (node.isTerminal())
        {
            words.add(incom);
        }
        for (String childV: node.children.keySet())
        {
            String tmp = incom;
            tmp+=childV;
            recurseNodes(node.getChild(childV), words,tmp);
        }
    }

    /**
     * NOTE: TO BE IMPLEMENTED IN ASSIGNMENT 1 Reads in a dictionary from file
     * and places all words into the trie.
     *
     * @param fileName the file to read from
     * @return the trie containing all the words
     */
    public static Trie readInDictionary(String fileName)
    {
        /*
        File and scanner were used as scanner was
        */
        File Rfile = new File(fileName);
        Trie parent = new Trie();
        try
        {
            Scanner modFile = new Scanner(Rfile);
            while(modFile.hasNextLine())
            {
                String[] wLine;
                String line = modFile.nextLine();
                //get rid of all punctuation that isnt an apostrophe.
                line = line.replaceAll("[?!.,—;-]"," ");
                line = line.toLowerCase(Locale.ROOT);
                //seperate line by whitespaces in order to get all words.
                wLine = line.split("\\s+");
                for (String s : wLine) {
                    parent.insert(s);
                }
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return parent;
    }
}
