package gitlet;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class Graph implements Serializable {
    private ArrayList<String> vertexList;
    private HashSet<Integer>[] adjList;

    private int vertexSize;
    private int adjSize;

    public Graph() {
        int initialSize = 10;
        vertexSize = 0;
        vertexList = new ArrayList<>(initialSize);
        adjList = (HashSet<Integer>[]) new HashSet[initialSize];
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

    public int vertexSize() {
        return this.vertexSize;
    }

    public Iterable<Integer> adj(int index) {
        return adjList[index];
    }

    public String index2name(int index) {
        return vertexList.get(index);
    }

    public int name2index(String vertexName) {
        return vertexList.indexOf(vertexName);
    }


    private HashSet<Integer>[] adjListResize(int newSize) {
        HashSet<Integer>[] newAdjList = (HashSet<Integer>[]) new HashSet[newSize];
        for (int i = 0; i < vertexSize; i++) {
            newAdjList[i] = adjList[i];
        }
        for (int i = vertexSize; i < newSize; i++) {
            newAdjList[i] = new HashSet<Integer>();
        }
        this.adjSize = newSize;
        return newAdjList;
    }

}
