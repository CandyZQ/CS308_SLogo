package slogo.view;


import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;

public class SubScene {

  public static final Color INITIAL_BACKGROUND_COLOR = Color.WHITE;
  public static final Color INITIAL_MARKER_COLOR = null;
  private Group root;
  private VBox vBox;
  private ColorPicker cp;
  private Object language;
  private Color clickedColor = INITIAL_BACKGROUND_COLOR;
  private Color markerClickedColor = INITIAL_MARKER_COLOR;
  private TextField name;
  private TextArea textArea;

  public SubScene() {
    root = new Group();
    vBox = new VBox(20);
    root.getChildren().add(vBox);
    createLabel(vBox, "Change Background Color");
    createBackgroundColorPicker();
    createButtons("Open File", "Load Turtle");
    createHBox();
    //createRectangle();
    createTextArea();
    createTextField();
    vBox.setAlignment(Pos.CENTER);
  }

  private void createHBox() {
    HBox hBox = new HBox(10);
    VBox vBoxLeft = new VBox();
    VBox vBoxRight = new VBox();
    createLabel(vBoxRight,"Change Marker Color");
    createMarkerColorPicker(vBoxRight);
    createLabel(vBoxLeft, "Change Language");
    createComboBox(vBoxLeft);
    hBox.getChildren().addAll(vBoxLeft, vBoxRight);
    vBox.getChildren().add(hBox);
  }

  private void createMarkerColorPicker(Pane pane) {
    ColorPicker markerCP = new ColorPicker(INITIAL_MARKER_COLOR);
    pane.getChildren().add(markerCP);
    markerCP.setOnAction(event ->  {
    markerClickedColor = markerCP.getValue();
    textArea.setText(textArea.getText() + "\n" + "New Marker Color Chosen: " + markerClickedColor.toString());
  });
  }

  private void createComboBox(Pane box) {
    String[] week_days =
        { "Chinese", "English", "French", "German", "Italian", "Portuguese", "Russian", "Spanish", "Syntax", "Urdu" };
    ComboBox<String> combo_box = new ComboBox<>(FXCollections.observableArrayList(week_days));
    box.getChildren().add(combo_box);
    combo_box.setOnAction(event ->  {
      language = combo_box.getValue();
      textArea.setText(textArea.getText() + "\n" + "New Chosen Language: " + language);
    });
  }

  private void createTextArea() {
    textArea = new TextArea("Type Commands Here");
    textArea.setPrefColumnCount(1);
    textArea.setPrefRowCount(10);
    textArea.setEditable(false);
    vBox.getChildren().add(textArea);
  }

  private void createLabel(Pane pane, String text) {
    Label label = new Label(text + ':');
    pane.getChildren().addAll(label);
  }

  private void createButtons(String leftButtonText, String rightButtonText) {
    HBox hbox = new HBox();
    Button leftButton = new Button(leftButtonText);
    Button rightButton = new Button(rightButtonText);
    hbox.getChildren().addAll(leftButton, rightButton);
    hbox.setAlignment(Pos.CENTER);
    vBox.getChildren().add(hbox);
  }

  private void createTextField() {
    name = new TextField();
    name.setPromptText("Enter an SLogo Command.");
    name.setPrefColumnCount(10);
    name.getText();
    vBox.getChildren().add(name);

    Label label = new Label();
    vBox.getChildren().add(label);


    //Setting an action for the Submit button
    root.setOnKeyPressed(ke -> {
      if(ke.getCode() == KeyCode.ENTER){
        if ((name.getText() != null && !name.getText().isEmpty())) {
          String the_text = name.getText();
          textArea.setText(textArea.getText() + "\n" + the_text);
          name.clear();
          label.setText(null);
          label.setText("Command successfully processed!");
        } else {
          label.setText("No command entered.");
        }
      }

    });
  }

//  private void createRectangle() {
//    rect = new Rectangle(50, 50, Color.BLUE);
//    vBox.getChildren().add(rect);
//  }

  private void createBackgroundColorPicker() {
    cp = new ColorPicker(INITIAL_BACKGROUND_COLOR);
    vBox.getChildren().add(cp);

    cp.setOnAction(event ->  {
      clickedColor = cp.getValue();
      textArea.setText(textArea.getText() + "\n" + "New Background Color Chosen: " + clickedColor.toString());
    });
  }


  public Group getRoot(){return root;}

  public Color getClickedColor() {return clickedColor;}

  public Object getLanguage() {return language;}

}
