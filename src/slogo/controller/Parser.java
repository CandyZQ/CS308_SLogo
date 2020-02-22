package slogo.controller;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import slogo.exceptions.CommandDoesNotExistException;
import slogo.exceptions.InvalidArgumentException;
import slogo.exceptions.LanguageIsNotSupportedException;
import slogo.exceptions.WrongCommandFormatException;
import slogo.model.Turtle;

public class Parser implements BackEndExternalAPI {

  private Turtle turtle;
  private List<Languages> supportedLanguages;
  private Languages currnetLan;

  // TODO: delete this
  public Parser() {
    supportedLanguages = Arrays.asList(Languages.values());
    currnetLan = null;
  }

  public Parser(Turtle turtle) {
    // TODO: potentially change to a list of turtles
    this.turtle = turtle;
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
  public Queue<Map<MovingObjectProperties, Double>> execute(String command)
      throws CommandDoesNotExistException, LanguageIsNotSupportedException, WrongCommandFormatException, InvalidArgumentException {
    String engCommand = command;
    // TODO: lan conversion
    List<String> commandList = separateCommand(engCommand);
    String commandName = commandList.get(0).toLowerCase();
    String[] parameters = commandList.subList(1, commandList.size()).toArray(new String[0]);

    Class<?> commandsClass = TurtleCommands.class;
    Method[] commands = commandsClass.getMethods();

    for (Method method : commands) {
      if (method.getName().toLowerCase().equals(commandName)) {
         checkCommandFormat(parameters, method);
         callMethod(parameters, method);
         break;
      }
    }

    throw new CommandDoesNotExistException(
        "User input command \"" + commandList.get(0) + "\" is not defined!");
  }

  private void checkCommandFormat(String[] parameters, Method method)
      throws WrongCommandFormatException {
    int actualParaNum = parameters.length- 1;
    int desireParaNum = method.getParameterCount();
    if (actualParaNum != desireParaNum) {
      throw new WrongCommandFormatException(
          "Expecting " + desireParaNum + " parameters, but found " + actualParaNum
              + " .");
    }
  }

  private void callMethod(String[] parameters, Method method) {
    Object[] objects = new Object[parameters.length];
    for (int i = 0; i < parameters.length; i++) {
      objects[i] = (Object) parameters[i];
    }
    method.invoke()
  }



  private List<String> separateCommand(String command) {
    return new ArrayList<>(Arrays.asList(command.split(" ")));
  }

  public static void main(String[] args) throws LanguageIsNotSupportedException {
    Parser parser = new Parser();
    parser.setLanguage("FF");
  }
}
