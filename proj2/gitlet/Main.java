package gitlet;


import java.io.File;
import java.io.IOException;

import static gitlet.Commands.*;
import static gitlet.Utils.*;
import static gitlet.Repository.*;
/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */

    public static void main(String[] args) {
    if (args.length == 0){
        System.out.println("Please enter a command.");
        System.exit(-1);
    }

        String cmd = args[0];
        switch(cmd) {
            case "init":
                validateNumArgs(args, 1);
                init();
                break;
            case "add":
                validateNumArgs(args, 2);
                if (!GITLET_DIR.exists()) {
                    System.out.println("Not in an initialized Gitlet directory.");
                    System.exit(0);
                }
                String filename1 = args[1];
                add(filename1);
                break;
            case "commit":
                if (args.length != 2 || args[1].trim().length() == 0 ) {
                    System.out.println("Please enter a commit message.");
                    System.exit(0);
                }
                String message = args[1];
                if (!GITLET_DIR.exists()) {
                    System.out.println("Not in an initialized Gitlet directory.");
                    System.exit(0);
                }
                commit(message);
                break;
            case "rm":
                validateNumArgs(args, 2);
                if (!GITLET_DIR.exists()) {
                    System.out.println("Not in an initialized Gitlet directory.");
                    System.exit(0);
                }
                String filename2 = args[1];
                rm(filename2);
                break;
            case "log":
                validateNumArgs(args, 1);
                if (!GITLET_DIR.exists()) {
                    System.out.println("Not in an initialized Gitlet directory.");
                    System.exit(0);
                }
                log();
                break;
            case "global-log":
                validateNumArgs(args, 1);
                if (!GITLET_DIR.exists()) {
                    System.out.println("Not in an initialized Gitlet directory.");
                    System.exit(0);
                }
                globalLog();
                break;
            case "find":
                validateNumArgs(args, 2);
                if (!GITLET_DIR.exists()) {
                    System.out.println("Not in an initialized Gitlet directory.");
                    System.exit(0);
                }
                String commitMessage = args[1];
                find(commitMessage);
                break;
            case "status":
                validateNumArgs(args, 1);
                if (!GITLET_DIR.exists()) {
                    System.out.println("Not in an initialized Gitlet directory.");
                    System.exit(0);
                }
                status();
                break;
            case "checkout":
                int length = args.length;
                if (!GITLET_DIR.exists()) {
                    System.out.println("Not in an initialized Gitlet directory.");
                    System.exit(0);
                }
                switch(length) {
                    case 3:
                        if (!args[1].equals("--")){
                            System.out.println("Incorrect operands");
                            System.exit(0);
                        }
                        String filename3 = args[2];
                        checkout(filename3);
                        break;
                    case 4:
                        if (!args[2].equals("--")){
                            System.out.println("Incorrect operands");
                            System.exit(0);
                        }
                        String commitid = args[1];
                        String filename4 = args[3];
                        checkout(commitid, filename4);
                        break;
                    case 2:
                        String branchname = args[1];
                        checkoutBranch(branchname);
                        break;
                    default:
                        System.out.println("Incorrect operands");
                        System.exit(0);
                }
                break;
            case "branch":
                validateNumArgs(args, 2);
                if (!GITLET_DIR.exists()) {
                    System.out.println("Not in an initialized Gitlet directory.");
                    System.exit(0);
                }
                String branchname1 = args[1];
                branch(branchname1);
                break;
            case "rm-branch":
                validateNumArgs(args, 2);
                if (!GITLET_DIR.exists()) {
                    System.out.println("Not in an initialized Gitlet directory.");
                    System.exit(0);
                }
                String branchname2 = args[1];
                rmbranch(branchname2);
                break;
            case "reset":
                validateNumArgs(args, 2);
                if (!GITLET_DIR.exists()) {
                    System.out.println("Not in an initialized Gitlet directory.");
                    System.exit(0);
                }
                String commitID = args[1];
                reset(commitID);
                break;
            case "merge":
                validateNumArgs(args, 2);
                String branchName = args[1];
                merge(branchName);
                break;
            default:
                System.out.println("No command with that name exists.");
                System.exit(-1);
        }
    }

    /**Check whether the number of args is valid */
    public static void validateNumArgs( String[] args, int n) {
        if (args.length != n) {
            throw new RuntimeException(
                    String.format("Incorrect operands"));
        }
    }


}
