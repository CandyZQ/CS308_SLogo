package slogo.view;

import java.util.Map;
import java.util.Queue;
import javafx.stage.Stage;

public class CommandPrompt implements ExternalAPIViewable {


  @Override
  public Queue<Map<String, Integer>> getFinalInformation() {
    return null;
  }

  @Override
  public String getInputString() {
    return null;
  }

  @Override
  public void exceptionHandling(String errorMessage) {

  }

  @Override
  public Stage setScene() {
    return null;
  }

  @Override
  public String getLanguage() {
    return null;
  }
}
