package slogo.view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/*
View Manager will require these simple lines of code to create a textfield object

*         InputTextFieldObjectsViewable textField = new InputTextFieldObjectsViewable();
        BorderPane root = new BorderPane();
        root = textField.createRootObject(root);
        root = textField.editRoot(root);
        Scene sc = new Scene(root, 800, 800);
        s.setScene(sc);
        s.show();
        *
        * */

public class InputTextFieldObjectsViewable extends ObjectsViewable {

  private String text;
  private TextField name;
  private GridPane grid;
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
    root.setTop(name);
    //root.getChildren().add(name);

    Label label = new Label();
    root.setLeft(label);
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
