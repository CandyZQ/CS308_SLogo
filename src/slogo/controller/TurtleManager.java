package slogo.controller;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import slogo.controller.listings.MovingObjectProperties;
import slogo.exceptions.InvalidArgumentException;
import slogo.model.Turtle;

public class TurtleManager {
  public static final int TURTLE_NUM_MAX = 50;
  private Turtle[] turtles;
  private Set<Turtle> activeTurtles;
  private Set<Integer> turtlesExisted;
  private Queue<EnumMap<MovingObjectProperties, Object>> turtleStates;

  public TurtleManager(int turtleNumber) {
    turtles = new Turtle[TURTLE_NUM_MAX];
    activeTurtles = new HashSet<>();
    turtlesExisted = new HashSet<>();
    for (int i = 0; i < turtleNumber; i++) {
      turtles[i] = new Turtle(i);
      turtlesExisted.add(i);
      activeTurtles.add(turtles[i]);
    }

    turtleStates = new LinkedList<>();
  }

  public Queue<EnumMap<MovingObjectProperties, Object>> getTurtleStates() {
    return turtleStates;
  }

  void addStates(Turtle turtle) {
    turtleStates.add(turtle.getState().clone());
  }

  public void setActiveTurtles(String s) throws InvalidArgumentException {
    int index;
    try {
      index = Integer.parseInt(s);
    } catch (NumberFormatException e) {
      throw new InvalidArgumentException("Turtle id " + s + " not recognized.");
    }

    if (!turtlesExisted.contains(index)) {
      turtles[index] = new Turtle(index);
      turtlesExisted.add(index);
    }
    activeTurtles.add(turtles[index]);
  }

  void cleanState() {
    turtleStates = new LinkedList<>();
  }

  public Set<Turtle> getTurtles() {
    return activeTurtles;
  }

  public void resetActiveTurtles() {
    activeTurtles = new HashSet<>();
  }

  void putReturnValue(String returnVal, Turtle t) {
    Integer id = (Integer) t.getState().get(MovingObjectProperties.ID);
    turtles[id].getState().put(MovingObjectProperties.ID, returnVal);
  }
}
