package slogo.controller;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import slogo.controller.listings.BasicSyntax;
import slogo.controller.listings.CommandType;
import slogo.controller.listings.Languages;
import slogo.controller.operations.SystemCommands;
import slogo.exceptions.CommandDoesNotExistException;
import slogo.exceptions.CompilerException;
import slogo.exceptions.InvalidArgumentException;
import slogo.exceptions.LanguageIsNotSupportedException;

/**
 * This class loads available commands each time the language is changed from resource files into a
 * {@link Map}. Given user inputs, this class is able to convert those user inputs into callable
 * methods by comparing that input with entries of this map. The return value will be a {@link
 * CommandStructure} (or a {@link FunctionStructure} depending on the input).
 * <p>
 * A static inner class is created to provide information on the type of user input.
 *
 * @author Cady
 * @author Sarah
 * @version 1.1
 * @since 1.1
 */
public class CommandsMapHelper {

  public static final String RESOURCE_DIR = "resources/languages/";
  public static final String SYNTAX_FILE = "Syntax";
  public static final String OPERATIONS_DIR = "slogo.controller.operations.";
  public static final int FIRST_NUM = 50;
  public static final int SECOND_NUM = 90;
  public static final int BUTTON_NUM = 4;
  private static Map<String, Pattern> syntaxMap;
  private List<Languages> supportedLanguages;
  private Languages currnetLan;
  private Map<String, Pattern> commandsMap;

  /**
   * Creates a new instance of commands map helper. Language should be set before using this
   * instance.
   */
  public CommandsMapHelper() {
    supportedLanguages = Arrays.asList(Languages.values());
    currnetLan = null;
    commandsMap = new HashMap<>();
    syntaxMap = setUpCommandMap(SYNTAX_FILE);
  }

  String[] setLanguage(String language) throws LanguageIsNotSupportedException {
    for (Languages l : supportedLanguages) {
      if (l.name().equalsIgnoreCase(language)) {
        currnetLan = l;
        commandsMap = setUpCommandMap(currnetLan.name());
        return getDisplayCommands(currnetLan.name());
      }
    }
    throw new LanguageIsNotSupportedException(
        "Input language " + language.toUpperCase() + " is not supported!");
  }

  String[] getDisplayCommands(String filename) {
    var resources = ResourceBundle.getBundle(RESOURCE_DIR + filename);
    String[] displayCommands = new String[BUTTON_NUM];

    for (var key : Collections.list(resources.getKeys())) {
      String regex = resources.getString(key);
      switch (key) {
        case "Forward":
          displayCommands[0] = getText(regex, FIRST_NUM);
          break;
        case "Backward":
          displayCommands[1] = getText(regex, FIRST_NUM);
          break;
        case "Left":
          displayCommands[2] = getText(regex, SECOND_NUM);
          break;
        case "Right":
          displayCommands[3] = getText(regex, SECOND_NUM);
          break;
      }
    }
    return displayCommands;
  }

  private String getText(String regex, int num) {
    return String.valueOf(Pattern.compile(regex, Pattern.CASE_INSENSITIVE)).split("\\|")[0] + " "
        + num;
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

    for (Entry<String, Pattern> entry : commandsMap.entrySet()) {
      if (SyntaxHelper.isMatch(command, entry.getValue())) {
        return findClass(entry.getKey().toLowerCase());
      }
    }

    throw new CommandDoesNotExistException(
        "Cannot recognize command " + command + " for the given language.");
  }

  private boolean findInSysCommands(String command) {
    Class<SystemCommands> c = SystemCommands.class;
    for (Method m : c.getDeclaredMethods()) {
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
          return method.getName().equalsIgnoreCase(Parser.FUNCTION_METHOD) ? new FunctionStructure(
              commandsClass, method)
              : new CommandStructure(commandsClass, method);
        }
      } catch (ClassNotFoundException e) {
        throw new CompilerException(
            "Internal Error: operation class name defined in CommandType but not implemented.", e);
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

  /**
   * This static class is created to provide information on user input. {@link #isType(String,
   * BasicSyntax)} is able to indicate if the input is one of the types in {@link BasicSyntax}.
   *
   * @author Cady
   */
  public static class SyntaxHelper {

    private SyntaxHelper() {
    }

    /**
     * Evaluate whether a string is of certain {@link BasicSyntax}
     *
     * @param input a string
     * @param type  a {@link BasicSyntax} defined in resource file
     * @return whether this input is of this type
     */
    public static boolean isType(String input, BasicSyntax type) {
      try {
        return getInputType(input).equals(type);
      } catch (InvalidArgumentException e) {
        return false;
      }
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
