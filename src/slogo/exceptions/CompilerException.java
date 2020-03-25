package slogo.exceptions;

/**
 * Exception is thrown if the parser (compiler) has some kinds of internal errors.
 */
public class CompilerException extends RuntimeException {

  /**
   * Creates a new instance of Compiler Exception
   *
   * @param message the detail message (which is saved for later retrieval by the {@link
   *                #getMessage()} method).
   * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()}
   *                method).  (A {@code null} value is permitted, and indicates that the cause is
   *                nonexistent or unknown.)
   */
  public CompilerException(String message, Throwable cause) {
    super(message, cause);
  }
}
