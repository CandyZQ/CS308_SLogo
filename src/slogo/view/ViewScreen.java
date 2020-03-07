package slogo.view;


import java.util.*;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import slogo.controller.listings.MovingObjectProperties;

public class ViewScreen implements ExternalAPIViewable {


  private static ResourceBundle myResources = ResourceBundle.getBundle("resources", Locale.getDefault());
  public static final double STAGE_HEIGHT = 800;
  public static final double STAGE_WIDTH = 1000;
  public static final String STAGE_TITLE = myResources.getString("MainStageTitle");
  public static final String STYLE_SHEET = myResources.getString("MainStyleSheet");


  private SubSceneLeft scLeft;
  private SubSceneRight scRight;
  private Stage stage;
  private Scene scene;
  private boolean windowBoolean;

  private String[] displayCommands;

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
    BorderPane root = new BorderPane();
    scRight = new SubSceneRight();
    scRight.assignStage(stage);
    root.setRight(scRight.getRoot());
    displayCommands = new String[]{myResources.getString("FixedForward"), myResources.getString("FixedBackward"),
            myResources.getString("FixedLeft"),  myResources.getString("FixedRight")};
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
  public String getInputString() {
    return scRight.getTheText();
  }

  @Override
  public void exceptionHandling(String errorMessage) {
    if (errorMessage != null){
      scRight.setCommandText(errorMessage);
    }

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
    scRight.updateButtonLan();
    windowBoolean = scRight.getWindowBoolean();
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

  public boolean getWindowBoolean(){
    return windowBoolean;
  }
}
