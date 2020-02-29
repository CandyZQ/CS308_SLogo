package slogo.view;

import java.util.Map;
import java.util.Queue;
import javafx.stage.Stage;

/**
 * The purpose of this interface is to communicate between the controller package and the view
 * package. These packages will have to do a lot of communication for the code to work, as they will
 * be sending and returning the information from the back end about where the object, turtle, should
 * move and end at. The methods included in this Interface are to take in the map of the final
 * position from the controller and give this to the view, as well as a method to give the input
 * string from the command line to the controller for error checking and parsing. This API might
 * also include error checking as it will have to pass the string of the command to the controller
 * whether or not the string is a valid command. In the controller it will check if the command is a
 * valid string and a valid final position for the turtle to end in. This will then throw an
 * exception of our own creation in the controller package. This exception will be caught by the
 * view package and handled there where it will create an error window with the tag from the
 * exception displayed.
 */
public interface ExternalAPIViewable {

  /**
   * This will be called by the controller and the view to pass the information of the final
   * position of the object on screen to the view, which will then move the turtle.
   *
   * @return A Queue of Maps that store the x position, y position, heading, and other information
   * about the turtle
   */
  Queue<Map<String, Integer>> getFinalInformation();

  /**
   * This method will give the string from the command line to the controller for error handing and
   * parsing.
   *
   * @return A string that houses the text that the user input to the command line on the screen.
   */
  String getInputString();

  /**
   * This method will handle the exception that is caught by the controller if the input information
   * is bad.
   */
  void exceptionHandling(String errorMessage);

  /**
   * This method will be called by the controller to initialize and set the scene for the first time
   * in the initial configuration.
   *
   * @return The Stage of the GUI
   */
  Stage setScene();

  /*
   * This method will be called in the loop to constantly update the front end of the code, whether
   * or not a new command is passed. If a new command is passed, the scene will update and change,
   * if no command is passed it will update and stay the same.

  void update();*/

  /**
   * This command will be called constantly in the loop from the controller to always check what
   * language the commands should be read in for error checking.
   *
   * @return The string of the language that was selected to identify the commands in.
   */
  String getLanguage();
}
