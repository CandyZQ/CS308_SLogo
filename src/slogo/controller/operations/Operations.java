package slogo.controller.operations;

import slogo.controller.TurtleManager;
import slogo.controller.UserDefinedFields;
import slogo.model.Turtle;

public abstract class Operations {
  Turtle turtle;
  UserDefinedFields userDefinedFields;
  TurtleManager tm;

  public Operations(Turtle turtle, UserDefinedFields userDefinedFields, TurtleManager tm) {
    this.turtle = turtle;
    this.userDefinedFields = userDefinedFields;
    this.tm = tm;
  }
}
