package slogo.test;

import org.junit.Assert;
import org.junit.Test;
import slogo.controller.MovingObjectProperties;
import slogo.model.Turtle;

public class TurtleTest {
    @Test
    public void shouldSetCoordinates() {
        Integer set_ID = 0;
        double set_X = 100;
        double set_Y = 10;
        Turtle turtle = new Turtle(set_ID);
        turtle.setCoordinates(set_X, set_Y);
        Assert.assertEquals(set_ID,turtle.getState().get(MovingObjectProperties.ID));
        Assert.assertEquals(set_X, turtle.getState().get(MovingObjectProperties.X));
        Assert.assertEquals(set_Y, turtle.getState().get(MovingObjectProperties.Y));
    }

}
