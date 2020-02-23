package slogo.controller;


import slogo.view.ExternalAPIViewable;
import slogo.view.ViewScreen;

public class Controller {

  private ViewScreen viewScreen;
  private long time;

  public Controller(ViewScreen viewScreen) {
    this.viewScreen = viewScreen;
    time = System.currentTimeMillis();

  }



  public void update() {
    ViewScreen.update();
  }
}
