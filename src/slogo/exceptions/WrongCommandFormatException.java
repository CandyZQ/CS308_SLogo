package slogo.exceptions;

public class WrongCommandFormatException extends Exception{

  public WrongCommandFormatException(String message, Throwable cause) {
    super(message, cause);
  }

  public WrongCommandFormatException(String message) {
    super(message);
  }

  public WrongCommandFormatException(Throwable cause) {
    super(cause);
  }
}
