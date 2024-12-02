import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class WildSuffixTrie
{
    private SuffixTrieNode root = new SuffixTrieNode();

    public SuffixTrieNode insert(String str)
    {
        SuffixTrieNode parent = root;
        String[] Lstr = str.split("");
        //starting from each character, add each suffix into the suffix Trie
        for (int i = 0; i < str.length(); i++)
        {
            parent = root;
            for (int j = i; j < str.length(); j++)
            {
                SuffixTrieNode child = new SuffixTrieNode();
                //check if current node has child with label of the next character
                if (parent.getChild(Lstr[j]) != null)
                    parent = parent.getChild(Lstr[j]);

                else
                {
                    parent.addChild(Lstr[j], child);
                    parent = child;
                }
            }
            //add what word suffix belongs to
            parent.addWord(str);
            //set end of suffix
            parent.wordEnd();
        }
        return parent;
    }

    /**
     * Get the suffix trie node associated with the given (sub)string.
     *
     * @param str the (sub)string to search for
     * @return  the final node in the (sub)string
     */
    public SuffixTrieNode get(String str)
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
     * Helper/Factory method to build a SuffixTrie object from the text in the
     * given file.  The text file is broken up into sentences and each sentence
     * is inserted into the suffix trie.
     *
     * It is called in the following way
     * <code>SuffixTrie st = SuffixTrie.readInFromFile("Frank01e.txt");</code>
     *
     * @param fileName
     * @return
     */
    public static WildSuffixTrie readInFromFile(String fileName)
    {
        File Rfile = new File(fileName);
        WildSuffixTrie parent = new WildSuffixTrie();
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
                for (String s : wLine)
                {
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
