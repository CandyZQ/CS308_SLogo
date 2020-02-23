package slogo.test;

import java.util.Objects;
import org.junit.Assert;
import org.junit.Test;
import slogo.controller.Languages;
import slogo.controller.MovingObjectProperties;
import slogo.controller.Parser;
import slogo.exceptions.CommandDoesNotExistException;
import slogo.exceptions.InvalidArgumentException;
import slogo.exceptions.LanguageIsNotSupportedException;
import slogo.exceptions.WrongCommandFormatException;
import slogo.model.Turtle;

public class ParserTest {
  @Test
  public void shouldSetLanguage() {

  }

  @Test
  public void shouldExecute() {
    Parser parser = new Parser(new Turtle(0));
    try {
      parser.setLanguage("English");
    } catch (LanguageIsNotSupportedException e) {
      e.printStackTrace();
    }

    try {
      Assert.assertEquals(50.0, parser.execute("forward 50").peek().get(MovingObjectProperties.Y));
    } catch (CommandDoesNotExistException e) {
      e.printStackTrace();
    } catch (LanguageIsNotSupportedException e) {
      e.printStackTrace();
    } catch (WrongCommandFormatException e) {
      e.printStackTrace();
    } catch (InvalidArgumentException e) {
      e.printStackTrace();
    }

  }
}
