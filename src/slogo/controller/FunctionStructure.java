package slogo.controller;

import java.lang.reflect.Method;

/**
 * This class is a modification from {@link slogo.controller.CommandStructure} so that it supports
 * the call of user-defined commands. Specifically, this class has a different way to determine the
 * number of parameters needed, get type of parameters, and a different getter that feeds all these
 * parameters into Reflection API method caller.
 *
 * @author Cady
 * @version 1.1
 * @since 1.1
 * @see CommandStructure
 */
public class FunctionStructure extends CommandStructure {

  /**
   * Creates a new instance of function structure that manages a function
   *
   * @param c the class in which this method will be invoked at
   * @param m the method that will be called
   */
  public FunctionStructure(Class<?> c, Method m) {
    super(c, m);
  }

  void setParaNum(int funcParaNum) {
    numOfPara = funcParaNum;
  }

  @Override
  Class<?> getNextParaType() {
    if (paras.size() == numOfPara) {
      return null;
    }
    if (paras.size() == 0) {
      return String.class;
    }
    return Integer.class;
  }

  @Override
  Object[] getMethodInvokePara() {
    Object[] par = new Object[1];
    par[0] = paras;
    return par;
  }
}
