package spacey.states;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import spacey.gfx.GUI;
import spacey.gfx.UIObject;
import spacey.gfx.image.Assets;
import spacey.states.gridState.Tile;
import spacey.states.gridState.Tile.TileType;
import spacey.states.gridState.World;

public class TutorialState extends State {

    World world;
    GUI gui;
    private boolean editable = false;
    private static TileType currentlySelected = Tile.TileType.BATTERY;
    private ArrayList<UIObject> menu = new ArrayList<>();
    private ArrayList<UIObject> taskA = new ArrayList<>();
    private ArrayList<UIObject> taskB = new ArrayList<>();
    private ArrayList<UIObject> taskC = new ArrayList<>();
    UIObject title = new UIObject("Welcome to Spacey!", handler.getWidth() / 2, handler.getHeight() / 10, true, Color.white, Color.white, GUI.font50, menu);
    UIObject welcomeA = new UIObject("Today we are going to build the most basic ship.", handler.getWidth() / 2, handler.getHeight() / 3 + 60, true, Color.white, Color.white, GUI.font35, taskA);
    UIObject continueA = new UIObject("Click here to continue.", handler.getWidth() / 2, handler.getHeight() / 3 + 100, true, Color.white, Color.yellow, GUI.font35, taskA);
    UIObject batteryB = new UIObject("Batterys Needed.  0/1", handler.getWidth() / 2, handler.getHeight() / 10 + 55, true, Color.red, Color.red, GUI.font, taskB);
    UIObject reactorB = new UIObject("Reactors Needed.  0/1", handler.getWidth() / 2, handler.getHeight() / 10 + 80, true, Color.red, Color.red, GUI.font, taskB);
    UIObject welcomeB = new UIObject("Every ship needs power and batterys. Add some.", handler.getWidth() / 2, handler.getHeight() - 130, true, Color.white, Color.white, GUI.font35, taskB);
    UIObject continueB = new UIObject("Click here to continue.", handler.getWidth() / 2, handler.getHeight() - 90, true, Color.white, Color.yellow, GUI.font35, taskB);
    UIObject thrusterC = new UIObject("Thrusters Needed.  0/2", handler.getWidth() / 2, handler.getHeight() / 10 + 55, true, Color.red, Color.red, GUI.font, taskC);
    UIObject controlStationC = new UIObject("Control Station Needed.  0/1", handler.getWidth() / 2, handler.getHeight() / 10 + 80, true, Color.red, Color.red, GUI.font, taskC);
    UIObject welcomeC = new UIObject("Great. Now add some thrusters, and a control station.", handler.getWidth() / 2, handler.getHeight() - 130, true, Color.white, Color.white, GUI.font35, taskC);
    UIObject continueC = new UIObject("Click here to continue.", handler.getWidth() / 2, handler.getHeight() - 90, true, Color.white, Color.yellow, GUI.font35, taskC);
    UIObject returnToMenu = new UIObject("Return to Main Menu", handler.getWidth() / 2, handler.getHeight() - 30, true, Color.white, Color.yellow, GUI.font35, menu);

    UIObject sizeUI = new UIObject("Size: " + 0, 0, 15, false, Color.DARK_GRAY, Color.DARK_GRAY, taskB);
    UIObject thrusterUI = new UIObject("Thrusters Needed: " + 0, 0, 40, false, Color.DARK_GRAY, Color.DARK_GRAY, taskB);
    UIObject powerNeededUI = new UIObject("Power: " + "0/0", 0, 65, false, Color.DARK_GRAY, Color.DARK_GRAY, taskB);
    UIObject hullTileUI = new UIObject("Ship Hull", handler.getWidth() - 200, 15, false, Color.DARK_GRAY, Color.DARK_GRAY, taskB);
    UIObject wallTileUI = new UIObject("Ship Wall", handler.getWidth() - 200, 55, false, Color.DARK_GRAY, Color.DARK_GRAY, taskB);
    UIObject controlStationTileUI = new UIObject("Control Station", handler.getWidth() - 200, 95, false, Color.DARK_GRAY, Color.DARK_GRAY, taskB);
    UIObject batteryTileUI = new UIObject("Battery", handler.getWidth() - 200, 135, false, Color.white, Color.yellow, taskB);
    UIObject thrusterTileUI = new UIObject("Thruster", handler.getWidth() - 200, 175, false, Color.DARK_GRAY, Color.DARK_GRAY, taskB);
    UIObject solarTileUI = new UIObject("Solar Panel", handler.getWidth() - 200, 215, false, Color.DARK_GRAY, Color.DARK_GRAY, taskB);
    UIObject reactorTileUI = new UIObject("Reactor", handler.getWidth() - 200, 255, false, Color.white, Color.yellow, taskB);
    UIObject shieldTileUI = new UIObject("Shield", handler.getWidth() - 200, 295, false, Color.DARK_GRAY, Color.DARK_GRAY, taskB);
    UIObject warpDriveUI = new UIObject("Warp Drive", handler.getWidth() - 200, 335, false, Color.DARK_GRAY, Color.DARK_GRAY, taskB);
    UIObject emptyTileUI = new UIObject("Remove", handler.getWidth() - 200, 375, false, Color.white, Color.yellow, taskB);

    UIObject sizeUIC = new UIObject("Size: " + 0, 0, 15, false, Color.DARK_GRAY, Color.DARK_GRAY, taskC);
    UIObject thrusterUIC = new UIObject("Thrusters Needed: " + 0, 0, 40, false, Color.DARK_GRAY, Color.DARK_GRAY, taskC);
    UIObject powerNeededUIC = new UIObject("Power: " + "0/0", 0, 65, false, Color.DARK_GRAY, Color.DARK_GRAY, taskC);
    UIObject hullTileUIC = new UIObject("Ship Hull", handler.getWidth() - 200, 15, false, Color.DARK_GRAY, Color.DARK_GRAY, taskC);
    UIObject wallTileUIC = new UIObject("Ship Wall", handler.getWidth() - 200, 55, false, Color.DARK_GRAY, Color.DARK_GRAY, taskC);
    UIObject controlStationTileUIC = new UIObject("Control Station", handler.getWidth() - 200, 95, false, Color.white, Color.yellow, taskC);
    UIObject batteryTileUIC = new UIObject("Battery", handler.getWidth() - 200, 135, false, Color.DARK_GRAY, Color.DARK_GRAY, taskC);
    UIObject thrusterTileUIC = new UIObject("Thruster", handler.getWidth() - 200, 175, false, Color.white, Color.yellow, taskC);
    UIObject solarTileUIC = new UIObject("Solar Panel", handler.getWidth() - 200, 215, false, Color.DARK_GRAY, Color.DARK_GRAY, taskC);
    UIObject reactorTileUIC = new UIObject("Reactor", handler.getWidth() - 200, 255, false, Color.DARK_GRAY, Color.DARK_GRAY, taskC);
    UIObject shieldTileUIC = new UIObject("Shield", handler.getWidth() - 200, 295, false, Color.DARK_GRAY, Color.DARK_GRAY, taskC);
    UIObject warpDriveUIC = new UIObject("Warp Drive", handler.getWidth() - 200, 335, false, Color.DARK_GRAY, Color.DARK_GRAY, taskC);
    UIObject emptyTileUIC = new UIObject("Remove", handler.getWidth() - 200, 375, false, Color.white, Color.yellow, taskC);

    public TutorialState(Handler handler) {
        super(handler);
        world = new World(handler);
        gui = new GUI(handler);
        for (UIObject x : menu) {
            addText(gui, x);
        }
        for (UIObject x : taskA) {
            addText(gui, x);
        }
        for (UIObject x : taskB) {
            addText(gui, x);
            x.active = false;
        }
        for (UIObject x : taskC) {
            addText(gui, x);
            x.active = false;
        }
    }

    int batteryCount = 0;
    int thrusterCount = 0;
    int controlStationCount = 0;
    int reactorCount = 0;

    public void countShip() {
        batteryCount = 0;
        thrusterCount = 0;
        controlStationCount = 0;
        reactorCount = 0;
        for (Tile x : world.voidSpace) {
            if (x.getTileType() == Tile.TileType.THRUSTER) {
                thrusterCount++;
            }
            if (x.getTileType() == Tile.TileType.CONTROLSTATION) {
                controlStationCount++;
            }
            if (x.getTileType() == Tile.TileType.REACTOR) {
                reactorCount++;
            }
            if (x.getTileType() == Tile.TileType.BATTERY) {
                batteryCount++;
            }
        }
    }

    boolean battery = false;
    boolean controlStation = false;
    boolean reactor = false;
    boolean thruster = false;

    @Override
    public void tick() {
        world.tick();
        gui.tick();
        if (batteryTileUI.wasClicked()) {
            currentlySelected = Tile.TileType.BATTERY;
            return;
        }
        if (reactorTileUI.wasClicked()) {
            currentlySelected = Tile.TileType.REACTOR;
            return;
        }
        if (controlStationTileUIC.wasClicked()) {
            currentlySelected = Tile.TileType.CONTROLSTATION;
            return;
        }
        if (thrusterTileUIC.wasClicked()) {
            currentlySelected = Tile.TileType.THRUSTER;
            return;
        }
        if (emptyTileUI.wasClicked()) {
            currentlySelected = Tile.TileType.EMPTYSPACE;
            return;
        }
        if (returnToMenu.wasClicked()) {
            handler.switchToMenuState();
        }
        if (continueA.wasClicked()) {
            editable = true;
            for (UIObject x : taskA) {
                x.active = false;
            }
            for (UIObject x : taskB) {
                addText(gui, x);
                x.active = true;
            }
            int hW = handler.getWidth() / 2;
            int hH = handler.getHeight() / 2;
            world.getTileAt(hW, hH).setTileType(Tile.TileType.SHIPHULL);
            world.getTileAt(hW, hH).setTexture(-1);
            world.getTileAt(hW, hH).setChangeable(false);

        }
        if (continueB.wasClicked()) {
            if (!battery || !reactor) {
                return;
            }
            for (Tile x : world.voidSpace) {
                if (x.getTileType() != Tile.TileType.VOID) {
                    x.unchangable = true;
                }
            }
            currentlySelected = Tile.TileType.CONTROLSTATION;
            for (UIObject x : taskB) {
                addText(gui, x);
                x.active = false;
            }
            for (UIObject x : taskC) {
                addText(gui, x);
                x.active = true;
            }
        }
        if (continueC.wasClicked()) {
            if (!controlStation || !thruster) {
                return;
            }
            for (UIObject x : taskC) {
                addText(gui, x);
                x.active = false;
            }
            for (UIObject x : taskA) {
                addText(gui, x);
                x.active = true;
            }
            editable = false;
            continueA.active = false;
            title.setText("You are now ready to play!");
            welcomeA.setY(welcomeA.getY() - 180);
            welcomeA.setAllColors(Color.green);
            welcomeA.setText("*HINT* During FLIGHT mode theres options in the top right.");
        }
        if (editable) {
            battery = batteryCount == 1;
            reactor = reactorCount == 1;
            controlStation = controlStationCount == 1;
            thruster = thrusterCount == 2;
            if (battery) {
                batteryB.setAllColors(Color.green);
            } else {
                batteryB.setAllColors(Color.red);
            }
            if (reactor) {
                reactorB.setAllColors(Color.green);
            } else {
                reactorB.setAllColors(Color.red);
            }
            if (controlStation) {
                controlStationC.setAllColors(Color.green);
            } else {
                controlStationC.setAllColors(Color.red);
            }
            if (thruster) {
                thrusterC.setAllColors(Color.green);
            } else {
                thrusterC.setAllColors(Color.red);
            }
            batteryB.setText("Batterys Needed.  " + batteryCount + "/1");
            reactorB.setText("Reactors Needed.  " + reactorCount + "/1");
            thrusterC.setText("Thrusters Needed.  " + thrusterCount + "/2");
            countShip();
            int hW = handler.getWidth() / 2;
            int hH = handler.getHeight() / 2;
            if (handler.getMM().isLeftPressed()) {
                if (world.getTileAt(hW, hH).equals(world.getTileAt(handler.getMM().getMouseX(), handler.getMM().getMouseY()))) {
                    world.getTileAt(hW, hH).setTileType(currentlySelected);
                }
                changeNearTile(world.getTileAt(handler.getMM().getMouseX(), handler.getMM().getMouseY()), currentlySelected);
                world.getTileAt(handler.getMM().getMouseX(), handler.getMM().getMouseY()).setTexture(-1);
            }
            if (handler.getMM().isRightPressed()) {
                world.getTileAt(handler.getMM().getMouseX(), handler.getMM().getMouseY()).setTileType(Tile.TileType.VOID);
            }
        }
    }

    int width = (int) (40 * Handler.Scale);
    int height = width;

    public void changeNearTile(Tile tile, TileType a) {
        for (Tile x : world.voidSpace) {
            if (x.getBounds().contains(tile.getX() + 1, tile.getY() - 1)) {
                if (x.getTileType() != Tile.TileType.VOID) {
                    tile.setTileType(a);
                }
            } else if (x.getBounds().contains(tile.getX() + width + 1, tile.getY() + 1)) {
                if (x.getTileType() != Tile.TileType.VOID) {
                    tile.setTileType(a);
                }
            } else if (x.getBounds().contains(tile.getX() - 1, tile.getY() + 1)) {
                if (x.getTileType() != Tile.TileType.VOID) {
                    tile.setTileType(a);
                }
            } else if (x.getBounds().contains(tile.getX() + 1, tile.getY() + height + 1)) {
                if (x.getTileType() != Tile.TileType.VOID) {
                    tile.setTileType(a);
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        for (Tile x : world.voidSpace) {
            if (x.getTileType() != Tile.TileType.VOID) {
                x.render(g);
            }
        }
        world.render(g);
        gui.render(g);
        if (editable) {
            g.drawImage(Assets.shipHull, handler.getWidth() - 200 - 40, 0, 40, 40, null);
            g.drawImage(Assets.shipWall, handler.getWidth() - 200 - 40, 40, 40, 40, null);
            g.drawImage(Assets.controlStation, handler.getWidth() - 200 - 40, 40 * 2, 40, 40, null);
            g.drawImage(Assets.battery, handler.getWidth() - 200 - 40, 40 * 3, 40, 40, null);
            g.drawImage(Assets.thruster, handler.getWidth() - 200 - 40, 40 * 4, 40, 40, null);
            g.drawImage(Assets.solarPanel, handler.getWidth() - 200 - 40, 40 * 5, 40, 40, null);
            g.drawImage(Assets.reactor, handler.getWidth() - 200 - 40, 40 * 6, 40, 40, null);
            g.drawImage(Assets.shieldBlock, handler.getWidth() - 200 - 40, 40 * 7, 40, 40, null);
            g.drawImage(Assets.warpDrive, handler.getWidth() - 200 - 40, 40 * 8, 40, 40, null);
            g.drawImage(Assets.remove, handler.getWidth() - 200 - 40, 40 * 9, 40, 40, null);
        }
    }

}
