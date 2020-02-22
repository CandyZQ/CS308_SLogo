/**
 * @author Sarah Gregorich
 */

package slogo.model;

import slogo.controller.MovingObjectProperties;

import java.util.EnumMap;
import java.util.Map;

public class Turtle implements MovingObject {

    private EnumMap<MovingObjectProperties, Object> stateMap = new EnumMap<>(MovingObjectProperties.class);
    private Map<MovingObjectProperties, Object> defaultStateMap = Map.of(MovingObjectProperties.X, 0.0D, MovingObjectProperties.Y, 0.0D, MovingObjectProperties.HEADING, 90.0D, MovingObjectProperties.VISIBILITY, true, MovingObjectProperties.PEN, true, MovingObjectProperties.RETURN_VALUE, 0.0D);
    private double myDistanceTravelled;

    private static final double UP_ANGLE = 90;
    private static final double LEFT_ANGLE = 180;
    private static final double DOWN_ANGLE = 270;
    private static final double RIGHT_ANGLE = 360;

    private static final boolean RESTRICT_HEADING = true;
    private static final double MIN_ANGLE = -360;
    private static final double MAX_ANGLE = 360;
    private static final double POS_ANGLE = 0;

    private static final double INF_ANGLE = 1000000;
    private static final String HEADING_TOO_LARGE = "New heading is too large";
    // Default for calculating rotation is difference moving counterclockwise

    /**
     * Turtle.java is updated from Controller according to commands, and returns TurtleState object as needed for View to update display/track history.
     */
    public Turtle(Integer ID) {
        myDistanceTravelled = 0;
        this.stateMap.put(MovingObjectProperties.ID, ID);
        this.stateMap.put(MovingObjectProperties.X, defaultStateMap.get(MovingObjectProperties.X));
        this.stateMap.put(MovingObjectProperties.Y, defaultStateMap.get(MovingObjectProperties.Y));
        this.stateMap.put(MovingObjectProperties.HEADING, defaultStateMap.get(MovingObjectProperties.HEADING));
        this.stateMap.put(MovingObjectProperties.VISIBILITY, defaultStateMap.get(MovingObjectProperties.VISIBILITY));
        this.stateMap.put(MovingObjectProperties.PEN, defaultStateMap.get(MovingObjectProperties.PEN));
        this.stateMap.put(MovingObjectProperties.RETURN_VALUE, defaultStateMap.get(MovingObjectProperties.RETURN_VALUE));
    }

    /**
     * Gets the distance this {@link MovingObject} travelled when the state changes
     *
     * @return the distance travelled
     */
    @Override
    public double getDistanceTravelled() {
        return myDistanceTravelled;
    }

    /**
     * Sets (x,y) coordinates of Turtle's current state.
     * Assumes grid center (0,0).
     * @Param x is new x-location of Turtle
     * @param y is new y-location of Turtle
     */
    @Override
    public double setCoordinates(double x, double y) {
        double distance = findDistance((double) stateMap.get(MovingObjectProperties.X), (double) stateMap.get(MovingObjectProperties.Y),x,y);
        this.stateMap.put(MovingObjectProperties.X, x);
        this.stateMap.put(MovingObjectProperties.Y,y);
        updateDistanceTravelled(distance);
        this.stateMap.put(MovingObjectProperties.RETURN_VALUE,distance);
        return distance;
    }

    /**
     * Sets ABSOLUTE heading of Turtle's current state in degrees.
     * Assumes north of (0,0) is 0 degrees.
     * Turning clockwise from NORTH increases angle such that:
     *      Heading EAST is 90 degrees.
     *      Heading WEST is 270 degrees.
     * ABSOLUTE - if current heading is 90 and new heading is 95, the total rotation is 5, not 95
     * @param newHeading is new angle heading of turtle, may be passed as any value but as long as
     *                  RESTRICTED_HEADING is true the value will be normalized to [0,360]
     * @return absolute value of number of degrees moved, assume when heading set turtle always moves COUNTERCLOCKWISE
     */
    @Override
    public double setHeading(double newHeading) {
        double currentHeading = (double) this.stateMap.get(MovingObjectProperties.HEADING);
        if (RESTRICT_HEADING) {
            newHeading = getCoterminal(newHeading, MIN_ANGLE);
        }
        this.stateMap.put(MovingObjectProperties.HEADING,newHeading);
        double rotation = getRotation(currentHeading, newHeading);
        this.stateMap.put(MovingObjectProperties.RETURN_VALUE,rotation);
        return rotation;
    }

    /**
     * Updates Turtle heading to face (x,y)
     * Does this by calculating angle then called public double setheading(double angle)
     * @param (x,y) is point turtle has rotated to face.
     * @return absolute value of number of degrees moved
     */
    @Override
    public double setHeading(double x, double y) {
        return setHeading(findAngle(x,y));
    }

    /**
     * Rotates turtle by changing heading
     * @param offset Positive offset is clockwise, negative offset is counterclockwise
     * @return offset
     */
    @Override
    public double rotate(double offset) {
        double currentHeading = (double) this.stateMap.get(MovingObjectProperties.HEADING);
        double newHeading = currentHeading + offset;
        setHeading(newHeading);
        this.stateMap.put(MovingObjectProperties.RETURN_VALUE,offset);
        return offset;
    }

    /**
     * Moves this object forward/backward certain distance
     *
     * @param distance the distance to be moved, positive value moves this object forward while
     *                 negative value moves it backward
     * @return the distance moved
     */
    @Override
    public double moveDistance(double distance) {
        double currentHeading = getCoterminal((double) this.stateMap.get(MovingObjectProperties.HEADING), POS_ANGLE);
        double hypotenuse = Math.abs(distance);
        double opposite = hypotenuse*Math.sin(Math.toRadians(getCoterminal((double) this.stateMap.get(MovingObjectProperties.HEADING), POS_ANGLE)));
        double adjacent = hypotenuse*Math.cos(Math.toRadians(getCoterminal((double) this.stateMap.get(MovingObjectProperties.HEADING), POS_ANGLE)));
        double currentX = (double) this.stateMap.get(MovingObjectProperties.X);
        double currentY = (double) this.stateMap.get(MovingObjectProperties.Y);
        double[] delta = getDeltas(distance, currentHeading, opposite, adjacent);
        this.stateMap.put(MovingObjectProperties.X,currentX+delta[0]);
        this.stateMap.put(MovingObjectProperties.Y,currentY+delta[1]);
        updateDistanceTravelled(distance);
        this.stateMap.put(MovingObjectProperties.RETURN_VALUE,distance);
        return distance;
    }

    /**
     * Returns this object to the center of the screen and sets heading ot 0
     */
    @Override
    public double reset() { // Does not count this towards distance travelled
        this.stateMap.put(MovingObjectProperties.X, defaultStateMap.get(MovingObjectProperties.X));
        this.stateMap.put(MovingObjectProperties.Y, defaultStateMap.get(MovingObjectProperties.Y));
        this.stateMap.put(MovingObjectProperties.HEADING, defaultStateMap.get(MovingObjectProperties.HEADING));
        // Returns distance turtle moved TODO: do they mean to get home? Or total distance for all time?
        this.stateMap.put(MovingObjectProperties.RETURN_VALUE,myDistanceTravelled);
        return myDistanceTravelled;
    }

    /**
     * Gets all state information of this object as a {@link Map}
     *
     * @return a {@link Map} containing all state information
     */
    @Override
    public EnumMap<MovingObjectProperties, Object> getState() {
        return this.stateMap;
    }


    private void updateDistanceTravelled(double distance) {
        myDistanceTravelled = myDistanceTravelled + distance;
    }

    private double findDistance(double x_start, double y_start, double x_end, double y_end) {
        double root = (x_start-x_end)*(x_start-x_end) + (y_start-y_end)*(y_start-y_end);
        return Math.sqrt(root);
    }

    private double getCoterminal(double angle, double lower_bound) {
        if (Math.abs(angle) > INF_ANGLE) { // Avoid infinite loops, shouldn't need to set outside of 1M
            throw new IllegalArgumentException(HEADING_TOO_LARGE);
        }
        while (angle < lower_bound) {
            angle = angle + MAX_ANGLE;
        }
        while (angle > MAX_ANGLE) {
            angle = angle - MAX_ANGLE;
        }
        return angle;
    }

    private double getRotation(double currentHeading, double newHeading) {
        // Assumes movement is counterclockwise for setHeading()
        return Math.abs(getCoterminal(currentHeading,POS_ANGLE)-getCoterminal(currentHeading,POS_ANGLE));
    }

    private double findAngle(double to_x, double to_y) {

        double from_x = (double) this.stateMap.get(MovingObjectProperties.X);
        double from_y = (double) this.stateMap.get(MovingObjectProperties.Y);
        double hypotenuse = findDistance(from_x,from_y,to_x,to_y);
        double opposite = to_y-from_y;
        double angle = Math.toDegrees(Math.asin(opposite/hypotenuse));
        boolean x_pos = (to_x-from_x)>=POS_ANGLE;
        boolean angle_pos = angle >=POS_ANGLE;

        if (angle_pos && !x_pos) {
            return LEFT_ANGLE-angle;
        } else if (!angle_pos && !x_pos) {
            return DOWN_ANGLE+angle;
        } else if (!angle_pos && x_pos) {
            return RIGHT_ANGLE+angle;
        } else {
            return angle;
        }

    }

    private double[] getDeltas(double distance, double currentHeading, double opposite, double adjacent) {
        double[] delta = {0,0};
        if (currentHeading <= UP_ANGLE) {
            delta[0] = Math.abs(adjacent);
            delta[1] = Math.abs(opposite);
        } else if (currentHeading > UP_ANGLE && currentHeading <= LEFT_ANGLE) {
            delta[0] = - Math.abs(adjacent);
            delta[1] = Math.abs(opposite);
        } else if (currentHeading > LEFT_ANGLE && currentHeading < DOWN_ANGLE) {
            delta[0] = - Math.abs(adjacent);
            delta[1] = - Math.abs(opposite);
        } else {
            delta[0] = Math.abs(adjacent);
            delta[1] = - Math.abs(opposite);
        }
        // Before setting, flip according to distance
        if (distance < POS_ANGLE) {
            delta[0] = -delta[0];
            delta[1] = -delta[1];
        }
        return delta;
    }


}
