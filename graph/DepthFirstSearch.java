import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author lewi0146
 */
public class DepthFirstSearch {

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

    public DepthFirstSearch(Graph g, Vertex source)
    {
        prev = new HashMap<>();
        dist = new HashMap<>();
        traversalOrder = new LinkedList<>();
        List<Vertex> vs = g.getVertices();
        for (Vertex v : vs) {
            if (v.equals(source)) {
                source = v;
            }
            g.clearState();
            g.setState(v, Vertex.VertexState.UNVISITED);

        }

        bfs(source, g);

    }

    private void bfs(Vertex v, Graph g) {
        dist.put(v, 0);
        g.setState(v, Vertex.VertexState.DISCOVERED);
        recursbfs(v,g);
    }

    private void recursbfs(Vertex v, Graph g)
    {
        if (g.adjacentTo(v) != null)
        {
            for (Vertex v2 : g.adjacentTo(v)) {
                if (g.getState(v2) == Vertex.VertexState.UNVISITED)
                {
                    g.setState(v2, Vertex.VertexState.DISCOVERED);
                    dist.put(v2, 1 + dist.get(v));
                    prev.put(v2, v);
                    recursbfs(v2, g);
                }
            }
        }
        g.setState(v, Vertex.VertexState.FINISHED);
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
