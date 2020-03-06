package slogo.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ButtonG {

    private HBox box;
    private List<HBox> hboxes;
    private VBox vbox;
    private List<Button> buttons;
    private List<String> commands;
    private static final int SPACING = 30;

    private String[] numButtons;
    private static ResourceBundle myResources =
            ResourceBundle.getBundle("resources", Locale.getDefault());

    public ButtonG(String[] number){
        vbox = new VBox();
        vbox.getStyleClass().add("buttonvbox");
        numButtons = number;
        hboxes = new ArrayList<>();
        buttons = new ArrayList<>();
        createButtons();
        formatHBoxes();
        addElementsToVBox();
    }

    private void createButtons(){
        commands = new ArrayList<>();
        for(int i =0; i<numButtons.length; i++){
            commands.add(numButtons[i].split("\\|")[0]+" 50");
            buttons.add(new Button(commands.get(i)));
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
            box = new HBox(SPACING);
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
        box = new HBox(SPACING);
        box.getStyleClass().add(myResources.getString("HBox"));
        box.getChildren().addAll(alpha);
        box.setAlignment(Pos.CENTER);
        hboxes.add(box);
    }

    private void addElementsToVBox() {
        for (HBox hbox : hboxes) {
            vbox.getChildren().add(hbox);
        }
    }

    public List<Button> getButtons(){
        return buttons;
    }

}
