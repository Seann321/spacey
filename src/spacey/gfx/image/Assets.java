package spacey.gfx.image;

import java.awt.image.BufferedImage;

public class Assets {

    public static final int width = 16, height = 16;

    public static BufferedImage shipHull, shipWall, battery, thruster, controlStation, solarPanel, remove, reactor,
            voidA, voidB, voidC, voidD, shield, shieldBlock, offThruster, planetA, planetB, planetC, planetD, planetE, planetF,
            activeShieldBlock, asteroidA, asteroidB, asteroidC, warpDrive, activeWarpDrive, player, oxygenGenerator;
    //public static BufferedImage[] player2;

    public static void init() {

        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("sheet.png"));

        shipHull = sheet.crop(0, 0, width, height);
        shipWall = sheet.crop(width, 0, width, height);
        battery = sheet.crop(width * 2, 0, width, height);
        thruster = sheet.crop(width * 3, 0, width, height);
        controlStation = sheet.crop(width * 4, 0, width, height);
        solarPanel = sheet.crop(width * 5, 0, width, height);
        remove = sheet.crop(width * 6, 0, width, height);
        reactor = sheet.crop(width * 7, 0, width, height);
        voidA = sheet.crop(0, height, width, height);
        voidB = sheet.crop(width * 1, height, width, height);
        voidC = sheet.crop(width * 2, height, width, height);
        voidD = sheet.crop(width * 3, height, width, height);
        shield = sheet.crop(width * 4, height, width, height);
        shieldBlock = sheet.crop(width * 5, height, width, height);
        activeShieldBlock = sheet.crop(width * 5, height * 2, width, height);
        warpDrive = sheet.crop(width * 6, height * 2, width, height);
        activeWarpDrive = sheet.crop(width * 6, height * 3, width, height);
        player = sheet.crop(width * 6, height * 4, width, height);
        oxygenGenerator = sheet.crop(width * 6, height * 5, width, height);
        offThruster = sheet.crop(width * 6, height, width, height);
        planetA = sheet.crop(width * 7, height, width, height);
        planetB = sheet.crop(width * 7, height * 2, width, height);
        planetC = sheet.crop(width * 7, height * 3, width, height);
        planetD = sheet.crop(width * 7, height * 4, width, height);
        planetE = sheet.crop(width * 7, height * 5, width, height);
        planetF = sheet.crop(width * 7, height * 6, width, height);
        asteroidA = sheet.crop(0, height * 2, width, height);
        asteroidB = sheet.crop(0, height * 3, width, height);
        asteroidC = sheet.crop(0, height * 4, width, height);
    }
}
