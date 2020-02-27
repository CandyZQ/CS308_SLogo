package slogo.view;

import java.util.EnumMap;
import java.util.Queue;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import slogo.controller.listings.MovingObjectProperties;

public abstract class SubScene {

  protected Group root;
  protected VBox vBox;

  public Group getRoot() {
    return root;
  }

  public abstract void update(Queue<EnumMap<MovingObjectProperties, Object>> movements);
}
