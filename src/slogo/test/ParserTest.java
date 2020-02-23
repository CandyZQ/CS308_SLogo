package slogo.test;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
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
      Queue<EnumMap<MovingObjectProperties, Object>> q = parser.execute("SetPosition 3 4");
      Assert.assertEquals(3D, q.peek().get(MovingObjectProperties.X));
      Assert.assertEquals(4D, q.peek().get(MovingObjectProperties.Y));
      Assert.assertEquals(5D, q.peek().get(MovingObjectProperties.RETURN_VALUE));

      q = parser.execute("forward 50");
      Assert.assertEquals(54.0, q.peek().get(MovingObjectProperties.Y));
      Assert.assertEquals(55.0, q.peek().get(MovingObjectProperties.RETURN_VALUE));

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
