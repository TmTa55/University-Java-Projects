import java.util.*;
import java.io.*;

public class Trie
{

    private TrieNode root = new TrieNode();

    /**
     * Inserts a string into the trie and returns the last node that was
     * inserted.
     *
     * @param str The string to insert into the trie
     * @param data	The data associated with the string
     * @return The last node that was inserted into the trie
     */
    public TrieNode insert(String str, TrieData data)
    {
        char[] Lstr = str.toCharArray();
        TrieNode parent = root;
        for (char c : Lstr)
        {
            TrieNode child = new TrieNode();
            if (parent.getChild(c) != null)
                parent = parent.getChild(c);

            else
            {
                parent.addChild(c, child);
                parent = child;
            }
        }
        parent.addData(data);
        parent.wordEnd();
        parent.setWord(str);
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
    public TrieNode getNode(String str)
    {
        char[] Lstr = str.toCharArray();
        TrieNode parent = root;
        for (int i = 0; i < Lstr.length; i++)
        {
            TrieNode child = new TrieNode();
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
    public TrieNode get(String str)
    {
        TrieNode parent = getNode(str);
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
        TrieNode parent = getNode(prefix);
        List<String> fWords = new ArrayList<>();
        if (parent == null)
        {
            return Collections.singletonList("");
        }
        else
        {
            recurseNodes(parent,fWords,prefix);
        }
        Collections.sort(fWords);
        return fWords;
    }
    public void recurseNodes(TrieNode node, List<String> words, String incom)
    {
        if (node.isTerminal())
        {
            words.add(incom);
        }
        for (Character childV: node.children.keySet())
        {
            String tmp = incom;
            tmp+=childV;
            recurseNodes(node.getChild(childV), words,tmp);
        }
    }
    /**
     * NOTE: TO BE IMPLEMENTED IN ASSIGNMENT 1 Finds the most frequently
     * occurring word represented in the trie (according to the dictionary file)
     * that begins with the provided prefix.
     *
     * @param prefix The prefix to search for
     * @return The most frequent word that starts with prefix
     */
    public String getMostFrequentWordWithPrefix(String prefix)
    {
        /*
        * this code gets all the words starting with the prefix and their frequency
        * then it finds the highest frequency, correlates that frequency to a word
        * then returns that word.
        * */
        TrieNode parent = getNode(prefix);
        List<String> fWords = new ArrayList<>();
        List<Integer> frequent = new ArrayList<>();
        if (parent == null)
            return prefix;

        else
            recurseNodes2(parent,fWords,prefix,frequent);

        int maxFreq = Collections.max(frequent);
        int maxFreqPos = frequent.indexOf(maxFreq);
        return fWords.get(maxFreqPos);
    }
    public void recurseNodes2(TrieNode node, List<String> words, String incom, List<Integer> freq)
    {
        /*most of this code is copied from the other recurse nodes thus the name
        the only difference is that it now takes the word frequency as well*/
        if (node.isTerminal())
        {
            words.add(incom);
            freq.add(node.getFrequency());
        }
        for (Character childV: node.children.keySet())
        {
            String tmp = incom;
            tmp+=childV;
            recurseNodes2(node.getChild(childV),words,tmp,freq);
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
        The file struct is used to read in the whole file.
        The scanner struct is used to read the file struct line by line.
        both were used for simplicity
        */
        File Rfile = new File(fileName);
        Trie parent = new Trie();
        try {
            Scanner modFile = new Scanner(Rfile);
            while(modFile.hasNextLine())
            {
                String[] wLine;
                String line = modFile.nextLine();
                wLine = line.split("\\s+");
                TrieData freq = new TrieData(Integer.parseInt(wLine[2]));
                parent.insert(wLine[1],freq);
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return parent;
    }
}
