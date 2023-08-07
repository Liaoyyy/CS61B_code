package gitlet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Graph {
    private ArrayList<String> vertexList;
    private HashSet<Integer>[] adjList ;

    private int vertexSize;
    private int adjSize;

    public Graph() {
        int initialSize = 10;
        vertexSize = 0;
        vertexList = new ArrayList<>(initialSize);
        adjList = new HashSet[initialSize];
        for (int i = 0; i < initialSize; i++) {
            adjList[i] = new HashSet<Integer>();
        }
        adjSize = initialSize;
    }

    public void addVertex(String vertexName) {
        if (vertexList.contains(vertexName)) {
            System.out.println("This vertex already exists");
            return;
        }
        vertexList.add(vertexName);
        vertexSize += 1;
        if (vertexSize >= adjSize) {
            adjList = adjListResize((int) (vertexSize * 1.2));
        }
    }

    public void addEdge(String vertexName1, String vertexName2) {
        int index1 = vertexList.indexOf(vertexName1);
        int index2 = vertexList.indexOf(vertexName2);
        adjList[index1].add(index2);
        adjList[index2].add(index1);
    }

    public Iterator<String> adj(String vertexName) {
        return new adjIterator(vertexName);
    }

    private class adjIterator implements Iterator<String> {
        private Iterator<Integer> vertexIterator;
        public adjIterator(String vertexName) {
            int index = vertexList.indexOf(vertexName);
            vertexIterator = adjList[index].iterator();
        }
        @Override
        public boolean hasNext() {
            return vertexIterator.hasNext();
        }

        @Override
        public String next() {

            Integer index = vertexIterator.next();
            return vertexList.get(index);
        }
    }
    private HashSet<Integer>[] adjListResize(int newSize) {
        HashSet<Integer>[] newAdjList = new HashSet[newSize];
        for (int i = 0; i < vertexSize; i++) {
            newAdjList[i] = adjList[i];
        }
        for (int i = vertexSize; i < newSize; i++) {
            newAdjList[i] = new HashSet<Integer>();
        }
        this.adjSize = newSize;
        return newAdjList;
    }


    public static void main(String[] args) {
        Graph g = new Graph();
        g.addVertex("a");
        g.addVertex("b");
        g.addVertex("c");
        g.addVertex("d");
        g.addVertex("e");
        g.addVertex("f");
        g.addVertex("g");
        g.addVertex("h");
        g.addVertex("i");
        g.addVertex("j");
        g.addVertex("k");

        g.addEdge("a", "b");
        g.addEdge("a", "b");
        g.addEdge("a", "b");
        g.addEdge("a", "d");
        g.addEdge("b", "c");
        g.addEdge("c", "d");
        g.addEdge("d", "e");

        Iterator<String> it = g.adj("a");
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
