package slogo.controller;

import slogo.model.Turtle;

class TurtleCommands {

  Turtle turtle;

  TurtleCommands(Turtle turtle) {
    this.turtle = turtle;
  }

  void forward(Double pixels) {
    turtle.moveDistance(pixels);
  }

  void backward(Double pixels) {
    turtle.moveDistance(-pixels);
  }

  void left(Double degrees) {
    turtle.setHeading((Double) turtle.getState().get(MovingObjectProperties.HEADING) + degrees);
  }

  void right(Double degrees) {
    left(-degrees);
  }

  void setHeading(Double degrees) {
    turtle.setHeading(degrees);
  }

  void setTowards(Double x, Double y) {
    turtle.setHeading(x, y);
  }

  void setPosition(Double x, Double y) {
    turtle.setHeading(x, y);
    turtle.setCoordinates(x, y);
  }

  void penDown() {
    turtle.getState().put(MovingObjectProperties.PEN, 1);
    turtle.getState().put(MovingObjectProperties.RETURN_VALUE, 1);
  }

  void penUp() {
    turtle.getState().put(MovingObjectProperties.PEN, 0);
    turtle.getState().put(MovingObjectProperties.RETURN_VALUE, 0);
  }

  void showTurtle() {
    turtle.getState().put(MovingObjectProperties.VISIBILITY, 1);
    turtle.getState().put(MovingObjectProperties.RETURN_VALUE, 1);
  }

  void hideTurtle() {
    turtle.getState().put(MovingObjectProperties.VISIBILITY, 0);
    turtle.getState().put(MovingObjectProperties.RETURN_VALUE, 0);
  }

  void home() {
    turtle.setCoordinates(0, 0);
  }

  void clearScreen() {
    // TODO: remove screen trails
    turtle.setCoordinates(0, 0);
  }
}
