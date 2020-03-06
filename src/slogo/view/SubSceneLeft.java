package slogo.view;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import slogo.controller.listings.MovingObjectProperties;
import slogo.controller.scripting.Script;

public class SubSceneLeft extends SubScene {

  private static final double ANGLE_CORRECTION = 90;
  private static int INITIAL_TURTLE_X;
  private static int INITIAL_TURTLE_Y;
  private static final double SLIDER_LOW_VALUE = 0.01;
  private static final double SLIDER_HIGH_VALUE = 10;
  private static final int SLIDER_STARTING_VALUE = 2;
  private final Circle pen = new Circle(0, 0, 2);

  private Turtle turtle = new Turtle(myResources.getString("Turtle"), 1);

  private Rectangle rect;
  private Slider turtleSpeed;
  private Slider thick;
  private double initialX;
  private double initialY;
  private TextField tf;
  private Color markerColor;
  private double markerThickness;

  private Queue<Map<MovingObjectProperties, Object>> queue;

  private int statID;
  private double statX;
  private double statY;
  private double statHeading;
  private String statPen;
  private double statThickness;
  private Label theLabel;
  private TextArea scriptTextArea;
  private TextField scriptName;

  private ButtonG groupOfButtons;

  public SubSceneLeft(String[] dispCommands) {
    markerThickness = 2;
    initialX = 0;
    initialY = 0;
    root = new Group();
    vBox = new VBox();
    vBox.getStyleClass().add(myResources.getString("VBoxStyle"));
    root.getChildren().add(vBox);
    createRectangle();
    INITIAL_TURTLE_X = (int) Math
        .round(rect.getX() + rect.getWidth() / 2 - Turtle.size / 2);
    INITIAL_TURTLE_Y = (int) Math
        .round(rect.getY() + rect.getHeight() / 2 - Turtle.size / 2);
    vBox.getChildren().add(createLabel(myResources.getString("TurtleSpeedLabel")));
    turtleSpeed = createSlider();
    vBox.getChildren().add(turtleSpeed);
    vBox.getChildren().add(createLabel(myResources.getString("MarkerThicknessLabel")));
    thick = createSlider();
    vBox.getChildren().add(thick);
    groupOfButtons = new ButtonG(dispCommands);

    for (int i = 0; i < groupOfButtons.getButtons().size(); i++) {
      Button button = groupOfButtons.getButtons().get(i);
      String commando = button.getText();
      button.setOnAction(e -> setCommand(commando));
    }

    commandEntered = false;
    vBox.getChildren().add(groupOfButtons.getBoxes());
    root.getChildren().add(createTurtle());

    turtleStatsPopUp();
    scriptPopUp();
  }

  private void turtleStatsPopUp() {
    ScrollPane statsRoot = new ScrollPane();
    Stage statsStage = new Stage();
    VBox vb = new VBox();

    theLabel = new Label(
            myResources.getString("TurtleID") + ' ' + statID + myResources.getString("TurtleX") + ' ' + (int) statX
            + myResources.getString("TurtleY") + ' ' + (int) statY + myResources.getString("TurtleHead") + ' '
            + statHeading
            + myResources.getString("TurtlePen") + ' ' + statPen + myResources.getString("TurtleThick") + ' '
            + statThickness);

    vb.getChildren().addAll(theLabel);
    statsRoot.setContent(vb);

    Scene statsScene = new Scene(statsRoot);
    statsScene.getStylesheets().add(myResources.getString("StatsStyle"));
    statsStage.setScene(statsScene);
    statsStage.show();
  }

  private void makeOtherWindow(String title) {
    Stage sideStage = new Stage();
    ScrollPane scrollRoot = new ScrollPane();
    sideStage.setTitle(myResources.getString(title));
    VBox vb = new VBox();
    vb.getChildren().addAll();
    scrollRoot.setContent(vb);
    Scene statsScene = new Scene(scrollRoot);
    statsScene.getStylesheets().add(myResources.getString("StatsStyle"));
    sideStage.setScene(statsScene);
    sideStage.show();
  }

  private void scriptPopUp() { // @TODO make pretty?
    ScrollPane scriptRoot = new ScrollPane();
    Stage scriptStage = new Stage();
    // scriptStage.setTitle(myResources.getString("ScriptStageTitle")); @TODO attach to resource file so language changes
    scriptStage.setTitle(myResources.getString("NewScript"));
    VBox vb = new VBox();

    scriptName = new TextField();
    scriptTextArea = new TextArea();
    scriptTextArea.getStyleClass().add(myResources.getString("TextArea"));
    Button scriptSave = new Button();
    scriptSave.setText(myResources.getString("Save")); // @TODO attach to resource file so language changes
    scriptSave.setOnAction(event -> saveNewScript());

    vb.getChildren().addAll(scriptSave, scriptName, scriptTextArea);
    scriptRoot.setContent(vb);

    Scene scriptScene = new Scene(scriptRoot);
    scriptScene.getStylesheets().add(myResources.getString("ScriptStyle"));
    scriptStage.setScene(scriptScene);
    scriptStage.show();
  }

  private void saveNewScript() {
    Script script = new Script(scriptName.getText());
    script.addAll(scriptTextArea.getText());
  }

  private void updateStatsPopUp() {
    theLabel.setText(
            myResources.getString("TurtleID") + ' ' + statID + myResources.getString("TurtleX") + ' ' + (int) statX
            + myResources.getString("TurtleY") + ' ' + (int) statY + myResources.getString("TurtleHead") + ' '
            + statHeading
            + myResources.getString("TurtlePen") + ' ' + statPen + myResources.getString("TurtleThick") + ' '
            + statThickness);
  }

  private Animation clipAnimation(Path path) {
    Pane clip = new Pane();
    path.clipProperty().set(clip);

    ChangeListener pen_Listener = (o1, o2, o3) -> {
      Circle clip_eraser = new Circle(pen.getTranslateX(), pen.getTranslateY(), pen.getRadius());
      clip.getChildren().add(clip_eraser);
    };

    pen.translateXProperty().addListener(pen_Listener);
    pen.translateYProperty().addListener(pen_Listener);
    PathTransition pathTransition = new PathTransition(Duration.seconds(turtleSpeed.getValue()),
        path, pen);
    pathTransition.setOnFinished(t -> {
      path.setClip(null);
      clip.getChildren().clear();
    });

    return pathTransition;
  }

  private void recurse() {
    if (!queue.isEmpty()) {
      tf.setEditable(false); // .setVisible() will alternatively make it go away
      TranslateTransition t1 = move();

      t1.setOnFinished(event -> {

        if (!queue.isEmpty()) {
          TranslateTransition t2 = move();
          t2.setOnFinished(event1 -> recurse());
        } else {
          tf.setEditable(true);
        }
      });
    } else {
      tf.setEditable(true);
    }
  }

  private TranslateTransition move() {
    Map<MovingObjectProperties, Object> movements = queue.remove();
    statID = (Integer) movements.get(MovingObjectProperties.ID);
    statX = (Double) movements.get(MovingObjectProperties.X);
    statY = (Double) movements.get(MovingObjectProperties.Y);
    statHeading = (Double) movements.get(MovingObjectProperties.HEADING);
    statThickness = markerThickness;
    statPen = penUpDown();
    TranslateTransition t1 = moveTurtle(statX, -statY, -statHeading + ANGLE_CORRECTION);
    t1.play();
    return t1;
  }

  private void setCommand(String command) {
    theText = command;
    commandEntered = true;
  }

  private TranslateTransition moveTurtle(double xFinal, double yFinal, double heading) {
    turtle.changeHeading(heading);
    TranslateTransition trans = new TranslateTransition(Duration.seconds(turtleSpeed.getValue()),
        turtle);
    trans.setFromX(initialX);
    trans.setFromY(initialY);
    trans.setToX(xFinal);
    trans.setToY(yFinal);

    Path path = new Path();
    root.getChildren().addAll(path);

    path.getElements().addAll(

        new MoveTo(INITIAL_TURTLE_X + initialX + Turtle.size / 2,
            INITIAL_TURTLE_Y + initialY + Turtle.size / 2),
        new LineTo(INITIAL_TURTLE_X + xFinal + Turtle.size / 2,
            INITIAL_TURTLE_Y + yFinal + Turtle.size / 2)

    );
    path.setStroke(markerColor);
    path.setStrokeWidth(thick.getValue());

    Animation path_animation = clipAnimation(path);
    path_animation.play();

    initialX = xFinal;
    initialY = yFinal;
    return trans;
  }

  private ImageView createTurtle() {
    turtle.setX(INITIAL_TURTLE_X);
    turtle.setY(INITIAL_TURTLE_Y);
    return turtle;
  }

  private void createRectangle() {
    rect = new Rectangle(ViewScreen.STAGE_WIDTH / 2, ViewScreen.STAGE_HEIGHT / 2,
        SubSceneRight.INITIAL_BACKGROUND_COLOR);
    rect.getStyleClass().add(myResources.getString("StyleClass"));
    vBox.getChildren().add(rect);
  }

  private Slider createSlider() {
    return new Slider(SLIDER_LOW_VALUE, SLIDER_HIGH_VALUE, SLIDER_STARTING_VALUE);
  }

  @Override
  public void update(Queue<Map<MovingObjectProperties, Object>> queue) {
    this.queue = queue;
    recurse();
    updateStatsPopUp();
  }

  private String penUpDown() {
    if (markerColor == null) {
      return myResources.getString("PenUp");
    }
    return myResources.getString("PenDown");
  }

  public String getCommand() {
    String temp = theText;
    theText = null;
    return temp;
  }

  public void updateButtons(String[] displayCo) {
    vBox.getChildren().remove(groupOfButtons.getBoxes());
    groupOfButtons = new ButtonG(displayCo);
    for (int i = 0; i < groupOfButtons.getButtons().size(); i++) {
      Button button = groupOfButtons.getButtons().get(i);
      String commando = button.getText();
      button.setOnAction(e -> setCommand(commando));
    }
    vBox.getChildren().add(groupOfButtons.getBoxes());
  }

  public void updateDisplayWords() {
    rect.getStyleClass().add(myResources.getString("StyleClass"));
  }

  public void setTurtle(Image newTurtle) {
    turtle.setImage(newTurtle);
  }

  public void setRectangleColor(Color color) {
    rect.setFill(color);
  }

  public void setMarkerColor(Color color) {
    markerColor = color;
  }

  public void listenToDisableTextField(TextField tf) {
    this.tf = tf;
  }
}
