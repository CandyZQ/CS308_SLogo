package slogo.controller;

import static slogo.controller.listings.BasicSyntax.COMMAND;
import static slogo.controller.listings.BasicSyntax.COMMENT;
import static slogo.controller.listings.BasicSyntax.CONSTANT;
import static slogo.controller.listings.BasicSyntax.LISTEND;
import static slogo.controller.listings.BasicSyntax.LISTSTART;
import static slogo.controller.listings.BasicSyntax.VARIABLE;

import java.io.IOException;
import java.util.*;

import slogo.controller.CommandsMapHelper.SyntaxHelper;
import slogo.controller.listings.MovingObjectProperties;
import slogo.controller.scripting.FileReader;
import slogo.exceptions.CommandDoesNotExistException;
import slogo.exceptions.InvalidArgumentException;
import slogo.exceptions.LanguageIsNotSupportedException;
import slogo.exceptions.WrongCommandFormatException;
import slogo.model.Turtle;

public class Parser implements BackEndExternalAPI {
  private CommandsMapHelper commandsMapHelper;
  private UserDefinedFields userDefinedFields;
  private TurtleManager tm;

  private Stack<String> commandsLeft;
  private Stack<CommandStructure> pausedCommands;

  public Parser(int turtleNumber) {
    commandsMapHelper = new CommandsMapHelper();
    userDefinedFields = new UserDefinedFields();
    tm = new TurtleManager(turtleNumber);
    initialize();
  }

  private void initialize() {
    commandsLeft = new Stack<>();
    pausedCommands = new Stack<>();
    tm.cleanState();
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
    initialize();
    fillStack(command);

    Stack<String> temp = (Stack<String>) commandsLeft.clone();
    for (Turtle t: tm.getTurtles()) {
      commandsLeft = (Stack<String>) temp.clone();
      while (!commandsLeft.empty()) {
        executeNextCommand(t);
      }
    }
    return tm.getTurtleStates();
  }

  @Override
  public Queue<EnumMap<MovingObjectProperties, Object>> runScript(String filename) throws IOException, WrongCommandFormatException, InvalidArgumentException, LanguageIsNotSupportedException, CommandDoesNotExistException {
    Queue<EnumMap<MovingObjectProperties, Object>> commandResults = new LinkedList<>();
    Queue<EnumMap<MovingObjectProperties, Object>> commandResult;
    EnumMap<MovingObjectProperties, Object> turtleState;
    FileReader file = new FileReader(filename);
    List<String> commands = file.processScript();
    for (int i = 0; i < commands.size(); i++) {
      commandResult = execute(commands.get(i));
      while(!commandResult.isEmpty()) {
        turtleState = commandResult.remove();
        commandResults.add(turtleState);
      }
    }
    return commandResults;
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
    return userDefinedFields.getUserVars();
  }

  public Map<String, List<String>> getFunctions() {
    return userDefinedFields.getFunctions();
  }

  private void executeNextCommand(Turtle t)
      throws CommandDoesNotExistException, WrongCommandFormatException, InvalidArgumentException, LanguageIsNotSupportedException {
    String commandName = popNext();
    if (!SyntaxHelper.isType(commandName, COMMAND)) {
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
    checkPausedCommands(tm, t);
  }

  private void checkPausedCommands(TurtleManager tm, Turtle t)
      throws InvalidArgumentException, WrongCommandFormatException {
    String returnVal = null;
    while (!pausedCommands.isEmpty()) {
      if (!pausedCommands.peek().needMoreParas()) {
        returnVal = pausedCommands.pop().singleExecute(tm, userDefinedFields, t).toString();
      } else if (!pausedCommands.isEmpty() && returnVal != null) {
        pausedCommands.peek().addPara(returnVal);
        while (!commandsLeft.empty()) {
          if (!canAddPara(pausedCommands.peek())) {
            break;
          }
        }
        returnVal = null;
      }
    }
  }

  private boolean canAddPara(CommandStructure structure)
      throws WrongCommandFormatException, InvalidArgumentException {
    String next = popNext();
    Class<?> nextType = structure.getNextParaType();

    // TODO: potentially add more types if necessary
    if (nextType.equals(Double.class) || nextType.equals(Integer.class) || nextType
        .equals(double.class) || nextType.equals(int.class)) {
      if (SyntaxHelper.isType(next, CONSTANT)) {
        structure.addPara(next);
        return true;
      }
      if (SyntaxHelper.isType(next, VARIABLE)) {
        structure.addPara(userDefinedFields.getVar(next));
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
    while (!commandsLeft.isEmpty() && SyntaxHelper.isType(next.toString(), COMMENT)) {
      next = new StringBuilder(commandsLeft.pop());
    }

    if (SyntaxHelper.isType(next.toString(), LISTSTART)) {
      String s = next.toString();
      while (!SyntaxHelper.isType(s, LISTEND)) {
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
