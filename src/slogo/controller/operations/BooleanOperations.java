package slogo.controller.operations;

import java.util.List;
import java.util.Map;
import slogo.model.Turtle;

public class BooleanOperations {

  public BooleanOperations(Turtle turtle, Map<String, Double> map, Map<String, List<String>> functions) {

  }
  /**
   * @param a = expr1
   * @param b = expr2
   * @return returns 1 if the value of expr1 is strictly less than the value of expr2, otherwise 0
   */
  public Integer lessThan(Double a, Double b) {
    if (a < b) {
      return 1;
    } else {
      return 0;
    }
  }

  /**
   *
   * @param a = expr1
   * @param b = expr2
   * @return returns 1 if the value of expr1 is strictly greater than the value of expr2, otherwise 0
   */
  public Integer greaterThan(Double a, Double b) {
    if (a > b) {
      return 1;
    } else {
      return 0;
    }
  }

  /**
   *
   * @param a = expr1
   * @param b = expr2
   * @return returns 1 if the value of expr1 and the value of expr2 are equal, otherwise 0
   */
  public Integer equal(Double a, Double b) {
    if (a.equals(b)) {
      return 1;
    } else {
      return 0;
    }
  }

  /**
   *
   * @param a = expr1
   * @param b = expr2
   * @return returns 1 if the value of expr1 and the value of expr2 are not equal, otherwise 0
   */
  public Integer notEqual(Double a, Double b) {
    if (!a.equals(b)) {
      return 1;
    } else {
      return 0;
    }
  }

  /**
   *
   * @param a = test1
   * @param b = test2
   * @return returns 1 if test1 and test2 are non-zero, otherwise 0
   */
  public Integer and(Double a, Double b) {
    if (a.equals(0d) || b.equals(0d)) {
      return 0;
    } else {
      return 1;
    }
  }

  /**
   *
   * @param a = test1
   * @param b = test2
   * @return returns 1 if test1 or test2 are non-zero, otherwise 0
   */
  public Integer or(Double a, Double b) {
    if (!a.equals(0d)|| !b.equals(0d)) {
      return 1;
    } else {
      return 0;
    }
  }

  /**
   *
   * @param a = test
   * @return returns 1 if test is 0 and 0 if test is non-zero
   */
  public Integer not(Double a) {
    if (a.equals(0d)) {
      return 1;
    } else {
      return 0;
    }
  }
}
