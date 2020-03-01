package slogo.controller;

import static slogo.controller.listings.BasicSyntax.COMMAND;
import static slogo.controller.listings.BasicSyntax.COMMENT;
import static slogo.controller.listings.BasicSyntax.CONSTANT;
import static slogo.controller.listings.BasicSyntax.LISTEND;
import static slogo.controller.listings.BasicSyntax.LISTSTART;
import static slogo.controller.listings.BasicSyntax.VARIABLE;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import slogo.controller.listings.MovingObjectProperties;
import slogo.exceptions.CommandDoesNotExistException;
import slogo.exceptions.InvalidArgumentException;
import slogo.exceptions.LanguageIsNotSupportedException;
import slogo.exceptions.WrongCommandFormatException;
import slogo.model.Turtle;

public class Parser implements BackEndExternalAPI {
  private CommandsMapHelper commandsMapHelper;
  private Stack<String> commandsLeft;
  private Stack<CommandStructure> pausedCommands;
  private List<Turtle> animals;
  private int turtleOperating;

  // List entries: variable and then command
  private Map<String, List<String>> functions;
  private Map<String, Double> userVars;

  public Parser(int animalNum) {
    commandsMapHelper = new CommandsMapHelper();
    commandsLeft = new Stack<>();
    pausedCommands = new Stack<>();

    animals = new ArrayList<>();
    for (int i = 0; i < animalNum; i++) {
      animals.add(new Turtle(i));
    }

    userVars = new HashMap<>();
    functions = new HashMap<>();
  }

  /**
   * Sets the language in which commands will be interpreted in. Should be called in program's main
   * loop.
   *
   * @param language the language user inputs will be in, not case-sensitive
   * @throws LanguageIsNotSupportedException
   */
  @Override
  public void setLanguage(String language) throws LanguageIsNotSupportedException {
    commandsMapHelper.setLanguage(language);
  }

  /**
   * Executes the command from user input
   *
   * @param command the command that the user inputs
   * @return a {@link Queue} of status of existed turtles
   * @throws CommandDoesNotExistException
   * @throws LanguageIsNotSupportedException
   * @throws WrongCommandFormatException
   * @throws InvalidArgumentException
   */
  @Override
  public Queue<EnumMap<MovingObjectProperties, Object>> execute(String command)
      throws CommandDoesNotExistException, LanguageIsNotSupportedException, WrongCommandFormatException, InvalidArgumentException {
    TurtleStatesManager tm = new TurtleStatesManager();
    fillStack(command);

    while (!commandsLeft.empty()) {
      executeNextCommand(tm);
    }

    return tm.getTurtleStates();
  }

  private void fillStack(String command) {
    String[] c = command.split(" ");
    for (int i = c.length - 1; i >= 0; i--) {
      commandsLeft.push(c[i]);
    }
  }

  /**
   * Gets the user defined variables
   *
   * @return
   */
  public Map<String, Double> gerUserVars() {
    return Collections.unmodifiableMap(userVars);
  }

  public Map<String, List<String>> getFunctions() {
    return Collections.unmodifiableMap(functions);
  }

  private void executeNextCommand(TurtleStatesManager tm)
      throws CommandDoesNotExistException, WrongCommandFormatException, InvalidArgumentException, LanguageIsNotSupportedException {
    String commandName = popNext();
    if (!commandsMapHelper.isType(commandName, COMMAND)) {
      throw new InvalidArgumentException(
          "The command " + commandName + " is not a valid SLogo command!");
    }
    CommandStructure current = commandsMapHelper.convertUserInput(commandName);

    while (current.needMoreParas()) {
      // execute if other commands need to be executed for return values
      if (!canAddPara(current)) {
        pausedCommands.add(current);
        return;
      }
    }
    pausedCommands.add(current);
    checkPausedCommands(tm);
  }

  private void checkPausedCommands(TurtleStatesManager tm)
      throws InvalidArgumentException, WrongCommandFormatException {
    String returnVal = null;
    while (!pausedCommands.isEmpty()) {
      if (!pausedCommands.peek().needMoreParas()) {
        returnVal = pausedCommands.pop().execute(animals.get(turtleOperating), userVars, functions)
            .toString();
        storeTurtleStates(returnVal, tm);
      }
      if (!pausedCommands.isEmpty() && returnVal != null) {
        pausedCommands.peek().addPara(returnVal);
        returnVal = null;
      }
    }
  }

  private void storeTurtleStates(String returnVal, TurtleStatesManager tm) {
    try {
      if (commandsMapHelper.isType(returnVal, CONSTANT)) {
        animals.get(turtleOperating).getState()
            .put(MovingObjectProperties.RETURN_VALUE, Double.parseDouble(returnVal));
      }
//    } catch (InvalidArgumentException e) {
//      String[] ss = returnVal.split(" ");
//      for (int i = ss.length - 1; i >= 0; i--) {
//        commandsLeft.push(ss[i]);
//      }
//    }
    } catch (Exception e) {

    }

    tm.addStates(animals.get(turtleOperating));
  }


  private boolean canAddPara(CommandStructure structure)
      throws WrongCommandFormatException, InvalidArgumentException {
    String next = popNext();
    Class<?> nextType = structure.getNextParaType();

    // TODO: potentially add more types if necessary
    if (nextType.equals(Double.class) || nextType.equals(Integer.class) || nextType
        .equals(double.class) || nextType.equals(int.class)) {
      if (commandsMapHelper.isType(next, CONSTANT)) {
        structure.addPara(next);
        return true;
      }
      if (commandsMapHelper.isType(next, VARIABLE)) {
        structure.addPara(userVars.get(next).toString());
      }
      commandsLeft.push(next);
      return false;
    }

    if (nextType.equals(String.class)) {
      structure.addPara(next);
      return true;
    }

    throw new WrongCommandFormatException(
        "Command " + structure.getName() + " is not in the correct format!");
  }

  private String popNext() throws InvalidArgumentException {
    StringBuilder next = new StringBuilder(commandsLeft.pop());
    while (!commandsLeft.isEmpty() && commandsMapHelper.isType(next.toString(), COMMENT)) {
      next = new StringBuilder(commandsLeft.pop());
    }

    if (commandsMapHelper.isType(next.toString(), LISTSTART)) {
      String s = next.toString();
      while (!commandsMapHelper.isType(s, LISTEND)) {
        s = commandsLeft.pop();
        next.append(" ");
        next.append(s);
      }
    }

//    if (functions.containsKey(next.toString())) {
//      List<String> com = functions.get(next.toString());
//      String[] ss = com.get(1).split(" ");
//      for (int i = ss.length - 1; i >= 0; i--) {
//        commandsLeft.push(ss[i]);
//      }
//      next = new StringBuilder(popNext());
//    }

    return next.toString();
  }
}
