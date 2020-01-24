package Klausurvorbereitung.ub3;

import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

import Klausurvorbereitung.ub3.Graph.Vertex;

public class Main {

	public static void main(String[] args) {
		String inputFile = args.length == 0 ? "blatt3_aufgabe1_b_graph.txt" : args[0];
		Graph graph = Graph.fromFile(inputFile);
		graph.display();

		// Test whether there is an Eulerian cycle
		if (!graph.connected()) {
			System.out.println("Graph is not connected");
			return;
		}

		boolean nodeDegreesFit = graph.getVertices().stream().allMatch(v -> v.getConnectedVerticesCount() % 2 == 0);
//		if (!nodeDegreesFit) {
//			System.out.println("There are nodes with odd degrees.");
//			return;
//		}
		// Knotengrad ermitteln
		int counter = 0;
		for (Vertex v : graph.getVertices()) {
			if (v.getConnectedVerticesCount() % 2 != 0) {
				counter++;
			}
		}
		if (counter != 0 && counter != 2) { // -> Pfad möglich wenn counter=2 kreis bei =0
			System.out.println("There are to many nodes with odd degrees." + counter);
			return;
		}

		// Compute Eulerian cycle
		// idea:
		// oTour contains path from base to node v with no unvisited edge loops
		// hStack contains path from v to base
		// move vertices from hStack to oTour with completion if unvisited edges
		Graph graphCopy = graph.clone();
		Stack<Integer> oTour = new Stack<>();
		Stack<Integer> hStack = new Stack<>();
		int v = 0; // start node
		hStack.push(v);
		while (hStack.size() > 0) {
			v = hStack.pop();
			while (graphCopy.getNode(v).hasConnectedVertices()) {
				hStack.push(v);
				int w = graphCopy.getNode(v).getConnectedNodeIDs().get(0); // next node
				graphCopy.deleteEdge(v, w);
				v = w;
			}
			oTour.push(v);

		}

		// Output tour
		Integer[] oOutput = new Integer[oTour.size()];
		oTour.toArray(oOutput);
		Collections.reverse(Arrays.asList(oOutput));
		System.out.println("Eulertour: ");
		System.out.println(Arrays.toString(oOutput));
	}
}
