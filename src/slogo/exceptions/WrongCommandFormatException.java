package slogo.exceptions;

/**
 * Exception is thrown if the user input does not format a command correctly. For example, it has
 * more parameters than needed.
 */
public class WrongCommandFormatException extends Exception {

  /**
   * Creates a new instance of WrongCommandFormat
   *
   * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
   *              (A {@code null} value is permitted, and indicates that the cause is nonexistent or
   *              unknown.)
   */
  public WrongCommandFormatException(Throwable cause) {
    super(cause);
  }

  /**
   * Creates a new instance of WrongCommandFormatException
   *
   * @param message the detail message (which is saved for later retrieval by the {@link
   *                #getMessage()} method).
   */
  public WrongCommandFormatException(String message) {
    super(message);
  }

  /**
   * Creates a new instance of WrongCommandFormatException
   *
   * @param message the detail message (which is saved for later retrieval by the {@link
   *                #getMessage()} method).
   * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()}
   *                method).  (A {@code null} value is permitted, and indicates that the cause is
   *                nonexistent or unknown.)
   */
  public WrongCommandFormatException(String message, Throwable cause) {
    super(message, cause);
  }
}
