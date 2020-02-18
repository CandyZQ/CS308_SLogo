package slogo.view;

import java.util.Map;

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
public interface ViewExternalAPI {

  /**
   * This will be called by the controller and the view to pass the information of the final
   * position of the object on screen to the view, which will then move the turtle.
   *
   * @return A Map that stores the x position, y position, heading, and other information about the
   * turtle
   */
  Map<String, Integer> getFinalPosition();

  /**
   * This method will give the string from the command line to the controller for error handing and
   * parsing.
   *
   * @return A string that houses the text that the user input to the command line on the screen.
   */
  String giveInputString();

  /**
   * This method will handle the exception that is thrown by the controller if the input information
   * is bad.
   *
   * @throws Exception This is a general exception that is bound to change as the code takes shape,
   *                   but will catch the exception or be clarified in the overrides of this message
   *                   as we write our own exception to use.
   */
  void exceptionHandling() throws Exception;
}
