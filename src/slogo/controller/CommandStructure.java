package slogo.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import slogo.exceptions.InvalidArgumentException;
import slogo.exceptions.WrongCommandFormatException;

class CommandStructure {

  Class<?> c;
  Method m;
  List<Object> paras;
  List<Class<?>> paraTypes;
  int numOfPara;

  public CommandStructure(Class<?> c, Method m, int numOfPara) {
    this.c = c;
    this.m = m;
    initializeParaTypes();
    this.numOfPara = numOfPara;
  }

  private void initializeParaTypes() {
    paraTypes = Arrays.asList(m.getParameterTypes());
  }

  Class<?> getNextParaType() {
    if (paras.size() == numOfPara) {
      return null;
    }
    return paraTypes.get(paras.size());
  }

  boolean needMoreParas() {
    return numOfPara == paras.size();
  }

  public void addPara(String s) throws InvalidArgumentException {
    try {
      paras.add(getNextParaType().getConstructor(String.class).newInstance(s));
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new InvalidArgumentException("Exception occurred when converting argument " + s
          + " to the correct type of method call. Check whether arguments are of the correct type!");
    }
  }

  public String getName() {
    return m.getName();
  }

  private void checkCommandFormat(String[] parameters, Method method)
      throws WrongCommandFormatException {
    int actualParaNum = parameters.length;
    int desireParaNum = method.getParameterCount();
    if (actualParaNum != desireParaNum) {
      throw new WrongCommandFormatException(
          "Expecting " + desireParaNum + " parameters, but found " + actualParaNum
              + ".");
    }
  }

}
