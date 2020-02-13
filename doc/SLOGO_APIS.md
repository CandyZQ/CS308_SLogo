#### API 1: Backend External
This APi will communicate between the model, or the back-end of the code, and the controller, or the middle of the code. The controller will call on the differnt classes or sub-classes in the back end in order to set the initial values, change these values every update call, and change values for each case that would warrent them changing. These will be accomplished through method such as getValue(), Update(), setCurrentValue(), getCurrentValue(), setNextValue(), getNextValue(), getNewValue(), and move(). If we had to guess, a lot of these classes will be abstracted from a parent class as each of the different model classes will need to run them. They will also probably all inherit from an inteface for them to be more generalized as well as run for many different types of parameters passed in. The controller sub-package will call on these methods in the classes of the back-end, which will change the values for them to be reported to the front-end.

#### API 2: Backend Internal
The internal communication in the backend will mostly be between the error checking code and the different classes that can be called. First the error checking codes much be run to make sure that there are not issues with what is being passed to the other back-end classes. This error checking code will then pass on the corrected information to the back-end classes which will set up the simulations and run the rules for them.


#### API 3: Frontend External
The frontend external API will communicate with the controller and returns the current states of the turtle and the command receives. The backend should calculate Some basic methods include setNextCommand(), setTurtlePosition(), etc. 


#### API 4: Frontend Internal
This API will have classes for turtles, text, and other basic objects that are displayed on the screen. This part of the API does not communicate with any part of the backend and focuses solely on bringing the images and frontend objects into existence. These classes must be abstract enough for API 3 to use the methods and attributes flexibly