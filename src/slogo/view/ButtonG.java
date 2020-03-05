package slogo.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class ButtonG {

    private HBox box;
    private ArrayList<HBox> hboxes;
    private VBox vbox;
    private final int VBOX_SPACING = 20;
    private ArrayList<Button> buttons;
    private ArrayList<String> commands;
    private String commandToDo;
    private boolean ctd = false;

    private String[] numButtons;
    private static ResourceBundle myResources =
            ResourceBundle.getBundle("resources", Locale.getDefault());

    public ButtonG(String[] number){
        vbox = new VBox(VBOX_SPACING);
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

    public ArrayList<Button> getButtons(){
        return buttons;
    }

}
