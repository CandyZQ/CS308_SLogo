package slogo.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import slogo.exceptions.CommandDoesNotExistException;
import slogo.exceptions.LanguageIsNotSupportedException;

class CommandsMapHelper {
  public static final String RESOURCE_DIR = "resources/languages/";
  public static final String SYNTAX_FILE = "Syntax";

  private List<Languages> supportedLanguages;
  private Languages currnetLan;

  private Map<String, Pattern> commandsMap;
  private Map<String, Pattern> syntaxMap;

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

  String convertUserInput(String command)
      throws CommandDoesNotExistException, LanguageIsNotSupportedException {
    if (commandsMap == null || commandsMap.size() == 0) {
      throw new LanguageIsNotSupportedException(
          "Cannot find any commands of the given language " + currnetLan.name() + ".");
    }
    for (String key : commandsMap.keySet()) {
      if (isMatch(command, commandsMap.get(key))) {
        return key.toLowerCase();
      }
    }
    throw new CommandDoesNotExistException(
        "Cannot recognize command " + command + " for the given language.");
  }

  private boolean isMatch(String command, Pattern pattern) {
    return pattern.matcher(command).matches();
  }
}
