import java.util.ArrayList;

public class SuffixTrieData {

    private ArrayList<SuffixIndex> startIndexes = new ArrayList<>();

    public void addStartIndex(SuffixIndex startIndex) {
        startIndexes.add(startIndex);
    }

    public String toString() {
        return startIndexes.toString();
    }
    //return the amount of times the string appears in text
    public int getFreq(){return startIndexes.size();}
}