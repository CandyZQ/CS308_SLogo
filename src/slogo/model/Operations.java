package slogo.model;

import java.util.Random;
import slogo.exceptions.InvalidArgumentException;

/**
 * This class contains exclusively of static method that perform basic math and boolean operations.
 * The methods of this class throws any exceptions that can happen during a math operation.
 */
public interface Operations {

  static final String NONNEGATIVE_ARG = "The argument should be non-negative!";

  /**
   * Adds two numbers together
   *
   * @param a the first value
   * @param b the second value
   * @return the result
   */
  static double sum(double a, double b) {
    return a + b;
  }

  /**
   * Subtracts the second value from the first
   *
   * @param a the first value
   * @param b the second value
   * @return the result
   */
  static double difference(double a, double b) {
    return a - b;
  }

  /**
   * Multiplies two values
   *
   * @param a the first value
   * @param b the second value
   * @return the result
   */
  static double product(double a, double b) {
    return a * b;
  }

  /**
   * Divides two doubles
   *
   * @param a the first double
   * @param b the second double
   * @return the result
   */
  static double quotient(double a, double b) {
    return a / b;
  }

  /**
   * Returns remainder on dividing the values of the second value from the first value
   *
   * @param a the first int
   * @param b the second int
   * @return the result
   */
  static double remainder(int a, int b) {
    return a % b;
  }


  /**
   * Returns negative of the value
   *
   * @param a the value
   * @return the negative of the value
   */
  static double minus(double a) {
    return 0 - a;
  }

  /**
   * Returns random non-negative number strictly less than max
   *
   * @param max the upper bound of return value
   * @return a non-negative random value smaller than max
   */
  static double random(double max) throws InvalidArgumentException{
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
  static double sin(double degrees) {
    return Math.sin(Math.toRadians(degrees));
  }

  /**
   * Returns cosine of an angle
   *
   * @param degrees the angle in degree
   * @return the result
   */
  static double cos(double degrees) {
    return Math.cos(Math.toRadians(degrees));
  }

  /**
   * Returns tangent of an angle
   *
   * @param degrees the angle in degree
   * @return the result
   */
  static double tan(double degrees) {
    return Math.tan(Math.toRadians(degrees));
  }

  /**
   * Returns arctangent of an angle
   *
   * @param degrees the angle in degree
   * @return the result
   */
  static double atan(double degrees) {
    return Math.atan(Math.toRadians(degrees));
  }

  /**
   * Returns natural log of an angle
   *
   * @param degrees the angle in degree
   * @return the result
   */
  static double log(double degrees) {
    return Math.log(degrees);
  }

  /**
   * Returns base raised to the power of the exponent
   *
   * @param base     the base to be raised
   * @param exponent the exponent that will be raised to
   * @return the result
   */
  static double pow(double base, double exponent) {
    return Math.pow(base, exponent);
  }

  /**
   * Reports the number PI
   *
   * @return the PI value
   */
  static double pi() {
    return Math.PI;
  }
}
