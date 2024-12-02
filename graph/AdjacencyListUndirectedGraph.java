import java.util.*;

public class AdjacencyListUndirectedGraph extends Graph
{
    void addVertex(Vertex v)
    {
        if (!VList.containsValue(v))
        {
            VList.put(v.getLabel(),v);
            adjacencyList.put(v, new TreeMap<>());
            VList.put(v.getLabel(),v);
        }
    }
    /**
     * Add edge v-w.  Will also add Vertex v and w if they do not already exist.
     * Need to check if the v and w already exists to stop duplicates and add only if it does not exist.
     *
     * @param v vertex v of the edge
     * @param w vertex w of the edge
     */
    void addEdge(Vertex v, Vertex w)
    {
        TreeMap<Vertex, Integer> aList = new TreeMap<>();

        addVertex(v);
        addVertex(w);
        aList = adjacencyList.get(v);
        if (!aList.containsKey(w))
            aList.put(w,1);
        aList = adjacencyList.get(w);
        if (!aList.containsKey(v))
            aList.put(v,1);
    }

    List<Vertex> adjacentTo(Vertex v)
    {
        //HashMap<Vertex,Integer> neigh = adjacencyList.get(v);

        return new ArrayList<>(adjacencyList.get(v).keySet());
    }

    int degree(Vertex v)
    {
        TreeMap<Vertex,Integer> neighbors = adjacencyList.get(v);
        return neighbors.size();
    }

    /**
     * Get all the vertices associated with the graph in lexicographic order.
     *
     * @return a list of adjacent vertices
     */
    List<Vertex> getVertices()
    {
        Set<Vertex> sVertex = adjacencyList.keySet();
        return new ArrayList<>(sVertex);
    }

    /**
     * is v-w an edge in the graph
     *
     * @param v
     * @param w
     * @return
     */
    boolean hasEdge(Vertex v, Vertex w)
    {
        if (adjacencyList.containsKey(v))
        {
            TreeMap<Vertex, Integer> check =adjacencyList.get(v);
            return check.containsKey(w);
        }
        else
            return false;
    }

    /**
     * is v a vertex in the graph?
     *
     * @param v
     * @return
     */
    boolean hasVertex(Vertex v)
    {
        return adjacencyList.containsKey(v);
    }

    /**
     * Gets the vertex in the graph with the label v
     *
     * @param v
     * @return
     */
    Vertex getVertex(String v)
    {
        return VList.getOrDefault(v, null);
    }
}
