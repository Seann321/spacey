package spacey.states.gridState;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;
import spacey.states.Handler;

public class World {

    private Handler handler;
    public ArrayList<Tile> voidSpace;
    private ArrayList<Tile> newVoidSpace;
    private int wh = (int) (40 * Handler.Scale);

    public World(Handler handler) {
        this.handler = handler;
        voidSpace = new ArrayList<>();
        newVoidSpace = new ArrayList<>();
    }

    public void tick() {
        voidSpace.clear();
        voidSpace.addAll(newVoidSpace);
        for (Tile x : voidSpace) {
            if (x.parent) {
                Tile tile = x;
                for (Tile z : voidSpace) {
                    if (z.getBounds().contains(tile.getX() + 1, tile.getY() - 1)) {
                        if (new Random().nextInt(10) > 6) {
                            z.setTileType(Tile.TileType.ASTEROID);
                        } else {
                            x.parent = false;
                        }
                    }
                    if (z.getBounds().contains(tile.getX() - wh + 1, tile.getY() + 1)) {
                        if (new Random().nextInt(10) > 6) {
                            z.setTileType(Tile.TileType.ASTEROID);
                        } else {
                            x.parent = false;
                        }
                    }
                    if (z.getBounds().contains(tile.getX() - 1, tile.getY() + 1)) {
                        if (new Random().nextInt(10) > 6) {
                            z.setTileType(Tile.TileType.ASTEROID);
                        } else {
                            x.parent = false;
                        }
                    }
                    if (z.getBounds().contains(tile.getX() + 1, tile.getY() + wh + 1)) {
                        if (new Random().nextInt(10) > 6) {
                            z.setTileType(Tile.TileType.ASTEROID);
                        } else {
                            x.parent = false;
                        }
                    }
                }
            }
        }
    }

    public boolean contains(int x, int y) {
        for (Tile t : voidSpace) {
            if (t.getX() == x && t.getY() == y) {
                return true;
            }
            if (t.getX() % wh != 0 || t.getY() % wh != 0) {
                return true;
            }
        }
        return false;
    }


    public void render(Graphics g) {
        voidSpace.stream().filter((t) -> (t.getTileType() != Tile.TileType.ASTEROID)).forEachOrdered((t) -> {
            t.voidRender(g);
        });
        voidSpace.stream().filter((t) -> (t.getTileType() == Tile.TileType.ASTEROID)).forEachOrdered((t) -> {
            t.render(g);
        });

        int xStart = ((-(int) handler.xOffset) / wh) - 2;
        int yStart = ((-(int) handler.yOffset) / wh) - 2;
        int xEnd = ((int) (handler.getWidth() - handler.xOffset) / wh) + 2;
        int yEnd = ((int) (handler.getHeight() - handler.yOffset) / wh) + 2;

        for (Tile t : voidSpace) {
            if (t.getX() / wh > xEnd || t.getX() / wh < xStart) {
                newVoidSpace.remove(t);
                continue;
            }
            if (t.getY() / wh > yEnd || t.getY() / wh < yStart) {
                newVoidSpace.remove(t);
            }
        }

        for (int x = xStart; x < xEnd; x++) {
            for (int y = yStart; y < yEnd; y++) {
                if (!contains(x * wh, y * wh)) {
                    if (x * wh % wh == 0) {
                        if (y * wh % wh == 0) {
                            newVoidSpace.add(new Tile(handler, new Rectangle(x * wh, y * wh, wh, wh), Tile.TileType.VOID, x * wh, y * wh));
                            newVoidSpace.get(newVoidSpace.size() - 1).render(g);
                        }
                    }
                }
            }
        }
    }

    public Tile getTileAt(int x, int y) {
        for (Tile t : voidSpace) {
            if (t.getBounds().contains(x, y)) {
                return t;
            }
        }
        return voidSpace.get(0);
    }

}
