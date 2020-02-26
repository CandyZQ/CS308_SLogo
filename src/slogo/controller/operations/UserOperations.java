package slogo.controller.operations;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import slogo.controller.CommandExecuter;
import slogo.controller.CommandsMapHelper;
import slogo.controller.listings.BasicSyntax;
import slogo.exceptions.InvalidArgumentException;
import slogo.model.Turtle;

public class UserOperations {

  public Map<String, Double> UserVars = new HashMap<>();
  private CommandsMapHelper commandsMapHelper = new CommandsMapHelper();

  public UserOperations(Turtle turtle) {

  }

  public Double makeVariable(String variable, Double expr) throws InvalidArgumentException {
    if (!commandsMapHelper.getInputType(variable).equals(BasicSyntax.VARIABLE)) {
      throw new InvalidArgumentException("The first argument is not in the form of a variable.");
    }
    UserVars.put(variable, expr);
    return expr;
  }

  public String repeat(Integer expr, String commands) {
    StringBuilder sb = new StringBuilder();
    String as = commands.substring(2, commands.length() - 1);
    for (int i = 0; i < expr; i++) {
      sb.append(as);
    }
    return sb.toString();
  }

  // public double doTimes() { }

  // public double for_loop(String variable, int start, int end, int increment, String commands) { }

  public String if_statement(int expr, String commands) {
    if (expr != 0) {
      return commands;
    } else {
      return "";
    }
  }

  public String if_else(int expr, String truecommands, String falsecommands) {
    if (expr != 0) {
      return truecommands;
    } else {
      return falsecommands;
    }
  }

  //public String to(String commandName, String variables, String commands) { }
}
