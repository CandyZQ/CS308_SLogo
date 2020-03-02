/**
 * @author Sarah Gregorich
 */
package slogo.controller.scripting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Script {

    private File myFile;
    private FileWriter myScript;
    private static final String ERROR_OPEN = "Cannot open file";
    private static final String ERROR_CLOSE = "Cannot close file";
    private static final String ERROR_ADD = "Cannot add command";

    /**
     * Creates a Script object using the filename provided, which should be of the form "name.txt"
     * This object can then be written to
     * @param infilename
     */
    public Script(String infilename) {
        String filename = "resources/"+infilename;
        myFile = new java.io.File(filename);
        openFile(filename);
    }

    private void openFile(String filename) {
        try {
            myScript = new FileWriter(filename);
        } catch (IOException e) {
            System.out.println(ERROR_OPEN);
        }
    }

    /**
     * Call following add() function to correctly close file after writing
     * Does NOT need to be called after addAll()
     */
    public void closeFile() {
        try {
            myScript.close();
        } catch (IOException e) {
            System.out.println(ERROR_CLOSE);
        }
    }

    /**
     * If want to add to file one command String at a time, call add() then closeFile() on the Script object
     * @param command string to be written into file
     */
    public void add(String command){
        try {
            myScript.write(command+"\n");
        } catch (IOException e) {
            System.out.println(ERROR_ADD);
        }
    }

    /**
     * If want to take in one large string where commands delimited by new line characters, pass to this function
     * These will write into file the given commands, separated by a new line
     * @param script string of new line delimited commands
     */
    public void addAll(String script){
        String[] commands = script.split("\\r?\\n");
        try {
            for (int i = 0; i < commands.length; i++) {
                if (i != commands.length-1) {
                    myScript.write(commands[i]+"\n");
                } else {
                    myScript.write(commands[i]);
                }
            }
        } catch (IOException e) {
            System.out.println(ERROR_ADD);
        }
        closeFile();
    }

}
