package spacey.gfx;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import spacey.states.Handler;

public class GUI {

    public static Font font, font50, font35;
    public static GUI currentGUI = null;
    private final Handler handler;
    public ArrayList<UIObject> text = new ArrayList<>();

    public static void init() {
        font = FontLoader.loadFont("src\\spacey\\assets\\slkscr.ttf", 20);
        font35 = FontLoader.loadFont("src\\spacey\\assets\\slkscr.ttf", 35);
        font50 = FontLoader.loadFont("src\\spacey\\assets\\slkscr.ttf", 50);
    }

    public GUI(Handler handler) {
        this.handler = handler;
    }

    public void render(Graphics g) {
        for (UIObject e : text) {
            e.render(g);
        }
    }

    public void tick() {
        for (UIObject e : text) {
            e.tick();
        }
    }

    public void onMouseMove(MouseEvent e) {
        for (UIObject o : text) {
            o.onMouseMove(e);
        }
    }

    public void onMouseRelease(MouseEvent e) {
        for (UIObject o : text) {
            o.onMouseReleased(e);
        }
    }

    public void addText(UIObject e) {
        text.add(e);
    }

    public void removeText(UIObject e) {
        text.remove(e);
    }

}
