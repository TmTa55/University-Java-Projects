import java.util.*;

/**
 *
 * @author lewi0146
 */
public class DijiCPriority
{
    CPriorityQ dQueue;

    int[] dist;
    HashMap<Vertex,Vertex> prev;
    Vertex start;

    public DijiCPriority(Graph g, Vertex source)
    {
        start = source;
        prev = new HashMap<>(1500);
        dist = new int[g.getVertices().size()];
        dQueue = new CPriorityQ();
        dist[Integer.parseInt(source.getLabel())] = 0;
        dQueue.add(source,0);
        for (Vertex v :
                g.getVertices()) {
            if(v != source)
            {//setting max distance for every node aside from source
                dist[Integer.parseInt(v.getLabel())] = 999999;
                prev.put(v, null);
            }
        }
        while (dQueue.size() != 0)
        {
            Vertex smallV = dQueue.pop();
            HashMap<Vertex,Integer> vNeigh = g.adjacencyMatrix.get(smallV);
            for (Vertex v :
                    vNeigh.keySet())
            {//for each neighbour of smallV, check if the potential distance is smaller than saved distance
                int temp = dist[Integer.parseInt(smallV.getLabel())] + vNeigh.get(v);
                if (temp < dist[Integer.parseInt(v.getLabel())])
                {//if smaller than save the path and distance and then add the new route to the queue
                    prev.put(v,smallV);
                    dist[Integer.parseInt(v.getLabel())] = temp;
                    //System.out.println("succ out" + v.getLabel()+" "+temp);
                    //vPQ2.setWeight(temp);
                    dQueue.add(v, temp);
                }
            }
        }
    }

    public int getDistanceTo(Vertex to)
    {
        return dist[Integer.parseInt(to.getLabel())];
    }

    public List<Vertex> pathTo(Vertex to)
    {
        Stack<Vertex> path = new Stack<>();
        while (to != start)
        {
            path.push(to);
            to = prev.get(to);
        }
        path.push(start);
        return path;
    }

}