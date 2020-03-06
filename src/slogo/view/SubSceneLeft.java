package slogo.view;

import java.util.ArrayList;
import java.util.List;
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
  private static int INITIAL_TURTLE_X;
  private static int INITIAL_TURTLE_Y;
  private static final double SLIDER_LOW_VALUE = 0.01;
  private static final double SLIDER_HIGH_VALUE = 10;
  private static final int SLIDER_STARTING_VALUE = 2;
  private final Circle pen = new Circle(0, 0, 2);

  private ResourceBundle res = ResourceBundle.getBundle("resources", Locale.getDefault());
  private Turtle turtle = new Turtle(res.getString("Turtle"), 1);

  private Rectangle rect;
  private Slider turtleSpeed;
  private Slider thick;
  private double initialX;
  private double initialY;
  private TextField tf;
  private Color markerColor;
  private double markerThickness;

  private ArrayList<Path> pathList;
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
    vBox.getStyleClass().add(res.getString("VBoxStyle"));
    root.getChildren().add(vBox);
    createRectangle();
    INITIAL_TURTLE_X = (int) Math
        .round(rect.getX() + rect.getWidth() / 2 - Turtle.size / 2);
    INITIAL_TURTLE_Y = (int) Math
        .round(rect.getY() + rect.getHeight() / 2 - Turtle.size / 2);
    vBox.getChildren().add(createLabel(res.getString("TurtleSpeedLabel")));
    turtleSpeed = createSlider();
    vBox.getChildren().add(turtleSpeed);
    vBox.getChildren().add(createLabel(res.getString("MarkerThicknessLabel")));
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

    makeOtherWindow("Turtle Stats", true);
    makeOtherWindow("New Script", false);
  }

  private void makeOtherWindow(String title,
      boolean whichToCreate) { //Bad code, get rid of boolean. Not extendable
    Stage sideStage = new Stage();
    ScrollPane scrollRoot = new ScrollPane();
    sideStage.setTitle(title);     //res.getString(title)
    VBox vb = new VBox();
    if (whichToCreate) {
      theLabel = createLabel(
          res.getString("TurtleID") + ' ' + statID + res.getString("TurtleX") + ' ' + (int) statX
              + res.getString("TurtleY") + ' ' + (int) statY + res.getString("TurtleHead") + ' '
              + statHeading
              + res.getString("TurtlePen") + ' ' + statPen + res.getString("TurtleThick") + ' '
              + statThickness);
      vb.getChildren().add(theLabel);
    } else {
      vb.getChildren().addAll(scriptPopUp());
    }
    vb.getChildren().addAll();
    scrollRoot.setContent(vb);
    Scene statsScene = new Scene(scrollRoot);
    statsScene.getStylesheets().add("script.css");
    sideStage.setScene(statsScene);
    sideStage.show();
  }

  private List<Region> scriptPopUp() {
    List<Region> nodes = new ArrayList<>();
    scriptName = new TextField();
    scriptTextArea = new TextArea();
    scriptTextArea.getStyleClass().add("text-area");
    Button scriptSave = new Button();
    scriptSave.setText("Save"); // @TODO attach to resource file so language changes
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
    return res.getString("TurtleID") + ' ' + statID + res.getString("TurtleX") + ' ' + (int) statX
        + res.getString("TurtleY") + ' ' + (int) statY + res.getString("TurtleHead") + ' '
        + statHeading
        + res.getString("TurtlePen") + ' ' + statPen + res.getString("TurtleThick") + ' '
        + statThickness;
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
    rect.getStyleClass().add(res.getString("StyleClass"));
    vBox.getChildren().add(rect);
  }

  private Slider createSlider() {
    return new Slider(SLIDER_LOW_VALUE, SLIDER_HIGH_VALUE, SLIDER_STARTING_VALUE);
  }

  @Override
  public void update(Queue<Map<MovingObjectProperties, Object>> queue) {
    this.queue = queue;
    recurse();
    theLabel.setText(statsString());
  }

  private String penUpDown() {
    if (markerColor == null) {
      return "Pen Up";
    }
    return "Pen Down";
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

  public void setMarkerThickness(Double size) {
    markerThickness = size;
  }

  public void listenToDisableTextField(TextField tf) {
    this.tf = tf;
  }
}
