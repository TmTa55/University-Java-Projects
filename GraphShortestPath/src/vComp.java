import java.util.Objects;

public class vComp implements Comparable<vComp> {

    /**
     * The uniquely identifying label for the Vertex
     */
    private final Vertex key;
    private int value;
    private final String label;

    public vComp(Vertex key, int value, String label){
        this.key = key;
        this.value = value;
        this.label = label;
    }

    public void setWeight(int weight)
    {
        this.value = weight;
    }

    public Vertex getKey() {
        return this.key;
    }
    public int getValue(){return this.value;}
    public String getLabel(){return this.label;}


    @Override
    public int compareTo(vComp o)
    {
        return Integer.compare(this.value,o.value);
    }
}