import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import org.jdom2.JDOMException;

/**
 *
 * @author lewi0146
 */
public class GraphShortestPathDriver {

    /**
     *
     * The program must accept one line of input on the standard input
     * that contains the name of the file to load in graphml format, a space,
     * and then the index of the starting vertex (starting from 0). For example
     *
     * data/graphs/random_v10_e10_w50.graphml 0 f true
     * data/graphs/random_v100_e500_w50.graphml 1 f true
     * data/graphs/random_v100_e1000_w50.graphml 0 f true
     * data/graphs/graph1000.graphml 0 f true
     * @param args the command line arguments: <filename> <starting vertex>
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, JDOMException {

        //String filename = "data/graphs/graphSpecExample.graphml";
        //String filename = "data/graphs/graphTutorialExample.graphml";
        String filename = "data/graphs/graph10.graphml";

        int sourceVertex = 0;
        boolean printInfo = false;
        boolean printNoPath = false;
        int graphType = 0;

        Scanner s = new Scanner(System.in);
        String line = s.nextLine();

        if (!line.isEmpty()) {
            String[] params = line.split(" ");
            if (params.length > 0) {
                filename = params[0];
            }
            if (params.length > 1) {
                sourceVertex = Integer.parseInt(params[1]);
            }
            if (params.length > 2) {
                printInfo = params[2].equals("true");
            }
            if (params.length > 3) {
                printNoPath = params[3].equals("true");
            }
            if (params.length > 4) {
                graphType = Integer.parseInt(params[4]);
            }
        }

        //System.out.print("Building graph from file: " +filename +"...");
        Graph g = GraphBuilder.buildFromGraphML(filename);
        //System.out.println("done.");

        if (printInfo) {
            printGraph(g);
        }
        long tick = System.nanoTime();
        Dijkstra d = new Dijkstra(g,g.getVertex(sourceVertex));
        long tock = System.nanoTime();
        DijkstraPrior d2 = new DijkstraPrior(g,g.getVertex(sourceVertex));
        long tack = System.nanoTime();
        DijiCPriority d3 = new DijiCPriority(g,g.getVertex(sourceVertex));
        long teck = System.nanoTime();
        int step = 0;
        if (printNoPath)
        {
            if (graphType == 0 || graphType == 1)
            {
                System.out.println("\nUsing a List");
                for (Vertex v :
                        g.getVertices()) {
                    String dist = "NO PATH";
                    if (d.getDistanceTo(v) == 999999) {
                        System.out.println("shortest path to " + step + ": " + dist);
                    } else {
                        List<Vertex> VPathList = d.pathTo(v);
                        Collections.reverse(VPathList);
                        dist = Integer.toString(d.getDistanceTo(v));
                        String pathOfV = String.valueOf(VPathList);
                        pathOfV = pathOfV.replace("[", "");
                        pathOfV = pathOfV.replace("]", "");
                        pathOfV = pathOfV.replace(",", "");

                        System.out.println("shortest path to " + step + ": " + pathOfV + ": cost = " + dist);
                    }
                    step++;
                }
            }
            step = 0;
            if (graphType == 0 || graphType == 2)
            {
            System.out.println("\nUsing the built in priority queue");
            for (Vertex v :
                    g.getVertices()) {
                String dist = "NO PATH";
                if (d2.getDistanceTo(v) == 999999) {
                    System.out.println("shortest path to " + step + ": " + dist);
                }
                else
                {
                    List<Vertex> VPathList = d2.pathTo(v);
                    Collections.reverse(VPathList);
                    dist = Integer.toString(d2.getDistanceTo(v));
                    String pathOfV = String.valueOf(VPathList);
                    pathOfV = pathOfV.replace("[", "");
                    pathOfV = pathOfV.replace("]", "");
                    pathOfV = pathOfV.replace(",", "");

                    System.out.println("shortest path to " + step + ": " + pathOfV + ": cost = " + dist);
                }
                step++;
            }
            }
            step = 0;
            if (graphType == 0 || graphType == 3) {
                System.out.println("\nUsing a custom made priority queue");
                for (Vertex v :
                        g.getVertices()) {
                    String dist = "NO PATH";
                    if (d3.getDistanceTo(v) == 999999) {
                        System.out.println("shortest path to " + step + ": " + dist);
                    } else {
                        List<Vertex> VPathList = d3.pathTo(v);
                        Collections.reverse(VPathList);
                        dist = Integer.toString(d3.getDistanceTo(v));
                        String pathOfV = String.valueOf(VPathList);
                        pathOfV = pathOfV.replace("[", "");
                        pathOfV = pathOfV.replace("]", "");
                        pathOfV = pathOfV.replace(",", "");

                        System.out.println("shortest path to " + step + ": " + pathOfV + ": cost = " + dist);
                    }
                    step++;
                }
            }
        }


        System.out.println("List took "+ (tock-tick) + " nanoseconds");
        System.out.println("Priority queue took "+(tack-tock)+ " nanoseconds");
        System.out.println("Custom priority queue took "+ (teck-tack)+ " nanoseconds");
        // perform dijktra's shortest path from sourceVertex




    }
    public static void printGraph(Graph g) {

        List<Vertex> vs = g.getVertices();
        System.out.println("Vertices: " + vs);
        for (Vertex v : vs) {
            System.out.print(v + ": ");
            List<Vertex> adjVList = g.adjacentTo(v);
            for (Vertex av : adjVList) {
                System.out.print(av + " ");
            }
            System.out.println();

        }
    }
}
