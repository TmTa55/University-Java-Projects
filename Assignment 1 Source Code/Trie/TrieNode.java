import java.util.*;
public class TrieNode {

    private TrieData data = null;
    private boolean terminal = false;
    private int numChildren = 0;
    public Map<Character, TrieNode> children = new HashMap<>();
    private String str;

    /**
     * Lookup a child node of the current node that is associated with a
     * particular character label.
     *
     * @param label The label to search for
     * @return The child node associated with the provided label
     */
    public TrieNode getChild(char label){return children.getOrDefault(label, null);}

    public void setWord(String word){str = word;}

    /**
     * Add a child node to the current node, and associate it with the provided
     * label.
     *
     * @param label The character label to associate the new child node with
     * @param node The new child node that is to be attached to the current node
     */
    public void addChild(char label, TrieNode node)
    {
        children.put(label, node);
        numChildren++;
    }

    /**
     * Add a new data object to the node.
     *
     * @param dataObject The data object to be added to the node.
     */
    public void addData(TrieData dataObject) {data = dataObject;}

    public int getFrequency()
    {
        return data.getFrequency();
    }

    /**
     * The toString() method for the TrieNode that outputs in the format
     *   TrieNode; isTerminal=[true|false], data={toString of data}, #children={number of children}
     * for example,
     *   TrieNode; isTerminal=true, data=3, #children=1
     * @return
     */
    @Override
    public String toString()
    {
        if (data == null)
        {return "TrieNode; isTerminal="+terminal+", data=null, #children="+children.size();}
        else
        {return "TrieNode; isTerminal="+terminal+", data="+data.toString()+", #children="+children.size();}
    }

    public String getWord() {
        return str;
    }

    public boolean isTerminal() {
        return terminal;
    }
    public void wordEnd(){
        terminal = true;
    }

    public int getNumChildren() {
        return numChildren;
    }
}

