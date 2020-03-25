package slogo.controller.operations;

import static slogo.controller.Parser.APPEND_METHOD;
import static slogo.controller.Parser.TRIM;

import java.util.List;
import slogo.controller.CommandsMapHelper.SyntaxHelper;
import slogo.controller.TurtleManager;
import slogo.controller.UserDefinedFields;
import slogo.controller.listings.BasicSyntax;
import slogo.exceptions.CommandDoesNotExistException;
import slogo.exceptions.InvalidArgumentException;
import slogo.model.Turtle;

/**
 * This class contains all operations for system use. Users should not use any of these commands.
 *
 * @author cady
 */
public class SystemCommands extends Operations {

  public static final String INCREMENT_METHOD = "increment";

  public SystemCommands(Turtle turtle, UserDefinedFields userDefinedFields, TurtleManager tm) {
    super(turtle, userDefinedFields, tm);
  }

  private void doFunction(List<?> info)
      throws CommandDoesNotExistException, InvalidArgumentException {
    // info: the first String is function name, and then follows all parameters
    String funcName = (String) info.get(0);
    List<String> variables = userDefinedFields.getFunctionContent(funcName);

    if (info.size() != variables.size()) {
      throw new CommandDoesNotExistException(
          "Function " + funcName + " requires " + (variables.size() - 1) + " variables, but " + (
              info.size() - 1) + " are passed in.");
    }

    for (int i = 1; i < info.size(); i++) {
      if (!SyntaxHelper.isType(Integer.toString((Integer) info.get(i)), BasicSyntax.CONSTANT)) {
        throw new InvalidArgumentException(
            "The " + i + "th argument passed in of " + funcName + " is not a constant!");
      }
      userDefinedFields.putUserVar(variables.get(i), Double.valueOf((Integer) info.get(i)));
    }

    String commands = userDefinedFields.getFunctionContent(funcName).get(0);
    userDefinedFields.setExtraCommands(commands);
  }

  private void increment(String var, Double amount) {
    userDefinedFields.incrementVarBy(var, amount);
  }

  private void append(String variable, Integer end, Integer increment, String commands) {
    String as = commands.substring(TRIM, commands.length() - TRIM);
    StringBuilder sb = new StringBuilder();

    if (userDefinedFields.getUserVar(variable) <= end) {
      sb.append(as).append(" ");
      sb.append(INCREMENT_METHOD + " ").append(variable).append(" ").append(increment).append(" ");
      sb.append(APPEND_METHOD).append(" ").append(variable).append(" ").append(end).append(" ")
          .append(increment).append(" ").append(commands);
      userDefinedFields.setExtraCommands(sb.toString());
    }
  }

  private void condition(Integer expr, String commands) {
    if (expr != 0) {
      userDefinedFields.setExtraCommands(commands.substring(TRIM, commands.length() - TRIM));
    }
  }
}
