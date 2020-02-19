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
public interface InternalAPIViewable {

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
