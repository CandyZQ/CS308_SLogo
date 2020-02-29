package slogo.view;

import java.util.*;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import slogo.controller.listings.MovingObjectProperties;

public class SubSceneLeft extends SubScene {


  private static final int INITIAL_TURTLE_X = 280;
  private static final int INITIAL_TURTLE_Y = 230;
  private final double TURTLE_SIZE = 60; // turtle is 60 px x 60 px
  private static final double SLIDER_LOW_VALUE = 0.01;
  private static final double SLIDER_HIGH_VALUE = 10;
  private static final int SLIDER_STARTING_VALUE = 2;

  private static ResourceBundle myResources =
          ResourceBundle.getBundle("resources", Locale.getDefault());

  private ImageView turtle = new ImageView(new Image(myResources.getString("Turtle")));
  private Rectangle rect;
  private Slider turtleSpeed;
  private Slider thick;
  private double initialX;
  private double initialY;
  private TextField tf;
  private Color markerColor;
  private double markerThickness;

  private ArrayList<Path> pathList;
  private Queue<EnumMap<MovingObjectProperties, Object>> queue;


  public SubSceneLeft() {
    markerThickness = 2;
    root = new Group();
    vBox = new VBox();
    vBox.getStyleClass().add(myResources.getString("VBoxStyle"));
    root.getChildren().add(vBox);
    createRectangle();
    Label label1 = new Label("Turtle Speed");
    turtleSpeed = createSlider();
    vBox.getChildren().add(label1);
    vBox.getChildren().add(turtleSpeed);
    Label label2 = new Label("Marker Thickness");
    vBox.getChildren().add(label2);
    thick = createSlider();
    vBox.getChildren().add(thick);
    createButtons("FD 50", "BK 50", "LT 50", "RT 50");
    root.getChildren().add(createTurtle());
    initialX = 0;
    initialY = 0;

  }

  private Animation clipAnimation(Path path) {
    Pane clip = new Pane();
    path.clipProperty().set(clip);

    Circle pen = new Circle(0, 0, 2);

    ChangeListener pen_Listener = (observableValue, o1, o2) -> {
      Circle clip_eraser = new Circle(pen.getTranslateX(), pen.getTranslateY(), pen.getRadius());
      clip.getChildren().add(clip_eraser);
    };

    pen.translateXProperty().addListener(pen_Listener);
    pen.translateYProperty().addListener(pen_Listener);
    PathTransition pathTransition = new PathTransition(Duration.seconds(turtleSpeed.getValue()), path,
        pen);
    pathTransition.setOnFinished(t -> {
      path.setClip(null);
      clip.getChildren().clear();
    });

    return pathTransition;
  }

  private void createButtons(String firstName, String secondName, String thirdName,
                             String fourthName) {
    HBox hbox1 = new HBox(30);
    HBox hbox2 = new HBox(30);
    hbox1.getStyleClass().add(myResources.getString("HBox"));
    hbox2.getStyleClass().add(myResources.getString("HBox"));
    Button firstButton = new Button(firstName);
    Button secondButton = new Button(secondName);
    Button thirdButton = new Button(thirdName);
    Button fourthButton = new Button(fourthName);
    hbox1.getChildren().addAll(firstButton, secondButton);
    hbox2.getChildren().addAll(thirdButton, fourthButton);
    hbox1.setAlignment(Pos.CENTER);
    hbox2.setAlignment(Pos.CENTER);

    this.buttonListeners(firstButton, secondButton, thirdButton, fourthButton);
    vBox.getChildren().addAll(hbox1, hbox2);
  }

  private void buttonListeners(Button firstButton, Button secondButton, Button thirdButton,
                               Button fourthButton) {

    //firstButton.setOnAction(event ->); // fd 50

    //secondButton.setOnAction(event -> displayPopUp());

  }


  private TranslateTransition moveTurtle(double xFinal, double yFinal, double heading) {
    turtle.setRotate(heading);
    TranslateTransition trans = new TranslateTransition(Duration.seconds(turtleSpeed.getValue()),
        turtle);
    trans.setFromX(initialX);
    trans.setFromY(initialY);

    trans.setToX(xFinal);
    trans.setToY(yFinal);

    Path path = new Path();
    root.getChildren().addAll(path);

    path.getElements().addAll(

        new MoveTo(INITIAL_TURTLE_X + initialX + TURTLE_SIZE / 2,
            INITIAL_TURTLE_Y + initialY + TURTLE_SIZE / 2),
        new LineTo(INITIAL_TURTLE_X + xFinal + TURTLE_SIZE / 2,
            INITIAL_TURTLE_Y + yFinal + TURTLE_SIZE / 2)

    );
    path.setFill(null);
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
  public void update(Queue<EnumMap<MovingObjectProperties, Object>> queue) {
    this.queue = queue;
    recurse();
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
    EnumMap<MovingObjectProperties, Object> movements = queue.remove();
    TranslateTransition t1 = moveTurtle(-1 * (Double) movements.get(MovingObjectProperties.X),
        -1 * (Double) movements.get(MovingObjectProperties.Y),
        (Double) movements.get(MovingObjectProperties.HEADING) * -1 + 90);
    t1.play();
    return t1;
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
