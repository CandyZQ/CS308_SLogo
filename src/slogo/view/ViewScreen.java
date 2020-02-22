package slogo.view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import javafx.stage.Stage;

public class ViewScreen {

  private Stage stage;
  private Scene scene;
  private BorderPane root;

  public ViewScreen(Stage stage) {
    this.stage = stage;
    //Will need to make this a border pane and use resource sheets
    this.root = new BorderPane();
    addBoundsRectangle();
    addTextField();
    setAsScene(new Scene(root, ObjectsViewable.STAGE_WIDTH, ObjectsViewable.STAGE_HEIGHT));

  }

  private void setAsScene(Scene scene) {
    this.scene = scene;
  }

  private void addBoundsRectangle() {
    BoundsRectanglesObjectsViewable rect = new BoundsRectanglesObjectsViewable();
    root = rect.createRootObject(root);
  }

  private void addTextField() {
    InputTextFieldObjectsViewable textField = new InputTextFieldObjectsViewable();
    root = textField.createRootObject(root);
    root = textField.editRoot(root);
  }


  public Scene getScene() {
    return scene;
  }
}
