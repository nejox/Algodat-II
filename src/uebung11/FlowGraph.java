package uebung11;

import java.util.*;

public class FlowGraph extends DirectedWeightedGraph {
    private int source;
    private int target;

    // creates the Residual Graph according to a Graph G with zero flow,
    // extends the basic constructor
    public FlowGraph(DirectedWeightedGraph graph, int s, int t)
    {
        super(graph.getNodeCount());
        this.source = s;
        this.target = t;
        for (int i = 0; i < graph.getNodeCount(); i++) {
            for (DirectedWeightedGraph.Edge edge : graph.getNode(i).getEdges()) {
                addEdge(edge.from, edge.to, edge.weight);
                addEdge(edge.to, edge.from, 0.0);
            }
        }

    }

    public FlowGraph(int vertexCount, int s, int t) {
        super(vertexCount);
        source = s;
        target = t;
    }

    // increase weight by value
    private void increase(int from, int to, double value) {
        Vertex v = getNode(from);
        for(Edge e : v.getEdges()){
            if(e.to == to){
                e.weight += value;
            }
        }
    }

    // decrease weight by value
    private void decrease(int from, int to, double value) {
        increase(from, to, -value);
    }

    // compute Flow of the network
    public double ComputeFlow() {
        double flow = 0.0;
        for(Edge e : getAllEdges()){
            if(e.to == source){
                flow += e.weight;
            }
        }
        return flow;
    }

    // compute residual path from source to sink using bfs
    public Stack<Integer> computeResidualPath() {
        Stack<Integer> path = new Stack<>();
        boolean[] visited = new boolean[getNodeCount()];
        int[] pred = new int[getNodeCount()];
        for (int i = 0; i < getNodeCount(); i++) visited[i] = false;
        for (int i = 0; i < getNodeCount(); i++) pred[i] = -1;
        visited[source] = true;
        Queue<Integer> oqueue = new PriorityQueue<>();
        oqueue.add(source);
        while (oqueue.size() > 0) {
            int from = oqueue.poll();
            for (Edge edge :getNode(from).getEdges()) {
                if ((edge.weight > 0) && !visited[edge.to]) {    // dfs loop until target is reached
                    int to = edge.to;
                    if (to != target) {
                        // continue bfs
                        if (!visited[to]) {
                            visited[to] = true;
                            pred[to] = from;
                            oqueue.add(to);
                        }
                    } else {
                        // target is reached, extract path ...
                        pred[target] = from;
                        path.push(to);
                        while (to != source) {
                            to = pred[to];
                            path.push(to);
                        }
                        return path;
                    }
                }
            }
        }
        // no path found, return empty stack
        return path;
    }

    // Augments according to given Path
    public void augment(Stack<Integer> p) {
        if (p.empty()) return;
        Integer[] path = new Integer[p.size()];
        Collections.reverse(p);
        p.toArray(path);
        System.out.println("Adaptation Path: ");
        for (int i = 0; i < path.length; i++) System.out.print(path[i] + " ");
        System.out.println();

        // compute minimum along path
        double minvalue = Double.POSITIVE_INFINITY;
        for (int i = 1; i < path.length; i++) {
            double value = existsEdge(path[i - 1], path[i]);
            if (value < minvalue) minvalue = value;
        }
        // adjust values along vertices of the path
        for (int i = 1; i < path.length; i++) {
            this.decrease(path[i - 1], path[i], minvalue);
            this.increase(path[i], path[i - 1], minvalue);
        }
    }

    // returns length of edge if existing, infinity if not existing
    public double existsEdge(int v,int w)
    {
        if (v<0||v>getNodeCount()) return Double.POSITIVE_INFINITY;
        List<Edge> oEdges = getNode(v).getEdges();
        for (Edge edge : oEdges)
            if (edge.to==w) return edge.weight;
        return Double.POSITIVE_INFINITY;
    }
}