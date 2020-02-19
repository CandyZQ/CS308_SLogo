package slogo.Controller;

import java.util.Map;
import java.util.Queue;

/**
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
 */
public interface BackEndExternalAPI {

  /**
   * Sets the desired language commands the user passed will be interpreted in. This method can be
   * called multiple times for the user to change language.
   *
   * @param language the language user inputs will be in
   */
  void setLanguage(String language);

  /**
   * Accepts one command from user input and updates the backend states accordingly
   *
   * @param command the command that the user inputs
   * @return a {@code Queue} of {@code Map} that represents states of the backend
   */
  Queue<Map> execute(String command);
}
