package slogo.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import slogo.exceptions.InvalidArgumentException;

public class UserDefinedFields {

  // List entries: variable and then command
  private Map<String, List<String>> functions;
  private Map<String, Double> userVars;

  public UserDefinedFields() {
    userVars = new HashMap<>();
    functions = new HashMap<>();
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
}
