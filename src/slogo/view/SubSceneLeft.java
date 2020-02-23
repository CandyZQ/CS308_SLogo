package slogo.view;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SubSceneLeft {
  private Group root;
  private Rectangle rect;
  private VBox vBox;

  public SubSceneLeft() {
    root = new Group();
    vBox = new VBox(20);
    root.getChildren().add(vBox);
    createRectangle();
    vBox.setAlignment(Pos.BOTTOM_CENTER);
  }

  private void createRectangle() {
    rect = new Rectangle(ViewScreen.STAGE_WIDTH/2, ViewScreen.STAGE_HEIGHT/2, SubScene.INITIAL_BACKGROUND_COLOR);
    vBox.getChildren().add(rect);
  }


  public Group getRoot(){return root;}

  public void setRectangleColor(Color color) {
    rect.setFill(color);
  }
}
