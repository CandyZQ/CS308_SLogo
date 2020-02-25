package slogo.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import slogo.controller.operations.TurtleCommands;
import slogo.exceptions.InvalidArgumentException;
import slogo.exceptions.WrongCommandFormatException;
import slogo.model.Turtle;

class CommandStructure {

  Class<?> c;
  Method m;
  List<Object> paras;
  List<Class<?>> paraTypes;
  int numOfPara;

  public CommandStructure(Class<?> c, Method m, int numOfPara) {
    this.c = c;
    this.m = m;
    paras = new ArrayList<>();
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
    return numOfPara != paras.size();
  }

  void addPara(String s) throws InvalidArgumentException {
    try {
      paras.add(getNextParaType().getConstructor(String.class).newInstance(s));
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new InvalidArgumentException("Exception occurred when converting argument " + s
          + " to the correct type of method call. Check whether arguments are of the correct type!");
    }
  }

  String getName() {
    return m.getName();
  }

  Object execute(Turtle turtle)
      throws InvalidArgumentException, WrongCommandFormatException {
    if (needMoreParas()) {
      throw new WrongCommandFormatException(
          "Internal Error: Still need more parameters before executing");
    }

    Object res = null;
    try {
      res = m.invoke(new TurtleCommands(turtle), paras.toArray(new Object[0]));
    } catch (IllegalArgumentException e) {
      throw new InvalidArgumentException(e);
    } catch (IllegalAccessException e) {
      System.out.println("The method " + m.getName() + " called is not accessible");
    } catch (InvocationTargetException e) {
      // TODO: do something?
    }


    return res != null? res: turtle.getState().get(MovingObjectProperties.RETURN_VALUE);
  }
}
