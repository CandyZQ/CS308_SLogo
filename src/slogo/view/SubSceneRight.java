package slogo.view;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import slogo.controller.listings.MovingObjectProperties;

public class SubSceneRight extends SubScene {

  private static final int ENGLISH_IN_LIST = 3;
  private final ImageView helpImage0 = new ImageView(new Image(myResources.getString("HelpTitle")));
  private final ImageView helpImage1 = new ImageView(new Image(myResources.getString("BasicSyntax")));
  private final ImageView helpImage2 = new ImageView(new Image(myResources.getString("TurtleCommands")));
  private final ImageView helpImage3 = new ImageView(new Image(myResources.getString("TurtleQueries")));
  private final ImageView helpImage4 = new ImageView(new Image(myResources.getString("MathOperations")));
  private final ImageView helpImage5 = new ImageView(new Image(myResources.getString("BooleanOperations")));
  private final ImageView helpImage6 = new ImageView(new Image(myResources.getString("UserDefined")));


  private Turtle turtle = new Turtle(myResources.getString("Turtle"), 0);
  public static final Color INITIAL_BACKGROUND_COLOR = Color.WHITE;
  public static final Color INITIAL_MARKER_COLOR = null;
  public static String SUCCESSFUL_COMMAND = myResources.getString("SuccessCommand");
  private String NEW_MARKER_COLOR = myResources.getString("NewMarkerColor") + " ";
  private String NEW_LANGUAGE = myResources.getString("NewLanguage") + " ";
  private String NEW_BACKGROUND_COLOR = myResources.getString("NewBackgroundColor") + " ";
  private List<String> language_names = new ArrayList<>();
  private String MARKER_COLOR_LABEL = myResources.getString("MarkerColorLabel");
  private String CHANGE_LANGUAGE_LABEL = myResources.getString("ChangeLanguageLabel");
  private String TEXTFIELD_PROMPT_TEXT = myResources.getString("TextFieldPromptText");
  private String VARIABLE_AREA_TEXT = myResources.getString("VariableAreaText");
  private String USER_TEXT_AREA = myResources.getString("UserTextArea");
  private final FileChooser fileChooser = new FileChooser();


  private ColorPicker backgroundColorPicker;
  private ColorPicker markerColorPicker;
  private Color clickedColor = INITIAL_BACKGROUND_COLOR;
  private Color markerClickedColor = INITIAL_MARKER_COLOR;
  private TextField name;
  private TextField scriptFile;
  private String scriptName;
  private boolean runScript = false;
  private boolean window = false;
  private TextArea commandTextArea;
  private TextArea variableTextArea;
  private TextArea userDefinedCommandsTextArea;
  private Stage stage;

  private List<String> buttonNames;
  private ButtonGroup group;

  public SubSceneRight() {
    root = new Group();
    vBox = new VBox();
    vBox.getStyleClass().add(myResources.getString("VBox"));
    root.getChildren().add(vBox);
    for (String key : Collections.list(myLanguages.getKeys())) {
      language_names.add(myLanguages.getString(key));
    }
    // vBox.getChildren().add(createTable(new TableView<>()));
    makeHBox(createTextArea(variableTextArea = new TextArea(), VARIABLE_AREA_TEXT),
        createTextArea(userDefinedCommandsTextArea = new TextArea(), USER_TEXT_AREA), myResources.getString("ThisHBoxStyle"));
    String COMMAND_AREA_TEXT = myResources.getString("CommandArea");
    vBox.getChildren().add(makeVBox(createTextArea(commandTextArea = new TextArea(),
        COMMAND_AREA_TEXT), createTextField()));
    makeHBox(makeVBox(createLabel(CHANGE_LANGUAGE_LABEL), createComboBox()),
        makeVBox(createLabel(MARKER_COLOR_LABEL), createMarkerColorPicker()), myResources.getString("HBoxStyle"));
    String BACKGROUND_COLOR_LABEL = myResources.getString("BackgroundColorLabel");
    vBox.getChildren().add(createLabel(BACKGROUND_COLOR_LABEL));
    createBackgroundColorPicker();

    buttonNames =  new ArrayList<>(
        Arrays.asList(myResources.getString("LoadButton"),
            myResources.getString("HelpButton"), myResources.getString("ResetButton"),
            myResources.getString("UndoButton"),
            myResources.getString("PenUp"), myResources.getString("NewWindow")));
    group = new ButtonGroup(buttonNames);
    vBox.getChildren().add(group.getBoxes());
    buttonListeners(group);

    scriptRunTextField(); // added for scripting
    listenForCommandSelection();
    listenForVariableSelection();
  }

  private void makeHBox(Region left, Region right, String style) {
    HBox hBox = new HBox();
    hBox.getStyleClass().add(style);
    hBox.getChildren().addAll(left, right);
    vBox.getChildren().addAll(hBox);
  }

  private Region makeVBox(Region top, Region bottom) {
    VBox innerVBox = new VBox();
    innerVBox.getChildren().addAll(top, bottom);
    return innerVBox;
  }

  private void listenForCommandSelection() {
    commandTextArea.setOnContextMenuRequested(arg0 -> {
      String selectedText = commandTextArea.getSelectedText();
      name.setText(selectedText);
    });
  }

  private void listenForVariableSelection() {
    variableTextArea.setOnContextMenuRequested(arg0 -> {
      String selectedText = variableTextArea.getSelectedText();
      name.setText(selectedText);
    });
  }

  private Region createComboBox() {
    ComboBox<String> combo_box = new ComboBox<>(FXCollections.observableArrayList(language_names));
    combo_box.setValue(language_names.get(ENGLISH_IN_LIST));
    language = combo_box.getValue();
    combo_box.setOnAction(event -> {
      comboBoxListener(combo_box);
    });
    return combo_box;
  }

  private void comboBoxListener(ComboBox<String> combo_box) {
    language = combo_box.getValue();
    commandTextArea.setText(commandTextArea.getText() + "\n" + NEW_LANGUAGE + language);
  }

  private Control createTextArea(TextArea area, String text) {
    area.setText(text);
    area.setEditable(false);
    return area;
  }

  void updateButtonLan() {
    if (languageUpdated()) {
      buttonNames = new ArrayList<>(
          Arrays.asList(myResources.getString("LoadButton"),
              myResources.getString("HelpButton"), myResources.getString("ResetButton"),
              myResources.getString("UndoButton"),
              myResources.getString("PenUp")));
      vBox.getChildren().remove(group.getBoxes());
      group.updateLang(buttonNames);
      vBox.getChildren().addAll(group.getBoxes());
      buttonListeners(group);
    }
  }

  private void buttonListeners(ButtonGroup group) {
    List<Button> buttons = group.getButtons();
    buttons.get(0).setOnAction(event -> setTurtleImage());
    buttons.get(1).setOnAction(event -> displayPopUp());
    buttons.get(4).setOnAction(event -> setPenUp());
    buttons.get(5).setOnAction(event -> setWindowBoolean());
  }

  private void setWindowBoolean(){
    this.window = true;
  }

  public boolean getWindowBoolean(){
    boolean temp = window;
    window = false;
    return temp;
  }


  private void setPenUp() {
    markerClickedColor = null;
    markerColorPicker.setValue(INITIAL_MARKER_COLOR);
  }

  public void setTurtleImage() {
    File file = fileChooser.showOpenDialog(stage);
    if (file != null) {
      turtle = new Turtle(file.toURI().toString(), 1);
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
    errorScene.getStylesheets().add(myResources.getString("HelpStyle"));
    helpStage.setScene(errorScene);
    helpStage.show();
  }


  private Scene setUpPopUp(ScrollPane helpRoot) {
    return new Scene(helpRoot);
  }

  private Region createTextField() {
    name = new TextField();
    name.setPromptText(TEXTFIELD_PROMPT_TEXT);
    vBox.setOnKeyPressed(ke -> textFieldListener(ke.getCode()));
    return name;
  }

  private void scriptRunTextField() {
    scriptFile = new TextField();
    scriptFile.setPromptText(
       myResources.getString("ScriptPrompt"));
    scriptFile.getText();
    vBox.getChildren().add(scriptFile);
  }

  public boolean getRunScript() {
    return runScript;
  }

  public String getScript() {
    runScript = false;
    return scriptName;
  }

  public void execute(String command) {
    theText = command;
    commandEntered = true;
  }

  private void textFieldListener(KeyCode code) {
    if (code == KeyCode.ENTER) {
      if ((name.getText() != null && !name.getText().isEmpty())) {
        theText = name.getText().toLowerCase();
        commandEntered = true;
        commandTextArea.setText(commandTextArea.getText() + "\n" + theText);
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
    backgroundColorPicker.setOnAction(event -> activeColorBackgroundListener());
  }

  private void activeColorBackgroundListener() {
    clickedColor = backgroundColorPicker.getValue();
    commandTextArea.setText(
        commandTextArea.getText() + "\n" + NEW_BACKGROUND_COLOR + clickedColor.toString());
  }

  private Region createMarkerColorPicker() {
    markerColorPicker = new ColorPicker(INITIAL_MARKER_COLOR);
    markerColorPicker.setOnAction(event -> activeColorMarkerListener());
    return markerColorPicker;
  }

  // refactor into method 2 above this bc repeated code
  private void activeColorMarkerListener() {
    markerClickedColor = markerColorPicker.getValue();
    commandTextArea.setText(
        commandTextArea.getText() + "\n" + NEW_MARKER_COLOR + clickedColor.toString());
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
  public void update(Queue<Map<MovingObjectProperties, Object>> movements) {
    if (!(boolean) movements.peek().get(MovingObjectProperties.PEN) || (boolean) movements.peek()
        .get(MovingObjectProperties.CLEAR)) {
      markerClickedColor = null;
      markerColorPicker.setValue(null);
    }
  }

  @Override
  public void updateDisplayWords() {
    SUCCESSFUL_COMMAND = myResources.getString("SuccessCommand");
    VARIABLE_AREA_TEXT = myResources.getString("VariableAreaText");
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
