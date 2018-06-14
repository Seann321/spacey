package spacey.states;

import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;
import spacey.gfx.GUI;
import spacey.gfx.UIObject;
import spacey.gfx.sounds.SoundController;
import spacey.states.gridState.World;

public class MenuState extends State {

    private ArrayList<UIObject> menu = new ArrayList<>();
    private ArrayList<UIObject> optionsMenu = new ArrayList<>();
    World world;
    GUI gui;

    //Res Stuff
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = (int) screenSize.getWidth();
    int height = (int) screenSize.getHeight();

    //Tooltip
    UIObject tooltip = new UIObject("Not Very Informative.", handler.getWidth() / 2, handler.getHeight() / 10, false, Color.red, Color.red, GUI.font, menu);

    //Main Page
    UIObject title = new UIObject("Spacey", handler.getWidth() / 2, handler.getHeight() / 10, true, Color.white, Color.white, GUI.font50, menu);
    UIObject credits = new UIObject("Created By: Sean", handler.getWidth() / 2, handler.getHeight() / 10 + 40, true, Color.white, Color.white, menu);
    UIObject startGame = new UIObject("Start Game", handler.getWidth() / 2, handler.getHeight() / 3 + 40, true, Color.white, Color.yellow, GUI.font35, menu);
    UIObject startTutorial = new UIObject("Learn to play", handler.getWidth() / 2, handler.getHeight() / 3 + 80, true, Color.white, Color.yellow, GUI.font35, menu);
    UIObject options = new UIObject("Options", handler.getWidth() / 2, handler.getHeight() / 3 + 120, true, Color.white, Color.yellow, GUI.font35, menu);
    UIObject exitGame = new UIObject("Exit", handler.getWidth() / 2, handler.getHeight() / 3 + 160, true, Color.white, Color.yellow, GUI.font35, menu);

    //Options
    UIObject returnToMenu = new UIObject("Return", handler.getWidth() / 2, handler.getHeight() / 3 + 120, true, Color.white, Color.yellow, GUI.font35, optionsMenu);
    UIObject applySettings = new UIObject("Apply", handler.getWidth() / 2, handler.getHeight() / 3 + 160, true, Color.green, Color.yellow, GUI.font35);
    UIObject changeVolume = new UIObject("Volume: " + (int) (SoundController.volume * 100) + "%", handler.getWidth() / 2, handler.getHeight() / 3 + 40, true, Color.white, Color.yellow, GUI.font35, optionsMenu);
    UIObject muteVolume = new UIObject("Mute Audio", handler.getWidth() / 2, handler.getHeight() / 3 + 80, true, Color.white, Color.yellow, GUI.font35, optionsMenu);

    int resolutionLevels[][] = new int[][]{{1024, 576}, {1152, 648}, {1280, 720}, {1366, 768}, {1600, 900}, {width, height}};
    UIObject toggleFullScreen = new UIObject("Resolution " + resolutionLevels[Handler.ResSpot][0] + "*" + resolutionLevels[Handler.ResSpot][1], handler.getWidth() / 2, handler.getHeight() / 3, true, Color.white, Color.yellow, GUI.font35, optionsMenu);
    UIObject toggleBorderless = new UIObject("Toggle Borderless Mode", handler.getWidth() / 2, handler.getHeight() / 3 - 40, true, Color.white, Color.yellow, GUI.font35, optionsMenu);

    double volumeLevels[] = new double[]{0, .05, .1, .15, .2, .3, .4, .5, .6, .7, .8, .9, 1};
    int volumeLevelSpot = 2;

    //1024×576, 1152×648, 1280×720, 1366×768, 1600×900, 1920×1080
    public MenuState(Handler handler) {
        super(handler);
        gui = new GUI(handler);
        GUI.currentGUI = gui;
        world = new World(handler);
        for (UIObject x : menu) {
            addText(gui, x);
        }
        for (UIObject x : optionsMenu) {
            addText(gui, x);
            x.active = false;
        }
        addText(gui, applySettings);
        applySettings.active = false;
        tooltip.active = false;
        handler.getSC().playSound(SoundController.mp3s[0]);
    }

    String temp = "If you can read this, something is broken.";

    @Override
    public void tick() {
        world.tick();
        gui.tick();
        handler.xOffset++;
        handler.yOffset++;
        if (title.wasClicked()) {
            world = null;
            world = new World(handler);
        }
        if (startTutorial.wasClicked()) {
            handler.switchToTutorialState();
        }
        if (credits.hovering && credits.active) {
            tooltip.setAllColors(Color.green);
            tooltip.setText("Music by Kevin MacLeod");
            tooltip.active = true;
            tooltip.x = handler.getMM().getMouseX() + 10;
            tooltip.y = handler.getMM().getMouseY() - 10;
        }
        if (startTutorial.hovering && startTutorial.active) {
            tooltip.setAllColors(Color.red);
            if (!tooltip.active) {
                switch (new Random().nextInt(4) + 1) {

                    case 1:
                        temp = "Not Very Informative.";
                        break;
                    case 2:
                        temp = "For advanced players only.";
                        break;
                    case 3:
                        temp = "50/50 Chance of working.";
                        break;
                    case 4:
                        temp = "Check out my other games!";
                        break;

                }
            }
            tooltip.setText(temp);
            tooltip.active = true;
            tooltip.x = handler.getMM().getMouseX() + 10;
            tooltip.y = handler.getMM().getMouseY() - 10;
        }
        if (title.hovering && title.active) {
            tooltip.setAllColors(Color.green);
            tooltip.setText("From the makers of Cupcake Defender!");
            tooltip.active = true;
            tooltip.x = handler.getMM().getMouseX() + 10;
            tooltip.y = handler.getMM().getMouseY() - 10;
        }
        if (!(title.hovering && title.active) && !(startTutorial.hovering && startTutorial.active) && !(credits.hovering && credits.active)) {
            tooltip.active = false;
        }
        if (startGame.wasClicked()) {
            handler.switchToGridState();
        }
        if (options.wasClicked()) {
            for (UIObject x : menu) {
                x.active = false;
            }
            for (UIObject x : optionsMenu) {
                x.active = true;
            }
        }
        if (exitGame.wasClicked()) {
            System.exit(0);
        }
        if (returnToMenu.wasClicked()) {
            for (UIObject x : menu) {
                x.active = true;
            }
            for (UIObject x : optionsMenu) {
                x.active = false;
            }
        }
        if (changeVolume.wasClicked()) {
            if (volumeLevelSpot == volumeLevels.length - 1) {
                volumeLevelSpot = 0;
                handler.getSC().changeVolume(volumeLevels[volumeLevelSpot]);
            } else {
                volumeLevelSpot++;
                handler.getSC().changeVolume(volumeLevels[volumeLevelSpot]);
            }
            changeVolume.setText("Volume: " + (int) (volumeLevels[volumeLevelSpot] * 100) + "%");
        }
        if (changeVolume.wasRightClicked()) {
            if (volumeLevelSpot == 0) {
                volumeLevelSpot = volumeLevels.length - 1;
                handler.getSC().changeVolume(volumeLevels[volumeLevelSpot]);
            } else {
                volumeLevelSpot--;
                handler.getSC().changeVolume(volumeLevels[volumeLevelSpot]);
            }
            changeVolume.setText("Volume: " + (int) (volumeLevels[volumeLevelSpot] * 100) + "%");
        }
        if (muteVolume.wasClicked()) {
            volumeLevelSpot = 0;
            handler.getSC().changeVolume(volumeLevels[volumeLevelSpot]);
            changeVolume.setText("Volume: " + (int) (volumeLevels[volumeLevelSpot] * 100) + "%");
        }
        applySettings.active = resolutionLevels[Handler.ResSpot][0] != handler.getWidth();
        if (toggleFullScreen.wasClicked()) {
            if (Handler.ResSpot == resolutionLevels.length - 1) {
                Handler.ResSpot = 0;
            } else {
                Handler.ResSpot++;
            }
            if (resolutionLevels[Handler.ResSpot][0] != width) {
                toggleFullScreen.setText("Resolution " + resolutionLevels[Handler.ResSpot][0] + "*" + resolutionLevels[Handler.ResSpot][1]);
            } else {
                toggleFullScreen.setText("Resolution " + resolutionLevels[Handler.ResSpot][0] + "*" + resolutionLevels[Handler.ResSpot][1] + " (FullScreen)");
            }
        }
        if (toggleFullScreen.wasRightClicked()) {
            if (Handler.ResSpot == 0) {
                Handler.ResSpot = resolutionLevels.length - 1;
            } else {
                Handler.ResSpot--;
            }
            if (resolutionLevels[Handler.ResSpot][0] != width) {
                toggleFullScreen.setText("Resolution " + resolutionLevels[Handler.ResSpot][0] + "*" + resolutionLevels[Handler.ResSpot][1]);
            } else {
                toggleFullScreen.setText("Resolution " + resolutionLevels[Handler.ResSpot][0] + "*" + resolutionLevels[Handler.ResSpot][1] + " (FullScreen)");
            }
        }
        if (toggleBorderless.wasClicked()) {
            if (resolutionLevels[Handler.ResSpot][0] == width) {
                return;
            }
            Handler.borderless = !Handler.borderless;
            handler.getDisplay().setBorderless(Handler.borderless);
            handler.resetEverything();
        }
        if (applySettings.wasClicked()) {

            handler.getDisplay().changeSize(resolutionLevels[Handler.ResSpot][0], resolutionLevels[Handler.ResSpot][1]);
            handler.setWidth(resolutionLevels[Handler.ResSpot][0]);
            handler.setHeight(resolutionLevels[Handler.ResSpot][1]);

            if (resolutionLevels[Handler.ResSpot][0] == width && resolutionLevels[Handler.ResSpot][1] == height) {
                handler.getDisplay().setBorderless(true);
            } else {
                handler.getDisplay().setBorderless(false);
            }
            handler.resetEverything();

        }
        if (handler.getKM().keyJustPressed(KeyEvent.VK_F11)) {
            Handler.ResSpot = resolutionLevels.length - 1;
            handler.getDisplay().changeSize(resolutionLevels[Handler.ResSpot][0], resolutionLevels[Handler.ResSpot][1]);
            handler.setWidth(resolutionLevels[Handler.ResSpot][0]);
            handler.setHeight(resolutionLevels[Handler.ResSpot][1]);

            if (resolutionLevels[Handler.ResSpot][0] == width && resolutionLevels[Handler.ResSpot][1] == height) {
                handler.getDisplay().setBorderless(true);
            } else {
                handler.getDisplay().setBorderless(false);
            }
            handler.resetEverything();
        }
    }

    @Override
    public void render(Graphics g) {
        world.render(g);
        gui.render(g);
    }

}
