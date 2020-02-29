package slogo.view;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Queue;
import java.util.ResourceBundle;

import javafx.scene.Group;
import javafx.scene.layout.VBox;
import slogo.controller.listings.MovingObjectProperties;

public abstract class SubScene {

  protected Group root;
  protected VBox vBox;
  protected static ResourceBundle myResources =
          ResourceBundle.getBundle("resources", Locale.getDefault());
  protected static ResourceBundle myLanguages =
          ResourceBundle.getBundle("languages", Locale.getDefault());

  public Group getRoot() {
    return root;
  }

  public abstract void update(Queue<EnumMap<MovingObjectProperties, Object>> movements);
}
