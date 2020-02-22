package slogo.view;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SubScene {

  private Group root;
  private VBox vbox;
  private Rectangle rect;
  private ColorPicker cp;
  private Color clickedColor;
  private TextField name;

  public SubScene() {
    root = new Group();
    vbox = new VBox();
    root.getChildren().add(vbox);
    createColorPicker();
    createTextField();
    createButtons();
    createRectangle();

  }

  private void createButtons() {
    HBox hbox = new HBox(20);
    Button leftButton = new Button();
    Button rightButton = new Button();
    hbox.getChildren().addAll(leftButton, rightButton);
    vbox.getChildren().add(hbox);
  }

  private void createTextField() {
    name = new TextField();
    name.setPromptText("Enter an SLogo Command.");
    name.setPrefColumnCount(10);
    name.getText();
    vbox.getChildren().add(name);


    Label label = new Label();
    vbox.getChildren().add(label);
    //root.getChildren().add(label);


    //Setting an action for the Submit button
    root.setOnKeyPressed(new EventHandler<KeyEvent>() {

      @Override
      public void handle(KeyEvent ke) {
        if(ke.getCode() == KeyCode.ENTER){
          if ((name.getText() != null && !name.getText().isEmpty())) {
            String the_text = name.getText();
            System.out.println(the_text);
            name.clear();
            label.setText(null);
            label.setText("Command successfully processed!");
          } else {
            label.setText("No command entered.");
          }
        }

      }
    });
  }

  private void createRectangle() {
    rect = new Rectangle(50, 50, Color.BLUE);
    vbox.getChildren().add(rect);
  }

  private void createColorPicker() {
    cp = new ColorPicker(Color.BLUE);
    vbox.getChildren().add(cp);

    cp.setOnAction(new EventHandler() {
      public void handle(Event t) {
        clickedColor = cp.getValue();
      }
    });
  }

  public Group getRoot(){return root;}

  public Color getClickedColor() {return clickedColor;}

  private void editRectangle(Color color) {
    rect.setFill(color);
  }


}
