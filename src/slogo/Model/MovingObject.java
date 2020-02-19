package slogo.Model;

import java.util.Map;

/**
 * This is the backend internal API for the slogo project.
 * <p></p>
 * This class stores necessary state information, multiple operations that changes some or all of
 * the state information, and a getter methods that returns all state information. The state
 * information includes:
 * <ul>
 *   <li>the x position</li>
 *   <li>the y position</li>
 *   <li>the direction in which this object is heading</li>
 *   <li>the visibility</li>
 * </ul>
 */
public interface MovingObject<K> {

  /**
   * Gets the distance this {@link MovingObject} travelled when the state changes
   *
   * @return the distance travelled
   */
  K getDistanceTravelled();


  /**
   * Moves this object forward/backward certain distance
   *
   * @param distance the distance to be moved, positive value moves this object forward while
   *                 negative value moves it backward
   * @return the distance moved
   */
  K moveDistance(K distance);

  /**
   * Sets the new heading of this object
   *
   * @param angle the new heading, which assumes north of (0,0) is 0 degree and turning clockwise
   *              from NORTH increases angle
   * @return the number of degree moved
   */
  K setHeading(K angle);

  /**
   * Sets the position of this object
   *
   * @param x the x position
   * @param y the y position
   * @return the distance moved
   */
  K setCoordinates(K x, K y);


  /**
   * Returns this object to the center of the screen and sets heading ot 0
   */
  void reset();

  /**
   * Gets all state information of this object as a {@link Map}
   *
   * @return a {@link Map} containing all state information
   */
  Map<MovingObjectProperties, K> getState();

}