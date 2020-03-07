package slogo.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.InvalidationListener;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
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
  private static final double SLIDER_LOW_VALUE = 0.01;
  private static final double SLIDER_HIGH_VALUE = 10;
  private static final int SLIDER_STARTING_VALUE = 2;
  private int INITIAL_TURTLE_X;
  private int INITIAL_TURTLE_Y;
  private final Circle pen = new Circle(0, 0, 2);

  private Turtle turtle = new Turtle(res.getString("Turtle"), 1);

  private Rectangle rect;
  private Slider turtleSpeed;
  private Slider thick;
  private double initialX;
  private double initialY;
  private TextField tf;
  private Color markerColor;
  private double markerThickness;

  private Queue<Map<MovingObjectProperties, Object>> queue;
  private Path path;
  private int statID;
  private double statX;
  private double statY;
  private double statHeading;
  private String statPen;
  private double statThickness;
  private Label theLabel;
  private TextArea scriptTextArea;
  private TextField scriptName;

  private ButtonGroup groupOfButtons;

  public SubSceneLeft(List<String> dispCommands) {
    root = new Group();
    vBox = new VBox();
    vBox.getStyleClass().add(res.getString("VBoxStyle"));
    root.getChildren().add(vBox);
    setInitialConditions();
    groupOfButtons = new ButtonGroup(dispCommands);
    setUpDisplayObjects();
    initialTurtlePosition();
    root.getChildren().add(createTurtle());

    makeOtherWindow(res.getString("StatsStageTitle"));
    makeOtherWindow(res.getString("NewScript"));
  }

  private void setInitialConditions() {
    markerThickness = 2;
    initialX = 0;
    initialY = 0;
    commandEntered = false;
  }

  @Override
  protected void setUpDisplayObjects() {
    createRectangle();
    vBox.getChildren().add(createLabel(res.getString("TurtleSpeedLabel")));
    turtleSpeed = createSlider();
    vBox.getChildren().add(turtleSpeed);
    vBox.getChildren().add(createLabel(res.getString("MarkerThicknessLabel")));
    thick = createSlider();
    vBox.getChildren().add(thick);
    makeButtons();
  }

  @Override
  protected void makeButtons() {
    for (int i = 0; i < groupOfButtons.getButtons().size(); i++) {
      Button button = groupOfButtons.getButtons().get(i);
      String commando = button.getText();
      button.setOnAction(e -> setCommand(commando));
    }
    vBox.getChildren().add(groupOfButtons.getBox());
  }

  private void initialTurtlePosition() {
    INITIAL_TURTLE_X = (int) Math.round(rect.getX() + rect.getWidth() / 2 - Turtle.size / 2);
    INITIAL_TURTLE_Y = (int) Math.round(rect.getY() + rect.getHeight() / 2 - Turtle.size / 2);
  }

  private void makeOtherWindow(String title) {
    Stage sideStage = new Stage();
    sideStage.setTitle(title);
    VBox vb = new VBox();
    if (title.contentEquals(res.getString("StatsStageTitle"))) {
      theLabel = createLabel(statsString());
      vb.getChildren().add(theLabel);
    } else if (title.contentEquals(res.getString("NewScript"))) {
      vb.getChildren().addAll(scriptPopUp());
    }
    vb.getChildren().addAll();
    Scene statsScene = new Scene(vb);
    statsScene.getStylesheets().add(res.getString("WindowStyle"));
    sideStage.setScene(statsScene);
    sideStage.show();
  }

  private List<Region> scriptPopUp() {
    List<Region> nodes = new ArrayList<>();
    scriptName = new TextField();
    scriptTextArea = new TextArea();
    scriptTextArea.getStyleClass().add(res.getString("TextArea"));
    Button scriptSave = new Button();
    scriptSave.setText(res.getString("Save"));
    scriptSave.setOnAction(event -> saveNewScript());
    nodes.add(scriptSave);
    nodes.add(scriptName);
    nodes.add(scriptTextArea);
    return nodes;
  }

  private void saveNewScript() {
    Script script = new Script(scriptName.getText());
    script.addAll(scriptTextArea.getText());
  }

  private String statsString() {
    return res.getString("TurtleID") + ' ' + statID + res.getString("TurtleX") + ' '
        + (int) statX
        + res.getString("TurtleY") + ' ' + (int) statY + res.getString("TurtleHead")
        + ' '
        + statHeading
        + res.getString("TurtlePen") + ' ' + statPen + res.getString("TurtleThick")
        + ' '
        + statThickness;
  }

  private Animation clipAnimation(Path path) {
    Pane clip = new Pane();
    path.clipProperty().set(clip);

    InvalidationListener pen_Listener = o1 -> {
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
      if ((boolean) queue.peek().get(MovingObjectProperties.CLEAR)) {
        clearScreen();
      }
      tf.setEditable(false);
      TranslateTransition firstTransition = move();
      firstTransition.setOnFinished(event -> {
        if (!queue.isEmpty()) {
          TranslateTransition secondTransition = move();
          secondTransition.setOnFinished(event1 -> recurse());
        } else {
          tf.setEditable(true);
        }
      });
    } else {
      tf.setEditable(true);
    }
  }

  private void clearScreen() {
    //Place to write how to clear the marker from the screen, could not figure out how to remove it
    //As the paths need to be garbage collected to work for some reason.
    root.getChildren().remove(path);
  }

  private TranslateTransition move() {
    Map<MovingObjectProperties, Object> movements = queue.remove();
    statID = (Integer) movements.get(MovingObjectProperties.ID);
    statX = (Double) movements.get(MovingObjectProperties.X);
    statY = (Double) movements.get(MovingObjectProperties.Y);
    statHeading = (Double) movements.get(MovingObjectProperties.HEADING);
    statThickness = markerThickness;
    statPen = penUpDown();
    TranslateTransition transition = moveTurtle(statX, -statY, -statHeading + ANGLE_CORRECTION);
    transition.play();
    return transition;
  }

  private TranslateTransition moveTurtle(double xFinal, double yFinal, double heading) {
    turtle.changeHeading(heading);
    TranslateTransition trans = new TranslateTransition(Duration.seconds(turtleSpeed.getValue()),
        turtle);
    trans.setFromX(initialX);
    trans.setFromY(initialY);
    trans.setToX(xFinal);
    trans.setToY(yFinal);

    path = new Path();
    root.getChildren().add(path);

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
    rect.getStyleClass().add(res.getString("StyleClass"));
    vBox.getChildren().add(rect);
  }

  private Slider createSlider() {
    return new Slider(SLIDER_LOW_VALUE, SLIDER_HIGH_VALUE, SLIDER_STARTING_VALUE);
  }

  private String penUpDown() {
    if (markerColor == null) {
      return res.getString("PenUp");
    }
    return res.getString("PenDown");
  }

  private void setCommand(String command) {
    theText = command;
    commandEntered = true;
  }

  /**
   * Updates the queue with all the relevant turtle information and executes the method to move the
   * turtle based on the contents of the queue
   *
   * @param queue - the list of information passed from the backend which contains all the
   *              information about the turtle and the marker
   */
  @Override
  public void update(Queue<Map<MovingObjectProperties, Object>> queue) {
    this.queue = queue;
    recurse();
    theLabel.setText(statsString());
  }

  /**
   * Return the fixed command which is created as a result of pressing one of the four buttons
   *
   * @return the string which represents the command which will be fed into a text field
   */
  public String getCommand() {
    String temp = theText;
    theText = null;
    return temp;
  }

  /**
   * Updates the language of the text shown on the buttons.
   */
  public void updateButtons(List<String> displayCo) {
    vBox.getChildren().remove(groupOfButtons.getBox());
    groupOfButtons = new ButtonGroup(displayCo);
    makeButtons();
  }

  public void updateDisplayWords() {
    rect.getStyleClass().add(res.getString("StyleClass"));
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
