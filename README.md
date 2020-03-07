parser
====

This project implements a development environment that helps users write SLogo programs.

Names:
- Cady Zhou (zz160)
- Ryan Mecca (rm358)
- Ameer Syedibrahim (as877)
- Sarah Gregorich (seg47)

### Timeline

Start Date: 2/13/20

Finish Date: 3/6/20

Hours Spent: 70

### Primary Roles
- Ryan:
    - I worked on the front end of the code where I mainly did the communication between the main class in controller and the front end. This included getting the turtle to move and recognize the information passed from the backend.
    - I also worked on creating and organizing the scene in a disciplined manner.
    - Refactored lots of the front end code
    - Wrote code for turtle to move
    - Wrote the code for many of the display items, mainly the right side. (variable displays and updates, command view, color pickers)
    - Worked with property files
    - Completed all work for css sheets
    - Refactored for many lambdas
- Cady:
     - Worked on the backend code, mainly implemented the logic of parser that processes inputs and returns turtle's final states using Java Reflection API and regular expression
     - Wrote the code that converts user inputs in different languages into processable commands
     - Wrote helper classes that invoke appropriate methods with Reflection API
- Ameer:
    - Worked on the front end of the code
    - Completed the work for smooth turtle animations, and sequential transitions
    - Implemented the path transition of the turtle (i.e. the marker) and added a slider to toggle its thickness
    - Wrote the code for multiple windows to open and run with parser
    - Refactored the front end code and added all string to property files
    - Enabled selection of commands in the console to repopulate the text field
    - Created a Turtle statistics window which updates the turtle information for each step
    - Created Help Screen that provides information on the turtle commands
    - Created a ButtonGroup class to allow for a general implementation of a group of buttons
- Sarah: 
    - Worked on backend and front end of the code
    - Wrote the model package
    - Wrote scripting for the front end and back end
    - Wrote logic of the display buttons to move the turtle
    - Worked on parts of the parser

### Resources Used
- https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html
- https://docs.oracle.com/javafx/2/api/javafx/scene/doc-files/cssref.html#scrollpane
- https://coursework.cs.duke.edu/compsci308_2020spring/lab_advanced/-/blob/master/src/browser/view/NanoBrowserView.java
- https://www.dummies.com/programming/java/javafx-how-to-use-property-events/
- https://www.google.com/search?q=google+translate&oq=google+&aqs=chrome.0.69i59j69i57j69i59j69i60l5.911j0j7&sourceid=chrome&ie=UTF-8


### Running the Program

Main class: ```src/slogo/controller/Main.java``

Data files needed: 
- Commands in different languages: src.resources.languages
- UI labels: resources.resources bundle
- css sheets

Features implemented:
- turtle recognizes all commands given
- turtle responds to commands interactively 
- display errors in a friendly way
- set background and pen color using a drop-down menu
- set an turtle image
- present previous commands and created variables and user-defined commands
- allow users to choose different languages and changes texts of all components 
- user is able to access help page
- buttons that allow easy access to frequently used methods 

### Notes/Assumptions

Assumptions or Simplifications:
In completing this project the group made a few assumptions
- We assumed the user could use the help window to look for commands that would run in the language selected
- Assumed user can speak English for the help page
- Assumed user can reset scene or create new one 

Interesting data files:
- Demos given from Professor Duvall shown working

Known Bugs:
- The backend of multiple turtles are implemented and tested, but the frontend currently does not support displaying multiple turtles
- Does not support the commands to change the colors from commands
- Stats do not update for the repeat commands
- cs commands does not remove the marker from the screen
- Do not support Urdu language on the UI (but it is supported for commands)
- Help window and some text on screen does not update to the selected language, but all buttons do
- Turtle moves slower than expected

Extra credit:
- Added multiple windows
- Have working changing text with language
- turtle movement is animated and the line drawing is animated nicely
- recognize almost all additional calls for the turtle
- able to script, or run and create files of commands on own
- able to execute commands from history
- have buttons to move turtle set amounts
- Display turtle information and can change pen states
- Recursion works

### Impressions
Although SLogo sounds easier than previous projects, it requires much more planning before and during the project. Both frontend and backend heavily refactored the code in the middle of the implementation. It is also a much more flexible project and we can come up with many different ways to implement required features. We thus need to make many team decisions to determine details of how some features need to be implemented.  
