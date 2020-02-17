/**
 * @author Sarah Gregorich, Cady Zhou
 */

package slogo.Model;

public class TurtleState {

    private static double myX;
    private static double myY;
    private static double myHeading;
    private static boolean myPenDown;
    private static boolean myVisible;

    public TurtleState() {
        myX = 0;
        myY = 0 ;
        myHeading = 0;
        myPenDown = false;
        myVisible = true;
    }

    /**
     * Returns x-coordinate as double value.
     * Assumes (0,0) is center of grid.
     * @return double value of x-coordinate
     */
    public static double getX() {
        return myX;
    }

    /**
     * Returns y-coordinate as double value.
     * Assumes (0,0) is center of grid.
     * @return double value of y-coordinate
     */
    public static double getY() {
        return myY;
    }

    /**
     * Returns heading in degrees.
     * Assumes north of (0,0) is 0 degrees.
     * Turning clockwise from NORTH increases angle such that:
     *      Heading EAST from north is 90 degrees.
     *      Heading WEST from north is 270 degrees.
     * @return double value of current heading in degrees
     */
    public static double getHeading() {
        return myHeading;
    }

    /**
     * Returns pen status as boolean.
     * Result is true if pen is down to write, false otherwise.
     * @return boolean value indicating if pen is down to write.
     */
    public static boolean isPenDown() {
        return myPenDown;
    }

    /**
     * Returns visibility as boolean.
     * Result is true if visible, false otherwise.
     * @return boolean value indicating if visible.
     */
    public static boolean isVisible() {
        return myVisible;
    }


}
