package slogo.view;

import java.util.EnumMap;

import java.util.Locale;
import java.util.Queue;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.scene.Group;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.VBox;
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
  private Slider slider;
  private double initialX;
  private double initialY;
  private TextField tf;
  private Color markerColor;

  private Path path;
  private Queue<EnumMap<MovingObjectProperties, Object>> queue;


  public SubSceneLeft() {
    root = new Group();
    vBox = new VBox();
    vBox.getStyleClass().add("VBoxStyle");
    root.getChildren().add(vBox);
    createRectangle();
    createSlider();
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
    PathTransition pathTransition = new PathTransition(Duration.seconds(slider.getValue()), path,
        pen);
    pathTransition.setOnFinished(t -> {
      path.setClip(null);
      clip.getChildren().clear();
    });

    return pathTransition;
  }


  private TranslateTransition moveTurtle(double xFinal, double yFinal, double heading) {
    turtle.setRotate(heading);
    TranslateTransition trans = new TranslateTransition(Duration.seconds(slider.getValue()),
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
    path.setStrokeWidth(2);

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
    rect.getStyleClass().add("StyleClass");
    vBox.getChildren().add(rect);
  }

  private void createSlider() {
    slider = new Slider(SLIDER_LOW_VALUE, SLIDER_HIGH_VALUE, SLIDER_STARTING_VALUE);
    vBox.getChildren().add(slider);
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

  public void listenToDisableTextField(TextField tf) {
    this.tf = tf;
  }
}
