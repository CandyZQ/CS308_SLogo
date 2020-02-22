/**
 * @author Sarah Gregorich, Cady Zhou
 */

package slogo.model;

public class Turtle implements MovingObject {
    private static TurtleState myState;
    private static double myDistanceTravelled;

    /**
     * Turtle.java is updated from Controller according to commands, and returns TurtleState object as needed for View to update display/track history.
     */
    public Turtle() {
        myState = new TurtleState();
        myDistanceTravelled = 0;
    }

    /**
     * Returns x-coordinate of Turtle's current state as double value.
     * Assumes (0,0) is center of grid.
     * @return double value of x-coordinate
     */
    public static double getX() {
        return myState.getX();
    }

    /**
     * Returns y-coordinate of Turtle's current state as double value.
     * Assumes (0,0) is center of grid.
     * @return double value of y-coordinate
     */
    public static double getY() {
        return myState.getY();
    }

    /**
     * Returns heading of Turtle's current state in degrees.
     * Assumes north of (0,0) is 0 degrees.
     * Turning clockwise from NORTH increases angle such that:
     *      Heading EAST from north is 90 degrees.
     *      Heading WEST from north is 270 degrees.
     * @return double value of current heading in degrees
     */
    public static double getHeading() {
        return myState.getHeading();
    }

    /**
     * Returns pen status of Turtle's current state as boolean.
     * Result is true if pen is down to write, false otherwise.
     * @return boolean value indicating if pen is down to write.
     */
    public static boolean isPenDown() {
        return myState.isPenDown();
    }

    /**
     * Returns visibility of Turtle's current state as boolean.
     * Result is true if turtle is currently visible, false otherwise.
     * @return boolean value indicating if turtle is currently visible.
     */
    public static boolean isVisible() {
        return myState.isVisible();
    }

    /**
     * Returns total path length Turtle has travelled since Turtle created.
     * This is not distance from starting point but rather path length:
     *      i.e. if the Turtle went forward 50, back 50, this returns 100, not 0.
     * @return double value of total path length Turtle has travelled.
     */
    public static double getDistanceTravelled() {
        return myDistanceTravelled;
    }

    /**
     * Sets (x,y) coordinates of Turtle's current state.
     * Assumes grid center (0,0).
     * @Param x is new x-location of Turtle
     * @param y is new y-location of Turtle
     */
    public static void setCoordinates(double x, double y) {
        TurtleState.setCoordinates(x,y);
    }

    /**
     * Sets ABSOLUTE heading of Turtle's current state in degrees.
     * Assumes north of (0,0) is 0 degrees.
     * Turning clockwise from NORTH increases angle such that:
     *      Heading EAST is 90 degrees.
     *      Heading WEST is 270 degrees.
     * ABSOLUTE - if current heading is 90 and new heading is 95, the total rotation is 5, not 95
     * @param angle is new angle heading of turtle
     * @return absolute value of number of degrees moved
     */
    public static double setHeading(double angle) {
        if (angle < 0 || angle > 360) {
            System.out.println("Angle out of bounds"); // TODO Exception handling for invalid inputs
        }
        TurtleState.setHeading(angle);
        return Math.abs(TurtleState.getHeading() - angle);
    }

    /**
     * Updates Turtle heading to face (x,y)
     * Does this by calculating angle then called public double setheading(double angle)
     * @param (x,y) is point turtle has rotated to face.
     * @return absolute value of number of degrees moved
     */
    public static double setHeading(double x, double y) {

    }
}
