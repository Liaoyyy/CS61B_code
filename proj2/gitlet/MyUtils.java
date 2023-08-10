package gitlet;

import java.io.File;
import java.util.*;

import static gitlet.Commands.checkoutBranch;
import static gitlet.Commit.readCommit;
import static gitlet.Repository.*;
import static gitlet.Utils.*;
import static gitlet.Utils.readContentsAsString;

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

        public String splitPoint(Collection<String> collection) {
            int resultIndex = findSplitPoint(vertex, collection);
            if (resultIndex != -1) {
                return G.index2name(resultIndex);
            }
            return null;
        }

        private int findSplitPoint(String vertexName, Collection<String> collection) {
            Queue<Integer> fringe = new PriorityQueue<Integer>();

            int index = G.name2index(vertexName);
            if (collection.contains(index)) {
                return index;
            }
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

    public static void untrackedFileCheck(Commit currentCommit, Commit branchCommit) {
        List<String> curList = plainFilenamesIn(CWD);
        for (String curFile: curList) {
            if (currentCommit.getBlobSHA1(curFile) == null
                    && branchCommit.getBlobSHA1(curFile) != null) {
                System.out.println("There is an untracked file in the way; "
                        + "delete it, or add and commit it first.");
                System.exit(0);
            }
        }
    }

    public static Set<String> setUnion(Commit a, Commit b, Commit c) {
        Set<String> unionSet = new HashSet<>();
        unionSet.addAll(a.hashmap().keySet());
        unionSet.addAll(b.hashmap().keySet());
        unionSet.addAll(c.hashmap().keySet());
        return unionSet;
    }

    public static void mergeFailureCheck(String branchName) {
        Stage add = readObject(ADDFILE, Stage.class);
        Stage remove = readObject(REMOVEFILE, Stage.class);
        if (!add.isEmpty() || !remove.isEmpty()) {
            System.out.println("You have uncommitted changes.");
            System.exit(0);
        }

        File branchFile = join(BRANCH_DIR, branchName);
        if (!branchFile.exists()) {
            System.out.println("A branch with that name does not exist.");
            System.exit(0);
        }
        if (readContentsAsString(HEAD).equals(branchName)) {
            System.out.println("Cannot merge a branch with itself.");
            System.exit(0);
        }
    }

    public static void splitCommitCheck(Commit split, Commit current,
                                        Commit branch, String branchName) {
        if (split.commitSHA1().equals(current.commitSHA1())) {
            checkoutBranch(branchName);
            System.out.println("Current branch fast-forwarded.");
            System.exit(0);
        } else if (split.commitSHA1().equals(branch.commitSHA1())) {
            System.out.println("Given branch is an ancestor of the current branch.");
            System.exit(0);
        }
    }
}
