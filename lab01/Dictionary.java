
import java.io.*;
import java.util.*;

/**
 *
 * @author lewi0146
 */
public class Dictionary {

    private Map<String, DictionaryData> dictionaryMap = null;

    public Dictionary() {
        dictionaryMap = new HashMap<>();
    }

    /**
     * Extends this dictionary by adding the new word
     * identified with <code>word</code> and the data <code>data</code>.
     * @param word the word to add to the <code>Dictionary</code>.
     * @param data the data about the word s
     */
    public void insert(String word, DictionaryData data) {
        word = word.toUpperCase(Locale.ROOT);
        dictionaryMap.put(word.toUpperCase(), data);
    }

    /**
     * Removes the word identified by <code>word</code> from this Dictionary.
     * @param word the word to remove to the <code>Dictionary</code>.
     * @return
     */
    public DictionaryData remove(String word) {
        if (dictionaryMap.containsKey(word.toUpperCase()))
        {
            dictionaryMap.remove(word.toUpperCase());
        }
        return null;
    }

    /**
     * Look up the dictionary entry for a particular word
     *
     * @param word the particular word to look up.
     * @return the data associated with the word identified by <code>word</code>.
     */
    public DictionaryData lookup(String word) {
        if (dictionaryMap.containsKey(word.toUpperCase()))
        {
            DictionaryData value = dictionaryMap.get(word.toUpperCase());
            return value;
        }
        return null;
    }

    /**
     * Check to see whether a word is in the dictionary or not (returns true/false)
     * @param word the word to check
     * @return <code>true</code> if in this <code>Dictionary</code>, <code>false</code> otherwise.
     */
    public boolean contains(String word) {
        if (dictionaryMap.containsKey(word.toUpperCase()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Builder method for the <code>Dictionary</code> class that builds
     * a dictionary from the given file name.
     * It is expected that each entry is on a seperate line in the form
     * <pre>
     *     862 buddy 2743
     * </pre>
     * where 862 is the rank, buddy is the word, and 2743 is the frequency.
     *
     * @param fileName the file to load the dictionary data from
     * @return the created <code>Dictionary</code> or <code>null</code> on error.
     */
    public static Dictionary readInDictionary(String fileName) {
        Dictionary d = new Dictionary();
        Scanner fileScanner;

        try {
            // use a FileInputStream to ensure correct reading end-of-file
            fileScanner = new Scanner(new FileInputStream("data" + File.separator +fileName));

            while (fileScanner.hasNextLine()) {
                String nextLine = fileScanner.nextLine();
                 //System.out.println("nextLine: " + nextLine); //uncomment if you want to see what is read in
                DictionaryData data = new DictionaryData(nextLine);
                String[] splits = nextLine.split(" ");
                d.insert(splits[1].toUpperCase(), data);
            }

        } catch (FileNotFoundException ex) {
            System.out.println("could not find the file " +fileName+ "in the data directory!");
            return null;
        }

        return d;
    }

    /**
     * Read in a file and list all the words not found in this dictionary.
     * @param fileName the file to read and check.
     * @return List of words not found in this <code>Dictionary</code>.
     */
    public List<String> spellCheck(String fileName) {
        Scanner fileScanner;
        List<String> listed = new ArrayList<String>();
        String[] readWords;

        try {
            // use a FileInputStream to ensure correct reading end-of-file
            fileScanner = new Scanner(new FileInputStream("data" + File.separator +fileName));

            while (fileScanner.hasNextLine()) {
                String nextLine = fileScanner.nextLine();
                //System.out.println("nextLine: " + nextLine); //uncomment if you want to see what is read in
                readWords = nextLine.split(" ");
                /*for (int i = 0; i < readWords.length; i++)
                {
                    System.out.println(i+readWords[i]);
                }*/
                for (int i = 0; i < readWords.length; i++)
                {
                    if (dictionaryMap.containsKey(readWords[i].toUpperCase()))
                    {}
                    else
                    {
                        if (readWords.length>1)
                        {
                            listed.add(readWords[i]);
                        }
                    }
                }
            }

        } catch (FileNotFoundException ex) {
            System.out.println("could not find the file " +fileName+ "in the data directory!");
            return null;
        }

        return listed;
    }

    /**
     * Creates a list of the words in this dictionary by alphabetical order.
     * @return the list of alphabetical sorted dictionary words.
     */
    public List<DictionaryData> alphabeticalList() {
        List<DictionaryData> ordered = new ArrayList<DictionaryData>();
        List<String> allKeys = new ArrayList<String>();
        for (String Key : dictionaryMap.keySet())
        {
            allKeys.add(Key);
        }
        Collections.sort(allKeys);
        for (int i = 0; i < 20; i++)
        {
            ordered.add(dictionaryMap.get(allKeys.get(i)));
        }
        return ordered;
    }

    /**
     * Creates a list of the words in this dictionary by ascending order of
     * frequency (those of the same frequency should be
     * then ordered in reverse alphabetical order).
     * @return the list of frequency sorted dictionary words.
     */
    public List<DictionaryData> frequencyOrderedList() {
        List<DictionaryData> ordered = new ArrayList<DictionaryData>();
        List<String> allKeys = new ArrayList<>();
        int minimum = 100000;
        for (DictionaryData Key : dictionaryMap.values())
        {
            if(Key.getFrequency() < minimum)
                minimum = Key.getFrequency();
        }
        for (DictionaryData Key : dictionaryMap.values())
        {
            if(Key.getFrequency() == minimum)
                allKeys.add(Key.getWord());
        }
        Collections.sort(allKeys);
        for (int i = allKeys.size()-1; i > -1; i--)
        {
            ordered.add(dictionaryMap.get((allKeys.get(i)).toUpperCase()));
        }
        allKeys.clear();
        if (ordered.size() < 20)
        {
            minimum++;
            for (DictionaryData Key : dictionaryMap.values())
            {
                if(Key.getFrequency() == minimum)
                    allKeys.add(Key.getWord());
            }
        }
        Collections.sort(allKeys);
        for (int i = allKeys.size()-1; i > -1; i--)
        {
            ordered.add(dictionaryMap.get((allKeys.get(i)).toUpperCase()));
        }
        allKeys.clear();
        return ordered;
    }

}
