package slogo.exceptions;

/**
 * Exception is thrown if the user input language is not known to the system
 */
public class LanguageIsNotSupportedException extends Exception {

  /**
   * Creates a new instance of LanguageIsNotSupportedException
   *
   * @param message the detail message (which is saved for later retrieval by the {@link
   *                #getMessage()} method).
   * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()}
   *                method).  (A {@code null} value is permitted, and indicates that the cause is
   *                nonexistent or unknown.)
   */
  public LanguageIsNotSupportedException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Creates a new instance of LanguageIsNotSupportedException
   *
   * @param message the detail message (which is saved for later retrieval by the {@link
   *                #getMessage()} method).
   */
  public LanguageIsNotSupportedException(String message) {
    super(message);
  }
}
