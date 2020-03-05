package slogo.view;


import java.util.*;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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

  private final ImageView helpImage0 = new ImageView(new Image(myResources.getString("HelpTitle")));
  private final ImageView helpImage1 = new ImageView(new Image(myResources.getString("BasicSyntax")));
  private final ImageView helpImage2 = new ImageView(new Image(myResources.getString("TurtleCommands")));
  private final ImageView helpImage3 = new ImageView(new Image(myResources.getString("TurtleQueries")));
  private final ImageView helpImage4 = new ImageView(
      new Image(myResources.getString("MathOperations")));
  private final ImageView helpImage5 = new ImageView(
      new Image(myResources.getString("BooleanOperations")));
  private final ImageView helpImage6 = new ImageView(
      new Image(myResources.getString("UserDefined")));


  private Turtle turtle = new Turtle(myResources.getString("Turtle"), 0);
  public static final Color INITIAL_BACKGROUND_COLOR = Color.WHITE;
  public static final Color INITIAL_MARKER_COLOR = null; // the code for having the pen up
  public static String SUCCESSFUL_COMMAND = myResources.getString("SuccessCommand");
  private static String EMPTY_COMMAND = myResources.getString("EmptyCommand");
  private static String NEW_MARKER_COLOR = myResources.getString("NewMarkerColor") + " ";
  private static String NEW_LANGUAGE = myResources.getString("NewLanguage") + " ";
  private static String NEW_BACKGROUND_COLOR =
      myResources.getString("NewBackgroundColor") + " ";
  private static String COMMAND_AREA_TEXT = myResources.getString("CommandArea");
  private static List<String> language_names = new ArrayList<>();
  private static String BACKGROUND_COLOR_LABEL = myResources
      .getString("BackgroundColorLabel");
  private static String MARKER_COLOR_LABEL = myResources.getString("MarkerColorLabel");
  private static String CHANGE_LANGUAGE_LABEL = myResources.getString("ChangeLanguageLabel");
  private static String TEXTFIELD_PROMPT_TEXT = myResources.getString("TextFieldPromptText");
  private static String VARIABLE_AREA_TEXT = myResources.getString("VariableAreaText");
  private static String USER_TEXT_AREA = myResources.getString("UserTextArea");
  private final FileChooser fileChooser = new FileChooser();


  private ColorPicker cp;

  private ColorPicker backgroundColorPicker;
  private ColorPicker markerColorPicker;
  //private Object language;
  private Color clickedColor = INITIAL_BACKGROUND_COLOR;
  private Color markerClickedColor = INITIAL_MARKER_COLOR;
  private TextField name;
  private TextField scriptFile;
  private String scriptName;
  private boolean runScript = false;
  private TextArea commandTextArea;
  private TextArea variableTextArea;
  private TextArea userDefinedCommandsTextArea;
  private String theText;
  private Boolean commandEntered = false;
  private Stage stage;
  private final ArrayList<String> buttonNames = new ArrayList<String>(Arrays.asList(myResources.getString("LoadButton"),
          myResources.getString("HelpButton"), myResources.getString("ResetButton"), myResources.getString("UndoButton"),
          myResources.getString("PenUp")));

  public SubSceneRight() {
    root = new Group();
    vBox = new VBox();
    for(String key: Collections.list(myLanguages.getKeys())){
      language_names.add(myLanguages.getString(key));
    }
    vBox.getStyleClass().add(myResources.getString("VBox"));
    root.getChildren().add(vBox);
    createLabel(vBox, BACKGROUND_COLOR_LABEL);
    createBackgroundColorPicker();
    ButtonGroup group = new ButtonGroup(buttonNames);
    vBox.getChildren().add(group.getBoxes());
    buttonListeners(group);
    createHBox();
    createTextArea(commandTextArea = new TextArea(), COMMAND_AREA_TEXT);
    createTextField();
    scriptRunTextField(); // added for scripting
    listenForCommandSelection();
    createTextArea(variableTextArea = new TextArea(), VARIABLE_AREA_TEXT);
    listenForVariableSelection();
    createTextArea(userDefinedCommandsTextArea = new TextArea(), USER_TEXT_AREA);
  }

  private void listenForCommandSelection() {
    commandTextArea.setOnContextMenuRequested(new EventHandler<Event>()
    {
      @Override
      public void handle(Event arg0)
      {
        String selectedText = commandTextArea.getSelectedText();
        name.setText(selectedText);
      }
    });
  }

  private void listenForVariableSelection() {
    variableTextArea.setOnContextMenuRequested(new EventHandler<Event>()
    {
      @Override
      public void handle(Event arg0)
      {
        String selectedText = variableTextArea.getSelectedText();
        name.setText(selectedText);
      }
    });
  }

  private void createHBox() {
    HBox hBox = new HBox();
    hBox.getStyleClass().add(myResources.getString("HBox"));
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
    markerColorPicker = new ColorPicker(INITIAL_MARKER_COLOR);
    pane.getChildren().add(markerColorPicker);
    markerColorPicker.setOnAction(event -> {
      markerClickedColor = markerColorPicker.getValue();
      commandTextArea
          .setText(
              commandTextArea.getText() + "\n" + NEW_MARKER_COLOR + markerClickedColor.toString());
    });
  }

  private void createComboBox(Pane box) {
    ComboBox<String> combo_box = new ComboBox<>(FXCollections.observableArrayList(language_names));
    box.getChildren().add(combo_box);
    combo_box.setValue(language_names.get(3));
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

  private void buttonListeners(ButtonGroup group) {

    ArrayList<Button> buttons = group.getButtons();
    buttons.get(0).setOnAction(event -> setTurtleImage());

    buttons.get(1).setOnAction(event -> displayPopUp());

    buttons.get(4).setOnAction(event -> setPenUp());

  }

  private void setPenUp(){
    markerClickedColor = null;
    markerColorPicker.setValue(INITIAL_MARKER_COLOR);
  }

  public void setTurtleImage() {
    File file = fileChooser.showOpenDialog(stage);
    if (file != null) {
      turtle = new Turtle(file.toURI().toString(), 0);
      //turtle.setX(150);
      //turtle.setY(150);
    }
  }


  public void assignStage(Stage incoming) {
    stage = incoming;
  }

  private void displayPopUp() {
    ScrollPane helpRoot = new ScrollPane();
    Stage helpStage = new Stage();
    helpStage.setTitle(myResources.getString("HelpStageTitle"));
    VBox vb = new VBox();

    vb.getChildren()
        .addAll(helpImage0, helpImage1, helpImage2, helpImage3, helpImage4, helpImage5, helpImage6);
    helpRoot.setContent(vb);
    Scene errorScene = setUpPopUp(helpRoot);
    helpStage.setScene(errorScene);
    helpStage.show();
  }


  private Scene setUpPopUp(ScrollPane helpRoot) {

    return new Scene(helpRoot, 600, 800, Color.LIGHTBLUE);
  }

  private void createTextField() {
    name = new TextField();
    name.setPromptText(TEXTFIELD_PROMPT_TEXT);
    name.getText();
    vBox.getChildren().add(name);

    //Setting an action for the Submit button
    root.setOnKeyPressed(ke -> textFieldListener(ke.getCode()));
  }

  private void scriptRunTextField() {
    scriptFile = new TextField();
    scriptFile.setPromptText("Enter file name of SLogo script"); // @TODO add to resource file so changes with language
    scriptFile.getText();
    vBox.getChildren().add(scriptFile);

    //Setting an action for the Submit button
    //root.setOnKeyPressed(ke -> scriptEnterListener(ke.getCode()));
  }

  public boolean getRunScript() {
    return runScript;
  }

  public String getScript() {
    runScript = false;
    return scriptName;
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
      if ((scriptFile.getText() != null && !scriptFile.getText().isEmpty())) {
        scriptName = scriptFile.getText().toLowerCase();
        runScript = true;
        scriptFile.clear();
      }
    }
  }

  private void createBackgroundColorPicker() {
    backgroundColorPicker = new ColorPicker(INITIAL_BACKGROUND_COLOR);
    vBox.getChildren().add(backgroundColorPicker);

    backgroundColorPicker.setOnAction(event -> {
      clickedColor = backgroundColorPicker.getValue();
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
//    if(movements.remove().get(MovingObjectProperties.PEN)) {
//
//    } else {
//      markerClickedColor = null;
//    }
  }

  @Override
  public void updateDisplayWords() {
    SUCCESSFUL_COMMAND = myResources.getString("SuccessCommand");
    VARIABLE_AREA_TEXT = myResources.getString("VariableAreaText");
    EMPTY_COMMAND = myResources.getString("EmptyCommand");
    NEW_MARKER_COLOR = myResources.getString("NewMarkerColor") + " ";
    NEW_LANGUAGE = myResources.getString("NewLanguage") + " ";
    NEW_BACKGROUND_COLOR = myResources.getString("NewBackgroundColor") + " ";
    MARKER_COLOR_LABEL = myResources.getString("MarkerColorLabel");
    CHANGE_LANGUAGE_LABEL = myResources.getString("ChangeLanguageLabel");
    TEXTFIELD_PROMPT_TEXT = myResources.getString("TextFieldPromptText");
    USER_TEXT_AREA = myResources.getString("UserTextArea");
    for (String key : Collections.list(myLanguages.getKeys())) {
      language_names.add(myLanguages.getString(key));
    }
  }

  public void setCommandText(String response) {
    commandTextArea.setText(commandTextArea.getText() + "\n" + response);
  }

  public Image getTurtle() {
    return turtle.getTurtleImage();
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
