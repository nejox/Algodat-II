package Klausurvorbereitung.ub10;

import java.util.List;
import java.util.Stack;

public class Main {

	public static void main(String[] args) {
		// Set Graph
		int oKnoten = 6;
		int s = 0;
		int t = 5;
		int[][] oKanten = { { 0, 1, 16 }, { 0, 2, 13 }, { 1, 3, 12 }, { 2, 1, 4 }, { 2, 4, 14 }, { 3, 2, 9 },
				{ 3, 5, 20 }, { 4, 3, 7 }, { 4, 5, 4 } };

		// --- ( 1 ) ---
		// wandeln Sie obige kanten in einen Graph um
		// zeigen Sie den Graph an
		DirectedWeightedGraph oGraph = new DirectedWeightedGraph(oKnoten);
		for (int i = 0; i < oKanten.length; i++) {
			oGraph.addEdge(oKanten[i][0], oKanten[i][1], oKanten[i][2]);
		}
		System.out.println("Graph:");
		oGraph.display();

		// --- ( 2 ) ---
		// wandeln Sie den Graph in einen Flussnetzwerk um
		// der Startknoten soll 0 sein, Endknoten 5
		// zeigen Sie das Flussnetzwerk an
		
		FlowGraph oFlowGraph = new FlowGraph(oGraph, s, t);
		System.out.println("Flow Graph:");
		oFlowGraph.display();

		// --- ( 3 ) ---
		// Lassen Sie sich den Residual-Pfad berechnen - erklären Sie die Routine
		// Geben Sie den Augmentierten Pfad (Stückweise) aus (.Augment(pfad)) und das
		// Flussnetzwerk
		
		Stack<Integer> path = oFlowGraph.computeResidualPath();
		System.out.println("Residual-Pfad:");
		for (Integer integer : path) {
			System.out.print(integer + " <-- ");
		}
		
		oFlowGraph.augment(path);
		oFlowGraph.display();

		// --- ( 4 ) ---
		// Geben Sie nochmals das Flussnetzwerk aus und berechnen Sie den Fluss
	}
}
