/**
 * Based on http://www.javapractices.com/topic/TopicAction.do?Id=87
 */

package slogo.controller.scripting;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import slogo.controller.CommandsMapHelper.SyntaxHelper;
import slogo.controller.listings.BasicSyntax;
import slogo.exceptions.InvalidArgumentException;

/**
 * Assumes UTF-8 encoding. JDK 7+.
 */
public class FileReader {

  private final Path filePath;
  private final static Charset ENCODING = StandardCharsets.UTF_8;
  private List<String> myCommands = new ArrayList<>();
  private static final String DELIMITER = "no delimiters for each line";

  /**
   * Constructor.
   *
   * @param infileName full name of an existing, readable file.
   */
  public FileReader(String infileName) throws IOException {
    String fileName = "data/examples/" + infileName;
    filePath = Paths.get(fileName);
  }

  /**
   * Call from Main after creating FileReader object
   *
   * @return Ordered list of strings representing the commands present in the given file
   * @throws IOException
   */
  public List<String> processScript() throws IOException, InvalidArgumentException {
    try (Scanner scanner = new Scanner(filePath, ENCODING.name())) {
      while (scanner.hasNextLine()) {
        processLine(scanner.nextLine());
      }
    }
    combineLines();
    return Collections.unmodifiableList(myCommands);
  }

  private void combineLines() throws InvalidArgumentException {
    StringBuilder sb = new StringBuilder();
    for (int i = 0, size = myCommands.size(); i < size; i++, size = myCommands.size()) {
      if (isComment(i)) {
        sb = new StringBuilder();
        break;
      }
      if (sb.length() != 0) {
        myCommands.remove(i - 1);
        i--;
      }
      sb.append(myCommands.get(i).strip()).append(" ");
      myCommands.set(i, sb.toString());
    }
  }

  private boolean isComment(int i) throws InvalidArgumentException {
    return SyntaxHelper.isType(myCommands.get(i).strip(), BasicSyntax.COMMENT);
  }


  private void processLine(String line) {
    //use a second Scanner to parse the content of each line
    try (Scanner scanner = new Scanner(line)) {
      scanner.useDelimiter(DELIMITER);
      if (scanner.hasNext()) {
        myCommands.add(scanner.next());
      }
    }
  }

}
