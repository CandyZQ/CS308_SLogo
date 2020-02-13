SLOGO_QUESTIONS

1) When does parsing need to take place and what does it need to start properly?

Parsing will take place after the user saves their program. At this point the program will go through the file created by the prgorammer and see what commands need to be executed and in what order. 

2) What is the result of parsing and who receives it?

The result of the parsing would be the return of the individual strings or even letters, depending on what character we parse on, to the controller end of the project. This controller would take the parsing done in the backend and give the results from this to the front end to show these results in the way that they are going to be portrayed.

3) When are errors detected and how are they reported?

Errors are detected before the program is compiled and displyed on the view. When the user enters a sequence of erroneous commands, the program maps out how the commands execute in the backend, catch the exception, and display a message saying that an error is caught.

4) What do commands know, when do they know it, and how do they get it?

The commands do not really know anything when they are typed in, and they first find out the distance that they will go, if there is a distance, in the model code where this number will be saved as a variable to be used in the controller. The rest of the string will then be parsed for what the code should accomplish from this, and it will realize what the command is in the backend. This will then relay the information to the controller, which will receive this information and then return to the front end information it will understand, such as the change of color or how to move the turtle. Turle movement will only be recognized in the front end and maybe the controller of the code.


5) How is the GUI updated after a command has completed execution?.md

Once a command has completed execution, the state of the turtle, which is updated in the backend now needs to be updated in the front-end. This information can be passed to the View module and the turtle position and trail can be updated accordingly.

