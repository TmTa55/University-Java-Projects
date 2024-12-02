
/**
 *
 * @author lewi0146
 */
public class DictionaryData {

    private String word;
    private int frequency;



    /**
     * Creates a new DictionaryData object based upon the the String line that
     * contains the data about the new data item.
     *
     * @param line the data about the new data item
     */
    public DictionaryData(String line)
    {
        String[] splits = line.split(" ");
        /*for (int i = 0; i < splits.length; i++)
        {
            System.out.println(splits[i]);
        }*/
        if (splits.length > 2)
        {
            setWord(splits[1]);
            setFrequency(Integer.parseInt(splits[2]));
        }
        else
        {
            setWord(splits[0]);
            setFrequency(Integer.parseInt(splits[1]));
        }

    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    /**
     * A string representation of the DataDictionary object. For example
     *
     *     "orange: frequency = 518"
     *
     * @return a string representation of the DataDictionary object
     */
    @Override
    public String toString() {

        return getWord() + ": frequency = " + getFrequency();
    }
}
