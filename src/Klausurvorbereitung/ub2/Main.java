package Klausurvorbereitung.ub2;

import java.util.List;

public class Main {

    public static void main(String[] args) {
	    Graph g = Graph.fromFile("blatt2_aufgabe1_a_graph.txt");
	    g.display();
	    List<String> comps = g.getConnectivitys();
	    System.out.println("Zusammenhangskomponenten:");
	    int i  = 1;
	    for (String string : comps) {
			System.out.println(i+ ".: "+string);
			i++;
		}
	    System.out.println("\nBreitensuche");
	    g.BFS(8);
	    System.out.println("\nTiefensuche rekursiv");
	    g.DFS(8);
    }
}
