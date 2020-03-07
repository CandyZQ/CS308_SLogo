package slogo.test;

import java.io.IOException;
import java.util.List;
import org.junit.Test;
import slogo.controller.scripting.FileReader;
import slogo.controller.scripting.Script;
import slogo.exceptions.InvalidArgumentException;

public class ScriptTest {

  private String myFileName = "testFile.txt";

  @Test
  public void shouldAddToFile() {
    Script script = new Script(myFileName);
    script.add("seth 100");
    script.add("fd 50");
    script.closeFile();
  }

  @Test
  public void shouldAddAllToFile() {
    Script script = new Script(myFileName);
    script.addAll("bk 40\ntowards 1 1");
    script.closeFile();
  }

  @Test
  public void shouldReadFromFile() throws IOException, InvalidArgumentException {
    FileReader myScriptFile = new FileReader(myFileName);
    List<String> commands = myScriptFile.processScript();
    for (int i = 0; i < commands.size(); i++) {
      System.out.println(commands.get(i));
    }
  }

}
