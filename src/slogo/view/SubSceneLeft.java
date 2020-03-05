package slogo.view;

import java.util.*;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import slogo.controller.listings.MovingObjectProperties;
import slogo.controller.scripting.Script;

public class SubSceneLeft extends SubScene {

  private static final int INITIAL_TURTLE_X = 300;
  private static final int INITIAL_TURTLE_Y = 250;
  private static final double SPACING_CONSTANT = 20;
  private static final double SLIDER_LOW_VALUE = 0.01;
  private static final double SLIDER_HIGH_VALUE = 10;
  private static final int SLIDER_STARTING_VALUE = 2;
  private static final int SCRIPT_WIDTH = 400;
  private static final int SCRIPT_HEIGHT = 400;
  private final Circle pen = new Circle(0, 0, 2);

  private static ResourceBundle res = ResourceBundle.getBundle("resources", Locale.getDefault());
  private Turtle turtle = new Turtle(res.getString("Turtle"), 0);
  private final List<String> buttonNames = new ArrayList<>(
      Arrays.asList(res.getString("FD50"), res.getString("BK50"),
          res.getString("LT50"), res.getString("RT50")));


  private Rectangle rect;
  private Slider turtleSpeed;
  private Slider thick;
  private double initialX;
  private double initialY;
  private TextField tf;
  private Color markerColor;
  private double markerThickness;
  private int statID;
  private double statX;
  private double statY;
  private double statHeading;
  private String statPen;
  private double statThickness;
  private Label theLabel;
  private TextArea scriptTextArea;
  private TextField scriptName;
  private Button scriptSave;
  private Queue<Map<MovingObjectProperties, Object>> queue;
  private String command;


  public SubSceneLeft() {
    markerThickness = 2;
    initialX = 0;
    initialY = 0;
    root = new Group();
    vBox = new VBox();
    vBox.getStyleClass().add(res.getString("VBoxStyle"));
    root.getChildren().add(vBox);
    createRectangle();

    createAddLabel(res.getString("TurtleSpeedLabel"));
    turtleSpeed = createSlider();
    vBox.getChildren().add(turtleSpeed);

    createAddLabel(res.getString("MarkerThicknessLabel"));
    thick = createSlider();
    vBox.getChildren().add(thick);

    ButtonGroup group = new ButtonGroup(buttonNames);
    vBox.getChildren().add(group.getBoxes());
    buttonListeners(group);

    root.getChildren().add(createTurtle());

    turtleStatsPopUp();
    scriptPopUp();
  }

  private void createAddLabel(String displayLabel){
    Label aLabel = new Label(displayLabel);
    vBox.getChildren().add(aLabel);
  }

  private void turtleStatsPopUp(){
    ScrollPane statsRoot = new ScrollPane();
    Stage statsStage = new Stage();
    statsStage.setTitle(res.getString("StatsStageTitle"));
    VBox vb = new VBox();

    theLabel = new Label(res.getString("TurtleID") + statID +  res.getString("TurtleX") + statX
    + res.getString("TurtleY") + statY + res.getString("TurtleHead") + statHeading
    + res.getString("TurtlePen") +  statPen +  res.getString("TurtleThick") + statThickness);

    vb.getChildren().addAll(theLabel);
    statsRoot.setContent(vb);

    Scene statsScene = new Scene(statsRoot, 400, 400, Color.LIGHTBLUE);
    statsStage.setScene(statsScene);
    statsStage.show();
  }

  private void buttonListeners(ButtonGroup group) {

    List<Button> buttons = group.getButtons();
    buttons.get(0).setOnAction(event -> setCommand("fd 50"));
    buttons.get(1).setOnAction(event -> setCommand("bk 50"));
    buttons.get(2).setOnAction(event -> setCommand("lt 50"));
    buttons.get(3).setOnAction(event -> setCommand("rt 50"));

  }

  private void setCommand(String command){
        this.command = command;
  }

  public String getCommand(){
    String temp = command;
    command = null;
    return temp;
  }


  private void scriptPopUp(){ // @TODO make pretty?
    ScrollPane scriptRoot = new ScrollPane();
    Stage scriptStage = new Stage();
    // scriptStage.setTitle(myResources.getString("ScriptStageTitle")); @TODO attach to resource file so language changes
    scriptStage.setTitle("New Script");
    VBox vb = new VBox();

    scriptName = new TextField();
    scriptTextArea = new TextArea();
    scriptTextArea.setPrefWidth(SCRIPT_WIDTH);
    scriptTextArea.setPrefHeight(SCRIPT_WIDTH*2);
    scriptSave = new Button();
    scriptSave.setText("Save"); // @TODO attach to resource file so language changes
    scriptSave.setOnAction(event -> saveNewScript());

    vb.getChildren().addAll(scriptSave, scriptName, scriptTextArea);
    scriptRoot.setContent(vb);

    Scene statsScene = new Scene(scriptRoot, SCRIPT_WIDTH, SCRIPT_HEIGHT, Color.LIGHTBLUE);
    scriptStage.setScene(statsScene);
    scriptStage.show();
  }

  private void saveNewScript() {
    Script script = new Script(scriptName.getText());
    script.addAll(scriptTextArea.getText());
  }

 private void updateStatsPopUp(){
   theLabel.setText(res.getString("TurtleID") + statID +  res.getString("TurtleX") + statX
           + res.getString("TurtleY") + statY + res.getString("TurtleHead") + statHeading
           + res.getString("TurtlePen") +  statPen +  res.getString("TurtleThick") + statThickness);
  }

  private Animation clipAnimation(Path path) {
    Pane clip = new Pane();
    path.clipProperty().set(clip);

    ChangeListener pen_Listener = (o1,o2,o3) -> {
      Circle clip_eraser = new Circle(pen.getTranslateX(), pen.getTranslateY(), pen.getRadius());
      clip.getChildren().add(clip_eraser);
    };

    pen.translateXProperty().addListener(pen_Listener);
    pen.translateYProperty().addListener(pen_Listener);
    PathTransition pathTransition = new PathTransition(Duration.seconds(turtleSpeed.getValue()), path, pen);
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
    TranslateTransition t1 = moveTurtle(statX,-1 * statY,statHeading * -1 + 90);
    t1.play();
    return t1;
  }

  private TranslateTransition moveTurtle(double xFinal, double yFinal, double heading) {
    turtle.changeHeading(heading);
    TranslateTransition trans = new TranslateTransition(Duration.seconds(turtleSpeed.getValue()), turtle);
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
    turtle.setX(rect.getBoundsInParent().getCenterX() + SPACING_CONSTANT + Turtle.size / 2);
    turtle.setY(rect.getBoundsInParent().getCenterY() + SPACING_CONSTANT + Turtle.size / 2);
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
//    while(!queue.isEmpty()) {
//      System.out.println(queue.remove().get(MovingObjectProperties.Y));} //to show issue with queue
    recurse();
    updateStatsPopUp();
  }

  public void updateDisplayWords() {
    rect.getStyleClass().add(res.getString("StyleClass"));
  }

  // if Pen is Up, return true, else return false
  private String penUpDown(){
    if (markerColor == null){
      return "Pen Up";
    }
    return "Pen Down";
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
