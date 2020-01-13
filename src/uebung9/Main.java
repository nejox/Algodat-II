package uebung9;


import java.util.List;

public class Main {

    public static void main(String[] args) {
    	
    	aufgabe1();
    	
        // Set Graph
        int oKnoten = 7;
        int[][] oKanten =
                {{0, 1, 3}, {0, 2, 2}, {0, 3, 3}, {1, 3, 2}, {1, 4, 1}, {2, 5, 2}, {2, 6, 7},
                        {3, 4, 2}, {3, 5, 3}, {3, 6, 4}, {4, 6, 1}, {5, 6, 3}};

        UndirectedWeightedGraph oGraph = new UndirectedWeightedGraph(oKnoten);
        for (int i = 0; i < oKanten.length; i++) {
            oGraph.addEdge(oKanten[i][0], oKanten[i][1], oKanten[i][2]);
        }

        // Display the graph
        oGraph.display();

        // idea:
        // greedy approach,
        // iteratively grow spanning tree by respective shortest connection
        // reachable nodes are efficiently stored in a heap

        double[] pi = new double[oGraph.getNodeCount()];    //reachable vertices length
        int[] pred = new int[oGraph.getNodeCount()];        //predeceesor nodes for these paths

        for (int i = 0; i < oGraph.getNodeCount(); i++) {
            pi[i] = Double.POSITIVE_INFINITY;
            pred[i] = -1;
        }

        int v = oGraph.getRootVertex().id; // v=0			//start for the spanning tree
        pi[v] = 0.0;

        IndexedHeap oHeap = new IndexedHeap(oGraph.getNodeCount());    //all nodes have to be covered yet
        for (int i = 0; i < oGraph.getNodeCount(); i++) {
            oHeap.insert(i, pi[i]);
        }

        while (!oHeap.empty()) {
            v = oHeap.deleteMin();            //minimum reachable node
            List<UndirectedWeightedGraph.Edge> oEdges = oGraph.getNode(v).getEdges();
            for (UndirectedWeightedGraph.Edge edge : oEdges)    //test edges from v
            {
                int w = edge.to;
                if (oHeap.contains(w) && edge.weight < pi[w]) {
                    pred[w] = v;
                    pi[w] = edge.weight;
                    oHeap.change(w, pi[w]);
                }
            }
        }

        // Output spanning tree
        System.out.println("Spanning tree:");
        double sum = 0.0;
        for (int i = 0; i < oGraph.getNodeCount(); i++) {
            System.out.print("edge " + i + "-" + pred[i] + " ");
            sum += pi[i];
        }
        System.out.println();
        System.out.println("with length: " + sum);
    }
    
    public static void aufgabe1() {
    	//UndirectedWeightedGraph oGraph = UndirectedWeightedGraph.fromFile("src/uebung9/some_points.txt");
    }
}
