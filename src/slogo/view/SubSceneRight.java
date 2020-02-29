package slogo.view;


import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import slogo.controller.listings.MovingObjectProperties;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class SubSceneRight extends SubScene {


  private final ImageView helpImage0 = new ImageView(new Image("file:resources/help_title.png"));
  private final ImageView helpImage1 = new ImageView(new Image("file:resources/basic_syntax.png"));
  private final ImageView helpImage2 = new ImageView(
      new Image("file:resources/turtle_commands.png"));
  private final ImageView helpImage3 = new ImageView(
      new Image("file:resources/turtle_queries.png"));
  private final ImageView helpImage4 = new ImageView(
      new Image("file:resources/math_operations.png"));
  private final ImageView helpImage5 = new ImageView(
      new Image("file:resources/boolean_operations.png"));
  private final ImageView helpImage6 = new ImageView(new Image("file:resources/user_defined.png"));


  private Image turtle = new Image("file:resources/defaultTurtle.png");
  public static final Color INITIAL_BACKGROUND_COLOR = Color.WHITE;
  public static final Color INITIAL_MARKER_COLOR = null; // the code for having the pen up
  public static final String SUCCESSFUL_COMMAND = "Command successfully processed!";
  public static final String FAILED_COMMAND = "Command not recognized";
  private static final String EMPTY_COMMAND = "No command entered.";
  private static final String NEW_MARKER_COLOR = "New Marker Color Chosen: ";
  private static final String NEW_LANGUAGE = "New Chosen Language: ";
  private static final String NEW_BACKGROUND_COLOR = "New Background Color Chosen: ";
  private static final String COMMAND_AREA_TEXT = "Command Display";
  private final String[] language_names = {"Chinese", "English", "French", "German",
      "Italian", "Portuguese", "Russian", "Spanish", "Urdu"};
  private static final String BACKGROUND_COLOR_LABEL = "Change Background Color";
  private static final String MARKER_COLOR_LABEL = "Change Marker Color";
  private static final String CHANGE_LANGUAGE_LABEL = "Change Language";
  private static final String TEXTFIELD_PROMPT_TEXT = "Enter an SLogo Command.";
  private static final String VARIABLE_AREA_TEXT = "Variable Display";
  private static final String USER_TEXT_AREA = "User Defined Commands Display";
  private final FileChooser fileChooser = new FileChooser();


  private ColorPicker cp;
  private Object language;
  private Color clickedColor = INITIAL_BACKGROUND_COLOR;
  private Color markerClickedColor = INITIAL_MARKER_COLOR;
  private TextField name;
  private TextArea commandTextArea;
  private TextArea variableTextArea;
  private TextArea userDefinedCommandsTextArea;
  private String theText;
  private Boolean commandEntered = false;
  private Stage stage;
  private String commandText;

  public SubSceneRight() {
    root = new Group();
    vBox = new VBox();
    vBox.getStyleClass().add("vbox");
    root.getChildren().add(vBox);
    createLabel(vBox, BACKGROUND_COLOR_LABEL);
    createBackgroundColorPicker();
    createButtons("Load Turtle", "Get Help", "Reset", "Undo");
    createHBox();
    createTextArea(commandTextArea = new TextArea(), COMMAND_AREA_TEXT);
    createTextField();
    createTextArea(variableTextArea = new TextArea(), VARIABLE_AREA_TEXT);
    createTextArea(userDefinedCommandsTextArea = new TextArea(), USER_TEXT_AREA);
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
      commandTextArea
          .setText(
              commandTextArea.getText() + "\n" + NEW_MARKER_COLOR + markerClickedColor.toString());
    });
  }

  private void createComboBox(Pane box) {
    ComboBox<String> combo_box = new ComboBox<>(FXCollections.observableArrayList(language_names));
    box.getChildren().add(combo_box);
    combo_box.setValue(language_names[1]);
    language = combo_box.getValue();
    combo_box.setOnAction(event -> {
      language = combo_box.getValue();
      commandTextArea.setText(commandTextArea.getText() + "\n" + NEW_LANGUAGE + language);
    });
  }

  private void createTextArea(TextArea area, String text) {
    area.setText(text);
    area.setEditable(false);
    vBox.getChildren().add(area);
  }

  private void createLabel(Pane pane, String text) {
    Label label = new Label(text + ':');
    pane.getChildren().addAll(label);
  }


  private void createButtons(String firstName, String secondName, String thirdName,
      String fourthName) {
    HBox hbox1 = new HBox(30);
    HBox hbox2 = new HBox(30);
    hbox1.getStyleClass().add("hbox");
    hbox2.getStyleClass().add("hbox");
    Button firstButton = new Button(firstName);
    Button secondButton = new Button(secondName);
    Button thirdButton = new Button(thirdName);
    Button fourthButton = new Button(fourthName);
    hbox1.getChildren().addAll(firstButton, secondButton);
    hbox2.getChildren().addAll(thirdButton, fourthButton);
    hbox1.setAlignment(Pos.CENTER);
    hbox2.setAlignment(Pos.CENTER);

    this.buttonListeners(firstButton, secondButton, thirdButton, fourthButton);
    vBox.getChildren().addAll(hbox1, hbox2);
  }

  private void buttonListeners(Button firstButton, Button secondButton, Button thirdButton,
      Button fourthButton) {

    firstButton.setOnAction(event -> setTurtleImage());

    secondButton.setOnAction(event -> displayPopUp());

    /*
    thirdButton.setOnAction(event ->{
      moveTurtle(fourthButton);
    });*/

  }


  public void setTurtleImage() {
    File file = fileChooser.showOpenDialog(stage);
    if (file != null) {
      turtle = new Image(file.toURI().toString(), 60, 60, false, true);
      //turtle.setX(150);
      //turtle.setY(150);
    }
  }


  public void assignStage(Stage incoming) {
    stage = incoming;
  }

  private void displayPopUp() {
    BorderPane helpRoot = new BorderPane();
    Stage helpStage = new Stage();
    helpStage.setTitle("Help Screen");
    VBox vb = new VBox();

    vb.getChildren()
        .addAll(helpImage0, helpImage1, helpImage2, helpImage3, helpImage4, helpImage5, helpImage6);
    ScrollBar s1 = new ScrollBar();
    s1.setMin(0);
    s1.setMax(1400);
    s1.setOrientation(Orientation.VERTICAL);
    helpRoot.getChildren().add(vb);
    helpRoot.setRight(s1);
    this.listenVBoxScroll(s1, vb);
    Scene errorScene = setUpPopUp(helpRoot);
    helpStage.setScene(errorScene);
    helpStage.show();
  }


  private Scene setUpPopUp(BorderPane helpRoot) {

    return new Scene(helpRoot, 600, 800, Color.LIGHTBLUE);
  }

  private void listenVBoxScroll(ScrollBar sb, VBox vb) {
    sb.valueProperty().addListener((ov, old_val, new_val) -> vb.setLayoutY(-new_val.doubleValue()));

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
        commandTextArea.setText(commandTextArea.getText() + "\n" + theText);
      } else {
        commandTextArea.setText(commandTextArea.getText() + "\n" + EMPTY_COMMAND);
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
      commandTextArea.setText(
          commandTextArea.getText() + "\n" + NEW_BACKGROUND_COLOR + clickedColor.toString());
    });
  }

  public void setVariableTextArea(Map<String, Double> vars) {
    variableTextArea.setText(VARIABLE_AREA_TEXT);
    for (Map.Entry<String, Double> entry : vars.entrySet()) {
      variableTextArea.setText(
          variableTextArea.getText() + "\n" + entry.getKey().substring(1) + " = " + entry
              .getValue());
    }
  }

  public void setUserTextArea(Map<String, List<String>> functions) {
    userDefinedCommandsTextArea.setText(USER_TEXT_AREA);
    for (Map.Entry<String, List<String>> entry : functions.entrySet()) {
      userDefinedCommandsTextArea.setText(
          userDefinedCommandsTextArea.getText() + "\n" + entry.getKey() + " : " + entry
              .getValue());
    }
  }

  @Override
  public void update(Queue<EnumMap<MovingObjectProperties, Object>> movements) {
  }

  public void setCommandText(String response) {
    commandText = response;
    commandTextArea.setText(commandTextArea.getText() + "\n" + commandText);
  }

  public Image getTurtle() {
    return turtle;
  }

  public TextField getTextField() {
    return name;
  }

  public Color getClickedColor() {
    return clickedColor;
  }

  public Object getLanguage() {
    return language;
  }

  public Color getMarkerClickedColor() {
    return markerClickedColor;
  }

  public String getTheText() {
    if (commandEntered) {
      commandEntered = false;
      return theText;
    }
    return null;
  }

}
