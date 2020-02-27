package slogo.view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.BorderPane;

public class BoundsRectanglesObjectsViewable extends ObjectsViewable {

  @Override
  public BorderPane createRootObject(BorderPane root) {
    Rectangle boundsRect = new Rectangle();
    boundsRect.setFill(Color.YELLOW);
    boundsRect.setHeight(ObjectsViewable.STAGE_HEIGHT - 100);
    boundsRect.setWidth(ObjectsViewable.STAGE_WIDTH/2);
    root.setLeft(boundsRect);
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
