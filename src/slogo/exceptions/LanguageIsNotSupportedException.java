package slogo.exceptions;

public class LanguageIsNotSupportedException extends Exception {

  public LanguageIsNotSupportedException(String message, Throwable cause) {
    super(message, cause);
  }

  public LanguageIsNotSupportedException(String message) {
    super(message);
  }
}
