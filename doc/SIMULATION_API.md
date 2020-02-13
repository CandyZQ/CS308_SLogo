## API Discovery

#### Method should not be part of the API (i.e., it should not be public)
The update grid method that is in controller. any of the models probably should not be part of the API, or can be simplified to a single method in the controller that can call update for all of the update methods for the different models. This could lessen the number of public methods in the API.

The getSimulationCols() method which is an abstract method implemented in the Simulation class simply returns the gri width and does not need to be a public method as a result. Having this method be private is a better way to encapsulate the grid width and hide it from specific simulations.

#### Method should be part of the external API because it helps people on the team working in other modules write their code
```public void addCells(int numNewCells, int state)```  of the Simulation abstract class because the UI class needs to update the cells dynamically.

#### Method should be part of the internal API because it helps future coders within the module extend it
getPolygon() of Triangle class and getShape() of Square class. Only the UI class needs to call them, but not other team memebers working on other modules of the project. 

