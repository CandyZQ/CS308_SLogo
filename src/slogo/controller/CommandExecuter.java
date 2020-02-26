package slogo.controller;

import static slogo.controller.listings.BasicSyntax.COMMAND;
import static slogo.controller.listings.BasicSyntax.COMMENT;
import static slogo.controller.listings.BasicSyntax.CONSTANT;
import static slogo.controller.listings.BasicSyntax.LISTEND;
import static slogo.controller.listings.BasicSyntax.LISTSTART;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import javafx.util.Pair;
import slogo.controller.listings.CommandType;
import slogo.controller.listings.MovingObjectProperties;
import slogo.exceptions.CommandDoesNotExistException;
import slogo.exceptions.InvalidArgumentException;
import slogo.exceptions.LanguageIsNotSupportedException;
import slogo.exceptions.WrongCommandFormatException;
import slogo.model.Turtle;

public class CommandExecuter {

  private List<Turtle> animals;
  private int turtleOperating;

  private CommandsMapHelper commandsMapHelper;
  private Stack<String> commandsLeft;
  private Queue<EnumMap<MovingObjectProperties, Object>> turtleStates;

  public CommandExecuter(int animalNum) {
    animals = new ArrayList<>();
    for (int i = 0; i < animalNum; i++) {
      animals.add(new Turtle(i));
    }
  }


  public String getNextCommand(CommandsMapHelper commandsMapHelper, Stack<String> commandsLeft,
      Queue<EnumMap<MovingObjectProperties, Object>> turtleStates)
      throws CommandDoesNotExistException, WrongCommandFormatException, InvalidArgumentException, LanguageIsNotSupportedException {
    this.commandsMapHelper = commandsMapHelper;
    this.commandsLeft = commandsLeft;
    this.turtleStates = turtleStates;

    String commandName = popNext();
    if (!commandsMapHelper.getInputType(commandName).equals(COMMAND)) {
      throw new InvalidArgumentException(
          "The command " + commandName + " is not a valid SLogo command!");
    }

    commandName = commandsMapHelper.convertUserInput(commandName);
    Pair<Class<?>, Method> pair = findClass(commandName);
    Class<?> c = pair.getKey();
    Method m = pair.getValue();
    CommandStructure current = new CommandStructure(c, m, getNumOfPara(m));

    while (current.needMoreParas()) {
      String nextPara = getNextPara(current);
      current.addPara(nextPara);
    }

    String returnVal = current.execute(animals.get(turtleOperating)).toString();
    animals.get(turtleOperating).getState().put(MovingObjectProperties.RETURN_VALUE, Double.parseDouble(returnVal));
    turtleStates.add(animals.get(turtleOperating).getState().clone());
    return returnVal;
  }

  private String getNextPara(CommandStructure structure)
      throws WrongCommandFormatException, CommandDoesNotExistException, InvalidArgumentException, LanguageIsNotSupportedException {
    String next = popNext();
    Class<?> nextType = structure.getNextParaType();

    // TODO: potentially add more types if necessary
    if (nextType.equals(Double.class) || nextType.equals(Integer.class) || nextType
        .equals(double.class) || nextType.equals(int.class)) {
      if (commandsMapHelper.getInputType(next).equals(CONSTANT)) {
        return next;
      }
      commandsLeft.push(next);
      return getNextCommand(commandsMapHelper, commandsLeft, turtleStates);
    } else if (nextType.equals(String.class)) {
      return next;
    } else {
      throw new WrongCommandFormatException(
          "Command " + structure.getName() + " is not in the correct format!");
    }
  }

  private String popNext() throws InvalidArgumentException {
    StringBuilder next = new StringBuilder(commandsLeft.pop());
    while (!commandsLeft.isEmpty() && commandsMapHelper.getInputType(next.toString())
        .equals(COMMENT)) {
      next = new StringBuilder(commandsLeft.pop());
    }

    if (commandsMapHelper.getInputType(next.toString()).equals(LISTSTART)) {
      String s = "";
      while (!commandsMapHelper.getInputType(s).equals(LISTEND)) {
        s = commandsLeft.pop();
        next.append(s);
      }
    }
    return next.toString();
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

  public void setTurtleOperating(int turtleOperating) {
    this.turtleOperating = turtleOperating;
  }
}
