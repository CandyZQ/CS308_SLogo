package slogo.controller.operations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import slogo.controller.CommandsMapHelper.SyntaxHelper;
import slogo.controller.TurtleManager;
import slogo.controller.UserDefinedFields;
import slogo.controller.listings.BasicSyntax;
import slogo.controller.listings.MovingObjectProperties;
import slogo.exceptions.InvalidArgumentException;
import slogo.model.Turtle;

public class UserOperations extends Operations{

  public static final int TRIM = 2;

  public static final String LOOP_EXPR = ":repcount";

  public UserOperations(Turtle turtle, UserDefinedFields userDefinedFields, TurtleManager tm) {
    super(turtle, userDefinedFields, tm);

  }

  public Double makeVariable(String variable, Double expr) throws InvalidArgumentException {
    if (!SyntaxHelper.isType(variable, BasicSyntax.VARIABLE)) {
      throw new InvalidArgumentException("The first argument is not in the form of a variable.");
    }
    userDefinedFields.putUserVar(variable, expr);
    return expr;
  }

  public void repeat(Integer expr, String commands) throws InvalidArgumentException {
    loop(1, expr, 1, commands, LOOP_EXPR);
  }

  private void loop(Integer start, Integer end, Integer increment, String commands, String variable)
      throws InvalidArgumentException {
    StringBuilder sb = new StringBuilder();
    userDefinedFields.putUserVar(variable, Double.valueOf(start));
    if (!isValidInsideBracket(commands)) {
      returnZero();
      return;
    }

    String as = commands.substring(TRIM, commands.length() - TRIM);
    for (; userDefinedFields.getUserVar(variable) <= end; userDefinedFields.incrementVarBy(variable, Double.valueOf(increment))) {
      sb.append(as);
      sb.append(" ");
    }
    userDefinedFields.setExtraCommands(sb.toString());
  }

  private void returnZero() {
    turtle.getState().put(MovingObjectProperties.RETURN_VALUE, 0);
  }

  public void doTimes(String vl, String commands) throws InvalidArgumentException {
    String variable = vl.split(" ")[1];
    checkType(variable, BasicSyntax.VARIABLE, 1);
    String l = vl.split(" ")[2];
    checkType(l, BasicSyntax.CONSTANT, 2);
    Integer limit = Integer.parseInt(l);

    loop(1, limit, 1, commands, variable);
   }

   public void FOR(String loopCondition, String commands) throws InvalidArgumentException {
    String variable = loopCondition.split(" ")[1];
    checkType(variable, BasicSyntax.VARIABLE, 1);
    String start = loopCondition.split(" ")[2];
    checkType(start, BasicSyntax.CONSTANT, 2);
    String end = loopCondition.split(" ")[3];
    checkType(end, BasicSyntax.CONSTANT, 3);
    String increment = loopCondition.split(" ")[4];
    checkType(increment, BasicSyntax.CONSTANT, 4);

    loop(Integer.parseInt(start), Integer.parseInt(end), Integer.parseInt(increment), commands, variable);
   }

   private void checkType(String s, BasicSyntax type, int num) throws InvalidArgumentException {
     if (!SyntaxHelper.isType(s, type)) {
       throw new InvalidArgumentException("The " + num + " parameter in " + s + " is not a " + type.name() + " .");
     }
   }

  public void IF(Integer expr, String commands) {
    if (expr != 0) {
      userDefinedFields.setExtraCommands(commands.substring(2, commands.length() - 2));
    } else {
      returnZero();
    }
  }

  public void ifElse(Integer expr, String trueCommands, String falsecommands) {
    if (expr != 0) {
      userDefinedFields.setExtraCommands(trueCommands.substring(TRIM, trueCommands.length() - TRIM));
    } else {
      userDefinedFields.setExtraCommands(falsecommands.substring(TRIM, falsecommands.length() - TRIM));
    }
  }

  public Integer makeUserInstruction(String commandName, String variables, String commands)
      throws InvalidArgumentException {
    List<String> current = new ArrayList<>(
        Collections.singletonList(commands.substring(TRIM, commands.length() - TRIM)));
    if (isValidInsideBracket(variables)) {
      String[] vars = variables.substring(TRIM, variables.length() - TRIM).split(" ");
      for (String v : vars) {
        if (!SyntaxHelper.isType(v, BasicSyntax.VARIABLE)) {
          returnZero();
          throw new InvalidArgumentException(
              "Variable " + v + " passed in with function " + commandName
                  + " is not an valid variable");
        }
        current.add(v);
      }
    }
    userDefinedFields.putFunction(commandName, current);
    return 1;
  }

  private boolean isValidInsideBracket(String command) {
    return command.length() > TRIM * 2;
  }
}
