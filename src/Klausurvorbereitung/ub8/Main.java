package Klausurvorbereitung.ub8;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import Klausurvorbereitung.ub8.UndirectedWeightedGraph.Edge;

public class Main {

	public static void main(String[] args) {
		// Set Graph
		int oKnoten = 7;
		int[][] oKanten = { { 0, 1, 3 }, { 0, 2, 2 }, { 0, 3, 3 }, { 1, 3, 2 }, { 1, 4, 1 }, { 2, 5, 2 }, { 2, 6, 7 },
				{ 3, 4, 2 }, { 3, 5, 3 }, { 3, 6, 4 }, { 4, 6, 1 }, { 5, 6, 3 } };

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

		double[] pi = new double[oGraph.getNodeCount()]; // reachable vertices length
		int[] pred = new int[oGraph.getNodeCount()]; // predeceesor nodes for these paths

		for (int i = 0; i < oGraph.getNodeCount(); i++) {
			pi[i] = Double.POSITIVE_INFINITY;
			pred[i] = -1;
		}

		int v = oGraph.getRootVertex().id; // v=0 //start for the spanning tree
		pi[v] = 0.0;

		IndexedHeap oHeap = new IndexedHeap(oGraph.getNodeCount()); // all nodes have to be covered yet
		for (int i = 0; i < oGraph.getNodeCount(); i++) {
			oHeap.insert(i, pi[i]);
		}

		while (!oHeap.empty()) {
			v = oHeap.deleteMin(); // minimum reachable node
			List<UndirectedWeightedGraph.Edge> oEdges = oGraph.getNode(v).getEdges();
			for (UndirectedWeightedGraph.Edge edge : oEdges) // test edges from v
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

//		aufgabe1();
		aufgabe2();
	}

	public static void aufgabe1() {

		UndirectedWeightedGraph oGraph = buildfullyconnectedGraphFromCoordinates(
				"C:\\Users\\Jojo\\Workspace\\Algodat-II\\src\\Klausurvorbereitung\\ub8\\some_points.txt");

		// Prim for Spanning Tree
		double[] pi = new double[oGraph.getNodeCount()];
		int[] pred = new int[oGraph.getNodeCount()];

		for (int i = 0; i < oGraph.getNodeCount(); i++) {
			pi[i] = Double.POSITIVE_INFINITY;
			pred[i] = -1;
		}

		int v = oGraph.getRootVertex().id; // v=0 //start for the spanning tree
		pi[v] = 0.0;

		IndexedHeap oHeap = new IndexedHeap(oGraph.getNodeCount());
		for (int i = 0; i < oGraph.getNodeCount(); i++) {
			oHeap.insert(i, pi[i]);
		}

		while (!oHeap.empty()) {
			int u = oHeap.deleteMin();
			List<Edge> oEdges = oGraph.getNode(u).getEdges();
			for (Edge edge : oEdges) {
				int w = edge.to;
				if (oHeap.contains(w) && edge.weight < pi[w]) {
					pred[w] = u;
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

	public static void aufgabe2() {

		UndirectedWeightedGraph oGraph = buildfullyconnectedGraphFromCoordinates(
				"C:\\Users\\Jojo\\Workspace\\Algodat-II\\src\\Klausurvorbereitung\\ub8\\cities_xy.txt");

		// read city names
		String[] cityNames = new String[oGraph.getNodeCount()];
		try (BufferedReader br = new BufferedReader(new FileReader(
				"C:\\Users\\Jojo\\Workspace\\Algodat-II\\src\\Klausurvorbereitung\\ub8\\city_names.txt"))) {

			int index = 0;

			while (br.ready()) {
				String line = br.readLine();
				cityNames[index] = line;
				index++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// minimal spanning tree
		double[] pi = new double[oGraph.getNodeCount()];
		int[] pred = new int[oGraph.getNodeCount()];

		for (int i = 0; i < oGraph.getNodeCount(); i++) {
			pi[i] = Double.POSITIVE_INFINITY;
			pred[i] = -1;
		}

//		int v = oGraph.getRootVertex().id;
		pi[112] = 0.0d; //113 = Roswell
		pred[112] = 112;
		IndexedHeap oHeap = new IndexedHeap(oGraph.getNodeCount());
		for (int i = 0; i < oGraph.getNodeCount(); i++) {
			oHeap.insert(i, pi[i]);
		}

		while (!oHeap.empty()) {
			int u = oHeap.deleteMin();

			ArrayList<Edge> oEdges = oGraph.getNode(u).getEdges();
			for (Edge edge : oEdges) {
				int w = edge.to;
				if (oHeap.contains(w) && edge.weight < pi[w]) {
					pred[w] = u;
					pi[w] = edge.weight;
					oHeap.change(w, pi[w]);
				}
			}
		}

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(
				"C:\\Users\\Jojo\\Workspace\\Algodat-II\\src\\Klausurvorbereitung\\ub8\\citySpanningTree.txt"))) {

			bw.write("Spanning Tree:");
			bw.newLine();

			double sum = 0.0d;
			bw.write("Start: 0 = " + cityNames[0]);
			bw.newLine();
			for (int i = 0; i < oGraph.getNodeCount(); i++) {
 				bw.write(cityNames[i] + "(" + i + ")" + " -> " + cityNames[pred[i]] + "(" + pred[i] + ")");
				sum += pi[i];
			}
			bw.newLine();
			bw.write("with Length: " + sum);
			bw.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static UndirectedWeightedGraph buildfullyconnectedGraphFromCoordinates(String file) {
		// Set Points
		List<Point> liste = new ArrayList<Point>();

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {

			int index = 0;

			while (br.ready()) {
				String line = br.readLine();
				double lon = Double.valueOf(line.substring(0, line.indexOf(";")));
				double lat = Double.valueOf(line.substring(line.indexOf(";") + 1));
				liste.add(new Point(index, lon, lat));
				index++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// Set Graph
		Point[] a = new Point[1];
		Point[] oKnoten = liste.toArray(a);

		UndirectedWeightedGraph oGraph = new UndirectedWeightedGraph(oKnoten.length);
		for (int i = 0; i < oKnoten.length; i++) {
			for (int j = i; j < oKnoten.length; j++) {
				if (i != j) {
					// distance between 2 coordinates d² = x² + y²
					double x = oKnoten[i].getLon() - oKnoten[j].getLon();
					double y = oKnoten[i].getLat() - oKnoten[j].getLat();
					double d = Math.sqrt((x * x) + (y * y));

					oGraph.addEdge(oKnoten[i].getKey(), oKnoten[j].getKey(), d);
				}
			}

		}

		// Display the graph
		System.out.println("Graph from File: " + file);
//		oGraph.display();

		return oGraph;
	}
}
