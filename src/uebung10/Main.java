package uebung10;


import java.util.List;

public class Main {

    public static void main(String[] args) {
	/*
			int oVariables = 5;
			int[][] A =
			{{1,-1,0,0,0},{1,0,0,0,-1},{0,1,0,0,-1},{-1,0,1,0,0},
			{-1,0,0,1,0},{0,0,-1,1,0},{0,0,-1,0,1},{0,0,0,-1,1}};
			double[] b = {0,-1,1,5,4,-1,-3,-3};
	*/

        int oVariables = 6;
        int[][] A =
                {{-1, 0, 0, 0, 0, 1}, {-1, 0, 0, 0, 1, 0}, {0, 0, 0, 0, -1, 1}, {0, 0, -1, 0, 0, 1}, {0, 0, 1, -1, 0, 0}, {0, 1, 0, 0, 0, -1}, {0, -1, 0, 1, 0, 0}};
        double[] b = {6, 2, -3, 1, -1, 1, 1};

	/*
			int oVariables = 6;
			int[][] A =
			{{1,-1,0,0,0,0},{1,0,0,-1,0,0},{0,1,-1,0,0,0},{0,1,0,0,-1,0},{0,1,0,0,0,-1},{0,0,1,0,0,-1},{0,-1,0,1,0,0},{-1,0,0,0,1,0},{0,0,0,-1,1,0},{0,0,-1,0,0,1}};
			double[] b = {1,-4,2,7,5,10,2,-1,3,-8};
	*/
	/*
			int oVariables = 5;
			int[][] A =
			{{1,-1,0,0,0},{1,0,0,0,-1},{0,1,0,-1,0},{0,-1,1,0,0},
			{-1,0,0,1,0},{0,0,-1,1,0},{0,0,0,1,-1},{0,0,-1,0,1},{0,0,0,-1,1}};
			double[] b = {4,5,-6,1,3,5,10,-4,-8};
			 */

        // Transfer to graph
        DirectedWeightedGraph oGraph = new DirectedWeightedGraph(oVariables + 1);
        for (int i = 0; i < A.length; i++) {
            int v = -1;
            int w = -1;
            for (int j = 0; j < A[0].length; j++) {
                if (A[i][j] == 1) w = j + 1;
                if (A[i][j] == -1) v = j + 1;
            }
            oGraph.addEdge(v, w, b[i]);
        }
        for (int i = 1; i <= oVariables; i++)
            oGraph.addEdge(0, i, 0);

        // Display the grap
        oGraph.display();

        int vStart = 0;        //compute shortest paths node 0

        // Bellmann-Ford:
        // iterative relaxation,
        // try to decrease the length by inserting a vertex at the end

        double[] pi = new double[oGraph.getNodeCount()];    //shortest known path lengths
        int[] pred = new int[oGraph.getNodeCount()];        //predeceesor nodes for these paths
        for (int i = 0; i < oGraph.getNodeCount(); i++) {
            pi[i] = Double.POSITIVE_INFINITY;
            pred[i] = -1;
        }
        pi[vStart] = 0;

        List<DirectedWeightedGraph.Edge> oEdges = oGraph.getAllEdges();
        for (int i = 0; i < oGraph.getNodeCount() - 1; i++) {
            for (int j = 0; j < oEdges.size(); j++)    //test edges all edges
            {
                int w = oEdges.get(j).from;
                int u = oEdges.get(j).to;
                double weight = oEdges.get(j).weight;
                if (pi[u] > pi[w] + weight)            //edge leads to shorter path
                {
                    pi[u] = pi[w] + weight;
                    pred[u] = w;
                }
            }
        }

        // Test whether negative cycle
        for (int i = 0; i < oEdges.size(); i++)    //test all edges
        {
            int w = oEdges.get(i).from;
            int u = oEdges.get(i).to;
            double weight = oEdges.get(i).weight;
            if (pi[u] > pi[w] + weight) {
                System.out.println("No solution exists");
                return;
            }
        }
        // Output shortest path lengths
        System.out.println("Solution: ");
        for (int i = 1; i <= oVariables; i++)
            System.out.print(i + " to " + pi[i] + ", ");
        System.out.println();
    }
}
