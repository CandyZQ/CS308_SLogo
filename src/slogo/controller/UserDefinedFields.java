package slogo.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import slogo.controller.CommandsMapHelper.SyntaxHelper;
import slogo.controller.listings.BasicSyntax;
import slogo.exceptions.InvalidArgumentException;

public class UserDefinedFields {

  // List entries: commands and then all variables in different strings
  private Map<String, List<String>> functions;
  private Map<String, Double> userVars;
  private String extraCommands;
  private boolean hasReturned;

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

  public void putUserVar(String name, Double value) throws InvalidArgumentException {
    if (!SyntaxHelper.isType(name, BasicSyntax.VARIABLE)) {
      throw new InvalidArgumentException("All variables start with : , but " + name + " is not");
    }

    userVars.put(name, value);
  }

  public Double getUserVar(String name) {
    return userVars.get(name);
  }

  public void incrementVarBy(String name, Double amount) {
    userVars.put(name, userVars.get(name) + amount);
  }

  public void putFunction(String commandName, List<String> current) {
    functions.put(commandName, current);
  }

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
