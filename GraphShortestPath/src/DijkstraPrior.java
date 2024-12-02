import java.util.*;

import static java.lang.Integer.MAX_VALUE;

/**
 *
 * @author lewi0146
 */
public class DijkstraPrior
{
    PriorityQueue<vComp> dQueue;

    int[] dist;
    HashMap<Vertex,Vertex> prev;
    Vertex start;

    public DijkstraPrior(Graph g, Vertex source)
    {
        start = source;
        prev = new HashMap<>(1500);
        dist = new int[g.getVertices().size()];
        dQueue = new PriorityQueue<>();
        dist[Integer.parseInt(source.getLabel())] = 0;
        vComp vSource = new vComp(source,0, source.getLabel());
        dQueue.add(vSource);
        for (Vertex v :
                g.getVertices()) {
            if(v != source)
            {//setting max distance for every node aside from source
                dist[Integer.parseInt(v.getLabel())] = 999999;
                prev.put(v, null);
            }
        }
        while (!(dQueue.isEmpty()))
        {
            vComp vPQ = dQueue.poll();//pull out the shortest distance node
            Vertex smallV = vPQ.getKey();
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
                    vComp vPQ2 = new vComp(v,temp,v.getLabel());
                    dQueue.add(vPQ2);
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