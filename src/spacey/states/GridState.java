package spacey.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import spacey.gfx.GUI;
import spacey.gfx.UIObject;
import spacey.gfx.image.Assets;
import spacey.states.gridState.Camera;
import spacey.states.gridState.Tile;
import spacey.states.gridState.Tile.TileType;
import spacey.states.gridState.World;

public class GridState extends State {

    //Warning. This class is a complete mess. Do not look in the eyes.
    //ArrayList
    private ArrayList<Tile> gridSpace;
    private ArrayList<Tile> newGridSpace;
    private ArrayList<UIObject> menu = new ArrayList<>();
    private ArrayList<UIObject> gameGUI = new ArrayList<>();

    //GUI
    GUI gui;
    UIObject debug = new UIObject("Currently Selected Tile: " + Tile.TileType.SHIPHULL, 0, handler.getHeight() - 10, false, Color.white, Color.white);
    UIObject start = new UIObject("Click to enter flight mode", 0, handler.getHeight() - 35, false, Color.white, Color.yellow);
    UIObject returnToMenu = new UIObject("Click to return to menu", 0, handler.getHeight() - 60, false, Color.white, Color.yellow, menu);

    //Gameplay GUI
    UIObject powerUI = new UIObject("Power Left: " + 0, 0, 15, false, Color.white, Color.white, gameGUI);
    UIObject speedUI = new UIObject("Vert Speed: " + 0 + " Side Speed: " + 0, 0, 40, false, Color.white, Color.white, gameGUI);
    UIObject cordsUI = new UIObject("Current Cords: ", 0, 65, false, Color.white, Color.white, gameGUI);
    UIObject recallShipUI = new UIObject("Return Home", 0, 90, false, Color.lightGray, Color.gray, gameGUI);
    UIObject stopShipUI = new UIObject("Stop Ship (Will only stop speeds under 10)", 0, 115, false, Color.lightGray, Color.gray, gameGUI);

    //Power Levels
    UIObject enginePowerUI = new UIObject("* Engine Power: " + "100.0%", handler.getWidth() - 290, 15, false, Color.lightGray, Color.gray, gameGUI);
    UIObject shieldPowerUI = new UIObject("* Shield Powered: " + false, handler.getWidth() - 290, 45, false, Color.lightGray, Color.gray, gameGUI);
    UIObject warpPowerUI = new UIObject("* Warp Powered: " + false, handler.getWidth() - 290, 75, false, Color.lightGray, Color.gray, gameGUI);

    //Objects
    UIObject sizeUI = new UIObject("Size: " + 0, 0, 65, false, Color.white, Color.white, menu);
    UIObject thrusterUI = new UIObject("Thrusters Needed: " + 0, 0, 40, false, Color.white, Color.white, menu);
    UIObject hullTileUI = new UIObject("Ship Hull", handler.getWidth() - 200, 15, false, Color.white, Color.yellow, menu);
    UIObject wallTileUI = new UIObject("Ship Wall", handler.getWidth() - 200, 55, false, Color.white, Color.yellow, menu);
    UIObject controlStationTileUI = new UIObject("Control Station", handler.getWidth() - 200, 95, false, Color.white, Color.yellow, menu);
    UIObject batteryTileUI = new UIObject("Battery", handler.getWidth() - 200, 135, false, Color.white, Color.yellow, menu);
    UIObject thrusterTileUI = new UIObject("Thruster", handler.getWidth() - 200, 175, false, Color.white, Color.yellow, menu);
    UIObject solarTileUI = new UIObject("Solar Panel", handler.getWidth() - 200, 215, false, Color.white, Color.yellow, menu);
    UIObject reactorTileUI = new UIObject("Reactor", handler.getWidth() - 200, 255, false, Color.white, Color.yellow, menu);
    UIObject shieldTileUI = new UIObject("Shield", handler.getWidth() - 200, 295, false, Color.white, Color.yellow, menu);
    UIObject warpDriveUI = new UIObject("Warp Drive", handler.getWidth() - 200, 335, false, Color.white, Color.yellow, menu);
    UIObject emptyTileUI = new UIObject("Remove", handler.getWidth() - 200, 375, false, Color.white, Color.yellow, menu);

    UIObject message = new UIObject("", handler.getWidth() / 2, handler.getHeight() / 6, true, Color.white, Color.white, GUI.font50, gameGUI);

    private final int width = (int) (40 * Handler.Scale), height = (int) (40 * Handler.Scale);
    private TileType currentlySelected = Tile.TileType.SHIPHULL;
    private boolean flightMode = false, renderGUI = true;
    private int thrusterCount = 0, controlStationCount = 0, batteryCount = 0, solarCount = 0, reactorCount = 0, shieldBlockCount = 0, warpDriveCount = 0, oxygenGeneratorCount = 0;
    private int powerCellsRemaining = 0;
    private int powerCellsLimit = 0;
    private int powerCellsCapacity = 0;
    private double shipSize, speed = 0;
    private double sideVelo = 0, vertVelo = 0;
    private double enginePower = 1;
    private boolean shieldPower = false;
    private boolean warpPower = false;

    private Camera camera = new Camera(handler);
    private World world = new World(handler);

    public GridState(Handler handler) {
        super(handler);
        gui = new GUI(handler);
        gridSpace = new ArrayList<>();
        newGridSpace = new ArrayList<>();
        addText(gui, debug);
        addText(gui, start);
        for (UIObject x : menu) {
            addText(gui, x);
            if (!x.getText().equals("Click to return to menu")) {
                x.xOffset = 40;
                x.yOffset = 20;
            }
        }
        for (UIObject x : gameGUI) {
            addText(gui, x);
            x.active = false;
        }
        createGrid();
    }

    int hW = handler.getWidth() / width;
    int hH = handler.getHeight() / height;

    public void createGrid() {
        for (int i = 0; i < hW; i++) {
            for (int ii = 0; ii < hH; ii++) {
                newGridSpace.add(new Tile(handler, new Rectangle(i * width, ii * height, width, height), Tile.TileType.EMPTYSPACE, i * width, ii * height));
            }
        }
        newGridSpace.get(hW * hH / 2 + hH / 2).setTileType(Tile.TileType.SHIPHULL);
        newGridSpace.get(hW * hH / 2 + hH / 2).setChangeable(false);
    }

    public void changeNearTile(Tile tile, TileType a) {
        for (Tile x : gridSpace) {
            if (x.getBounds().contains(tile.getX() + 1, tile.getY() - 1)) {
                if (x.getTileType() != Tile.TileType.EMPTYSPACE) {
                    tile.setTileType(a);
                    tile.setStartType(a);
                }
            } else if (x.getBounds().contains(tile.getX() + width + 1, tile.getY() + 1)) {
                if (x.getTileType() != Tile.TileType.EMPTYSPACE) {
                    tile.setTileType(a);
                    tile.setStartType(a);

                }
            } else if (x.getBounds().contains(tile.getX() - 1, tile.getY() + 1)) {
                if (x.getTileType() != Tile.TileType.EMPTYSPACE) {
                    tile.setTileType(a);
                    tile.setStartType(a);

                }
            } else if (x.getBounds().contains(tile.getX() + 1, tile.getY() + height + 1)) {
                if (x.getTileType() != Tile.TileType.EMPTYSPACE) {
                    tile.setTileType(a);
                    tile.setStartType(a);
                }
            }
        }
    }

    public void changeNearTile(Tile tile, TileType a, TileType b, TileType c) {
        if (tile.isSheilded()) {
            return;
        }
        for (Tile x : gridSpace) {
            if (x.getTileType() == c || x.getTileType() != b || tile.equals(x)) {
                continue;
            }
            if (x.getBounds().contains(tile.getX() + 1, tile.getY() - 1)) {
                x.setTileType(a);
                x.setSheilded(true);
            }
            if (x.getBounds().contains(tile.getX() + width + 1, tile.getY() + 1)) {
                x.setTileType(a);
                x.setSheilded(true);
            }
            if (x.getBounds().contains(tile.getX() - 1, tile.getY() + 1)) {
                x.setTileType(a);
                x.setSheilded(true);
            }
            if (x.getBounds().contains(tile.getX() + 1, tile.getY() + height + 1)) {
                x.setTileType(a);
                x.setSheilded(true);
            }
        }
    }

    @Override
    public void tick() {
        world.tick();
        gui.tick();
        gridSpace.clear();
        gridSpace.addAll(newGridSpace);
        gridSpace.forEach((x) -> {
            x.tick();
        });
        commands();
        input();
        switchModes();
        usePower();
        if (flightMode) {
            gameTick();
        } else {
            buildTick();
        }
    }

    boolean powerWarningPrinted = false;

    public void gameTick() {
        if (!warpPower && !centeringShip) {
            for (Tile t : gridSpace) {
                if (t.getTileType() == Tile.TileType.EMPTYSPACE) {
                    continue;
                }
                for (Tile a : world.voidSpace) {
                    if (a.getTileType() != Tile.TileType.ASTEROID) {
                        continue;
                    }
                    if (t.getBounds().intersects(a.getBounds())) {
                        if (shieldPower) {
                            vertVelo = 0;
                            sideVelo = 0;
                            shieldPower = false;
                        } else {
                            vertVelo = vertVelo / 2;
                            sideVelo = sideVelo / 2;
                        }
                        for (Tile tile : gridSpace) {
                            if (tile.getBounds().intersects(a.getBounds())) {
                                tile.setTileType(Tile.TileType.EMPTYSPACE);
                            }
                        }
                        countShip();
                    }
                }
            }
        }
        //Checks for shield active, then draws sheild.

        if (warpPower) {
            for (Tile t : gridSpace) {
                if (t.getTileType() == Tile.TileType.WARPDRIVE) {
                    t.setTexture(1);
                }
            }
            speed = (((thrusterCount * 4) - shipSize) / 15 * enginePower) * 100;
        } else {
            for (Tile t : gridSpace) {
                if (t.getTileType() == Tile.TileType.WARPDRIVE) {
                    t.setTexture(0);
                }
            }
            speed = ((thrusterCount * 4) - shipSize) / 15 * enginePower;
        }
        if (powerCellsRemaining < shieldBlockCount && shieldPower) {
            shieldPower = false;
            printMessage(Color.red, "Power too low. Shields disabled.");
        }
        if (powerCellsRemaining < warpDriveCount * 3 && warpPower) {
            warpPower = false;
            printMessage(Color.red, "Power too low. Warp disabled.");
        }

        if ((solarCount == 0 && reactorCount == 0) && !powerWarningPrinted) {
            powerWarningPrinted = true;
            printMessage(Color.red, "Out of Power. Add Generators.");
        }
        if (speed < 0) {
            speed = 0;
        }
        cordsUI.setText("Current Cords: X " + newGridSpace.get(hW * hH / 2 + hH / 2).getX() + ", Y " + newGridSpace.get(hW * hH / 2 + hH / 2).getY());
        speedUI.setText("Vert Speed: " + Math.floor(vertVelo * -1) + " Side Speed: " + Math.floor(sideVelo));
        shieldPowerUI.setText("* Shield Powered: " + shieldPower);
        warpPowerUI.setText("* Warp Powered: " + warpPower);
        if (shieldPower) {
            if (sideVelo > 1 || sideVelo < -1 || vertVelo > 1 || vertVelo < -1) {
                return;
            }
            for (Tile t : gridSpace) {
                if (t.getTileType() == Tile.TileType.SHIELDBLOCK) {
                    t.setTexture(1);
                }
                if (t.getTileType() == Tile.TileType.EMPTYSPACE) {
                    continue;
                }
                changeNearTile(t, Tile.TileType.SHIELD, Tile.TileType.EMPTYSPACE, Tile.TileType.SHIELD);
            }
        } else {
            for (Tile t : gridSpace) {
                if (t.getTileType() == Tile.TileType.SHIELDBLOCK) {
                    t.setTexture(0);
                }
                if (t.getTileType() == Tile.TileType.SHIELD) {
                    t.setTileType(Tile.TileType.EMPTYSPACE);
                }
            }
        }
    }

    int tempShipSize = 0;
    int tempThrustersNeeded = 0;
    int tempEnergyNeeded = 0;
    int tempBatterys = 0;

    public void buildTick() {
        placeTile();
        if (handler.getKM().keyJustPressed(KeyEvent.VK_R)) {
            gridSpace.forEach((x) -> {
                x.setTileType(Tile.TileType.EMPTYSPACE);
                x.setStartType(Tile.TileType.EMPTYSPACE);
            });
            newGridSpace.get(hW * hH / 2 + hH / 2).setTileType(Tile.TileType.SHIPHULL);
        }
        tempShipSize = 0;
        tempThrustersNeeded = 0;
        tempEnergyNeeded = 0;
        tempBatterys = 0;
        gridSpace.forEach((x) -> {
            if (x.getTileType() != Tile.TileType.EMPTYSPACE) {
                tempShipSize += x.getWeight();
                if (x.getTileType() == Tile.TileType.THRUSTER) {
                    tempThrustersNeeded++;
                }
                if (x.getTileType() == Tile.TileType.BATTERY) {
                    tempBatterys += 5;
                }
                if (x.getTileType() == Tile.TileType.REACTOR) {
                    tempEnergyNeeded += 3;
                }
                if (x.getTileType() == Tile.TileType.SOLARPANEL) {
                    tempEnergyNeeded += 1;
                }
                if (x.getTileType() == Tile.TileType.CONTROLSTATION) {
                    tempEnergyNeeded -= 1;
                }
                if (x.getTileType() == Tile.TileType.OXYGENGENERATOR) {
                    tempEnergyNeeded -= 1;
                }
            }
        });

        countShip();
        powerUI.active = true;
        usePower();

        thrusterUI.setText("Thrusters Needed: " + tempThrustersNeeded + "/" + (tempShipSize / 4 + 1));
        sizeUI.setText("Size: " + tempShipSize);
    }

    public void usePower() {
        //powerCellsCapacity
        powerCellsRemaining = powerCellsLimit;
        if (shieldPower) {
            powerCellsRemaining -= shieldBlockCount;
        }
        if (warpPower) {
            powerCellsRemaining -= warpDriveCount * 3;
        }
        powerCellsRemaining -= controlStationCount;
        int tempWithThrusters = powerCellsRemaining - thrusterCount;

        if (powerCellsRemaining >= 0) {
            powerUI.setText("Power Left: " + (powerCellsRemaining) + "/" + powerCellsCapacity + " | With Thrusters: " + tempWithThrusters + "/" + powerCellsCapacity);
        } else {
            powerUI.setText("Power Left: " + 0 + "/" + powerCellsCapacity);
        }
    }

    public void countShip() {
        warpDriveCount = 0;
        batteryCount = 0;
        controlStationCount = 0;
        thrusterCount = 0;
        solarCount = 0;
        shipSize = 0;
        reactorCount = 0;
        vertVelo = 0;
        sideVelo = 0;
        shieldBlockCount = 0;
        oxygenGeneratorCount = 0;
        newGridSpace.forEach((x) -> {
            if (x.getTileType() == Tile.TileType.OXYGENGENERATOR) {
                oxygenGeneratorCount++;
            }
            if (x.getTileType() != Tile.TileType.EMPTYSPACE) {
                shipSize += x.getWeight();
            }
            if (x.getTileType() == Tile.TileType.BATTERY) {
                batteryCount++;
            }
            if (x.getTileType() == Tile.TileType.SHIELDBLOCK) {
                shieldBlockCount++;
            }
            if (x.getTileType() == Tile.TileType.REACTOR) {
                reactorCount++;
            }
            if (x.getTileType() == Tile.TileType.CONTROLSTATION) {
                controlStationCount++;
            }
            if (x.getTileType() == Tile.TileType.THRUSTER) {
                thrusterCount++;
            }
            if (x.getTileType() == Tile.TileType.SOLARPANEL) {
                solarCount++;
            }
            if (x.getTileType() == Tile.TileType.WARPDRIVE) {
                warpDriveCount++;
            }
        });

        speed = ((thrusterCount * 4) - shipSize) / 15 * enginePower;

        if (speed < 0) {
            speed = 0;
        }

        if (batteryCount < 1) {
            printMessage(Color.red, "No Power. Add batterys");
        }
        if (controlStationCount < 1) {
            printMessage(Color.red, "No Controller. Add Control Station");
        }
        powerCellsCapacity = batteryCount * 5;
        powerCellsLimit = (reactorCount * 3) + solarCount;
        if (powerCellsLimit > powerCellsCapacity) {
            powerCellsLimit = powerCellsCapacity;
        }
    }

    public void switchModes() {
        if (start.wasClicked() || handler.getKM().keyJustPressed(KeyEvent.VK_ESCAPE)) {
            flightMode = !flightMode;
            if (flightMode) {
                warpPower = false;
                shieldPower = false;
                powerWarningPrinted = false;
                message.setText("");
                for (UIObject x : gameGUI) {
                    x.active = true;
                }
                menu.forEach((x) -> {
                    x.active = false;
                });
                engineSpeedSpot = engineSpeeds.length - 1;
                enginePower = engineSpeeds[engineSpeedSpot];
                enginePowerUI.setText("* Engine Power: " + enginePower * 100 + "%");
                countShip();
                start.setText("Click to enter build mode");
                start.setY(start.getY() + 23);
                debug.active = false;
            } else {
                for (Tile t : gridSpace) {
                    t.setTileType(t.getStartType());
                    if (t.getTileType() == Tile.TileType.THRUSTER) {
                        t.setTexture(0);
                    }
                    if (t.getTileType() == Tile.TileType.WARPDRIVE) {
                        t.setTexture(0);
                    }
                    if (t.getTileType() == Tile.TileType.SHIELD) {
                        t.setTileType(Tile.TileType.EMPTYSPACE);
                    }
                }
                for (UIObject x : gameGUI) {
                    x.active = false;
                }
                menu.forEach((x) -> {
                    x.active = true;
                });
                start.setText("Click to enter flight mode");
                start.setY(start.getY() - 23);
                debug.active = true;
                handler.xOffset = 0;
                handler.yOffset = 0;
                for (Tile t : gridSpace) {
                    t.setX(t.STARTX);
                    t.setY(t.STARTY);
                }
            }
        }
        if (returnToMenu.wasClicked()) {
            handler.switchToMenuState();
        }
    }

    boolean centeringShip = false, stoppingShip = false;

    double engineSpeeds[] = new double[]{0, .2, .4, .6, .8, 1};
    int engineSpeedSpot = engineSpeeds.length - 1;

    public void commands() {

        if (enginePowerUI.wasClicked()) {
            if (engineSpeedSpot == engineSpeeds.length - 1) {
                engineSpeedSpot = 0;
                enginePower = engineSpeeds[engineSpeedSpot];
            } else {
                engineSpeedSpot++;
                enginePower = engineSpeeds[engineSpeedSpot];
            }
            enginePowerUI.setText("Engine Power: " + enginePower * 100 + "%");
        }
        if (enginePowerUI.wasRightClicked()) {
            if (engineSpeedSpot == 0) {
                engineSpeedSpot = engineSpeeds.length - 1;
                enginePower = engineSpeeds[engineSpeedSpot];
            } else {
                engineSpeedSpot--;
                enginePower = engineSpeeds[engineSpeedSpot];
            }
            enginePowerUI.setText("Engine Power: " + enginePower * 100 + "%");
        }
        if (shieldPowerUI.wasClicked()) {
            if (sideVelo > 1 || sideVelo < -1 || vertVelo > 1 || vertVelo < -1) {
                printMessage(Color.red, "Must be still to toggle shields.");
                return;
            }
            if (shieldBlockCount > 0) {
                shieldPower = !shieldPower;
            } else {
                printMessage(Color.red, "No Shield Controller.");
            }
        }
        if (warpPowerUI.wasClicked()) {
            if (warpDriveCount > 0) {
                warpPower = !warpPower;
            } else {
                printMessage(Color.red, "No Warp Drive.");
            }
        }
        if (recallShipUI.wasClicked()) {
            centeringShip = !centeringShip;
        }
        if (stopShipUI.wasClicked()) {
            stoppingShip = !stoppingShip;
        }
        if (stoppingShip) {
            if (powerCellsRemaining < 1) {
                stoppingShip = false;
                printMessage(Color.red, "Not Enough Power.");
                return;
            }
            if (sideVelo > -10 && sideVelo < 10) {
                powerCellsRemaining -= thrusterCount;
                sideVelo = 0;
            }
            if (vertVelo > - 10 && vertVelo < 10) {
                powerCellsRemaining -= thrusterCount;
                vertVelo = 0;
            }
            stoppingShip = false;

        }
        if (centeringShip) {
            if ((newGridSpace.get(hW * hH / 2 + hH / 2).getX() >= handler.getWidth() / 2 - 150)
                    && (newGridSpace.get(hW * hH / 2 + hH / 2).getX() <= handler.getWidth() / 2 + 150)
                    && (newGridSpace.get(hW * hH / 2 + hH / 2).getY() >= handler.getHeight() / 2 - 150)
                    && (newGridSpace.get(hW * hH / 2 + hH / 2).getY() <= handler.getHeight() / 2 + 150)) {
                centeringShip = false;
                sideVelo = 0;
                vertVelo = 0;

            } else {
                if (powerCellsRemaining < 1) {
                    printMessage(Color.red, "Not Enough Power.");
                    centeringShip = false;
                    return;
                }
                if (newGridSpace.get(hW * hH / 2 + hH / 2).getX() > handler.getWidth() / 2 - 150) {
                    if (sideVelo < -10) {
                        return;
                    }
                    sideVelo -= (speed) * thrusterCount * 2;
                    powerCellsRemaining -= thrusterCount;
                }
                if (newGridSpace.get(hW * hH / 2 + hH / 2).getX() < handler.getWidth() / 2 + 150) {
                    if (sideVelo > 10) {
                        return;
                    }
                    sideVelo += (speed) * thrusterCount * 2;
                    powerCellsRemaining -= thrusterCount;
                }
                if (newGridSpace.get(hW * hH / 2 + hH / 2).getY() > handler.getHeight() / 2 - 150) {
                    if (vertVelo < -10) {
                        return;
                    }
                    vertVelo -= (speed) * thrusterCount * 2;
                    powerCellsRemaining -= thrusterCount;
                }
                if (newGridSpace.get(hW * hH / 2 + hH / 2).getY() < handler.getHeight() / 2 + 150) {
                    if (vertVelo > 10) {
                        return;
                    }
                    vertVelo += (speed) * thrusterCount * 2;
                    powerCellsRemaining -= thrusterCount;
                }
            }
        }
    }

    public void input() {
        if (!flightMode) {
            if (hullTileUI.wasClicked()) {
                currentlySelected = Tile.TileType.SHIPHULL;
            }
            if (wallTileUI.wasClicked()) {
                currentlySelected = Tile.TileType.SHIPWALL;
            }
            if (controlStationTileUI.wasClicked()) {
                currentlySelected = Tile.TileType.CONTROLSTATION;
            }
            if (batteryTileUI.wasClicked()) {
                currentlySelected = Tile.TileType.BATTERY;
            }
            if (solarTileUI.wasClicked()) {
                currentlySelected = Tile.TileType.SOLARPANEL;
            }
            if (thrusterTileUI.wasClicked()) {
                currentlySelected = Tile.TileType.THRUSTER;
            }
            if (reactorTileUI.wasClicked()) {
                currentlySelected = Tile.TileType.REACTOR;
            }
            if (shieldTileUI.wasClicked()) {
                currentlySelected = Tile.TileType.SHIELDBLOCK;
            }
            if (emptyTileUI.wasClicked()) {
                currentlySelected = Tile.TileType.EMPTYSPACE;
            }
            if (warpDriveUI.wasClicked()) {
                currentlySelected = Tile.TileType.WARPDRIVE;
            }
        } else {
            if (message.wasClicked()) {
                message.setText("");
                timer.purge();
                activeTimer = false;
            }
            camera.move((int) sideVelo, (int) vertVelo);
            for (Tile t : gridSpace) {
                t.moveTile((int) sideVelo, (int) vertVelo);

            }
            if (controlStationCount > 0 && powerCellsRemaining >= thrusterCount * enginePower) {
                if (thrusterCount < tempThrustersNeeded) {
                    if (handler.getKM().left || handler.getKM().right || handler.getKM().up || handler.getKM().down) {
                        if (!message.getText().equals("Need more Thrusters.")) {
                            printMessage(Color.red, "Need more Thrusters.");
                        }
                    }
                }
                for (Tile t : gridSpace) {
                    if (t.getTileType().equals(Tile.TileType.THRUSTER)) {
                        if (handler.getKM().left) {
                            if (powerCellsRemaining - thrusterCount >= 0) {
                                powerCellsRemaining -= thrusterCount;
                                sideVelo -= speed;
                            }
                        }
                        if (handler.getKM().right) {
                            if (powerCellsRemaining - thrusterCount >= 0) {
                                powerCellsRemaining -= thrusterCount;
                                sideVelo += speed;
                            }
                        }
                        if (handler.getKM().up) {
                            if (powerCellsRemaining - thrusterCount >= 0) {
                                powerCellsRemaining -= thrusterCount;
                                vertVelo -= speed;
                            }
                        }
                        if (handler.getKM().down) {
                            if (powerCellsRemaining - thrusterCount >= 0) {
                                powerCellsRemaining -= thrusterCount;
                                vertVelo += speed;
                            }
                        }
                        afterThrust();
                    }
                }
            } else {
                for (Tile t : gridSpace) {
                    if (t.getTileType() == Tile.TileType.THRUSTER) {
                        t.setTexture(0);
                    }
                }
            }
        }
    }

    public void afterThrust() {
        if (sideVelo >= 1 || sideVelo <= -1 || vertVelo >= 1 || vertVelo <= -1) {
            for (Tile t : gridSpace) {
                if (t.getTileType() == Tile.TileType.THRUSTER) {
                    t.setTexture(1);
                }
            }
        } else {
            for (Tile t : gridSpace) {
                if (t.getTileType() == Tile.TileType.THRUSTER) {
                    t.setTexture(0);
                }
            }
        }
    }

    Timer timer;
    boolean activeTimer = false;

    public void printMessage(Color c, String s) {
        if (!activeTimer) {
            activeTimer = true;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    message.setText("");
                    activeTimer = false;
                }
            }, 5000);
            message.setText(s);
            message.setColor(c);
        }
    }

    public void placeTile() {
        int mouseX = handler.getMM().getMouseX();
        int mouseY = handler.getMM().getMouseY();
        if (handler.getMM().isLeftPressed() && currentlySelected != Tile.TileType.EMPTYSPACE) {
            changeNearTile(getTileAt(mouseX, mouseY), currentlySelected);
            getTileAt(mouseX, mouseY).setSheilded(false);
        } else if (handler.getMM().isLeftPressed()) {
            getTileAt(mouseX, mouseY).setTileType(currentlySelected);
            getTileAt(mouseX, mouseY).setSheilded(false);
        } else if (handler.getMM().isRightPressed()) {
            getTileAt(mouseX, mouseY).setTileType(Tile.TileType.EMPTYSPACE);
            getTileAt(mouseX, mouseY).setStartType(Tile.TileType.EMPTYSPACE);
            getTileAt(mouseX, mouseY).setSheilded(false);
        }
        if (getTileAt(mouseX, mouseY).equals(newGridSpace.get(hW * hH / 2 + hH / 2)) && handler.getMM().isLeftPressed()) {
            newGridSpace.get(hW * hH / 2 + hH / 2).setTileType(currentlySelected);
        }
        debug.setText("Currently Selected Tile: " + currentlySelected);

    }

    public Tile getTileAt(int x, int y) {
        for (Tile t : gridSpace) {
            if (t.getBounds().contains(x, y)) {
                return t;
            }
        }
        return gridSpace.get(0);
    }

    //TODO
    //Don't render tiles that have tiles above them.
    @Override
    public void render(Graphics g) {
        world.render(g);
        gridSpace.forEach((x) -> {
            x.render(g);
        });
        if (!flightMode) {
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
        if (renderGUI) {
            gui.render(g);
        }
    }

}
