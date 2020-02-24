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

public class SubSceneRight extends SubScene {

  public static final Color INITIAL_BACKGROUND_COLOR = Color.WHITE;
  public static final Color INITIAL_MARKER_COLOR = null;
  private static final String SUCCESSFUL_COMMAND = "Command successfully processed!";
  private static final String EMPTY_COMMAND = "No command entered.";
  private static final String NEW_MARKER_COLOR = "New Marker Color Chosen: ";
  private static final String NEW_LANGUAGE = "New Chosen Language: ";
  private static final String NEW_BACKGROUND_COLOR = "New Background Color Chosen: ";
  private static final String COMMAND_AREA_TEXT = "Command Display";
  private static final String[] language_names = {"Chinese", "English", "French", "German",
      "Italian", "Portuguese", "Russian", "Spanish",
      "Syntax", "Urdu"};
  private static final String BACKGROUND_COLOR_LABEL = "Change Background Color";
  private static final String MARKER_COLOR_LABEL = "Change Marker Color";
  private static final String CHANGE_LANGUAGE_LABEL = "Change Language";
  private static final String TEXTFIELD_PROMPT_TEXT = "Enter an SLogo Command.";
  private static final String VARIABLE_AREA_TEXT = "Variable Display";

  private ColorPicker cp;
  private Object language;
  private Color clickedColor = INITIAL_BACKGROUND_COLOR;
  private Color markerClickedColor = INITIAL_MARKER_COLOR;
  private TextField name;
  private TextArea textArea;
  private String theText;
  private Boolean commandEntered = false;

  public SubSceneRight() {
    root = new Group();
    vBox = new VBox();
    vBox.getStyleClass().add("vbox");
    root.getChildren().add(vBox);
    createLabel(vBox, BACKGROUND_COLOR_LABEL);
    createBackgroundColorPicker();
    createButtons("Open File", "Load Turtle");
    createHBox();
    createTextArea(COMMAND_AREA_TEXT);
    createTextField();
    createButtons("Help", "Undo");
    createTextArea(VARIABLE_AREA_TEXT);
  }

  private void createHBox() {
    HBox hBox = new HBox();
    hBox.getStyleClass().add("hbox");
    VBox vBoxLeft = new VBox();
    VBox vBoxRight = new VBox();
    createLabel(vBoxRight, MARKER_COLOR_LABEL);
    createMarkerColorPicker(vBoxRight);
    createLabel(vBoxLeft, CHANGE_LANGUAGE_LABEL);
    createComboBox(vBoxLeft);
    hBox.getChildren().addAll(vBoxLeft, vBoxRight);
    vBox.getChildren().add(hBox);
  }

  private void createMarkerColorPicker(Pane pane) {
    ColorPicker markerCP = new ColorPicker(INITIAL_MARKER_COLOR);
    pane.getChildren().add(markerCP);
    markerCP.setOnAction(event -> {
      markerClickedColor = markerCP.getValue();
      textArea
          .setText(textArea.getText() + "\n" + NEW_MARKER_COLOR + markerClickedColor.toString());
    });
  }

  private void createComboBox(Pane box) {
    ComboBox<String> combo_box = new ComboBox<>(FXCollections.observableArrayList(language_names));
    box.getChildren().add(combo_box);
    combo_box.setOnAction(event -> {
      language = combo_box.getValue();
      textArea.setText(textArea.getText() + "\n" + NEW_LANGUAGE + language);
    });
  }

  private void createTextArea(String text) {
    textArea = new TextArea(text);
    textArea.setEditable(false);
    vBox.getChildren().add(textArea);
  }

  private void createLabel(Pane pane, String text) {
    Label label = new Label(text + ':');
    pane.getChildren().addAll(label);
  }

  private void createButtons(String leftButtonText, String rightButtonText) {
    HBox hbox = new HBox();
    hbox.getStyleClass().add("hbox");
    Button leftButton = new Button(leftButtonText);
    Button rightButton = new Button(rightButtonText);
    hbox.getChildren().addAll(leftButton, rightButton);
    hbox.setAlignment(Pos.CENTER);
    vBox.getChildren().add(hbox);
  }

  private void createTextField() {
    name = new TextField();
    name.setPromptText(TEXTFIELD_PROMPT_TEXT);
    name.getText();
    vBox.getChildren().add(name);

    //Setting an action for the Submit button
    root.setOnKeyPressed(ke -> textFieldListener(ke.getCode()));
  }


  private void textFieldListener(KeyCode code) {
    if (code == KeyCode.ENTER) {
      if ((name.getText() != null && !name.getText().isEmpty())) {
        theText = name.getText().toLowerCase();
        commandEntered = true;
        textArea.setText(textArea.getText() + "\n" + theText);
        textArea.setText(textArea.getText() + "\n" + SUCCESSFUL_COMMAND);
      } else {
        textArea.setText(textArea.getText() + "\n" + EMPTY_COMMAND);
      }
      name.clear();
    }
  }

//  private void createRectangle() {
//    rect = new Rectangle(50, 50, Color.BLUE);
//    vBox.getChildren().add(rect);
//  }

  private void createBackgroundColorPicker() {
    cp = new ColorPicker(INITIAL_BACKGROUND_COLOR);
    vBox.getChildren().add(cp);

    cp.setOnAction(event -> {
      clickedColor = cp.getValue();
      textArea.setText(textArea.getText() + "\n" + NEW_BACKGROUND_COLOR + clickedColor.toString());
    });
  }

  public Group getRoot() {
    return root;
  }

  public Color getClickedColor() {
    return clickedColor;
  }

  public Object getLanguage() {
    return language;
  }

  public String getTheText() {
    if (commandEntered) {
      commandEntered = false;
      return theText;
    }
    return null;
  }

}
