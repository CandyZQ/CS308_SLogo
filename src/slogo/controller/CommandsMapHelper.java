package slogo.controller;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import slogo.controller.listings.BasicSyntax;
import slogo.controller.listings.CommandType;
import slogo.controller.listings.Languages;
import slogo.controller.operations.SystemCommands;
import slogo.exceptions.CommandDoesNotExistException;
import slogo.exceptions.InvalidArgumentException;
import slogo.exceptions.LanguageIsNotSupportedException;

public class CommandsMapHelper {

  public static final String RESOURCE_DIR = "resources/languages/";
  public static final String SYNTAX_FILE = "Syntax";
  public static final String OPERATIONS_DIR = "slogo.controller.operations.";

  private List<Languages> supportedLanguages;
  private Languages currnetLan;

  private static Map<String, Pattern> syntaxMap;
  private Map<String, Pattern> commandsMap;

  public CommandsMapHelper() {
    supportedLanguages = Arrays.asList(Languages.values());
    currnetLan = null;
    commandsMap = new HashMap<>();
    syntaxMap = setUpCommandMap(SYNTAX_FILE);
  }

  void setLanguage(String language) throws LanguageIsNotSupportedException {
    for (Languages l : supportedLanguages) {
      if (l.name().toUpperCase().equals(language.toUpperCase())) {
        currnetLan = l;
        commandsMap = setUpCommandMap(currnetLan.name());
        return;
      }
    }
    throw new LanguageIsNotSupportedException(
        "Input language " + language.toUpperCase() + " is not supported!");
  }

  private Map<String, Pattern> setUpCommandMap(String filename) {
    var resources = ResourceBundle.getBundle(RESOURCE_DIR + filename);
    Map<String, Pattern> map = new HashMap<>();
    for (var key : Collections.list(resources.getKeys())) {
      String regex = resources.getString(key);
      map.put(key, Pattern.compile(regex, Pattern.CASE_INSENSITIVE));
    }
    return map;
  }

  CommandStructure convertUserInput(String command)
      throws CommandDoesNotExistException, LanguageIsNotSupportedException {
    if (commandsMap == null || commandsMap.size() == 0) {
      throw new LanguageIsNotSupportedException(
          "Cannot find any commands of the given language " + currnetLan.name() + ".");
    }

    if (findInSysCommands(command)) {
      return findClass(command.toLowerCase());
    }

    for (String key : commandsMap.keySet()) {
      if (SyntaxHelper.isMatch(command, commandsMap.get(key))) {
        return findClass(key.toLowerCase());
      }
    }

    throw new CommandDoesNotExistException(
        "Cannot recognize command " + command + " for the given language.");
  }

  private boolean findInSysCommands(String command) {
    Class<SystemCommands> c = SystemCommands.class;
    for (Method m: c.getDeclaredMethods()) {
      if (m.getName().equalsIgnoreCase(command)) {
        return true;
      }
    }
    return false;
  }

  private CommandStructure findClass(String commandName) throws CommandDoesNotExistException {
    for (CommandType c : CommandType.values()) {
      try {
        Class<?> commandsClass = Class.forName(OPERATIONS_DIR + c.name());
        Method method = findMethod(commandsClass.getDeclaredMethods(), commandName);
        if (method != null) {
          return method.getName().equalsIgnoreCase(Parser.FUNCTION_METHOD) ? new FunctionStructure(commandsClass, method)
              : new CommandStructure(commandsClass, method);
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
      if (method.getName().equalsIgnoreCase(commandName)) {
        return method;
      }
    }
    return null;
  }

  public static class SyntaxHelper {
    public static boolean isType(String input, BasicSyntax type) throws InvalidArgumentException {
      return getInputType(input).equals(type);
    }

    private static BasicSyntax getInputType(String input) throws InvalidArgumentException {
      for (String key : syntaxMap.keySet()) {
        if (isMatch(input, syntaxMap.get(key))) {
          return BasicSyntax.valueOf(key.toUpperCase());
        }
      }
      throw new InvalidArgumentException("The input " + input + " is not valid in SLogo.");
    }

    private static boolean isMatch(String command, Pattern pattern) {
      return pattern.matcher(command).matches();
    }
  }
}
