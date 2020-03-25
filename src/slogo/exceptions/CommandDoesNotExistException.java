package slogo.exceptions;

/**
 * Exception is thrown if the user input does not corresponds to any know commands.
 */
public class CommandDoesNotExistException extends Exception {

  /**
   * Creates a new instance of CommandDoesNotExistException
   *
   * @param message the detail message (which is saved for later retrieval by the {@link
   *                #getMessage()} method).
   */
  public CommandDoesNotExistException(String message) {
    super(message);
  }
}
