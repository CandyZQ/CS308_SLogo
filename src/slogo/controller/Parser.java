package slogo.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import javafx.util.Pair;
import slogo.controller.operations.TurtleCommands;
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

      while (current.needMoreParas()) {

      }

//    List<String> commandList = separateCommand(command);
//    String commandName = commandsMapHelper.convertUserInput(commandList.get(0).toLowerCase());
//    String[] parameters = commandList.subList(1, commandList.size()).toArray(new String[0]);

      checkCommandFormat(parameters, method);
      callMethod(parameters, method);
    }
    return getTurtleStates();
  }

  private void fillStack(String command) {
    String[] c = command.split(" ");
    for (int i = c.length - 1; i >= 0; i--) {
      commandsLeft.push(c[i]);
    }
  }

  private String getNextCommand() throws CommandDoesNotExistException {
    String commandName = commandsLeft.pop();
    Pair<Class<?>, Method> pair = findClass(commandName);
    Class<?> c = pair.getKey();
    Method m = pair.getValue();
    CommandStructure current = new CommandStructure(c, m, getNumOfPara(m));

    if (current.needMoreParas()) {
      getNextPara();
    }
  }

  private String getNextPara(CommandStructure structure) throws WrongCommandFormatException {
    String next = commandsLeft.pop();
    Class<?> nextType = structure.getNextParaType();
    if (nextType.equals(Double.class) || nextType.equals(Integer.class)) {
      if (isNumeric(next)) {
        return next;
      }
      return getNextCommand();
    } else if (nextType.equals(String.class)) {
      return next;
    } else {
      throw new WrongCommandFormatException("Command " + structure.getName());
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


  private void callMethod(String[] parameters, Method method)
      throws InvalidArgumentException {

    // TODO: add other operations
    try {
      method.invoke(new TurtleCommands(turtles.get(turtleOperating)), objects);
    } catch (IllegalArgumentException e) {
      throw new InvalidArgumentException(e);
    } catch (IllegalAccessException e) {
      System.out.println("The method called is not accessible");
    } catch (InvocationTargetException e) {
      // TODO: do something?
    }
  }

  private List<String> separateCommand(String command) {
    return new ArrayList<>(Arrays.asList(command.split(" ")));
  }

  private Queue<EnumMap<MovingObjectProperties, Object>> getTurtleStates() {
    Queue<EnumMap<MovingObjectProperties, Object>> turtleStates = new LinkedList<>();
    for (Turtle t : turtles) {
      turtleStates.add(t.getState());
    }
    return turtleStates;
  }

  public Object getReturnValue() {
    return turtles.get(turtleOperating).getState().get(MovingObjectProperties.RETURN_VALUE);
  }
}
