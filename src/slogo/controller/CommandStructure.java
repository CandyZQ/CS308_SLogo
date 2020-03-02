package slogo.controller;

import static slogo.controller.listings.BasicSyntax.CONSTANT;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import slogo.controller.CommandsMapHelper.SyntaxHelper;
import slogo.controller.listings.MovingObjectProperties;
import slogo.exceptions.InvalidArgumentException;
import slogo.exceptions.WrongCommandFormatException;
import slogo.model.Turtle;

class CommandStructure {

  Class<?> c;
  Method m;
  List<Object> paras;
  List<Class<?>> paraTypes;
  int numOfPara;

  public CommandStructure(Class<?> c, Method m) {
    this.c = c;
    this.m = m;
    paras = new ArrayList<>();
    initializeParaTypes();
    numOfPara = m.getParameterCount();
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

  Object execute(TurtleManager tm, UserDefinedFields userDefinedFields)
      throws InvalidArgumentException, WrongCommandFormatException {
    Set<Turtle> turtles = tm.getTurtles();
    if (needMoreParas()) {
      throw new WrongCommandFormatException(
          "Internal Error: Still need more parameters before executing");
    }

    Object res = null;
    for (Turtle t: turtles) {
      res = singleTurtleExecute(t, userDefinedFields, tm);
      storeTurtleStates(res.toString(), tm, t);
    }

    return res;
  }

  private Object singleTurtleExecute(Turtle turtle, UserDefinedFields userDefinedFields, TurtleManager tm)
      throws InvalidArgumentException {
    Object res = null;
    try {
      res = m.invoke(c.getConstructor(Turtle.class, UserDefinedFields.class, TurtleManager.class)
          .newInstance(turtle, userDefinedFields, tm), paras.toArray(new Object[0]));
    } catch (IllegalArgumentException e) {
      throw new InvalidArgumentException(e);
    } catch (IllegalAccessException e) {
      System.out.println("The method " + m.getName() + " called is not accessible");
    } catch (InvocationTargetException | NoSuchMethodException | InstantiationException e) {
      // TODO: do something?
    }

    return res != null ? res : turtle.getState().get(MovingObjectProperties.RETURN_VALUE);
  }

  private void storeTurtleStates(String returnVal, TurtleManager tm, Turtle t) {
    try {
      if (SyntaxHelper.isType(returnVal, CONSTANT)) {
        tm.putReturnValue(returnVal, t);
      }
//    } catch (InvalidArgumentException e) {
//      String[] ss = returnVal.split(" ");
//      for (int i = ss.length - 1; i >= 0; i--) {
//        commandsLeft.push(ss[i]);
//      }
//    }
    } catch (Exception e) {

    }
    tm.addStates(t);
  }

}
