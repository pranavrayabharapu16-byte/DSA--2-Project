import java.util.*;

/**
 * CO3 Topic: Minimum Spanning Tree using Kruskal Algorithm
 * This program finds the MST of a weighted undirected graph.
 */
public class CO3_KruskalMST {
    static class Edge implements Comparable<Edge> {
        int source;
        int destination;
        int weight;

        Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.weight, other.weight);
        }
    }

    static class DisjointSet {
        private int[] parent;
        private int[] rank;

        DisjointSet(int vertices) {
            parent = new int[vertices];
            rank = new int[vertices];

            for (int i = 0; i < vertices; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        int find(int vertex) {
            if (parent[vertex] != vertex) {
                parent[vertex] = find(parent[vertex]);
            }

            return parent[vertex];
        }

        boolean union(int u, int v) {
            int rootU = find(u);
            int rootV = find(v);

            if (rootU == rootV) {
                return false;
            }

            if (rank[rootU] < rank[rootV]) {
                parent[rootU] = rootV;
            } else if (rank[rootU] > rank[rootV]) {
                parent[rootV] = rootU;
            } else {
                parent[rootV] = rootU;
                rank[rootU]++;
            }

            return true;
        }
    }

    static class Graph {
        private int vertices;
        private List<Edge> edges;

        Graph(int vertices) {
            this.vertices = vertices;
            this.edges = new ArrayList<>();
        }

        void addEdge(int source, int destination, int weight) {
            edges.add(new Edge(source, destination, weight));
        }

        void kruskalMST() {
            Collections.sort(edges);
            DisjointSet disjointSet = new DisjointSet(vertices);

            List<Edge> mstEdges = new ArrayList<>();
            int totalCost = 0;

            for (Edge edge : edges) {
                if (disjointSet.union(edge.source, edge.destination)) {
                    mstEdges.add(edge);
                    totalCost += edge.weight;
                }

                if (mstEdges.size() == vertices - 1) {
                    break;
                }
            }

            System.out.println("Edges selected in the Minimum Spanning Tree:");
            for (Edge edge : mstEdges) {
                System.out.println(vertexName(edge.source) + " - " +
                                   vertexName(edge.destination) +
                                   " : " + edge.weight);
            }

            System.out.println("Total MST Cost: " + totalCost);
        }

        private String vertexName(int vertex) {
            return String.valueOf((char) ('A' + vertex));
        }
    }

    public static void main(String[] args) {
        Graph graph = new Graph(6);

        // Vertices: A=0, B=1, C=2, D=3, E=4, F=5
        graph.addEdge(0, 1, 4); // A-B
        graph.addEdge(0, 2, 3); // A-C
        graph.addEdge(1, 2, 2); // B-C
        graph.addEdge(1, 3, 5); // B-D
        graph.addEdge(2, 3, 7); // C-D
        graph.addEdge(2, 4, 8); // C-E
        graph.addEdge(3, 4, 6); // D-E
        graph.addEdge(3, 5, 4); // D-F
        graph.addEdge(4, 5, 1); // E-F
        graph.addEdge(1, 4, 9); // B-E

        System.out.println("CO3 - Kruskal Minimum Spanning Tree Demonstration");
        System.out.println("------------------------------------------------");

        graph.kruskalMST();

        System.out.println("\nTime Complexity:");
        System.out.println("Sorting Edges : O(E log E)");
        System.out.println("Union-Find    : Almost O(1) per operation");
        System.out.println("Overall       : O(E log E)");
    }
}
