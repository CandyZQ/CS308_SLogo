package slogo.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ButtonGroup {

    private HBox box;
    private List<HBox> hboxes;
    private VBox vbox;
    private final int VBOX_SPACING = 20;
    private List<Button> buttons;
    private List<String> numButtons;

    private static ResourceBundle myResources =
            ResourceBundle.getBundle("resources", Locale.getDefault());

    public ButtonGroup(List<String> number){
        vbox = new VBox(VBOX_SPACING);
        numButtons = number;
        hboxes = new ArrayList<>();
        buttons = new ArrayList<>();
        createButtons();
        formatHBoxes();
        addElementsToVBox();
    }

    private void createButtons(){
        for(int i =0; i<numButtons.size(); i++){
            buttons.add(new Button(numButtons.get(i)));
            buttons.get(i).setWrapText(true);
        }
    }

    private void formatHBoxes(){
        int extent;
        if(buttons.size() % 2 == 0){
            extent = buttons.size();
        }else{
            extent = buttons.size() - 1;
        }
        for(int i =0; i < extent; i+=2){
            Button alpha = buttons.get(i);
            Button beta = buttons.get(i+1);
            box = new HBox(30);
            box.getStyleClass().add(myResources.getString("HBox"));
            box.getChildren().addAll(alpha, beta);
            box.setAlignment(Pos.CENTER);
            hboxes.add(box);
        }
        if(extent == buttons.size() - 1){
            addOddBox();
        }
    }

    public VBox getBoxes(){
        return vbox;
    }

    private void addOddBox(){
        Button alpha = buttons.get(buttons.size() - 1);
        box = new HBox(30);
        box.getStyleClass().add(myResources.getString("HBox"));
        box.getChildren().addAll(alpha);
        box.setAlignment(Pos.CENTER);
        hboxes.add(box);
    }

    private void addElementsToVBox(){
        for(int i = 0; i < hboxes.size(); i++){
            vbox.getChildren().add(hboxes.get(i));
        }
    }

    public List<Button> getButtons(){
        return buttons;
    }

}
