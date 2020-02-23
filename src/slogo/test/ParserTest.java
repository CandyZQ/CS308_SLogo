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
    Parser parser = new Parser(new Turtle(0));

    try {
      parser.setLanguage("English");
    } catch (LanguageIsNotSupportedException e) {
      e.printStackTrace();
    }

    Queue<EnumMap<MovingObjectProperties, Object>> q = null;
    try {
      q = parser.execute("FORWARD 50");
      Assert.assertEquals(50D, q.peek().get(MovingObjectProperties.Y));
      Assert.assertEquals(50D, q.peek().get(MovingObjectProperties.RETURN_VALUE));

      q = parser.execute("fd 30");
      Assert.assertEquals(80D, q.peek().get(MovingObjectProperties.Y));
      Assert.assertEquals(30D, q.peek().get(MovingObjectProperties.RETURN_VALUE));
    } catch (CommandDoesNotExistException | LanguageIsNotSupportedException | WrongCommandFormatException | InvalidArgumentException e) {
      e.printStackTrace();
    }

    try {
      parser.setLanguage("Chinese");
    } catch (LanguageIsNotSupportedException e) {
      e.printStackTrace();
    }

    try {
      q = parser.execute("houtui 20");
      Assert.assertEquals(60D, q.peek().get(MovingObjectProperties.Y));
      Assert.assertEquals(-20D, q.peek().get(MovingObjectProperties.RETURN_VALUE));

      q = parser.execute("qj 40");
      Assert.assertEquals(100D, q.peek().get(MovingObjectProperties.Y));
      Assert.assertEquals(40D, q.peek().get(MovingObjectProperties.RETURN_VALUE));
    } catch (CommandDoesNotExistException | LanguageIsNotSupportedException | WrongCommandFormatException | InvalidArgumentException e) {
      e.printStackTrace();
    }

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
      Queue<EnumMap<MovingObjectProperties, Object>> q = parser.execute("goto 3 4");
      Assert.assertEquals(3D, q.peek().get(MovingObjectProperties.X));
      Assert.assertEquals(4D, q.peek().get(MovingObjectProperties.Y));
      Assert.assertEquals(5D, q.peek().get(MovingObjectProperties.RETURN_VALUE));

      // TODO: resolve this
      q = parser.execute("forward 50");
      Assert.assertEquals(50.0, q.peek().get(MovingObjectProperties.RETURN_VALUE));
      Assert.assertEquals(44D, q.peek().get(MovingObjectProperties.Y));


    } catch (CommandDoesNotExistException | LanguageIsNotSupportedException | WrongCommandFormatException | InvalidArgumentException e) {
      e.printStackTrace();
    }
  }
}
