package slogo.controller.operations;

import slogo.controller.TurtleManager;
import slogo.controller.UserDefinedFields;
import slogo.controller.listings.MovingObjectProperties;
import slogo.exceptions.InvalidArgumentException;
import slogo.model.Turtle;

public class MultiTurtleOperations extends Operations {
  public static final int INITIAL_INDEX = 2;
  public static final int END_INDEX = -2;

  public MultiTurtleOperations(Turtle turtle, UserDefinedFields userDefinedFields,
      TurtleManager tm) {
    super(turtle, userDefinedFields, tm);
  }

  public void tell(String turtles) throws InvalidArgumentException {
    String[] as = turtles.substring(INITIAL_INDEX, turtles.length() + END_INDEX).split(" ");
    tm.resetActiveTurtles();
    for (String s: as) {
      tm.setActiveTurtles(s);
    }
  }

  public Integer id () {
    return (Integer) turtle.getState().get(MovingObjectProperties.ID);
  }
}
