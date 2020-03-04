package slogo.controller.operations;

import slogo.controller.TurtleManager;
import slogo.controller.UserDefinedFields;
import slogo.controller.listings.MovingObjectProperties;
import slogo.model.Turtle;

public class TurtleQueries extends Operations{

  public TurtleQueries(Turtle turtle, UserDefinedFields userDefinedFields, TurtleManager tm) {
    super(turtle, userDefinedFields, tm);
  }

  public Double xCoordinate() {
    return (Double) turtle.getState().get(MovingObjectProperties.X);
  }

  public Double yCoordinate() {
    return (Double) turtle.getState().get(MovingObjectProperties.Y);
  }

  public Double heading() {
    return (Double) turtle.getState().get(MovingObjectProperties.HEADING);
  }

  // TODO: confirm number
  public Integer isPenDown() {
    return (Integer) turtle.getState().get(MovingObjectProperties.PEN);
  }

  // TODO: confirm number
  public Integer isShowing() {
    return (Integer) turtle.getState().get(MovingObjectProperties.VISIBILITY);
  }
}
