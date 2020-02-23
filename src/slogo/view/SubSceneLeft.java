package slogo.view;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SubSceneLeft extends SubScene {

  private final ImageView turtle = new ImageView(new Image("file:resources/defaultTurtle.png"));
  private Rectangle rect;

  public SubSceneLeft() {
    root = new Group();
//    vBox = new VBox();
//    vBox.getStyleClass().add("leftvbox");
//    root.getChildren().add(vBox);
    createRectangle();
    createTurtle();
  }

  private void createTurtle() {
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
