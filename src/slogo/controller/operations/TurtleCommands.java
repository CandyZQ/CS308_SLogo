package slogo.controller.operations;

import slogo.controller.TurtleManager;
import slogo.controller.UserDefinedFields;
import slogo.controller.listings.MovingObjectProperties;
import slogo.model.Turtle;

/**
 * This class contains all turtle commands.
 *
 * @author cady
 */
public class TurtleCommands extends Operations {

  public TurtleCommands(Turtle turtle, UserDefinedFields userDefinedFields, TurtleManager tm) {
    super(turtle, userDefinedFields, tm);
  }

  private void forward(Double pixels) {
    turtle.moveDistance(pixels);
  }

  private void backward(Double pixels) {
    turtle.moveDistance(-pixels);
  }

  private void left(Double degrees) {
    turtle.rotate(degrees);
  }

  private void right(Double degrees) {
    turtle.rotate(-degrees);
  }

  private void setHeading(Double degrees) {
    turtle.setHeading(degrees);
  }

  private void setTowards(Double x, Double y) {
    turtle.setHeading(x, y);
  }

  private void setPosition(Double x, Double y) {
    turtle.setHeading(x, y);
    turtle.setCoordinates(x, y);
  }

  private void penDown() {
    turtle.getState().put(MovingObjectProperties.PEN, true);
    turtle.getState().put(MovingObjectProperties.RETURN_VALUE, 1);
  }

  private void penUp() {
    turtle.getState().put(MovingObjectProperties.PEN, false);
    turtle.getState().put(MovingObjectProperties.RETURN_VALUE, 0);
  }

  private void showTurtle() {
    turtle.getState().put(MovingObjectProperties.VISIBILITY, true);
    turtle.getState().put(MovingObjectProperties.RETURN_VALUE, 1);
  }

  private void hideTurtle() {
    turtle.getState().put(MovingObjectProperties.VISIBILITY, false);
    turtle.getState().put(MovingObjectProperties.RETURN_VALUE, 0);
  }

  private void home() {
    turtle.reset();
  }

  private void clearScreen() {
    // TODO: remove screen trails
    turtle.reset();
    turtle.clear();
  }
}
