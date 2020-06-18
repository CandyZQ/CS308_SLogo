package slogo.controller.operations;

import slogo.controller.TurtleManager;
import slogo.controller.UserDefinedFields;
import slogo.model.Turtle;

/**
 * This is an abstract parent class for all operations. All operations should be done on {@link
 * #turtle}. {@link #userDefinedFields} and {@link #tm} are here for the program to manage other
 * user-defined variables and commands, and other active turtles.
 *
 * @author Cady
 */
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
