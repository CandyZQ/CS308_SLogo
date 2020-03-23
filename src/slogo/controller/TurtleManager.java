package slogo.controller;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import slogo.controller.listings.MovingObjectProperties;
import slogo.exceptions.InvalidArgumentException;
import slogo.model.Turtle;

/**
 * This class manages all active and inactive turtles when multiple turtles are presented. The
 * states of turtles should be updated each time {@code ask [ ]} is called by a user.
 *
 * @author Cady
 * @version 1.1
 * @since 1.1
 * @see Turtle
 */
public class TurtleManager {

  public static final int TURTLE_NUM_MAX = 500;

  private Turtle[] turtles;
  private Set<Turtle> activeTurtles;
  private Set<Integer> turtlesExisted;
  private Queue<Map<MovingObjectProperties, Object>> turtleStates;

  /**
   * Creates a new instance of turtle manager and manages multiple turtles
   *
   * @param turtleNumber  the number of turtles that will be on the screen when the program starts
   */
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

  Queue<Map<MovingObjectProperties, Object>> getTurtleStates() {
    return new LinkedList<>(turtleStates);
  }

  void addStates(Turtle turtle) {
    turtleStates.add(turtle.getState().clone());
  }

  /**
   * Sets the turtle of some index active. Creates a new turtle if that turtle does not exist
   *
   * @param s a String of that index
   * @throws InvalidArgumentException if {@code s} is not a valid index (Integer)
   */
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

  /**
   * Gets a list of active turtles
   *
   * @return a list of active {@link Turtle}
   */
  public Set<Turtle> getTurtles() {
    return Collections.unmodifiableSet(activeTurtles);
  }

  /**
   * Resets active turtles so that no turtle is active anymore
   */
  public void resetActiveTurtles() {
    activeTurtles = new HashSet<>();
  }

  void putReturnValue(Object returnVal, Turtle t) {
    Integer id = (Integer) t.getState().get(MovingObjectProperties.ID);
    turtles[id].getState().put(MovingObjectProperties.RETURN_VALUE, returnVal);
  }
}
