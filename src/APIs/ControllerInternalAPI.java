package APIs;

import java.util.List;
import java.util.Set;
import slogo.controller.TurtleManager;
import slogo.controller.UserDefinedFields;
import slogo.controller.listings.BasicSyntax;
import slogo.exceptions.InvalidArgumentException;
import slogo.model.Turtle;

/**
 * This is the internal API for sub-packages in the controller.
 * <p></p>
 * Most methods (except 1) in this interface are used by methods in {@link
 * slogo.controller.operations} to change states of current turtles, variables, user-defined
 * commands, etc. Once operation methods changed those states, parser will return the changed states
 * back to frontend to update the UI.
 * <p></p>
 * The only static method {@link #isType(String, BasicSyntax)} is a utility method that evaluates
 * the type of certain input.
 *
 * @author Cady
 */
public interface ControllerInternalAPI {

  /**
   * Evaluate whether a string is of certain {@link BasicSyntax}. Note that this is a static method
   * and thus can be called without creating any instance variables.
   *
   * @param input a string
   * @param type  a {@link BasicSyntax} defined in resource file
   * @return whether this input is of this type
   * @see slogo.controller.CommandsMapHelper.SyntaxHelper
   */
  boolean isType(String input, BasicSyntax type);

  /**
   * Sets a turtle of some index active. Creates a new turtle if that turtle does not exist.
   *
   * @param s a String of that index
   * @throws InvalidArgumentException if {@code s} is not a valid index (Integer)
   * @see TurtleManager
   */
  void setActiveTurtles(String s) throws InvalidArgumentException;

  /**
   * Gets a list of active turtles
   *
   * @return a list of active {@link Turtle}
   * @see TurtleManager
   */
  Set<Turtle> getTurtles();


  /**
   * Resets active turtles so that no turtle is active anymore.
   *
   * @see TurtleManager
   */
  void resetActiveTurtles();

  /**
   * Stores a new user-defined variable.
   *
   * @param name  the name of the variable
   * @param value the value of the variable
   * @throws InvalidArgumentException if the name fo the variable does not fulfill SLogo standard
   * @see UserDefinedFields
   */
  void putUserVar(String name, Double value) throws InvalidArgumentException;

  /**
   * Gets the value of a user-defined variable
   *
   * @param name the name of that variable
   * @return the value of that variable
   * @see UserDefinedFields
   */
  Double getUserVar(String name);

  /**
   * Increases a variable by certain amount
   *
   * @param name   the name of that variable
   * @param amount the value to be incremented
   * @see UserDefinedFields
   */
  void incrementVarBy(String name, Double amount);

  /**
   * Puts a new user-defined command
   *
   * @param commandName the name of that command
   * @param current     a list, the 0 index is the command itself, rest entries are name of
   *                    variables
   * @see UserDefinedFields
   */
  void putFunction(String commandName, List<String> current);

  /**
   * Gets a user-defined command by name
   *
   * @param name the name of that command
   * @return a list, the 0 index is the command itself, rest entries are name of variable
   * @see UserDefinedFields
   */
  List<String> getFunctionContent(String name);

  /**
   * Gets (extra) commands created by the previous command
   *
   * @param extraCommands the commands created by the previous command
   * @see UserDefinedFields
   */
  void setExtraCommands(String extraCommands);
}
