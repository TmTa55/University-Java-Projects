import java.util.*;

/**
 *
 * @author lewi0146
 */
public class TopoSearch {

    /**
     * Maintain the previous Vertex for each Vertex to discover paths through the graph.
     */
    Map<Vertex,Vertex> prev;

    /**
     * Maintain the distances from the source Vertex.
     */
    Map<Vertex,Integer> dist;

    /**
     * The list of Vertex objects in traversal order
     */
    List<Vertex> traversalOrder;

    public TopoSearch(Graph g)
    {
        prev = new HashMap<>();
        dist = new HashMap<>();
        traversalOrder = new LinkedList<>();
        List<Vertex> vs = g.getVertices();
        for (Vertex v : vs) {
            g.clearState();
            g.setState(v, Vertex.VertexState.UNVISITED);
        }

        lexiSearch(g);

    }

    private void lexiSearch(Graph g) {
        int minD = Integer.MAX_VALUE;
        Queue<Vertex> lexi = new LinkedList<>();
        ArrayList<Vertex> lexiValue = new ArrayList<>();
        for (int i:g.degreeList.values())
        {
            if (i<minD)
                minD = i;
        }

        for (Vertex v:
             g.degreeList.keySet())
        {
            if (g.degreeList.get(v) == minD)
            {

                lexi.add(v);
                lexiValue.add(v);
            }
        }
        Collections.reverse(lexiValue);
        for (Vertex v :
                lexiValue) {
            dist.put(v, 0);
            g.setState(v, Vertex.VertexState.FINISHED);
            traversalOrder.add(v);
        }

        for (int i = 0; i < lexi.size()+1; i++) {
            Vertex v = lexi.poll();
            //traversalOrder.add(v);
            recurslexi(v,g);
        }
    }

    private void recurslexi(Vertex v, Graph g)
    {
        //g.setState(v, Vertex.VertexState.FINISHED);
        if (g.adjacentTo(v) != null)
        {
            List<Vertex> revG = g.adjacentTo(v);
            Collections.reverse(revG);
            for (Vertex v2 : revG)
            {
                if (g.getState(v2) == Vertex.VertexState.UNVISITED)
                {
                    traversalOrder.add(v2);
                    g.setState(v2, Vertex.VertexState.DISCOVERED);
                }
            }
            for (Vertex v2 : revG) {
                if (g.getState(v2) == Vertex.VertexState.DISCOVERED)
                {
                    g.setState(v2, Vertex.VertexState.FINISHED);
                    dist.put(v2, 1 + dist.get(v));
                    prev.put(v2, v);
                    recurslexi(v2, g);
                }
            }
        }
        else
            traversalOrder.add(v);

    }

    public List<Vertex> getDepthFirstTraversalList()
    {
        return traversalOrder;
    }

    public int getDistanceTo(Vertex to)
    {
        if (dist.get(to) != null) {
            return dist.get(to);
        }
        else {
            return -1;
        }
    }

    public List<Vertex> pathTo(Vertex to)
    {
        Stack<Vertex> path = new Stack<>();
        while (dist.containsKey(to))
        {
            path.push(to);
            to = prev.get(to);
        }
        return path;
    }
}
