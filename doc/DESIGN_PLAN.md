DESIGN_PLAN.md

# PARSER DESIGN PLAN
#### Team Number: 15
#### Team Members:
Ryan Mecca (rm358)  
Cady Zhou (zz160)  
Sarah Gregorich (seg47)  
Ameer Syedibrahim (as877)  

## Introduction 
The goal of this project is to create a Java program that allows users to draw and learn programming with simple commands. This project should allow users to input known commands, and returns the desired result. User inputs can either be a command that makes a turtle move and draw on the white background, or a simple programming language (conditional statement, loop, math operation, variable assignment, etc). 

We will use MVC structure as the architecure of this project. This design should be able to be expanded later so that more drawing/operation commands can be accepted.

- The view reads in user inputs and passes them to the controller. It also gets the updated state of turtle from the controller and shows the drawing on the screen.
- The controller gets commands from the view. Depending on the command's type, it can call different methods from the model to update the turtle's state, or execute the command as a programming language. 
- The model saves the state of the turtle, updates it for each command, and passed it back to the controller. 

## Overview
- Internal Backend API will be responsible communicate between the model and the view. It consists of public methods in Model, Turtle.java, which allows the controller to get and set the current status of the Turtle. The status information includes the turtle's x position, y position, angle in which it is heading, and visibility.
![Structure of backend](https://i.imgur.com/fL67uOT.png)


- External Backend API will consist of public methods that reads individual commands from the view. Before reading in commands, a method that sets the user's language should also be called. After each command is interpreted and the backend updates the turtle's status, this API should pass the current status as a map of the turtle back to the frontend. All the implementation will be wrote in Controller.Parser.java. There are also multiple exceptiions that need to be handled.
![Strcture of controller](https://i.imgur.com/LCGsbrx.png)

![Exceptions](https://i.imgur.com/ZMhoybL.png)

- The internal Front-end API will deal with the communication between the classes in the front end of the code. This will include a class that will control the color of the pen chosen, the image for the turtle, the for loop for the turtle moving one pixel as the time. The classes will communicate between each other about the color that the marker should draw the line in, the width of the line, and the image used for the turtle. This can also communicate between themselves the differnt commands of the buttons where they would create a pull down window or pop out a new scene for the user. Lastly, the Frontend Internal API will deal with passing the turtle information back and forth that has to do with its movement, color, image, and properties. Also included in the internal API is the set on command pressed buttons of enter and if a button is pressed for a new color, this will need to be communicated to the view manager which can communicate to the External API/controller.

- The external Front-end API will deal with giving information back and forth between the controller and the View of the MVC design. This will include the public methods that handle giving the String of the command typed in to the command prompt for the object to move on the screen, put the marker down, or to change the color of the marker. This information will be sent to the controller from the front end. In the reverse direction, the controller will send information to the view package in order to give the final position of the turtle. This will be where the turtle will stop on the grid in the coordinate system of (0,0) is the center. This will be returned to the view as a Map to be read in. This API will also deal with the return and catching of an error thrown from the controller if the input string from the command prompt is not recognized, or if the command would put the Turtle out of bounds.

With regards to data structures, a Map was chose to hold the Turtle's state as opposed to passing a TurtleState object so that the front end is not required to handle classes implemented in the back end.

## User Interface 

![Image of the proposed front end view](https://i.imgur.com/JmHdvK0.jpg)

![Image of the proposed plan of View package](https://i.imgur.com/UE8U68b.png)





We plan on having three separate components for the UI. One section will be dedicated to the space where the turtle will move. Another section will constitute a tool bar where the user can choose a color, set an image for the turtle, set a background image for the space, etc. The last section will be a text field that will accept commands from the user which dictates the movement of the turtle.

Text Field
- Located underneath the color buttons, will accept the command from the user and be sent to the backend for parsing.
- The command will be in a simple font that the user can submit with the ENTER button.

Key Four Buttons
- Four buttons underneath that allow the user to set the backgorund color, turtle image, and open a file with all the old commands present in a TXT file and see user-defined commands currently available in the environment.

Turtle Image Button
- The button will launch the File manager and allow them to choose an image of a turtle which will then displayed on the screen at the (0,0) position


White Square Field
 - A plain white square field on the left size indicates the space in which the turtle will be moving. 
- A visible border will be placed around this box to signify the edges of the grid and bounds which the turtle may not cross.


Help Button
- A button for the "Help" section will also be available which will display a simple screen with all the commands recognized and their descriptions. 
- This list will be all of the commands that the prompt can take in without giving an error window to the user.
- When the Help button is pressed it will pop out a new window for the user to see all of the recognized commands
- This window will be a new stage that will house the page of all of the commands possible


The Marker Color Palette 
- We are planning a Microsoft Paint like setup where color options are provided to the user in a 'list' of the different colored rectangle objects. We feel this is the most uder friendly for children that would use the software, as they only need to click on the bounds of the rectangle to change the color of the marker used.
- This will be default to no marker, which then when a different color is pressed will start the marker and change its color.
- Whenever a button is pressed, or the button that takes it back to default no marker if no marker has been selected, that button will be set as not active or able to be pressed.
 
History Log
- The bottom right rectangle on the UI represents a history log of the ten most recent commands entered by the user
- The user's prior commands are displayed as a text field and moved up the list as new commands are entered
- Having this history log will allow us to revert commands in necessary later

Error checking
* If the user passes in a command that is not recognized by the program, or on the Help page of commands, it will still pass this command to the controller. Error checking of these commands will happen in the controller, which will throw an exception that we will make that is called something along the lines of BadInputInformation. This will have the text tag specific to the type of bad information that the user imputed, such as out of the bounds of the grid for the turtle to move or not a regognized command.
* This will have to be controlled in the Frontend External API, which will catch the error and then take the tag from the error that was thrown. This will then call another class which will create the error pop-up window that will display the error text from the exception thrown in this window.
* This window can then be closed, without the turtle performing the action, and the user can input another action.


## Design Details 
- Front End Internal:
    - As mentioned previously, the front-end internal API will contain all classes that communicate with one another in the front-end. 
    - The first class will control the color of the pen chosen. This class will allow the user to click a button and will corresponding set the marker to that color. It is intended to be used as only in the front-end and can be stored in a log for future use (potentially in complete)
    - We will also have a class for the image of the turtle. This will allow the user to click a button to determine how the turtle will look and a corresponding size will be associated with the turtle. This class will consist of either an ImageView or a Rectangle or some type of image holder.
    - Drawing lines and border will have their own class which will dictate the edges of the turtle's movement and the trail it leaves behind. Once again, this type of class will maybe include Rectangle objects. These classes will communicate with the position of the turtle to situate it accordingly and ensure the borders and trails are appropriate

- Front End External:
    - The external portion will be passing information back and forth between the front end and the back end through the Model and Controller. 
    - This constitutes many public methods spread across various classes in the Front End API which will handle incoming strings in the command prompt and a wide variety of events including turtle movement, putting the marker down, or changing the color of the marker
    - The controller on the other hand will be sending will be sending the View package information about the final position of the turtle for it to be displayed. 
    - This information from the controller will be passed in the form of a map which holds the X and Y positions and the heading position of the turtle
    - This API will also deal with the return and catching of an error thrown from the controller if the input string from the command prompt is not recognized
    - Exception handling will also take care of the turtle going out of bounds since it is handled in the Control and not the View.

- Backend Internal: 
    - Model.Turtle.java holds all state information of turtle, which include turtle's x position, y position, angle in which it is heading, visibility, and whether the pen is down or not. This list can be easily expanded in the future if necessary. All states have their own getter, which returns an immutable version of this state, and setter. Getters and setters will be public methods and called by the controller to update turtle's status after each command is passed in. The backend can also send all states together in the form of a map. The keys of the map will be possible states, all of which are going to be listed in a Enum to avoid using Strings. The values of the map are corresponding values of those states. By doing this, the controller and the view will be able to store and use the information in a more structured way.
    - Model.Turtle.java also has different public methods that do necessary calculations for state changes for more complicated commands. One example is when SETXY/GOTO *x y* is passed to the controller, the controller can call one method to update the xy position, as well as calculating the new angle in which the turtle is heading by using the old and new positions. The controller can directly call these methods, which siginificanly increases the readability of codes in the controller, and decreases the size of each method. 
    - The model will also implement Operation.java that stores all instance variables that the user creates and simple logic and math operatiosns. This class is likely to be implemented as a static class because no future extension will be needed. If more operations need to be added, adding individual methods will solve the problem. The controller can directly call these methods based on the command it gets.  

- Backend External:
    - Controller.Parser.java accepts individual commands as strings from the frontend (View) via Controller.Parser.execute(String command). 
    - The frontend can also call Controller.Parser.setLanguage(String language) which will tell the controller which property files to look at in order to decode the input strings.

## API as Code 

#### Front End
* External API
```java
package slogo.view;

import java.util.Map;
import java.util.Queue;
import javafx.scene.Scene;
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
  Queue<Map<MovingObjectProperties, Double>> getFinalInformation();

  /**
   * This method will give the string from the command line to the controller for error handing and
   * parsing.
   *
   * @return A string that houses the text that the user input to the command line on the screen.
   */
  String giveInputString();

  /**
   * This method will handle the exception that is caught by the controller if the input information
   * is bad.
   *
   * @param e This is a general exception that is bound to change as the code takes shape,
   *                   but will handle the exception or be clarified in the overrides of this message
   *                   as we write our own exception to use.
   */
  void exceptionHandling(Exception e);
  /**
   * This method will be called by the controller to initialize and set the scene for the first time
   * in the initial configuration.
   *
   * @return The Stage of the GUI
   */
  Stage setScene();

  /**
   * This method will be called in the loop to constantly update the front end of the code, whether
   * or not a new command is passed. If a new command is passed, the scene will update and change,
   * if no command is passed it will update and stay the same.
   */
  void update();

  /**
   * This command will be called constantly in the loop from the controller to always check what
   * language the commands should be read in for error checking.
   *
   * @return The string of the language that was selected to identify the commands in.
   */
  String getLanguage();
}

```
* Internal API

```java
package slogo.view;


import javafx.scene.Group;

/**
 * This interface will work between the different classes of the front end of our code. Since this
 * interface will be passing the root of our stage around, which is a security issue we will make
 * sure that only the objects that inherit this property from ViewObject will be able to edit the
 * root. This is our level of security added for when we pass around the root, which can be a
 * dangerous operation because it gives many classes unchecked access to the root that holds all of
 * the information about the scene. This inheritance structure will add a but of security to the use
 * of passing around the root, which is why we determined it could be done for this API.
 */
public interface ViewInternalAPI {

  /**
   * This method will be called by classes that will initialize and add their respective objects to
   * the root. This root will be passed into the classes and returned to the view manager, which
   * will attach the root to the scene.
   *
   * @param root The root for the current scene that is on the stage of the sLogo program.
   * @return The same root that was passed in, just edited from the class to add the objects.
   */
  Group createRootObject(Group root);

  /**
   * This method will be called by the classes every time that something needs to be updated on the
   * scene. This will be used to change a parameter of one of the objects on the scene or to change
   * a necessary part of the object such as the turtle moving across the scene or the marker drawing
   * the line.
   *
   * @param root The root for the current scene that is on the stage of the sLogo program.
   * @return The same root that was passed in, just edited from the class to add the objects.
   */
  Group editRoot(Group root);

  /**
   * This method will be called whenever an object needs to be removed from the scene. Probably will
   * not be called often, but thought it would be important to add.
   *
   * @param root The root for the current scene that is on the stage of the sLogo program.
   * @return The same root that was passed in, just edited from the class to add the objects.
   */
  Group removeFromRoot(Group root);

}

```


#### Back End
* External API
```java
package slogo.controller;

import java.util.Map;
import java.util.Queue;
import slogo.exceptions.CommandDoesNotExistException;
import slogo.exceptions.InvalidArgumentException;
import slogo.exceptions.LanguageIsNotSupportedException;
import slogo.exceptions.WrongCommandFormatException;
import slogo.model.MovingObjectProperties;

/**
 * This is the backend external API for the slogo project.
 * <p></p>
 * This class takes user inputs in a specific language as a String and interpret it as one of the
 * pre-set commands, which give specific instructions of on how the backend states need to be
 * updated. This class then correspondingly updates backend states, and returns the current state
 * and other necessary values. Each state is stored in a {@link Map}, and a {@link Queue} of maps
 * will be returned. In most cases the {@link Queue} contains only one {@link Map}, but multiple
 * {@code Maps} will be returned when a {@code FOR} command is executed.
 * <p></p>
 * Note, the language the user uses should be set before any user inputs are read, and then all user
 * inputs will be interpreted in that language until the language is reset.
 * <p></p>
 * User inputs will be checked to see whether commands are recognizable, and if yes, whether the
 * format fulfills the format of that command. Multiple exceptions can be thrown in this process.
 */
public interface BackEndExternalAPI {

  /**
   * Sets the desired language commands the user passed will be interpreted in. This method can be
   * called multiple times for the user to change language.
   *
   * @param language the language user inputs will be in
   */
  void setLanguage(String language) throws LanguageIsNotSupportedException;

  /**
   * Accepts one command from user input and updates the backend states accordingly
   *
   * @param command the command that the user inputs
   * @return a {@code Queue} of {@code Map} that represents states of the backend
   */
  Queue<Map<MovingObjectProperties, Double>> execute(String command)
      throws CommandDoesNotExistException, LanguageIsNotSupportedException, WrongCommandFormatException, InvalidArgumentException;
}


```
* Internal API

```java
package slogo.model;

import java.util.Map;

/**
 * This is the backend internal API for the slogo project.
 * <p></p>
 * This class stores necessary state information, multiple operations that changes some or all of
 * the state information, and a getter methods that returns all state information. The state
 * information includes:
 * <ul>
 *   <li>the x position</li>
 *   <li>the y position</li>
 *   <li>the direction in which this object is heading</li>
 *   <li>the visibility</li>
 * </ul>
 */
public interface MovingObject {

  /**
   * Gets the distance this {@link MovingObject} travelled when the state changes
   *
   * @return the distance travelled
   */
  double getDistanceTravelled();


  /**
   * Moves this object forward/backward certain distance
   *
   * @param distance the distance to be moved, positive value moves this object forward while
   *                 negative value moves it backward
   * @return the distance moved
   */
  double moveDistance(double distance);

  /**
   * Sets the new heading of this object
   *
   * @param angle the new heading, which assumes north of (0,0) is 0 degree and turning clockwise
   *              from NORTH increases angle
   * @return the number of degree moved
   */
  double setHeading(double angle);

  /**
   * Sets the position of this object
   *
   * @param x the x position
   * @param y the y position
   * @return the distance moved
   */
  double setCoordinates(double x, double y);


  /**
   * Returns this object to the center of the screen and sets heading ot 0
   */
  void reset();

  /**
   * Gets all state information of this object as a {@link Map}
   *
   * @return a {@link Map} containing all state information
   */
  Map<MovingObjectProperties, Double> getState();
}
```

```java 
package slogo.model;

import java.util.Random;
import slogo.exceptions.InvalidArgumentException;

/**
 * This class contains exclusively of static method that perform basic math and boolean operations.
 * The methods of this class throws any exceptions that can happen during a math operation.
 */
public interface Operations {

  /**
   * Adds two numbers together
   *
   * @param a the first value
   * @param b the second value
   * @return the result
   */
  static double sum(double a, double b) {
    return a + b;
  }

  /**
   * Subtracts the second value from the first
   *
   * @param a the first value
   * @param b the second value
   * @return the result
   */
  static double difference(double a, double b) {
    return a - b;
  }

  /**
   * Multiplies two values
   *
   * @param a the first value
   * @param b the second value
   * @return the result
   */
  static double product(double a, double b) {
    return a * b;
  }

  /**
   * Divides two doubles
   *
   * @param a the first double
   * @param b the second double
   * @return the result
   */
  static double quotient(double a, double b) {
    return a / b;
  }

  /**
   * Returns remainder on dividing the values of the second value from the first value
   *
   * @param a the first int
   * @param b the second int
   * @return the result
   */
  static double remainder(int a, int b) {
    return a % b;
  }


  /**
   * Returns negative of the value
   *
   * @param a the value
   * @return the negative of the value
   */
  static double minus(double a) {
    return 0 - a;
  }

  /**
   * Returns random non-negative number strictly less than max
   *
   * @param max the upper bound of return value
   * @return a non-negative random value smaller than max
   */
  static double random(double max) throws InvalidArgumentException{
    if (max < 0) {
      throw new InvalidArgumentException("The argument should be non-negative!");
    }
    Random rand = new Random();
    return rand.nextDouble() * max;
  }

  /**
   * Returns sine of an angle
   *
   * @param degrees the angle in degree
   * @return the result
   */
  static double sin(double degrees) {
    return Math.sin(degrees);
  }

  /**
   * Returns cosine of an angle
   *
   * @param degrees the angle in degree
   * @return the result
   */
  static double cos(double degrees) {
    return Math.cos(degrees);
  }

  /**
   * Returns tangent of an angle
   *
   * @param degrees the angle in degree
   * @return the result
   */
  static double tan(double degrees) {
    return Math.tan(degrees);
  }

  /**
   * Returns arctangent of an angle
   *
   * @param degrees the angle in degree
   * @return the result
   */
  static double atan(double degrees) {
    return Math.atan(degrees);
  }

  /**
   * Returns natural log of an angle
   *
   * @param degrees the angle in degree
   * @return the result
   */
  static double log(double degrees) {
    return Math.log(degrees);
  }

  /**
   * Returns base raised to the power of the exponent
   *
   * @param base     the base to be raised
   * @param exponent the exponent that will be raised to
   * @return the result
   */
  static double pow(double base, double exponent) {
    return Math.pow(base, exponent);
  }

  /**
   * Reports the number PI
   *
   * @return the PI value
   */
  static double pi() {
    return Math.PI;
  }
}

```


## Design Considerations
- Extensibility: We are expecting to add more new commands for user to use in the future. As a result, the extensibility of the parser is very important. Our first desing was to use a large switch case to select the correct backend methods to execute for each command passed in. This design was very intuitive and easy to implement. However, this design would result in a very large method, especially when we added more commands in the future, and is thus not ideal. We then decide to use the Reflect API or Regular Expressions to solve this problem. 
- Commands Passing: We also spent time discussing the format that commands should be in when passed from the frontend to the controller. Since the user can choose to write a script or execute commands interactively, it is possible that the view reads in more than one command at a time. In our design, the view will be responsible for separating multiple commands and passing them to the controller one by one in a loop. This design decision allows the UI to show an animation of the turtle's movement step by step. It also simplifies the controller's implementattion, and allows it to focus on parsing commands and update the model.
- Currently our desgin uses a map to store and pass turtle's state information as a whole. An alternative we came up with was to create another class TurtleState.java to hold all necessary information. The pro of the original design was that it saved data in a class and made getting and setting states easier. However, this desing made the MVC structure vague at the same time. The view needed to access TurtleState, which was placed at the backend, to update UI. The pro of our current design solves this problem. Both the model and the view can access a map data structure. As a tradeoff, the operation of getting and setting states on both ends become less intuitive, and additional operations are needed to construct and decode the map. 
### Use cases 
- Given
    - The view takes 'fd 50' from the user and passes it to Parser by calling execute(). Parser interprets this command, and calls corresponding methods from the backend. In this case, Parser resets y position to a number 50 less than the original one. After the state update, the view calls a method from the parser to get the current state of the turtle. All state information will be stored in a map. 
    - Pen to change color: Once the user selects a color from the JavaFX color picker, the ButtonViewable object will have a setOnClick method which will change the color parameter of the marker via the LineViewable class which will contain a setColor method
- Backend (4 Cases)
    - A user passes in an invalid string as a command. Main attempts to pass this string to the Parser - in parsing the string will be identified as having the incorrect format/command name and throw an exception. This exception will then be handled in Main.
    - The controller decodes a command "sum 30 50". The controller then calls the sum method on the two operands given as constants by the user in the Model package. The result, 80, is returned to the Controller who stores it temporarily pending a call from View to check what the output of the passed command was.
    - The language is set via a call in Controller which determines which property file is referenced while parsing commands. If setLanguage("German") is called then the properties file is German.properties and cannot be reset once chosen. If a new language is attempted to be set an error will be thrown. Specifically the parser class will alert main that an error has occurred in the language setting.
    - The command "SETHEADING" and "TOWARDS" will both be implemented in Model using a setHeading() method that can either accept an angle or an (x,y) coordinate to turn towards from the current position. In the event that the command SETHEADING 90 is passed. The controller will parse the command using the appropriate properties file, then call setHeading(90) in Model to update the Turtle state. This method will calculate the difference between the current heading and the desired heading and return the difference.
- Frontend (4 cases)
    - Use Case #1: Changing the image of the turtle
To change the image of the turtle, the user will first click on the corresonding button viewable object which will in turn open the FileManager, select the image, and create a TurtleRectangleView object (internally a new ImageView will be created)
    
    - Use Case #2: Changing the language
To change the language, the user selects an option from the DropDownViewable object which will set the language parameter to be passed to the backend. This will also be triggered with some type of onClick method. In the DropDown class, a sendLanugage method will provide the information to the appropriate back-end class. 

    - Use Case #3: Set the background color
To change the background color, wait for the user to pick a color with some type of an object that enables this functionality. Then, that corresponding portion of the stage will be filled the color that can be handled in t BoundsRectanglesView class. This class will merely set the color of the rectangle with a setColor method
    
    - Use Case #4: Pen down and pen up
The user has the ability to chose whether the pen is placed down or up in the turtle field. A ButtonView object, when triggered with an onClick method will allow change the state of a boolean variable. This information will then be passed to the LineViewable class which will either enable the lines to be displayed or not.
    
 
## Team Responsibilities 
- Sarah: Backend 
    - Updating state of Turtle in Model
    - Creating methods for boolean and mathematical operators
- Cady: Backend
    - Parsing of commands from UI
    - Error handling
    - Main
- Ameer:
    - Rules
    - List of commands
    - Image imports
    - Interal API
    - Error handling
    - Language button
    - Help button
- Ryan:
    - Command prompt
    - Buttons from css sheet
    - External API
    - List of previous commands
    - Scene information
    - Pop-out windows of other stages/scenes

