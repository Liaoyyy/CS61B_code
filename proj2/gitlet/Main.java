package gitlet;


import java.io.IOException;

import static gitlet.Commands.*;
import static gitlet.Utils.*;
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
                try {
                    Commands.init();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "add":
                validateNumArgs(args, 2);
                String filename1 = args[1];
                add(filename1);
                break;
            case "commit":
                if (args.length != 2) {
                    System.out.println("Please enter a commit message.");
                    System.exit(-1);
                }
                String message = args[1];
                try {
                    commit(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "rm":
                validateNumArgs(args, 2);
                String filename2 = args[1];
                rm(filename2);
                break;
            case "log":
                validateNumArgs(args, 1);
                log();
                break;
            case "global-log":
                validateNumArgs(args, 1);
                global_log();
                break;
            case "find":
                validateNumArgs(args, 2);
                String commitMessage = args[1];
                find(commitMessage);
                break;
            case "checkout":
                if (args.length == 3) {

                } else if (args.length == 4) {

                } else if (args.length == 2) {

                } else {
                    throw new RuntimeException(String.format("Incorrect operands"));
                }
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
