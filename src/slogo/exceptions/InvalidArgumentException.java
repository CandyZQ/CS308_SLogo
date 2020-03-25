package slogo.exceptions;

/**
 * Exception is thrown if the user input does not fulfill the command's requirement for parameters
 */
public class InvalidArgumentException extends Exception {

  /**
   * Creates a new instance of InvalidArgumentException
   *
   * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
   *              (A {@code null} value is permitted, and indicates that the cause is nonexistent or
   *              unknown.)
   */
  public InvalidArgumentException(Throwable cause) {
    super(cause);
  }

  /**
   * Creates a new instance of InvalidArgumentException
   *
   * @param message the detail message (which is saved for later retrieval by the {@link
   *                #getMessage()} method).
   */
  public InvalidArgumentException(String message) {
    super(message);
  }

  /**
   * Creates a new instance of InvalidArgumentException
   *
   * @param message the detail message (which is saved for later retrieval by the {@link
   *                #getMessage()} method).
   * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()}
   *                method).  (A {@code null} value is permitted, and indicates that the cause is
   *                nonexistent or unknown.)
   */
  public InvalidArgumentException(String message, Throwable cause) {
    super(message, cause);
  }
}
