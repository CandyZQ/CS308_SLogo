package slogo.test;

import java.util.EnumMap;
import java.util.Map;
import java.util.Queue;
import org.junit.Assert;
import org.junit.Test;
import slogo.controller.listings.MovingObjectProperties;
import slogo.controller.Parser;
import slogo.exceptions.CommandDoesNotExistException;
import slogo.exceptions.InvalidArgumentException;
import slogo.exceptions.LanguageIsNotSupportedException;
import slogo.exceptions.WrongCommandFormatException;

public class ParserTest {
  @Test
  public void shouldSetLanguage() {
    Parser parser = new Parser(1);

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
    Parser parser = new Parser(1);
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
//      Assert.assertEquals(44D, q.peek().get(MovingObjectProperties.Y));


    } catch (CommandDoesNotExistException | LanguageIsNotSupportedException | WrongCommandFormatException | InvalidArgumentException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void shouldExecuteNested() {
    Parser parser = new Parser(1);
    try {
      parser.setLanguage("English");
    } catch (LanguageIsNotSupportedException e) {
      e.printStackTrace();
    }

    try {
      Queue<EnumMap<MovingObjectProperties, Object>> q = parser.execute("forward sum 10 sum 25 50");
      q.poll();
      q.poll();
      Assert.assertEquals(85D, q.peek().get(MovingObjectProperties.Y));
      Assert.assertEquals(85D, q.peek().get(MovingObjectProperties.RETURN_VALUE));
      printQueue(q);

    } catch (CommandDoesNotExistException | LanguageIsNotSupportedException | WrongCommandFormatException | InvalidArgumentException e) {
      e.printStackTrace();
    }
  }

  private void printQueue(Queue<EnumMap<MovingObjectProperties, Object>> q) {
    while (!q.isEmpty()) {
      Map<MovingObjectProperties, Object> map = q.poll();
      System.out.println(map.get(MovingObjectProperties.Y));
//      System.out.println(map.get(MovingObjectProperties.HEADING));
//      System.out.println(map.get(MovingObjectProperties.RETURN_VALUE));
    }
  }

  @Test
  public void shouldLoop() {
    Parser parser = new Parser(1);

    try {
      parser.setLanguage("English");
    } catch (LanguageIsNotSupportedException e) {
      e.printStackTrace();
    }

    try {
      Queue<EnumMap<MovingObjectProperties, Object>> q = parser.execute("repeat 2 [ fd 5 rt 2 ]");
//      Assert.assertEquals(85D, q.peek().get(MovingObjectProperties.Y));
//      Assert.assertEquals(85D, q.peek().get(MovingObjectProperties.RETURN_VALUE));
      printQueue(q);

    } catch (CommandDoesNotExistException | LanguageIsNotSupportedException | WrongCommandFormatException | InvalidArgumentException e) {
      e.printStackTrace();
    }
  }
}
