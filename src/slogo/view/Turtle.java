package slogo.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Turtle extends ImageView {


    private int turtleID;
    private Image turtleImage;
    public static final double size = 60;

    public Turtle(String imagePath, int ID){
        turtleImage = new Image(imagePath);
        this.turtleID = ID;
    }


    public void changeHeading(double heading){
        this.setRotate(heading);
    }

    public int getTurtleID(){
        return turtleID;
    }

    public Image getTurtleImage(){
        return turtleImage;
    }

}
