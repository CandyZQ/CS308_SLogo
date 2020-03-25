package slogo.controller.operations;

import static slogo.controller.Parser.CONDITION_METHOD;
import static slogo.controller.Parser.TRIM;

import slogo.controller.Parser;
import slogo.controller.TurtleManager;
import slogo.controller.UserDefinedFields;
import slogo.controller.listings.MovingObjectProperties;
import slogo.exceptions.InvalidArgumentException;
import slogo.model.Turtle;

/**
 * This class contains all operations require multiple turtles.
 *
 * @author cady
 */
public class MultiTurtleOperations extends Operations {

  public static final String LEFT_SQUARE_BRACKET = "[";
  public static final String RIGHT_SQUARE_BRACKET = "]";

  public MultiTurtleOperations(Turtle turtle, UserDefinedFields userDefinedFields,
      TurtleManager tm) {
    super(turtle, userDefinedFields, tm);
  }

  private void tell(String turtles) throws InvalidArgumentException {
    String[] as = turtles.substring(Parser.TRIM, turtles.length() - Parser.TRIM).split(" ");
    tm.resetActiveTurtles();
    for (String s : as) {
      tm.setActiveTurtles(s);
    }
  }

  private Integer id() {
    return (Integer) turtle.getState().get(MovingObjectProperties.ID);
  }

  private void ask(String turtles, String commands) throws InvalidArgumentException {
    tell(turtles);
    userDefinedFields
        .setExtraCommands(commands.substring(Parser.TRIM, commands.length() - Parser.TRIM));
  }

  private void askWith(String condition, String commands) throws InvalidArgumentException {
    StringBuilder allTurtles = new StringBuilder(LEFT_SQUARE_BRACKET + " ");
    for (Turtle t : tm.getTurtles()) {
      allTurtles.append(t.getState().get(MovingObjectProperties.ID)).append(" ");
    }
    tell(allTurtles.append(RIGHT_SQUARE_BRACKET).toString());

    StringBuilder sb = new StringBuilder();
    sb.append(CONDITION_METHOD).append(" ")
        .append(condition.substring(TRIM, condition.length() - TRIM)).append(" ").append(commands);
    userDefinedFields.setExtraCommands(sb.toString());
  }
}
