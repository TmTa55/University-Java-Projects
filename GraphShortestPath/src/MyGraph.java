import java.util.*;

public class MyGraph extends Graph
{

    void addVertex(Vertex v)
    {
        if (!VList.containsValue(v))
        {
            VList.put(Integer.parseInt(v.getLabel()),v);
            adjacencyMatrix.put(v, new HashMap<>(1000));
        }
    }

    void addEdge(Vertex v, Vertex w, int weight)
    {
        addVertex(v);
        addVertex(w);
        HashMap<Vertex, Integer> aList = adjacencyMatrix.get(v);
        if (!aList.containsKey(w))
            aList.put(w,weight);
        if (!degreeList.containsKey(v))
            degreeList.put(v,0);
        if (!degreeList.containsKey(w))
            degreeList.put(w,1);
        else
        {
            degreeList.put(w,degreeList.get(w)+1);
        }
    }

    List<Vertex> adjacentTo(Vertex v)
    {
        return new ArrayList<>(adjacencyMatrix.get(v).keySet());
    }

    int degree(Vertex v)
    {
        HashMap<Vertex,Integer> neighbors = adjacencyMatrix.get(v);
        return neighbors.size();
    }

    List<Vertex> getVertices()
    {
        return new ArrayList<>(VList.values());
    }

    boolean hasEdge(Vertex v, Vertex w)
    {
        if (adjacencyMatrix.containsKey(v))
        {
            HashMap<Vertex, Integer> check =adjacencyMatrix.get(v);
            return check.containsKey(w);
        }
        else
            return false;
    }

    boolean hasVertex(Vertex v)
    {
        return adjacencyMatrix.containsKey(v);
    }

    Vertex getVertex(int v)
    {
        return VList.getOrDefault(v, null);
    }
}
