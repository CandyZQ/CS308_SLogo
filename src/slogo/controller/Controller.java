package slogo.controller;

import slogo.view.ViewManager;

public class Controller {

  private ViewManager viewManager;
  private long time;

  public Controller(ViewManager viewManager) {
    this.viewManager = viewManager;
    time = System.currentTimeMillis();

  }



  public void update() {
    ViewManager.update();
  }
}
