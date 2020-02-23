package slogo.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import slogo.exceptions.CommandDoesNotExistException;
import slogo.exceptions.InvalidArgumentException;
import slogo.exceptions.LanguageIsNotSupportedException;
import slogo.exceptions.WrongCommandFormatException;
import slogo.model.Turtle;

public class Parser implements BackEndExternalAPI {

  public static final String RESOURCE_DIR = "resources/languages/";

  private List<Turtle> turtles;
  private List<Languages> supportedLanguages;
  private Languages currnetLan;
  private int turtleOperating = 0;
  private Map<String, Pattern> commandMap;

  public Parser(Turtle... t) {
    turtles = new ArrayList<Turtle>(Arrays.asList(t));
    supportedLanguages = Arrays.asList(Languages.values());
    currnetLan = null;
    commandMap = new HashMap<>();
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
        setUpCommandMap();
        return;
      }
    }
    throw new LanguageIsNotSupportedException(
        "Input language " + language.toUpperCase() + " is not supported!");
  }

  private void setUpCommandMap() {
    var resources = ResourceBundle.getBundle(RESOURCE_DIR + currnetLan.name());
    commandMap = new HashMap<>();
    for (var key : Collections.list(resources.getKeys())) {
      String regex = resources.getString(key);
      commandMap.put(key, Pattern.compile(regex, Pattern.CASE_INSENSITIVE));
    }
  }

  @Override
  public Queue<EnumMap<MovingObjectProperties, Object>> execute(String command)
      throws CommandDoesNotExistException, LanguageIsNotSupportedException, WrongCommandFormatException, InvalidArgumentException {
    String executable = convertUserInput(command);

    List<String> commandList = separateCommand(executable);
    String commandName = commandList.get(0).toLowerCase();
    String[] parameters = commandList.subList(1, commandList.size()).toArray(new String[0]);

    Class<?> commandsClass = TurtleCommands.class;
    Method[] commands = commandsClass.getDeclaredMethods();

    Method method = findMethod(commands, commandName);
    checkCommandFormat(parameters, method);
    callMethod(parameters, method);

    return getTurtleStates();
  }

  private String convertUserInput(String command)
      throws CommandDoesNotExistException, LanguageIsNotSupportedException {
    if (commandMap == null || commandMap.size() == 0) {
      throw new LanguageIsNotSupportedException(
          "Cannot find any commands of the given language " + currnetLan.name() + ".");
    }
    for (String key : commandMap.keySet()) {
      if (isMatch(command, commandMap.get(key))) {
        return key;
      }
    }
    throw new CommandDoesNotExistException(
        "Cannot recognize command " + command + " for the given language.");
  }

  private boolean isMatch(String command, Pattern pattern) {
    return pattern.matcher(command).matches();
  }

  private Method findMethod(Method[] commands, String commandName)
      throws CommandDoesNotExistException {
    for (Method method : commands) {
      if (method.getName().toLowerCase().equals(commandName)) {
        return method;
      }
    }

    throw new CommandDoesNotExistException(
        "User input command \"" + commandName + "\" is not defined!");
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
      } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
        // TODO: can be more specific
        throw new InvalidArgumentException(
            "Exception occurred when converting argument type of method call. Check whether arguments are of the correct type!");
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
