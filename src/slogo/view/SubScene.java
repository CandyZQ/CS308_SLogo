package slogo.view;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import slogo.controller.listings.MovingObjectProperties;

public abstract class SubScene {

  protected Group root;
  protected VBox vBox;
  protected static String language;
  private String previousLan = "";
  protected static Map<String, List<String>> languageLocation = Map
      .of("French", List.of("fr", "FR"), "Spanish", List.of("es", "ES"), "English",
          List.of("en", "EN"), "Russian", List.of("ru", "RU"), "German", List.of("de", "DE"),
          "Chinese", List.of("zh", "CN"), "Italian", List.of("it", "IT"), "Portuguese",
          List.of("pt", "PT"));

  protected static ResourceBundle myResources =
      ResourceBundle.getBundle("resources", Locale.getDefault());
  protected static ResourceBundle myLanguages =
      ResourceBundle.getBundle("languages", Locale.getDefault());

  public boolean commandEntered = false;
  public String theText;

  public Group getRoot() {
    return root;
  }

  public abstract void update(Queue<Map<MovingObjectProperties, Object>> movements);

  public static void updateResourceBundle() {
      myResources = ResourceBundle.getBundle("resources",
          new Locale(languageLocation.get(language).get(0), languageLocation.get(language).get(1)));
  }

  public abstract void updateDisplayWords();
  protected boolean languageUpdated() {
    if (!previousLan.equals(language)) {
      previousLan = language;
      return true;
    }
    return false;
  }

}
