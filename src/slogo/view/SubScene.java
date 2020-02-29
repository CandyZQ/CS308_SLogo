package slogo.view;

import java.util.*;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import slogo.controller.listings.MovingObjectProperties;

public abstract class SubScene {

  //new Locale("fr", "FR")
  //Locale.getDefault()
  protected Group root;
  protected VBox vBox;
  protected static ResourceBundle myResources =
      ResourceBundle.getBundle("resources", new Locale("zh", "CN"));
  protected static ResourceBundle myLanguages =
      ResourceBundle.getBundle("languages", Locale.getDefault());

  public Group getRoot() {
    return root;
  }

  public abstract void update(Queue<EnumMap<MovingObjectProperties, Object>> movements);
}
