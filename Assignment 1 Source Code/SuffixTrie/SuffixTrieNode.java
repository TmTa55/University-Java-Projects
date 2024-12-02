import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SuffixTrieNode {

    SuffixTrieData data = null;
    int numChildren = 0;
    private boolean terminal = false;
    public Map<String, SuffixTrieNode> children = new HashMap<>();
    private Set<String> str = new HashSet<>();

    public SuffixTrieNode getChild(String label)
    {
        return children.getOrDefault(label, null);
    }

    public void addChild(String label, SuffixTrieNode node){
        children.put(label, node);
        numChildren++;
    }

    public void addData(int sentencePos, int characterPos){
        SuffixIndex Dsentance = new SuffixIndex(sentencePos, characterPos);
        if (data == null)
        {
            data = new SuffixTrieData();
        }
        data.addStartIndex(Dsentance);
    }

    //adds to the list of words with the suffix
    public void addWord(String word)
    {
        str.add(word);
    }

    public int getNumChildren(){return numChildren;}

    public String toString() {
        return data.toString();
    }

    //returns all the times substring appears in text.
    public int getFreq(){return data.getFreq();}

    public boolean isTerminal() {
        return terminal;
    }

    //returns all words with the suffix
    public Set<String> getWord() {
        return str;
    }

    public void wordEnd(){
        terminal = true;
    }
}
