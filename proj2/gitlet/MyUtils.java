package gitlet;

import java.util.Collection;
import java.util.Iterator;

public class MyUtils {
    public static class BFS {
        private boolean[] marked;
        private String[] edgeTo;
        private String vertex;
        private Graph G;
        public BFS(Graph G, String vertex) {
            this.vertex = vertex;
            marked = new boolean[G.vertexSize()];
            edgeTo = new String[G.vertexSize()];
            for (int i = 0; i < G.vertexSize(); i++) {
                marked[i] = false;
                edgeTo[i] = null;
            }
            this.G = G;
        }

        public String SplitPoint(Collection<String> collection) {

            return null;
        }

        private int findSplitPoint(String vertexName, Collection<String> collection) {
            int index = G.name2index(vertexName);
            marked[index] = true;
            for (int w: G.adj(index)) {
                if(!marked[w]) {
                    if (collection.contains(G.index2name(w))) {
                        return w;
                    }
                }
            }
            return -1;
        }
    }
}
