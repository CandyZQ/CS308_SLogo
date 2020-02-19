package slogo.Model;

public interface OperationsAble {
  /**
   *
   * Model.Operations.java will provide the below methods to Controller to perform mathematical
   * operations and logic operations demanded by the execution of commands.
   *
   */
  static double sum(double a, double b)
  static double difference(double a, double b)
  static double product(double a, double b)
  double quotient(double a, double b)
  double remainder(double a, double b)
  double minus(double a)
  double random(double max)
  double sin(double degrees)
  double cos(double degrees)
  double tan(degrees)
  double atan(degrees)
  double log(degrees)
  pow(degrees)
  double pi()
}
