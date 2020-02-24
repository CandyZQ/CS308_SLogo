package slogo.controller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import javafx.util.Pair;
import slogo.exceptions.CommandDoesNotExistException;
import slogo.exceptions.InvalidArgumentException;
import slogo.exceptions.LanguageIsNotSupportedException;
import slogo.exceptions.WrongCommandFormatException;
import slogo.model.Turtle;

public class Parser implements BackEndExternalAPI {

  private List<Turtle> turtles;
  private int turtleOperating = 0;
  private CommandsMapHelper commandsMapHelper;

  private Stack<String> commandsLeft;
  private List<String> currentCommand;

  public Parser(Turtle... t) {
    turtles = new ArrayList<>(Arrays.asList(t));
    commandsMapHelper = new CommandsMapHelper();
    commandsLeft = new Stack<>();
    currentCommand = new ArrayList<>();
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
    fillStack(command);

    while (!commandsLeft.empty()) {
      getNextCommand();
    }
    return getTurtleStates();
  }

  private void fillStack(String command) {
    String[] c = command.split(" ");
    for (int i = c.length - 1; i >= 0; i--) {
      commandsLeft.push(c[i]);
    }
  }

  private String getNextCommand()
      throws CommandDoesNotExistException, WrongCommandFormatException, InvalidArgumentException, LanguageIsNotSupportedException {
    String commandName = commandsLeft.pop();
    commandName = commandsMapHelper.convertUserInput(commandName);
    System.out.println(commandName);
    Pair<Class<?>, Method> pair = findClass(commandName);
    Class<?> c = pair.getKey();
    Method m = pair.getValue();
    CommandStructure current = new CommandStructure(c, m, getNumOfPara(m));

    while (current.needMoreParas()) {
      String nextPara = getNextPara(current);
      current.addPara(nextPara);
    }

    return current.execute(turtles.get(turtleOperating)).toString();
  }

  private String getNextPara(CommandStructure structure)
      throws WrongCommandFormatException, CommandDoesNotExistException, InvalidArgumentException, LanguageIsNotSupportedException {
    String next = commandsLeft.pop();
    Class<?> nextType = structure.getNextParaType();

    // TODO: potentially add more types if necessary
    if (nextType.equals(Double.class) || nextType.equals(Integer.class) || nextType.equals(double.class) || nextType.equals(int.class)) {
      if (isNumeric(next)) {
        return next;
      }
      commandsLeft.push(next);
      return getNextCommand();
    } else if (nextType.equals(String.class)) {
      return next;
    } else {
      throw new WrongCommandFormatException("Command " + structure.getName() + " is not in the correct format!");
    }
  }

  private boolean isNumeric(String str) {
    try {
      Double.parseDouble(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  private Pair<Class<?>, Method> findClass(String commandName) throws CommandDoesNotExistException {
    Class<?> commandsClass;
    for (CommandType c : CommandType.values()) {
      try {
        commandsClass = Class.forName("slogo.controller.operations." + c.name());
        Method method = findMethod(commandsClass.getDeclaredMethods(), commandName);
        if (method != null) {
          return new Pair<>(commandsClass, method);
        }
      } catch (ClassNotFoundException e) {
        System.out.println(
            "Internal Error: operation class name defined in CommandType but not implemented.");
      }
    }

    throw new CommandDoesNotExistException(
        "User input command \"" + commandName + "\" is not defined!");
  }

  private Method findMethod(Method[] commands, String commandName) {
    for (Method method : commands) {
      if (method.getName().toLowerCase().equals(commandName)) {
        return method;
      }
    }
    return null;
  }

  private int getNumOfPara(Method method) {
    return method.getParameterCount();
  }

  private Queue<EnumMap<MovingObjectProperties, Object>> getTurtleStates() {
    Queue<EnumMap<MovingObjectProperties, Object>> turtleStates = new LinkedList<>();
    for (Turtle t : turtles) {
      turtleStates.add(t.getState());
    }
    return turtleStates;
  }
}
