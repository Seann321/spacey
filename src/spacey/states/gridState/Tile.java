package spacey.states.gridState;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
import spacey.gfx.image.Assets;
import spacey.states.Handler;

public class Tile {

    private int texture = -1;
    private boolean changeable = true;
    public boolean unchangable = false;
    private Rectangle bounds;
    private Handler handler;
    private Color color;
    public final int STARTX, STARTY;
    public final Rectangle STARTBOUNDS;
    private int x, y, weight = 1;
    private boolean sheilded = false;
    public boolean parent = false;

    public static enum TileType {
        VOID, SHIPHULL, SHIPWALL, CONTROLSTATION, BATTERY, THRUSTER, SOLARPANEL, REACTOR, EMPTYSPACE, SHIELD, SHIELDBLOCK,
        ASTEROID, WARPDRIVE, OXYGENGENERATOR
    };

    private TileType tileType = TileType.VOID;

    private TileType startType = TileType.EMPTYSPACE;

    public Tile(Handler handler, Rectangle bounds, TileType tileType, int SX, int SY) {
        STARTX = SX;
        STARTY = SY;
        x = SX;
        y = SY;
        this.handler = handler;
        this.bounds = bounds;
        this.tileType = tileType;
        STARTBOUNDS = bounds;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public TileType getTileType() {
        return tileType;
    }

    public void setTileType(TileType tileType) {
        if (unchangable) {
            return;
        }
        if (changeable) {
            this.tileType = tileType;
        } else if (tileType != Tile.TileType.EMPTYSPACE && tileType != Tile.TileType.VOID) {
            this.tileType = tileType;
        }

    }

    public void tick() {
        bounds.x = x;
        bounds.y = y;
    }

    public void moveTile(int xAmt, int yAmt) {
        x += xAmt;
        y += yAmt;
    }

    public void render(Graphics g) {
        switch (tileType) {
            case VOID:
                weight = 0;
                if (texture >= 0) {
                    if (texture == 0) {
                        g.drawImage(Assets.voidA, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                    }
                    if (texture == 1) {
                        g.drawImage(Assets.voidB, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                    }
                    if (texture == 2) {
                        g.drawImage(Assets.voidC, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                    }
                    if (texture == 3) {
                        g.drawImage(Assets.voidA, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                    }
                    if (texture == 4) {
                        g.drawImage(Assets.planetA, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                    }
                    if (texture == 5) {
                        g.drawImage(Assets.planetB, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                    }
                    if (texture == 6) {
                        g.drawImage(Assets.planetC, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                    }
                    return;
                } else {
                    switch (new Random().nextInt(35)) {
                        case 0:
                            texture = 0;
                            g.drawImage(Assets.voidA, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                            break;
                        case 1:
                            texture = 1;
                            g.drawImage(Assets.voidB, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                            break;
                        case 2:
                            texture = 2;
                            g.drawImage(Assets.voidC, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                            break;
                        case 3:
                            texture = 3;
                            g.drawImage(Assets.voidD, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                            break;
                        case 4:
                            switch (new Random().nextInt(50)) {
                                case 1:
                                    texture = 4;
                                    g.drawImage(Assets.planetA, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                                    break;
                                case 2:
                                    texture = 5;
                                    g.drawImage(Assets.planetB, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                                    break;
                                case 3:
                                    texture = 6;
                                    g.drawImage(Assets.planetC, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                                    break;
                                case 4:
                                    texture = 7;
                                    g.drawImage(Assets.planetD, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                                    break;
                                case 5:
                                    texture = 8;
                                    g.drawImage(Assets.planetE, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                                    break;
                                case 6:
                                    texture = 9;
                                    g.drawImage(Assets.planetF, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                                    break;
                                default:
                                    if ((new Random().nextInt(50) > 47)) {
                                        if (handler.xOffset == 0 && handler.yOffset == 0) {
                                            texture = 0;
                                            break;
                                        }
                                        tileType = TileType.ASTEROID;
                                        parent = true;
                                        g.drawImage(Assets.asteroidA, (int) (x + handler.xOffset), (int) (y + handler.yOffset) + 40, bounds.width, bounds.height, null);
                                        g.drawImage(Assets.asteroidB, (int) (x + handler.xOffset) + 40, (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                                        g.drawImage(Assets.asteroidC, (int) (x + handler.xOffset) + 40, (int) (y + handler.yOffset) + 40, bounds.width, bounds.height, null);
                                        g.drawImage(Assets.asteroidA, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                                        render(g);
                                        break;
                                    } else {
                                        texture = 0;
                                        g.drawImage(Assets.voidA, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                                        break;
                                    }
                            }
                            break;
                        default:
                            texture = 0;
                            g.drawImage(Assets.voidA, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                            break;
                    }
                }
                return;
            case OXYGENGENERATOR:
                weight = 1;
                g.drawImage(Assets.oxygenGenerator, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                break;
            case SHIPHULL:
                weight = 0;
                g.drawImage(Assets.shipHull, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                break;
            case SHIPWALL:
                weight = 1;
                g.drawImage(Assets.shipWall, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                break;
            case CONTROLSTATION:
                weight = 2;
                g.drawImage(Assets.controlStation, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                break;
            case WARPDRIVE:
                weight = 4;
                if (texture == -1) {
                    texture = 0;
                    g.drawImage(Assets.warpDrive, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                }
                if (texture == 0) {
                    g.drawImage(Assets.warpDrive, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                }
                if (texture == 1) {
                    g.drawImage(Assets.activeWarpDrive, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                }
                break;
            case BATTERY:
                weight = 1;
                g.drawImage(Assets.battery, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                break;
            case THRUSTER:
                weight = 1;
                if (texture == -1) {
                    texture = 0;
                }
                if (texture == 0) {
                    g.drawImage(Assets.offThruster, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                }
                if (texture == 1) {
                    g.drawImage(Assets.thruster, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                }
                break;
            case SOLARPANEL:
                weight = 1;
                g.drawImage(Assets.solarPanel, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                break;
            case REACTOR:
                weight = 2;
                g.drawImage(Assets.reactor, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                break;
            case SHIELD:
                weight = 0;
                g.drawImage(Assets.shield, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                break;
            case SHIELDBLOCK:
                weight = 2;
                if (texture == -1) {
                    texture = 0;
                }
                if (texture == 0) {
                    g.drawImage(Assets.shieldBlock, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                } else if (texture == 1) {
                    g.drawImage(Assets.activeShieldBlock, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                }
                break;
            case ASTEROID:
                if (texture == -1) {
                    switch (new Random().nextInt(3)) {
                        case 0:
                            texture = 0;
                            break;
                        case 1:
                            texture = 1;
                            break;
                        case 2:
                            texture = 2;
                            break;
                        default:
                            texture = 2;
                            break;
                    }
                }
                if (texture == 0) {
                    g.drawImage(Assets.asteroidA, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                }
                if (texture == 1) {
                    g.drawImage(Assets.asteroidB, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                }
                if (texture == 2) {
                    g.drawImage(Assets.asteroidC, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
                }
                break;
        }

    }

    public void voidRender(Graphics g) {
        if (texture > 0) {
            if (texture == 0) {
                g.drawImage(Assets.voidA, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
            }
            if (texture == 1) {
                g.drawImage(Assets.voidB, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
            }
            if (texture == 2) {
                g.drawImage(Assets.voidC, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
            }
            if (texture == 3) {
                g.drawImage(Assets.voidD, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
            }
            if (texture == 4) {
                g.drawImage(Assets.planetA, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
            }
            if (texture == 5) {
                g.drawImage(Assets.planetB, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
            }
            if (texture == 6) {
                g.drawImage(Assets.planetC, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
            }
            if (texture == 7) {
                g.drawImage(Assets.planetD, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
            }
            if (texture == 8) {
                g.drawImage(Assets.planetE, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
            }
            if (texture == 9) {
                g.drawImage(Assets.planetF, (int) (x + handler.xOffset), (int) (y + handler.yOffset), bounds.width, bounds.height, null);
            }

        }
    }

    public Color getColor() {
        return color;
    }

    public void setChangeable(boolean changeable) {
        this.changeable = changeable;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isSheilded() {
        return sheilded;
    }

    public void setSheilded(boolean sheilded) {
        this.sheilded = sheilded;
    }

    public int getTexture() {
        return texture;
    }

    public void setTexture(int texture) {
        this.texture = texture;
    }

    public Rectangle getSTARTBOUNDS() {
        return STARTBOUNDS;
    }

    public TileType getStartType() {
        return startType;
    }

    public void setStartType(TileType startType) {
        if (unchangable) {
            return;
        }
        if (changeable) {
            this.startType = startType;
        } else if (tileType != Tile.TileType.EMPTYSPACE && tileType != Tile.TileType.VOID) {
            this.startType = startType;
        }
    }

}
