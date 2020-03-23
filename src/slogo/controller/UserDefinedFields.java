package slogo.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import slogo.controller.CommandsMapHelper.SyntaxHelper;
import slogo.controller.listings.BasicSyntax;
import slogo.exceptions.InvalidArgumentException;

/**
 * This class contains all user-defined fields and auto-generated commands. The parser should pass
 * an instance of this class to commands, so that commands have access to all user-defined fields.
 * This class also stores auto-generated commands generated by complicated commands (user-defined
 * commands, for loop, etc.) The parser should check whether extra commands exist and, if yes, add
 * them to the command stack.
 *
 * @author Cady
 * @version 1.1
 * @since 1.1
 */
public class UserDefinedFields {

  // List entries: commands and then all variables in different strings
  private Map<String, List<String>> functions;
  private Map<String, Double> userVars;
  private String extraCommands;
  private boolean hasReturned;

  /**
   * Creates an instance that stores user-defined fields
   */
  public UserDefinedFields() {
    userVars = new HashMap<>();
    functions = new HashMap<>();
    extraCommands = "";
    hasReturned = true;
  }

  String getVar(String key) throws InvalidArgumentException {
    if (!userVars.containsKey(key)) {
      throw new InvalidArgumentException("The variable " + key + " has not been created yet!");
    }
    return userVars.get(key).toString();
  }

  Map<String, List<String>> getFunctions() {
    return Collections.unmodifiableMap(functions);
  }

  Map<String, Double> getUserVars() {
    return Collections.unmodifiableMap(userVars);
  }

  /**
   * Stores a new user-defined variable
   *
   * @param name  the name of the variable
   * @param value the value of the variable
   * @throws InvalidArgumentException if the name fo the variable does not fulfill SLogo standard
   */
  public void putUserVar(String name, Double value) throws InvalidArgumentException {
    if (!SyntaxHelper.isType(name, BasicSyntax.VARIABLE)) {
      throw new InvalidArgumentException("All variables start with : , but " + name + " is not");
    }

    userVars.put(name, value);
  }

  /**
   * Gets the value of a user-defined variable
   *
   * @param name the name of that variable
   * @return the value of that variable
   */
  public Double getUserVar(String name) {
    return userVars.get(name);
  }

  /**
   * Increases a variable by certain amount
   *
   * @param name   the name of that variable
   * @param amount the value to be incremented
   */
  public void incrementVarBy(String name, Double amount) {
    userVars.put(name, userVars.get(name) + amount);
  }

  /**
   * Puts a new user-defined command
   *
   * @param commandName the name of that command
   * @param current     a list, the 0 index is the command itself, rest entries are name of
   *                    variables
   */
  public void putFunction(String commandName, List<String> current) {
    functions.put(commandName, current);
  }

  /**
   * Gets a user-defined command by name
   *
   * @param name the name of that command
   * @return a list, the 0 index is the command itself, rest entries are name of variable
   */
  public List<String> getFunctionContent(String name) {
    return functions.get(name);
  }

  String getExtraCommands() {
    // extra commands should have no [] around
    if (hasReturned) {
      return "";
    }
    hasReturned = true;
    return extraCommands;
  }

  /**
   * Gets (extra) commands set by the previous command
   *
   * @param extraCommands the commands created by the last command
   */
  public void setExtraCommands(String extraCommands) {
    this.extraCommands = extraCommands;
    hasReturned = false;
  }

  boolean isFunction(String name) {
    return functions.containsKey(name);
  }

  int getFuncParaNum(String name) {
    return functions.get(name).size() - 1;
  }
}
