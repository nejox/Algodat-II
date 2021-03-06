package Klausurvorbereitung.ub2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.stream.Collectors;

/// **************************************
/// undirected Graphs
/// implementation via adjacency lists
/// assumption:
/// vertices are enumerated by numbers 0 .. Nodes()-1
/// after creation, the number of vertices is fixed
/// **************************************
public class Graph {

	public class Vertex {
		final int id;
		final ArrayList<Integer> adjacentNodes = new ArrayList<>();

		public Vertex(int id) {
			this.id = id;
		}

		boolean hasConnectedVertices() {
			return !adjacentNodes.isEmpty();
		}

		ArrayList<Vertex> getConnectedVertices() {
			ArrayList<Vertex> list = new ArrayList<>();
			for (int nodeID : adjacentNodes) {
				list.add(vertices.get(nodeID));
			}
			return list;
		}

		/// node degree
		int getConnectedVerticesCount() {
			return adjacentNodes.size();
		}

		ArrayList<Integer> getConnectedNodeIDs() {
			return adjacentNodes;
		}

		void addEdgeTo(Vertex v) {
			addEdge(this, v);
		}

		void addEdgeTo(int v) {
			addEdge(this.id, v);
		}
	}

	/// adjacency list
	final private ArrayList<Vertex> vertices;

	/// Creates an empty graph with V vertices (vertex == node)
	public Graph(int vertexCount) {
		if (vertexCount < 0)
			throw new IllegalArgumentException("Number of vertices should not be negative\n");
		vertices = new ArrayList<>(vertexCount);
		for (int v = 0; v < vertexCount; v++)
			vertices.add(new Vertex(v));
	}

	/// loads graph from file
	public static Graph fromFile(String file) {
		Path path = Paths.get(file);
		try {
			List<int[]> parseResult = Files.readAllLines(Paths.get(file)).stream().map(x -> x.split(" "))
					.flatMap(Arrays::stream).filter(x -> !x.isEmpty())
					.map(x -> x.replaceAll("\\{", "").replaceAll("}", "").replaceAll(" ", "")).map(x -> x.split(","))
					.map(x -> new int[] { Integer.parseInt(x[0]), Integer.parseInt(x[1]) })
					.collect(Collectors.toList());
			int maxIndex = parseResult.stream().flatMapToInt(Arrays::stream).max().getAsInt();
			Graph graph = new Graph(maxIndex + 1);
			for (int[] arr : parseResult) {
				graph.addEdge(arr[0], arr[1]);
			}
			return graph;
		} catch (Exception e) {
			System.out
					.println("Something went wrong when trying to load your file " + path.toAbsolutePath().toString());
			throw new RuntimeException(e);
		}
	}

	// Creates a graph as copy of G
	public Graph clone() {
		Graph graph = new Graph(vertices.size());
		for (Vertex v : vertices) {
			for (Integer e : v.adjacentNodes) {
				graph.vertices.get(v.id).adjacentNodes.add(e);
			}
		}
		return graph;
	}

	/// Prints the graph
	public void display() {
		for (Vertex v : vertices) {
			System.out.println(v.id + ": " + Arrays.toString(v.adjacentNodes.toArray()));
		}
	}

	/// Adds an edge
	public void addEdge(int v, int w) {
		vertices.get(v).adjacentNodes.add(w);
		vertices.get(w).adjacentNodes.add(v);
	}

	/// Adds an edge
	public void addEdge(Vertex v, Vertex w) {
		addEdge(v.id, w.id);
	}

	/// Deletes an edge
	public void deleteEdge(Integer v, Integer w) {
		vertices.get(v).adjacentNodes.remove(w);
		vertices.get(w).adjacentNodes.remove(v);
	}

	/// Deletes an edge
	public void deleteEdge(Vertex v, Vertex w) {
		deleteEdge(v.id, w.id);
	}

	/// Returns first node
	public Vertex getRootVertex() {
		if (getNodeCount() == 0)
			throw new RuntimeException("Graph is empty\n");
		return vertices.get(0);
	}

	public Vertex getNode(int id) {
		return vertices.get(id);
	}

	/// Returns number of vertices
	public int getNodeCount() {
		return vertices.size();
	}

	public ArrayList<Vertex> getVertices() {
		return vertices;
	}

	/// Returns whether graph is connected
	public boolean connected() {
		// Trivially connected
		if (vertices.size() < 2)
			return true;

		// Try to visit all vertices using depth first search
		boolean[] visited = new boolean[vertices.size()];

		Stack<Integer> nextNodes = new Stack<>();
		visited[0] = true;
		nextNodes.push(0);
		while (nextNodes.size() > 0) {
			int v = nextNodes.pop();
			List<Integer> neighbors = getNode(v).getConnectedNodeIDs();
			for (Integer w : neighbors) {
				if (!visited[w]) {
					visited[w] = true;
					nextNodes.push(w);
				}
			}
		}
		for (int v = 0; v < vertices.size(); v++)
			if (!visited[v])
				return false;
		return true;
	}

	public List<String> getConnectivitys() {

		List<String> zusammenhangskomponenten = new ArrayList<>();

		boolean[] visited = new boolean[vertices.size()];
		Stack<Integer> erreichbare = new Stack<Integer>();

		String comp = "";
		for (int i = 0; i < vertices.size(); ++i) {
			if (!visited[i]) {
				// init start
				visited[i] = true;
				erreichbare.push(i);

				// start iterative DFS
				while (erreichbare.size() > 0) {
					int v = erreichbare.pop();
					comp += String.valueOf(v) + " ";
					List<Integer> neighbors = getNode(v).getConnectedNodeIDs();
					for (Integer w : neighbors) {
						if (!visited[w] && !erreichbare.contains(w)) {
							visited[w] = true;
							erreichbare.push(w);
						}
					}
				}
				zusammenhangskomponenten.add(comp);
			} else {
				comp = "";
			}
		}

		return zusammenhangskomponenten;

	}

	public Vertex BFS(int n) {

		Queue<Integer> queue = new LinkedList<Integer>();
		boolean[] visited = new boolean[getNodeCount()];

		visited[0] = true;
		queue.add(0);

		while (queue.size() > 0) {
			int v = queue.poll();
			System.out.println("besuchter Knoten: " + v);

			if (v == n) {
				System.out.println("Knoten gefunden: " + v);
				return getNode(v);
			}

			List<Integer> neighbors = getNode(v).getConnectedNodeIDs();
			for (Integer w : neighbors) {
				if (!visited[w]) {
					visited[w] = true;
					queue.add(w);
				}
			}
		}

		return null;
	}

	public Vertex DFS(int n) {

		boolean[] visited = new boolean[getNodeCount()];
		return DFS(n, 0, visited);

	}

	protected Vertex DFS(int n, int v, boolean[] visited) {

		visited[v] = true;
		
		System.out.println("besuchter Knoten " + v);
		if (v == n) {
			System.out.println("Knoten gefunden: " + v);
			return getNode(v);
		}
		
		List<Integer> neighbors = getNode(v).getConnectedNodeIDs();

		for (Integer w : neighbors) {
			if (!visited[w]) {
				return DFS(n, w, visited);
			}
		}
		
		return null;
	}
}