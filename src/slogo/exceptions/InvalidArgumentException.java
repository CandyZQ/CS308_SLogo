package slogo.exceptions;

public class InvalidArgumentException extends Exception{

  public InvalidArgumentException(Throwable cause) {
    super(cause);
  }
  public InvalidArgumentException(String message) {
    super(message);
  }

  public InvalidArgumentException(String message, Throwable cause) {
    super(message, cause);
  }
}
