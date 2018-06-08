package spacey;

import java.awt.Dimension;
import java.awt.Toolkit;
import spacey.states.Handler;

public class Spacey {

    static Main main;
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static int width = (int) screenSize.getWidth();
    static int height = (int) screenSize.getHeight();

    public static void main(String[] args) {
        if (width >= 1800 && height >= 1000) {
            Handler.ResSpot = 4;
            main = new Main("Spacey", 1600, 900);
            main.start();
        } else if (width < 1320 && height < 740) {
            Handler.ResSpot = 0;
            main = new Main("Spacey", 1024, 576);
            main.start();
        } else {
            main = new Main("Spacey", 1280, 720);
            main.start();
        }
    }
}
