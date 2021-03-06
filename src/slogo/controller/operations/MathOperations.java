package slogo.controller.operations;

import java.util.Random;
import slogo.controller.TurtleManager;
import slogo.controller.UserDefinedFields;
import slogo.exceptions.InvalidArgumentException;
import slogo.model.Turtle;

/**
 * This class contains exclusively of static method that perform basic math and boolean operations.
 * The methods of this class throws any exceptions that can happen during a math operation.
 *
 * @author Cady
 */
public class MathOperations extends Operations {

  public static final String NONNEGATIVE_ARG = "The argument should be non-negative!";

  public MathOperations(Turtle turtle, UserDefinedFields userDefinedFields, TurtleManager tm) {
    super(turtle, userDefinedFields, tm);
  }

  /**
   * Adds two numbers together
   *
   * @param a the first value
   * @param b the second value
   * @return the result
   */
  private static Double sum(Double a, Double b) {
    return a + b;
  }

  /**
   * Subtracts the second value from the first
   *
   * @param a the first value
   * @param b the second value
   * @return the result
   */
  private static Double difference(Double a, Double b) {
    return a - b;
  }

  /**
   * Multiplies two values
   *
   * @param a the first value
   * @param b the second value
   * @return the result
   */
  private static Double product(Double a, Double b) {
    return a * b;
  }

  /**
   * Divides two Doubles
   *
   * @param a the first Double
   * @param b the second Double
   * @return the result
   */
  private static Double quotient(Double a, Double b) {
    return a / b;
  }

  /**
   * Returns remainder on dividing the values of the second value from the first value
   *
   * @param a the first int
   * @param b the second int
   * @return the result
   */
  private static Integer remainder(Integer a, Integer b) {
    return a % b;
  }


  /**
   * Returns negative of the value
   *
   * @param a the value
   * @return the negative of the value
   */
  private static Double minus(Double a) {
    return 0 - a;
  }

  /**
   * Returns random non-negative number strictly less than max
   *
   * @param max the upper bound of return value
   * @return a non-negative random value smaller than max
   */
  private static Double random(Double max) throws InvalidArgumentException {
    if (max < 0) {
      throw new InvalidArgumentException(NONNEGATIVE_ARG);
    }
    Random rand = new Random();
    return rand.nextDouble() * max;
  }

  /**
   * Returns sine of an angle
   *
   * @param degrees the angle in degree
   * @return the result
   */
  private static Double sin(Double degrees) {
    return Math.sin(Math.toRadians(degrees));
  }

  /**
   * Returns cosine of an angle
   *
   * @param degrees the angle in degree
   * @return the result
   */
  private static Double cos(Double degrees) {
    return Math.cos(Math.toRadians(degrees));
  }

  /**
   * Returns tangent of an angle
   *
   * @param degrees the angle in degree
   * @return the result
   */
  private static Double tan(Double degrees) {
    return Math.tan(Math.toRadians(degrees));
  }

  /**
   * Returns arctangent of an angle
   *
   * @param degrees the angle in degree
   * @return the result
   */
  private static Double atan(Double degrees) {
    return Math.atan(Math.toRadians(degrees));
  }

  /**
   * Returns natural log of an angle
   *
   * @param degrees the angle in degree
   * @return the result
   */
  private static Double log(Double degrees) {
    return Math.log(degrees);
  }

  /**
   * Returns base raised to the power of the exponent
   *
   * @param base     the base to be raised
   * @param exponent the exponent that will be raised to
   * @return the result
   */
  private static Double pow(Double base, Double exponent) {
    return Math.pow(base, exponent);
  }

  /**
   * Reports the number PI
   *
   * @return the PI value
   */
  private static Double pi() {
    return Math.PI;
  }
}
