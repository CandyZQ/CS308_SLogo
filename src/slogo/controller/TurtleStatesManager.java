package slogo.controller;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Queue;
import slogo.controller.listings.MovingObjectProperties;
import slogo.model.Turtle;

public class TurtleStatesManager {
  private Queue<EnumMap<MovingObjectProperties, Object>> turtleStates;

  public TurtleStatesManager() {
    turtleStates = new LinkedList<>();
  }

  public Queue<EnumMap<MovingObjectProperties, Object>> getTurtleStates() {
    return turtleStates;
  }

  void addStates(Turtle turtle) {
    turtleStates.add(turtle.getState().clone());
  }
}
