package slogo.view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ViewScreen {

  private Stage stage;
  private Scene scene;
  private Group root;

  public ViewScreen(Stage stage) {
    this.stage = stage;
    //Will need to make this a border pane and use resource sheets
    this.root = new Group();
    addTextField();
    addBoundsRectangle();
    setAsScene(new Scene(this.root, ObjectsViewable.STAGE_WIDTH, ObjectsViewable.STAGE_HEIGHT));

  }

  private void setAsScene(Scene scene) {
    this.scene = scene;
  }

  private void addBoundsRectangle() {
//    BoundsRectanglesObjectsViewable rect = new BoundsRectanglesObjectsViewable();
//    root = rect.createRootObject(root);
    Rectangle boundsRect = new Rectangle();
    boundsRect.setY(50);
    boundsRect.setX(50);
    boundsRect.setFill(Color.BLACK);
    boundsRect.setHeight(ObjectsViewable.STAGE_HEIGHT - 100);
    boundsRect.setWidth(ObjectsViewable.STAGE_WIDTH/2 - 100);
    root.getChildren().add(boundsRect);
    System.out.println(root.getChildren());

  }

  private void addTextField() {
  }


  public Scene getScene() {
    return scene;
  }
}
