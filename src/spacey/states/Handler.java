package spacey.states;

import java.awt.image.BufferStrategy;
import spacey.Display;
import spacey.Main;
import spacey.controls.KeyManager;
import spacey.controls.MouseManager;
import spacey.gfx.GUI;
import spacey.gfx.sounds.SoundController;

public class Handler {

    private static SoundController sc;
    public double xOffset = 0;
    public double yOffset = 0;
    private Main main;
    public static boolean borderless = false;
    public static double Scale;
    public final double StartScale;
    public static int ResSpot = 2;

    public Handler(Main main) {
        this.main = main;
        sc = new SoundController();
        Scale = (double) (main.getWidth()) / 1600D;
        StartScale = Scale;
    }

    public State getCurrentState() {
        return State.currentState;
    }

    public int getHeight() {
        return main.getHeight();
    }

    public int getWidth() {
        return main.getWidth();
    }

    public void setHeight(int x) {
        main.setHeight(x);
    }

    public void setWidth(int x) {
        main.setWidth(x);
    }

    public void switchToGridState() {
        switchCleanUp();
        State.currentState = main.getGridState();
        GUI.currentGUI = main.getGridState().gui;
    }

    public void switchToMenuState() {
        switchCleanUp();
        State.currentState = main.getMenuState();
        GUI.currentGUI = main.getMenuState().gui;
    }

    public void switchToTutorialState() {
        switchCleanUp();
        main.setTutorialState(new TutorialState(this));
        State.currentState = main.getTutorialState();
        GUI.currentGUI = main.getTutorialState().gui;
    }

    public void switchCleanUp() {
        xOffset = 0;
        yOffset = 0;
    }

    public KeyManager getKM() {
        return main.getKeyManager();
    }

    public MouseManager getMM() {
        return main.getMouseManager();
    }

    public GUI getCurrentGUI() {
        return GUI.currentGUI;
    }

    public SoundController getSC() {
        return sc;
    }

    public Display getDisplay() {
        return main.getDisplay();
    }

    public BufferStrategy getBS() {
        return main.getBs();
    }

    public void setBS(BufferStrategy bs) {
        main.setBs(bs);
    }

    public void resetEverything() {
        Scale = (double) (main.getWidth()) / 1280D;
        SoundController.mediaPlayer.stop();
        SoundController.activeTimer = false;
        main.setMenuState(new MenuState(this));
        main.setTutorialState(new TutorialState(this));
        main.setGridState(new GridState(this));
        GUI.currentGUI = main.getMenuState().gui;
        State.currentState = main.getMenuState();
    }

}
