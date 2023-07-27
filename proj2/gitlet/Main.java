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
                validateNumArgs(cmd, args, 1);
                try {
                    Commands.init();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "add":
                validateNumArgs(cmd, args, 2);
                add();
                break;
            case "commit":

            default:
                System.out.println("No command with that name exists.");
                System.exit(-1);
        }
    }

    /**Check whether the number of args is valid */
    public static void validateNumArgs(String cmd, String[] args, int n) {
        if (args.length != n) {
            throw new RuntimeException(
                    String.format("Incorrect operands"));
        }
    }
}
