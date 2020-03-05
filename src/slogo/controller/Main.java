package slogo.controller;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.Queue;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.util.Duration;
import slogo.exceptions.CommandDoesNotExistException;
import slogo.exceptions.InvalidArgumentException;
import slogo.exceptions.LanguageIsNotSupportedException;
import slogo.exceptions.WrongCommandFormatException;
import slogo.view.ViewScreen;
import slogo.controller.listings.MovingObjectProperties;


public class Main extends Application {

    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private ViewScreen viewScreen;
    private Parser parser;

    /**
     * Start the program.
     */
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws LanguageIsNotSupportedException {
        viewScreen = new ViewScreen(primaryStage);
        parser = new Parser(1);
        parser.setLanguage(viewScreen.getLanguage());
        setTiming();
    }

    private void setTiming() {
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
            try {
                step();
            } catch (Exception ex) {
                viewScreen.exceptionHandling(ex.getMessage());
            }
        });
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    private void step()
            throws WrongCommandFormatException, InvalidArgumentException, LanguageIsNotSupportedException, CommandDoesNotExistException, IOException {
        String inputString = viewScreen.getInputString();
        boolean runScript = viewScreen.getRunScript();
        Queue<Map<MovingObjectProperties, Object>> commands = null;
        if (runScript) {
            commands = parser.runScript(viewScreen.getScript());
        } else if (inputString != null) {
            commands = parser.execute(inputString);
        }
        //viewScreen.getColor(String);
        parser.setLanguage(viewScreen.getLanguage());
        viewScreen.update(commands, parser.gerUserVars(), parser.getFunctions());
    }

    private void newWindow(){
        Stage newStage = new Stage();
        Thread thread = new Thread(() -> {
            Platform.runLater(() -> {
                Main newSimul = new Main();
                try {
                    newSimul.start(newStage);
                } catch (LanguageIsNotSupportedException e) {
                    System.out.println("Excet");
                }
            });
        });
        thread.start();
    }
}

