package slogo.controller.scripting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Script {

    private File myFile;
    private FileWriter myScript;

    /**
     * Creates a Script object using the filename provided, which should be of the form "name.txt"
     * This object can then be written to
     * @param infilename
     */
    public Script(String infilename) {
        if (!infilename.contains(".txt")) {
            System.out.println("Bad name"); // @TODO make exception
        }
        String filename = "resources/"+infilename;
        try {
            myFile = new java.io.File(filename);
            if (myFile.createNewFile()) {
                System.out.println("File created: " + myFile.getName());
            } else {
                System.out.println("File already exists.");
            }
            openFile(filename);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            // e.printStackTrace(); @TODO print stack trace
        }
    }

    private void openFile(String filename) {
        try {
            myScript = new FileWriter(filename);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            // e.printStackTrace(); @TODO print stack trace
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
            System.out.println("An error occurred.");
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
            System.out.println("An error occurred.");
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
            System.out.println("An error occurred.");
        }
        closeFile();
    }

}
