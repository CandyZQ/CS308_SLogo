package slogo.view;


import javafx.scene.Group;

/**
 * This interface will work between the different classes of the front end of our code. Since this interface will be passing the root of our stage around, which is a security issue
 * we will make sure that only the objects that inherit this property from ViewObject
 */
public interface ViewInternalAPI {

  Group createRootObject(Group root);

  Group editRoot(Group root);

  Group removeFromRoot(Group root);

}
