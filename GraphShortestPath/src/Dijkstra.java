import java.util.*;

import static java.lang.Integer.MAX_VALUE;

/**
 *
 * @author lewi0146
 */
public class Dijkstra {

    HashMap<Vertex,Vertex> prev;
    Map<Vertex,Integer> dist;

    public Dijkstra(Graph g,Vertex source)
    {
        prev = new HashMap<>(1500);
        dist = new HashMap<>(1500);
        List<Vertex> vs = g.getVertices();
        g.setState(source, Vertex.VertexState.FINISHED);
        for (Vertex v : vs) {
            if (v.equals(source)) {
                source = v;
            }
            g.clearState();
            g.setState(v, Vertex.VertexState.UNVISITED);
            dist.put(v,999999);
        }

        dist.put(source,0);
        g.setState(source, Vertex.VertexState.FINISHED);
        DijkSearch(source,g);
        /*for (Vertex vert :
                prev.keySet()) {
            System.out.println(vert+" "+prev.get(vert));

        }*/
    }

    private void DijkSearch(Vertex v,Graph g)
    {
        int minD = MAX_VALUE;
        HashMap<Vertex, Integer> vNeighWeigh = g.adjacencyMatrix.get(v);
        Vertex sV = null;
        for (Vertex w : vNeighWeigh.keySet())
        {
            if (g.getState(w) != Vertex.VertexState.FINISHED && getDistanceTo(v) + vNeighWeigh.get(w) < getDistanceTo(w))
            {//if neighboring node is not finished and the distance from v + v-w edge is smaller than the current distance of w
                    dist.put(w, getDistanceTo(v) + vNeighWeigh.get(w));
                //set distance[v] + weight(v,w) < distance[w]
                    prev.put(w,v);
                    //set predecessor[w] = v
            }
        }

        for (Vertex i:dist.keySet())
        {//search through and find the smallest unfinished path
            if (getDistanceTo(i) < minD && g.getState(i) == Vertex.VertexState.UNVISITED)
            {
                minD = getDistanceTo(i);
                sV = i;
            }
        }
        if (sV != null)
        {//if there is a neighboring node that is not done then do the smallest one.
            //else end the recursion
            g.setState(sV, Vertex.VertexState.FINISHED);
            DijkSearch(sV,g);
        }

    }


    public int getDistanceTo(Vertex to)
    {
        return dist.get(to);
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
