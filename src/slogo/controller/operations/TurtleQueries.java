package slogo.controller.operations;

import slogo.controller.TurtleManager;
import slogo.controller.UserDefinedFields;
import slogo.controller.listings.MovingObjectProperties;
import slogo.model.Turtle;

/**
 * This class contains all turtle queries.
 *
 * @author cady
 */
public class TurtleQueries extends Operations {

  public TurtleQueries(Turtle turtle, UserDefinedFields userDefinedFields, TurtleManager tm) {
    super(turtle, userDefinedFields, tm);
  }

  private Double xCoordinate() {
    return (Double) turtle.getState().get(MovingObjectProperties.X);
  }

  private Double yCoordinate() {
    return (Double) turtle.getState().get(MovingObjectProperties.Y);
  }

  private Double heading() {
    return (Double) turtle.getState().get(MovingObjectProperties.HEADING);
  }

  private Boolean isPenDown() {
    return (Boolean) (turtle.getState().get(MovingObjectProperties.PEN));
  }

  private Boolean isShowing() {
    return (Boolean) turtle.getState().get(MovingObjectProperties.VISIBILITY);
  }
}
