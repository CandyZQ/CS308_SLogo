package slogo.view;

import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class SubSceneLeft extends SubScene {

  private final ImageView turtle = new ImageView(new Image("file:resources/defaultTurtle.png"));
  private Rectangle rect;

  public SubSceneLeft() {
    root = new Group();
//    vBox = new VBox();
//    vBox.getStyleClass().add("leftvbox");
//    root.getChildren().add(vBox);
    createRectangle();
    root.getChildren().add(createTurtle());
    moveTurtle();
  }

  private void moveTurtle() {
    TranslateTransition trans = new TranslateTransition(Duration.seconds(2), turtle);
    trans.setToY(-100);
    turtle.setRotate(-45);
    trans.setToX(-100);
    trans.play();
  }

  private ImageView createTurtle() {
    turtle.setX(200);
    turtle.setY(150);
    return turtle;
  }

  private void createRectangle() {
    rect = new Rectangle(ViewScreen.STAGE_WIDTH / 2, ViewScreen.STAGE_HEIGHT / 2,
        SubSceneRight.INITIAL_BACKGROUND_COLOR);
    rect.getStyleClass().add("rectangle");
    root.getChildren().add(rect);
  }


  public Group getRoot(){return root;}

  public void setRectangleColor(Color color) {
    rect.setFill(color);
  }
}
