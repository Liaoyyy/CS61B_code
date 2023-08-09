package gitlet;

import java.util.*;

import static gitlet.Commit.readCommit;

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
            int resultIndex = findSplitPoint(vertex, collection);
            if (resultIndex != -1) {
                return G.index2name(resultIndex);
            }
            return null;
        }

        private int findSplitPoint(String vertexName, Collection<String> collection) {
            Queue<Integer> fringe = new PriorityQueue<Integer>();

            int index = G.name2index(vertexName);
            marked[index] = true;
            fringe.add(index);
            while (!fringe.isEmpty()) {
                int v = fringe.remove();
                for (int w: G.adj(v)) {
                    if (!marked[w]) {
                        fringe.add(w);
                        marked[w] = true;
                        edgeTo[w] = G.index2name(v);
                        if (collection.contains(G.index2name(w))) {
                            return w;
                        }
                    }
                }
            }
            return -1;

        }
    }

    public static HashSet<String> paths(String startSha1) {
        HashSet<String> branch2init = new HashSet<>();
        branch2init.add(startSha1);
        Commit flag = readCommit(startSha1);
        while (flag.parentID() != null) {
            branch2init.add(flag.parentID());
            HashSet<String> newbranch = null;
            if (flag.parentID2() != null) {
                newbranch = paths(flag.parentID2());
            }
            if (newbranch != null) {
                branch2init.addAll(newbranch);
            }
            flag = readCommit(flag.parentID());
        }
        return branch2init;
    }
}
