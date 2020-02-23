package slogo.controller;

import slogo.model.Turtle;

class TurtleCommands {
  Turtle turtle;

  public TurtleCommands(Turtle turtle) {
    this.turtle = turtle;
  }

  public void forward(Integer pixels) {
    turtle.moveDistance(pixels);
  }

}
