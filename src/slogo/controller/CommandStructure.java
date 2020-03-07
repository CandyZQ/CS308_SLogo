package slogo.controller;

import static slogo.controller.listings.BasicSyntax.CONSTANT;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.DrbgParameters.Instantiation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import slogo.controller.CommandsMapHelper.SyntaxHelper;
import slogo.controller.listings.MovingObjectProperties;
import slogo.exceptions.CompilerException;
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
      if (getNextParaType() == Integer.class && s.contains(".")) {
        s = s.substring(0, s.indexOf("."));
      }
      paras.add(getNextParaType().getConstructor(String.class).newInstance(s));
    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
      throw new InvalidArgumentException("Exception occurred when converting argument " + s
          + " to the correct type of method call. Check whether arguments are of the correct type!",
          e);
    }
  }

  String getName() {
    return m.getName();
  }

  Object[] getMethodInvokePara() {
    return paras.toArray(new Object[0]);
  }

  Object execute(TurtleManager tm, UserDefinedFields userDefinedFields, Turtle t)
      throws InvalidArgumentException, WrongCommandFormatException {
    if (needMoreParas()) {
      throw new WrongCommandFormatException(
          "Internal Error: Still need more parameters before executing");
    }

    Object res;
    try {
      m.setAccessible(true);
      res = m.invoke(c.getConstructor(Turtle.class, UserDefinedFields.class, TurtleManager.class)
          .newInstance(t, userDefinedFields, tm), getMethodInvokePara());
    } catch (IllegalArgumentException e) {
      throw new InvalidArgumentException(e);
    } catch (IllegalAccessException e) {
      throw new CompilerException("The method " + m.getName() + " called is not accessible", e);
    } catch (InvocationTargetException | NoSuchMethodException | InstantiationException e) {
      throw new CompilerException(e.getClass() + " occurred while running the command.", e);
    }

    storeTurtleStates(res, tm, t);
    return res != null ? res : t.getState().get(MovingObjectProperties.RETURN_VALUE);
  }

  private void storeTurtleStates(Object returnVal, TurtleManager tm, Turtle t)
      throws InvalidArgumentException {
    if (returnVal != null && SyntaxHelper.isType(returnVal.toString(), CONSTANT)) {
      tm.putReturnValue(returnVal, t);
    }
    tm.addStates(t);
  }
}
