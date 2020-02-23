package slogo.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import slogo.exceptions.CommandDoesNotExistException;
import slogo.exceptions.InvalidArgumentException;
import slogo.exceptions.LanguageIsNotSupportedException;
import slogo.exceptions.WrongCommandFormatException;
import slogo.model.Turtle;

public class Parser implements BackEndExternalAPI {

  private List<Turtle> turtles;
  private List<Languages> supportedLanguages;
  private Languages currnetLan;
  private int turtleOperating = 0;

  public Parser(Turtle... t) {
    // TODO: potentially change to a list of turtles
    turtles = new ArrayList<Turtle>(Arrays.asList(t));
    supportedLanguages = Arrays.asList(Languages.values());
    currnetLan = null;
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
    for (Languages l : supportedLanguages) {
      if (l.name().toUpperCase().equals(language.toUpperCase())) {
        currnetLan = l;
        return;
      }
    }
    throw new LanguageIsNotSupportedException(
        "Input language " + language.toUpperCase() + " is not supported!");
  }

  @Override
  public Queue<EnumMap<MovingObjectProperties, Object>> execute(String command)
      throws CommandDoesNotExistException, LanguageIsNotSupportedException, WrongCommandFormatException, InvalidArgumentException {
    String engCommand = command;
    // TODO: lan conversion
    List<String> commandList = separateCommand(engCommand);
    String commandName = commandList.get(0).toLowerCase();
    String[] parameters = commandList.subList(1, commandList.size()).toArray(new String[0]);

    Class<?> commandsClass = TurtleCommands.class;
    Method[] commands = commandsClass.getMethods();

    boolean hasMethod = false;
    for (Method method : commands) {
      if (method.getName().toLowerCase().equals(commandName)) {
        checkCommandFormat(parameters, method);
        callMethod(parameters, method);
        hasMethod = true;
        break;
      }
    }

    if (!hasMethod) {
      throw new CommandDoesNotExistException(
          "User input command \"" + commandList.get(0) + "\" is not defined!");
    }
    return getTurtleStates();
  }

  private void checkCommandFormat(String[] parameters, Method method)
      throws WrongCommandFormatException {
    int actualParaNum = parameters.length;
    int desireParaNum = method.getParameterCount();
    if (actualParaNum != desireParaNum) {
      throw new WrongCommandFormatException(
          "Expecting " + desireParaNum + " parameters, but found " + actualParaNum
              + ".");
    }
  }

  private void callMethod(String[] parameters, Method method)
      throws InvalidArgumentException {
    Object[] objects = new Object[parameters.length];
    Class<?>[] types = method.getParameterTypes();
    for (int i = 0; i < parameters.length; i++) {
      try {
        objects[i] = types[i].getConstructor(String.class).newInstance(parameters[i]);
      } catch (InstantiationException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      }
    }

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

  // TODO: add this to API
  private Queue<EnumMap<MovingObjectProperties, Object>> getTurtleStates() {
    Queue<EnumMap<MovingObjectProperties, Object>> turtleStates = new LinkedList<>();
    for (Turtle t : turtles) {
      turtleStates.add(t.getState());
    }
    return turtleStates;
  }
}
