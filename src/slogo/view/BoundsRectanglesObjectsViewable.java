package slogo.view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;

public class BoundsRectanglesObjectsViewable extends ObjectsViewable {

  @Override
  public Group createRootObject(Group root) {
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
  public Group editRoot(Group root) {
    return null;
  }

  @Override
  public Group removeFromRoot(Group root) {
    return null;
  }
}
