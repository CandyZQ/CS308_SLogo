package APIs;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import slogo.controller.listings.MovingObjectProperties;
import slogo.exceptions.CommandDoesNotExistException;
import slogo.exceptions.InvalidArgumentException;
import slogo.exceptions.LanguageIsNotSupportedException;
import slogo.exceptions.WrongCommandFormatException;

/**
 * This is the backend external API that communicates with the main class for this slogo project.
 * <p></p>
 * This class takes user inputs in a specific language as a String and interpret it as one of the
 * pre-set commands, which give specific instructions of on how the backend states need to be
 * updated. This class then correspondingly updates backend states, and returns the current state
 * and other necessary values. Each state is stored in a {@link Map}, and a {@link Queue} of maps
 * will be returned. In most cases the {@link Queue} contains only one {@link Map}, but multiple
 * {@code Maps} will be returned when a {@code FOR} command is executed.
 * <p></p>
 * Note, the language the user uses should be set before any user inputs are read, and then all user
 * inputs will be interpreted in that language until the language is reset.
 * <p></p>
 * User inputs will be checked to see whether commands are recognizable, and if yes, whether the
 * format fulfills the format of that command. Multiple exceptions can be thrown in this process.
 *
 * @author Cady
 */
public interface BackEndExternalMainAPI {

  /**
   * Sets the desired language commands the user passed will be interpreted in. This method can be
   * called multiple times for the user to change language.
   *
   * @param language the language user inputs will be in
   */
  String[] setLanguage(String language) throws LanguageIsNotSupportedException;

  /**
   * Accepts one command from user input and updates the backend states accordingly
   *
   * @param command the command that the user inputs
   * @return a {@code Queue} of {@code Map} that represents states of the backend
   */
  Queue<Map<MovingObjectProperties, Object>> execute(String command)
      throws CommandDoesNotExistException, LanguageIsNotSupportedException, WrongCommandFormatException, InvalidArgumentException;

  /**
   * Loads and runs a script of commands
   *
   * @param filename the file in which the script in
   * @return a {@link Queue} of status of existed turtles
   * @throws IOException                     if fails to open the file
   * @throws WrongCommandFormatException     if the command name exists but the format is wrong
   * @throws InvalidArgumentException        if the argument type of the command is not correct
   * @throws CommandDoesNotExistException    if the command is not defined
   * @throws LanguageIsNotSupportedException if the language resource file does not exist
   */
  Queue<Map<MovingObjectProperties, Object>> runScript(String filename)
      throws IOException, WrongCommandFormatException, InvalidArgumentException, LanguageIsNotSupportedException, CommandDoesNotExistException;


    /**
     * Gets all variables users have created.
     *
     * @return a Map whose key is the variable names and value is the value
     */
  Map<String, Double> gerUserVars();

  /**
   * Gets all user-created commands
   *
   * @return a Map whose key is the function names, value is a list of string. The 0 index is a long
   * string of commands in this function, and the rest are variables defined with this function.
   */
  Map<String, List<String>> getFunctions();
}
