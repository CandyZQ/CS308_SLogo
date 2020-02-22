package slogo.view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;

import javafx.scene.layout.BorderPane;

public class BoundsRectanglesObjectsViewable extends ObjectsViewable {

  @Override
  public BorderPane createRootObject(BorderPane root) {
    Rectangle boundsRect = new Rectangle();
    boundsRect.setY(STAGE_HEIGHT-(STAGE_HEIGHT - 50));
    boundsRect.setX(STAGE_WIDTH-(STAGE_WIDTH - 50));
    boundsRect.setFill(Color.BLACK);
    boundsRect.setHeight(STAGE_HEIGHT - 100);
    boundsRect.setWidth(STAGE_WIDTH/2 - 100);
    root.getChildren().add(boundsRect);
    return root;
  }

  @Override
  public BorderPane editRoot(BorderPane root) {
    return null;
  }

  @Override
  public BorderPane removeFromRoot(BorderPane root) {
    return null;
  }
}
