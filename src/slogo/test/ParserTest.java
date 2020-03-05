package slogo.test;

import java.util.Map;
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

    Queue<Map<MovingObjectProperties, Object>> q = null;
    try {
      q = parser.execute("FORWARD 50");
      Assert.assertEquals(50D, q.peek().get(MovingObjectProperties.Y));
      Assert.assertEquals(50D, q.peek().get(MovingObjectProperties.RETURN_VALUE));

      q = parser.execute("fd 30 fd 30");
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
      Queue<Map<MovingObjectProperties, Object>> q = parser.execute("goto 3 4");
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
      Queue<Map<MovingObjectProperties, Object>> q = parser.execute("forward sum 10 sum 25 50");
      q.poll();
      q.poll();
      Assert.assertEquals(85D, q.peek().get(MovingObjectProperties.Y));
      Assert.assertEquals(85D, q.peek().get(MovingObjectProperties.RETURN_VALUE));
      printQueue(q);

    } catch (CommandDoesNotExistException | LanguageIsNotSupportedException | WrongCommandFormatException | InvalidArgumentException e) {
      e.printStackTrace();
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
      Queue<Map<MovingObjectProperties, Object>> q = parser.execute("repeat 2 [ fd 50 ]");
//      Queue<Map<MovingObjectProperties, Object>> q = parser.execute("dotimes [ :a 3 ] [ fd 50 ]");
//      Queue<Map<MovingObjectProperties, Object>> q = parser
//          .execute("for [ :b 2 6 3 ] [ fd 50 ]");
      printQueue(q);

    } catch (CommandDoesNotExistException | LanguageIsNotSupportedException | WrongCommandFormatException | InvalidArgumentException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void shouldIf() {
    Parser parser = new Parser(1);

    try {
      parser.setLanguage("English");
    } catch (LanguageIsNotSupportedException e) {
      e.printStackTrace();
    }

    try {
      Queue<Map<MovingObjectProperties, Object>> q;
//      q = parser.execute("if equal? 50 50 [ fd 50 ]");
//      printQueue(q);
      q = parser.execute("if 1 [ fd 50 ]");
      printQueue(q);
//      q = parser.execute("ifelse 0 [ fd 50 ] [ fd 100 ]");
//      printQueue(q);

    } catch (CommandDoesNotExistException | LanguageIsNotSupportedException | WrongCommandFormatException | InvalidArgumentException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void shouldRun() {
    Parser parser = new Parser(1);

    try {
      parser.setLanguage("English");
    } catch (LanguageIsNotSupportedException e) {
      e.printStackTrace();
    }

    try {
      Queue<Map<MovingObjectProperties, Object>> q;
      q = parser.execute("make :a 1");
      q = parser.execute("fd :a");
//      Assert.assertEquals(85D, q.peek().get(MovingObjectProperties.Y));
//      Assert.assertEquals(85D, q.peek().get(MovingObjectProperties.RETURN_VALUE));
      printQueue(q);

    } catch (CommandDoesNotExistException | LanguageIsNotSupportedException | WrongCommandFormatException | InvalidArgumentException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void shouldFunction() {
    Parser parser = new Parser(1);

    try {
      parser.setLanguage("English");
    } catch (LanguageIsNotSupportedException e) {
      e.printStackTrace();
    }

    try {
      Queue<Map<MovingObjectProperties, Object>> q;
      q = parser.execute("to hi [ :a ] [ fd :a ]");
      printQueue(q);

      q = parser.execute("hi 1");
//      Assert.assertEquals(85D, q.peek().get(MovingObjectProperties.Y));
//      Assert.assertEquals(85D, q.peek().get(MovingObjectProperties.RETURN_VALUE));
      printQueue(q);

    } catch (CommandDoesNotExistException | LanguageIsNotSupportedException | WrongCommandFormatException | InvalidArgumentException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void shouldTell() {
    Parser parser = new Parser(1);

    try {
      parser.setLanguage("English");
    } catch (LanguageIsNotSupportedException e) {
      e.printStackTrace();
    }

    try {
      Queue<Map<MovingObjectProperties, Object>> q;
      q = parser.execute("tell [ 0 1 ]");
      printQueue(q);
//      q = parser.execute("fd 50");
      q = parser.execute("fd product id 5");
      printQueue(q);
    } catch (CommandDoesNotExistException | LanguageIsNotSupportedException | WrongCommandFormatException | InvalidArgumentException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void shouldGiveError() {
    Parser parser = new Parser(1);

    try {
      parser.setLanguage("English");
    } catch (LanguageIsNotSupportedException e) {
      e.printStackTrace();
    }

    try {
      Queue<Map<MovingObjectProperties, Object>> q;
      q = parser.execute("fd 50 fd 50");
      printQueue(q);
    } catch (WrongCommandFormatException | LanguageIsNotSupportedException | InvalidArgumentException | CommandDoesNotExistException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void complicatedCommand() {
    Parser parser = new Parser(1);

    try {
      parser.setLanguage("English");
    } catch (LanguageIsNotSupportedException e) {
      e.printStackTrace();
    }

    try {
      Queue<Map<MovingObjectProperties, Object>> q;
      q = parser.execute("for [ :v 0 5 0 ] [ fd 1 set :v ycor ]");
      printQueue(q);

    } catch (CommandDoesNotExistException | LanguageIsNotSupportedException | WrongCommandFormatException | InvalidArgumentException e) {
      System.out.println(".");
    }
  }


  private void printQueue(Queue<Map<MovingObjectProperties, Object>> q) {
    while (!q.isEmpty()) {
      Map<MovingObjectProperties, Object> map = q.poll();

//      System.out.println(map.get(MovingObjectProperties.HEADING));
      System.out.println("ID: " + map.get(MovingObjectProperties.ID) + ", Return: " + map.get(MovingObjectProperties.RETURN_VALUE));
      System.out.println("Y: " + map.get(MovingObjectProperties.Y));
    }
  }



}


