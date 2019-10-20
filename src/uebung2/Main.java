package uebung2;

import uebung2.Graph.Vertex;

public class Main {

	public static void main(String[] args) {
		Graph g = Graph.fromFile("blatt2_aufgabe1_a_graph.txt");
		g.display();
		System.out.println(g.countConnections());
		g.BFS(g.getVertices().get(3));
		g.DFS(g.getVertices().get(3));
	}
}
