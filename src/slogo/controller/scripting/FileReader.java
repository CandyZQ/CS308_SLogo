/**
 * Based on http://www.javapractices.com/topic/TopicAction.do?Id=87
 */

package slogo.controller.scripting;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/** Assumes UTF-8 encoding. JDK 7+. */
public class FileReader {

    private final Path filePath;
    private final static Charset ENCODING = StandardCharsets.UTF_8;
    private Scanner  myScanner;
    private List<String> myCommands = new ArrayList<>();

    /**
     Constructor.
     @param infileName full name of an existing, readable file.
     */
    public FileReader(String infileName) throws IOException {
        String fileName = "resources/"+infileName;
        filePath = Paths.get(fileName);
        try (Scanner scanner = new Scanner(filePath, ENCODING.name())){
            myScanner = scanner;
        }
    }

    /**
     * Call from Main after creating FileReader object
     * @return Ordered list of strings representing the commands present in the given file
     * @throws IOException
     */
    public List<String> processScript() throws IOException {
        try (Scanner scanner =  new Scanner(filePath, ENCODING.name())){
            while (scanner.hasNextLine()){
                processLine(scanner.nextLine());
            }
        }
        return myCommands;
    }

    private void processLine(String line){
        //use a second Scanner to parse the content of each line
        try(Scanner scanner = new Scanner(line)){
            scanner.useDelimiter("no delimiters for each line");
            if (scanner.hasNext()) {
                myCommands.add(scanner.next());
            }
        }
    }

}
