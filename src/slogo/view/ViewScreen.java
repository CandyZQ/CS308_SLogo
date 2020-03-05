package slogo.view;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import slogo.controller.listings.MovingObjectProperties;

public class ViewScreen implements ExternalAPIViewable {

  public static final double STAGE_HEIGHT = 600;
  public static final double STAGE_WIDTH = 800;
  public static final String STAGE_TITLE = "SLOGO";
  public static final String STYLE_SHEET = "style.css";


  private static SubSceneLeft scLeft;
  private static SubSceneRight scRight;
  private Stage stage;
  private Scene scene;

  private String[] displayCommands;
  private ArrayList<String> commandsToDo;

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
    BorderPane root = new BorderPane(); // might need to make this an instance variable for refactoring, got rid of warning for now with local variable
    scRight = new SubSceneRight();
    scRight.assignStage(stage);
    root.setRight(scRight.getRoot());
    displayCommands = new String[]{"FD 50", "BK 50", "LT 50", "RT 50"};
    scLeft = new SubSceneLeft(displayCommands);
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
  public void exceptionHandling(String errorMessage) {
    scRight.setCommandText(errorMessage);
  }

  @Override
  public Stage setScene() {
    return null;
  }


  public boolean getRunScript() { return scRight.getRunScript();}

  public String getScript() { return scRight.getScript();}


  public void update(
      Queue<Map<MovingObjectProperties, Object>> commands,
      Map<String, Double> variables,
      Map<String, List<String>> functions,
      String[] dispCommands) {
    SubScene.updateResourceBundle();
    scRight.updateDisplayWords();
    scLeft.setRectangleColor(scRight.getClickedColor());
    scLeft.setMarkerColor(scRight.getMarkerClickedColor());
    scLeft.setTurtle(scRight.getTurtle());
    scLeft.listenToDisableTextField(scRight.getTextField());
    scRight.setVariableTextArea(variables);
    scRight.setUserTextArea(functions);
    scRight.execute(scLeft.getCommand());
    if (commands != null && commands.peek() != null) {
      scRight.setCommandText(SubSceneRight.SUCCESSFUL_COMMAND);
      scLeft.update(commands);
      scRight.update(commands);
    }
    if (!dispCommands[0].equals(displayCommands[0])) {
      scLeft.updateButtons(dispCommands);
      displayCommands = dispCommands;
    }
  }

  @Override
  public String getLanguage() {
    return scRight.getLanguage().toString();
  }

  @Override
  public void getColor(String hexColor) {
    scLeft.setMarkerColor(Color.web(hexColor));
  }
}
