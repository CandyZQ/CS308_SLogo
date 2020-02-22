package slogo.view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;


public class InputTextFieldObjectsViewable extends ObjectsViewable {

  private TextField name;
  public InputTextFieldObjectsViewable(){

  }

  @Override
  public BorderPane createRootObject(BorderPane root) {
    name = new TextField();
    name.setPromptText("Enter an SLogo Command.");
    name.setPrefColumnCount(10);
    return root;
  }

  @Override
  public BorderPane editRoot(BorderPane root) {


    name.getText();
    root.setRight(name);
    //root.getChildren().add(name);

    Label label = new Label();
    root.setBottom(label);
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
    return root;
  }

  @Override
  public BorderPane removeFromRoot(BorderPane root) {
    //root.getChildren().remove(field);
    return null;
  }

}
