package slogo.view;

import java.util.Map;
import java.util.Queue;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import javafx.stage.Stage;

public class ViewScreen implements ExternalAPIViewable {

  public static final int STAGE_HEIGHT = 800;
  public static final int STAGE_WIDTH = 1000;
  public static final String STAGE_TITLE = "SLOGO";
  public static final String STYLE_SHEET = "style.css";

  private static SubSceneLeft scLeft;
  private static SubScene sc;
  private Stage stage;
  private Scene scene;
  private BorderPane root;

  public ViewScreen(Stage stage) {
    this.stage = stage;
    stage.setMaxHeight(STAGE_HEIGHT);
    stage.setMinHeight(STAGE_HEIGHT);
    stage.setMaxWidth(STAGE_WIDTH);
    stage.setMinWidth(STAGE_WIDTH);
    startView();
    stage.show();
  }

  private void startView() {
    this.root = new BorderPane();

    sc = new SubScene();
    root.setRight(sc.getRoot());
    scLeft = new SubSceneLeft();
    root.setLeft(scLeft.getRoot());
    setAsScene(new Scene(root, ObjectsViewable.STAGE_WIDTH, ObjectsViewable.STAGE_HEIGHT));
    stage.setScene(scene);
    stage.setTitle(STAGE_TITLE);
    scene.getStylesheets().add(STYLE_SHEET);
  }

  private void setAsScene(Scene scene) {
    this.scene = scene;
  }

  @Override
  public Queue<Map<String, Integer>> getFinalInformation() {
    return null;
  }

  @Override
  public String giveInputString() {
    return null;
  }

  @Override
  public void exceptionHandling() throws Exception {

  }

  @Override
  public Stage setScene() {
    return null;
  }


  public static void update() {
    scLeft.setRectangleColor(sc.getClickedColor());

  }

  @Override
  public String setLanguage() {
    return sc.getLanguage().toString();
  }
}
