package slogo.controller.operations;

import java.util.List;
import slogo.controller.CommandsMapHelper.SyntaxHelper;
import slogo.controller.TurtleManager;
import slogo.controller.UserDefinedFields;
import slogo.controller.listings.BasicSyntax;
import slogo.exceptions.CommandDoesNotExistException;
import slogo.exceptions.InvalidArgumentException;
import slogo.model.Turtle;

public class SystemCommands extends Operations {

  public SystemCommands(Turtle turtle, UserDefinedFields userDefinedFields, TurtleManager tm) {
    super(turtle, userDefinedFields, tm);
  }

  public void doFunction(List<String> info)
      throws CommandDoesNotExistException, InvalidArgumentException {
    // info: the first String is function name, and then follows all parameters
    String funcName = info.get(0);

    String commands = userDefinedFields.getFunctionContent(funcName).get(0);
    List<String> variables = userDefinedFields.getFunctionContent(funcName);

    if (info.size() != variables.size()) {
      throw new CommandDoesNotExistException(
          "Function " + funcName + " requires " + (variables.size() - 1) + " variables, but " + (
              info.size() - 1) + " are passed in.");
    }

    for (int i = 1; i < info.size(); i++) {
      if (!SyntaxHelper.isType(info.get(i), BasicSyntax.CONSTANT)) {
        throw new InvalidArgumentException(
            "The " + i + "th argument passed in of " + funcName + " is not a constant!");
      }
      userDefinedFields.putUserVar(variables.get(i), Double.valueOf(info.get(i)));
    }

    userDefinedFields.setExtraCommands(commands);
  }
}
