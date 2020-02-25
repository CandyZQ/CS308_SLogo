package slogo.view;

import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.File;

public class SubSceneLeft extends SubScene {

  private ImageView turtle = new ImageView(new Image("file:resources/defaultTurtle.png"));
  private Rectangle rect;
  private Slider slider;

  public SubSceneLeft() {
    root = new Group();
    vBox = new VBox();
    vBox.getStyleClass().add("leftvbox");
    root.getChildren().add(vBox);
    createRectangle();
    root.getChildren().add(createTurtle());
    createSlider();
    moveTurtle();
  }

  private void moveTurtle() {
    TranslateTransition trans = new TranslateTransition(Duration.seconds(slider.getValue()),
        turtle);
    trans.setToY(-100);
    turtle.setRotate(-45);
    trans.setToX(-100);
    trans.play();
  }

  private ImageView createTurtle() {
    turtle.setX(280);
    turtle.setY(230);
    return turtle;
  }

  public void setTurtle(Image newTurtle){
    turtle.setImage(newTurtle);
  }

  private void createRectangle() {
    rect = new Rectangle(ViewScreen.STAGE_WIDTH / 2, ViewScreen.STAGE_HEIGHT / 2,
        SubSceneRight.INITIAL_BACKGROUND_COLOR);
    rect.getStyleClass().add("rectangle");
    vBox.getChildren().add(rect);
  }

  private void createSlider() {
    slider = new Slider(1, 10, 5.5);
    vBox.getChildren().add(slider);
  }

  public Group getRoot() {
    return root;
  }

  public void setRectangleColor(Color color) {
    rect.setFill(color);
  }
}
