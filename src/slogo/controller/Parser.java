package slogo.controller;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import slogo.controller.listings.MovingObjectProperties;
import slogo.exceptions.CommandDoesNotExistException;
import slogo.exceptions.InvalidArgumentException;
import slogo.exceptions.LanguageIsNotSupportedException;
import slogo.exceptions.WrongCommandFormatException;

public class Parser implements BackEndExternalAPI {

  private CommandsMapHelper commandsMapHelper;
  private CommandExecuter commandExecuter;
  private Queue<EnumMap<MovingObjectProperties, Object>> turtleStates;

  private Stack<String> commandsLeft;

  public Parser(int animalNum) {
    turtleStates = new LinkedList<>();

    commandsMapHelper = new CommandsMapHelper();
    commandsLeft = new Stack<>();
    commandExecuter = new CommandExecuter(animalNum);

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
    turtleStates = new LinkedList<>();
    fillStack(command);

    while (!commandsLeft.empty()) {
      commandExecuter.getNextCommand(commandsMapHelper, commandsLeft, turtleStates);
    }

    return turtleStates;
  }

  private void fillStack(String command) {
    String[] c = command.split(" ");
    for (int i = c.length - 1; i >= 0; i--) {
      commandsLeft.push(c[i]);
    }
  }

  public void setTurtleOperating(int id) {
    commandExecuter.setTurtleOperating(id);
  }

  public Map<String, Double> gerUserVars() {
    return commandExecuter.getUserVars();
  }
}
