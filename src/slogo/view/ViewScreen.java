package slogo.view;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import slogo.controller.listings.MovingObjectProperties;

public class ViewScreen implements ExternalAPIViewable {


  private static ResourceBundle res = ResourceBundle.getBundle("resources", Locale.getDefault());
  public static final double STAGE_HEIGHT = 800;
  public static final double STAGE_WIDTH = 1000;
  public static String STAGE_TITLE = res.getString("MainStageTitle");
  public static String STYLE_SHEET = res.getString("MainStyleSheet");


  private SubSceneLeft scLeft;
  private SubSceneRight scRight;
  private Stage stage;
  private Scene scene;
  private boolean windowBoolean;

  private List<String> displayCommands;

  public ViewScreen(Stage stage) {
    this.stage = stage;
    startView();
    stage.show();
  }

  private void startView() {
    BorderPane root = new BorderPane();
    scRight = new SubSceneRight();
    scRight.assignStage(stage);
    root.setRight(scRight.getRoot());
    displayCommands = new ArrayList<>(
        Arrays.asList(res.getString("FixedForward"),
            res.getString("FixedBackward"), res.getString("FixedLeft"),
            res.getString("FixedRight")));
    scLeft = new SubSceneLeft(displayCommands);
    root.setLeft(scLeft.getRoot());
    setAsScene(new Scene(root));
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
    if (errorMessage != null) {
      scRight.setCommandText(errorMessage);
    }
  }

  @Override
  public boolean getRunScript() {
    return scRight.getRunScript();
  }

  public String getScript() {
    return scRight.getScript();
  }

  @Override
  public void update(
      Queue<Map<MovingObjectProperties, Object>> commands,
      Map<String, Double> variables,
      Map<String, List<String>> functions,
      List<String> dispCommands) {
    SubScene.updateResourceBundle();
    scRight.updateDisplayWords();
    scLeft.setRectangleColor(scRight.getClickedColor());
    scLeft.setMarkerColor(scRight.getMarkerClickedColor());
    scLeft.setTurtle(scRight.getTurtle());
    scLeft.listenToDisableTextField(scRight.getTextField());
    scRight.setTextAreas(variables, functions);
    windowBoolean = scRight.getWindowBoolean();
    scRight.execute(scLeft.getCommand());
    if (commands != null && commands.peek() != null) {
      scRight.setCommandText(SubSceneRight.SUCCESSFUL_COMMAND);
      scLeft.update(commands);
      scRight.update(commands);
    }
    if (!dispCommands.get(0).equals(displayCommands.get(0))) {
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

  @Override
  public boolean getWindowBoolean() {
    return windowBoolean;
  }
}
