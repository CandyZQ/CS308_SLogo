/**
 * @author Sarah Gregorich
 */

package slogo.test;

import org.junit.Assert;
import org.junit.Test;
import slogo.controller.listings.MovingObjectProperties;
import slogo.model.Turtle;

public class TurtleTest {

  /**
   * Tests constructor, setCoordinates, and getState for values correctly set and returned
   */
  @Test
  public void shouldSetCoordinates() {
    Integer set_ID = 0;
    double set_X = 100;
    double set_Y = 10;
    Turtle turtle = new Turtle(set_ID);
    turtle.setCoordinates(set_X, set_Y);
    Assert.assertEquals(set_ID, turtle.getState().get(MovingObjectProperties.ID));
    Assert.assertEquals(set_X, turtle.getState().get(MovingObjectProperties.X));
    Assert.assertEquals(set_Y, turtle.getState().get(MovingObjectProperties.Y));

    turtle.reset();
    Assert.assertEquals(0D, turtle.getState().get(MovingObjectProperties.X));
    Assert.assertEquals(0D, turtle.getState().get(MovingObjectProperties.Y));
  }

  /**
   * Tests overloaded method setHeading correctly sets heading for input argument and correctly
   * returns rotation value
   */
  @Test
  public void shouldSetHeading() {
    Integer set_ID = 0;
    Turtle turtle = new Turtle(set_ID);

    // setHeading(double angle) sets and returns expected value
    double first_angle = 95D;
    turtle.setHeading(first_angle);
    Assert.assertEquals(first_angle, turtle.getState().get(MovingObjectProperties.HEADING));
    double second_angle = 100D;
    turtle.setHeading(second_angle);
    Assert.assertEquals(second_angle, turtle.getState().get(MovingObjectProperties.HEADING));
    // Assert.assertEquals(second_angle-first_angle, turtle.getState().get(MovingObjectProperties.RETURN_VALUE));

    // getCoterminal() sets expected value for [-360,360] range
    double large_angle = 720D;
    turtle.setHeading(large_angle);
    Assert.assertEquals(360D, turtle.getState().get(MovingObjectProperties.HEADING));
    double small_angle = -722D;
    turtle.setHeading(small_angle);
    Assert.assertEquals(-2D, turtle.getState().get(MovingObjectProperties.HEADING));

    // setHeading(double x, double y) sets correct heading
    double set_X = 1D;
    double set_Y = 1D;
    double expd = 45D;
    turtle.setHeading(set_X, set_Y);
    Assert.assertTrue((double) turtle.getState().get(MovingObjectProperties.HEADING) > (expd * .99D)
        && (double) turtle.getState().get(MovingObjectProperties.HEADING) < (expd * 1.01D));
    set_X = 0D;
    set_Y = 1D;
    expd = 90D;
    turtle.setHeading(set_X, set_Y);
    Assert.assertTrue((double) turtle.getState().get(MovingObjectProperties.HEADING) > (expd * .99D)
        && (double) turtle.getState().get(MovingObjectProperties.HEADING) < (expd * 1.01D));
    set_X = -1D;
    set_Y = 0D;
    expd = 180D;
    turtle.setHeading(set_X, set_Y);
    Assert.assertTrue((double) turtle.getState().get(MovingObjectProperties.HEADING) > (expd * .99D)
        && (double) turtle.getState().get(MovingObjectProperties.HEADING) < (expd * 1.01D));
    set_X = 0D;
    set_Y = -1D;
    expd = 270D;
    turtle.setHeading(set_X, set_Y);
    Assert.assertTrue((double) turtle.getState().get(MovingObjectProperties.HEADING) > (expd * .99D)
        && (double) turtle.getState().get(MovingObjectProperties.HEADING) < (expd * 1.01D));
    set_X = -1D;
    set_Y = -1D;
    expd = 225D;
    turtle.setHeading(set_X, set_Y);
    Assert.assertTrue((double) turtle.getState().get(MovingObjectProperties.HEADING) > (expd * .99D)
        && (double) turtle.getState().get(MovingObjectProperties.HEADING) < (expd * 1.01D));
  }

  /**
   * Tests rotate() method for heading value correctly set
   */
  @Test
  public void shouldRotate() {
    Integer set_ID = 0;
    Turtle turtle = new Turtle(set_ID);

    double first_angle = 95D;
    turtle.setHeading(first_angle);
    Assert.assertEquals(first_angle, turtle.getState().get(MovingObjectProperties.HEADING));

    double left = -96D;
    turtle.rotate(left);
    Assert.assertEquals(-1D, turtle.getState().get(MovingObjectProperties.HEADING));

    double right = 360D;
    turtle.rotate(right);
    Assert.assertEquals(359D, turtle.getState().get(MovingObjectProperties.HEADING));
  }

  /**
   * Tests getCoterminal won't do an infinite loop
   */
  @Test
  public void shouldThrowExceptionLargeAngles() {
    Integer set_ID = 0;
    Turtle turtle = new Turtle(set_ID);
    double right = 10000000D;
    boolean thrown = false;
    try {
      turtle.rotate(right);
    } catch (IllegalArgumentException e) {
      thrown = true;
    }
    Assert.assertTrue(thrown);
  }

  /**
   * Tests moveDistance correctly sets final x and y coordinates
   */
  @Test
  public void shouldMoveDistance() {
    Integer set_ID = 0;
    Turtle turtle = new Turtle(set_ID);
    double heading = 45;
    double distance = 100;
    turtle.setHeading(heading);
    turtle.moveDistance(distance);
    double expd = distance / Math.sqrt(2);
    Assert.assertTrue((double) turtle.getState().get(MovingObjectProperties.X) > (expd * .99D)
        && (double) turtle.getState().get(MovingObjectProperties.X) < (expd * 1.01D));
    Assert.assertTrue((double) turtle.getState().get(MovingObjectProperties.Y) > (expd * .99D)
        && (double) turtle.getState().get(MovingObjectProperties.Y) < (expd * 1.01D));

    turtle.reset();
    heading = -45;
    turtle.setHeading(heading);
    turtle.moveDistance(distance);
    expd = distance / Math.sqrt(2);
    Assert.assertTrue((double) turtle.getState().get(MovingObjectProperties.X) > (expd * .99D)
        && (double) turtle.getState().get(MovingObjectProperties.X) < (expd * 1.01D));
    expd = -distance / Math.sqrt(2);
    Assert.assertTrue((double) turtle.getState().get(MovingObjectProperties.Y) < (expd * .99D)
        && (double) turtle.getState().get(MovingObjectProperties.Y) > (expd * 1.01D));
  }

}
