package slogo.view;

import java.util.EnumMap;
import java.util.Queue;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import slogo.controller.MovingObjectProperties;

import java.io.File;

public class SubSceneLeft extends SubScene {

  private ImageView turtle = new ImageView(new Image("file:resources/defaultTurtle.png"));
  private Rectangle rect;
  private Slider slider;
  private TranslateTransition trans;
  private int initialX;
  private int initialY;
  private int TURTLE_X = 280;
  private int TURTLE_Y = 230;
  private final int TURTLE_SIZE = 60; // turtle is 60 px x 60 px
  private static final int TURTLE_INITAL_X = 280;
  private static final int TURTLE_INITAL_Y = 230;
  private Path path;
  private Color markerColor;


  public SubSceneLeft() {
    root = new Group();
    vBox = new VBox();
    vBox.getStyleClass().add("leftvbox");
    root.getChildren().add(vBox);
    createRectangle();
    createSlider();
    path = new Path();
    root.getChildren().addAll(path);
    root.getChildren().add(createTurtle());
    initialX = 0;
    initialY = 0;

    TranslateTransition t1 = moveTurtle(TURTLE_INITAL_X + 50, TURTLE_INITAL_Y + 50, 135, 5);
    Animation path_animation = clipAnimation(path);
    path_animation.play();
    SequentialTransition s = new SequentialTransition(t1);
    s.play();
  }

  private Animation clipAnimation(Path path)
  {
    final Pane clip = new Pane();
    path.clipProperty().set(clip);

    final Circle pen = new Circle(0, 0, 2);

    ChangeListener pen_Listener = new ChangeListener()
    {
      @Override
      public void changed(ObservableValue observableValue, Object o1, Object o2)
      {
        Circle clip_eraser = new Circle(pen.getTranslateX(), pen.getTranslateY(), pen.getRadius());
        clip.getChildren().add(clip_eraser);
      }
    };

    pen.translateXProperty().addListener(pen_Listener);
    pen.translateYProperty().addListener(pen_Listener);
    //pen.rotateProperty().addListener(pen_Listener);
    PathTransition pathTransition = new PathTransition(Duration.seconds(5), path, pen);
    pathTransition.setOnFinished(new EventHandler<ActionEvent>()
    {
      @Override
      public void handle(ActionEvent t)
      {
        path.setClip(null);
        clip.getChildren().clear();
      }
    });

    return pathTransition;
  }

  private TranslateTransition moveTurtle(int xfinal, int yfinal, int heading, int duration) {
    turtle.setRotate(heading);
    path.getElements().addAll(
            new MoveTo(TURTLE_X + 30, TURTLE_Y + 30),
            new LineTo(xfinal + 30, yfinal + 30)

    );
    path.setFill(null);
    path.setStroke(markerColor);
    path.setStrokeWidth(2);

    trans = new TranslateTransition(Duration.seconds(duration), turtle); // slider.getValue() for Duration
    trans.setFromX(initialX);
    trans.setFromY(initialY);
    trans.setToX(initialX + (xfinal - TURTLE_X));
    trans.setToY(initialY + (yfinal - TURTLE_Y));
    initialX += xfinal - TURTLE_X;
    initialY += yfinal - TURTLE_Y;
    TURTLE_X = xfinal;
    TURTLE_Y = yfinal;
    return trans;


  }

  private ImageView createTurtle() {
    turtle.setX(TURTLE_X);
    turtle.setY(TURTLE_Y);
    return turtle;
  }

  public void setTurtle(Image newTurtle){
    turtle.setImage(newTurtle);
  }

  private void createRectangle() {
    rect = new Rectangle(ViewScreen.STAGE_WIDTH / 2, ViewScreen.STAGE_HEIGHT / 2,
        SubSceneRight.INITIAL_BACKGROUND_COLOR);
    rect.getStyleClass().add("rectangle");
    vBox.getChildren().add(rect);
  }

  private void createSlider() {
    slider = new Slider(1, 10, 5.5);
    vBox.getChildren().add(slider);
  }

  @Override
  public void update(Queue<EnumMap<MovingObjectProperties, Object>> commands) {

  }

  public void setRectangleColor(Color color) {
    rect.setFill(color);
  }

  public void getMarkerColor(Color color) {
    markerColor = color;
  }

}
