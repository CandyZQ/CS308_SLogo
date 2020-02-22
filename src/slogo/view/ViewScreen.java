package slogo.view;

import java.awt.Button;
import java.util.Map;
import java.util.Queue;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import javafx.stage.Stage;

public class ViewScreen implements ExternalAPIViewable {

  private Stage stage;
  private Scene scene;
  private BorderPane root;

  public ViewScreen(Stage stage) {
    this.stage = stage;
    stage.setMaxHeight(ObjectsViewable.STAGE_HEIGHT);
    stage.setMinHeight(ObjectsViewable.STAGE_HEIGHT);
    stage.setMaxWidth(ObjectsViewable.STAGE_WIDTH);
    stage.setMinWidth(ObjectsViewable.STAGE_WIDTH);
    startView();
    stage.show();
  }

  private void startView() {
    this.root = new BorderPane();
    addBoundsRectangle();
    addTextField();
    addColorPicker();
    setAsScene(new Scene(root, ObjectsViewable.STAGE_WIDTH, ObjectsViewable.STAGE_HEIGHT));
    stage.setScene(scene);

  }

  private void addButtons(){
    ButtonObjectsViewable blah = new ButtonObjectsViewable();
    blah.createRootObject(root);
  }

  private void addColorPicker() {
    ColorPickerViewable cp = new ColorPickerViewable();
    root = cp.createRootObject(root);

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


//  private Scene getScene() {
//    return scene;
//  }

  @Override
  public Queue<Map<String, Integer>> getFinalInformation() {
    return null;
  }

  @Override
  public String giveInputString() {
    return null;
  }

  @Override
  public void exceptionHandling() throws Exception {

  }

  @Override
  public Stage setScene() {
    return null;
  }


  @Override
  public String setLanguage() {
    return null;
  }
}
