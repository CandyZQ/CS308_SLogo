package slogo.view;

import java.util.EnumMap;
import java.util.Map;
import java.util.Queue;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import javafx.stage.Stage;
import slogo.controller.listings.MovingObjectProperties;

public class ViewScreen implements ExternalAPIViewable {

  public static final int STAGE_HEIGHT = 800;
  public static final int STAGE_WIDTH = 1000;
  public static final String STAGE_TITLE = "SLOGO";
  public static final String STYLE_SHEET = "style.css";

  private static SubSceneLeft scLeft;
  private static SubSceneRight scRight;
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
    scRight = new SubSceneRight();
    scRight.assignStage(stage);
    root.setRight(scRight.getRoot());
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
  public String getInputString() {
    return scRight.getTheText();
  }

  @Override
  public void exceptionHandling() throws Exception {

  }

  @Override
  public Stage setScene() {
    return null;
  }


  public static void update(
      Queue<EnumMap<MovingObjectProperties, Object>> commands,
      Map<String, Double> variables) {
    scLeft.setRectangleColor(scRight.getClickedColor());
    scLeft.setTurtle(scRight.getTurtle());
    scRight.setVariableTextArea(variables);
    //scRight.setUserTextArea();
    scLeft.listenToDisableTextField(scRight.getTextField());
    if (commands == null || commands.isEmpty()) {
      return;
    } else {
      scLeft.update(commands);
    }
  }

  @Override
  public String getLanguage() {
    return scRight.getLanguage().toString();
  }
}
