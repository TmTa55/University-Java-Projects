import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SuffixTrie
{

    private SuffixTrieNode root = new SuffixTrieNode();


    /**
     * Insert a String into the suffix trie.  For the assignment the string str
     * is a sentence from the given text file.
     *
     * @param str the sentence to insert
     * @param startPosition the starting index/position of the sentence
     * @return the final node inserted
     */
    public SuffixTrieNode insert(String str, int startPosition)
    {
        SuffixTrieNode parent = root;
        String[] Lstr = str.split("");
        int pos = 0;
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
                //add placement data of suffix
                parent.addData(startPosition,i);
            }
            parent.wordEnd();
            pos++;
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
    public static SuffixTrie readInFromFile(String fileName)
    {
        /*
        The file struct is used to read in the whole file.
        The scanner struct is used to read the file struct line by line.
        both were used for simplicity
        */
        File Rfile = new File(fileName);
        SuffixTrie parent = new SuffixTrie();
        try
        {
            Scanner modFile = new Scanner(Rfile);
            int i = 0;
            while(modFile.hasNextLine())
            {
                String[] wLine;
                String line = modFile.nextLine();
                String Rline = line.toLowerCase();
                wLine = Rline.split("[?!.]");
                for (String s : wLine)
                {
                    if (s != "")
                    {
                        String trim = s.trim();
                        parent.insert(trim, i);
                        i++;
                        //System.out.println(trim);
                    }
                }
                /*reason why count is wrong for some things is because the testing
                program counts ’ as 2 characters and em-dash as 3 characters. While I am
                able to accounts for em-dash as it is shorthanded to 3 dashes the apostrophe
                does not have a short form.
                 */
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return parent;
    }

}