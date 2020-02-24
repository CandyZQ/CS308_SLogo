package slogo.view;

import javafx.scene.layout.BorderPane;
import javafx.scene.control.*;
import javafx.scene.paint.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class ColorPickerViewable extends ObjectsViewable {

    @Override
    public BorderPane createRootObject(BorderPane root) {
        VBox align = new VBox(10);
        ColorPicker cp = new ColorPicker(Color.BLUE);
        align.getChildren().add(cp);
        root.setBottom(align);
        return root;
    }

    @Override
    public BorderPane editRoot(BorderPane root) {
        return null;
    }

    @Override
    public BorderPane removeFromRoot(BorderPane root) {
        return null;
    }
}
