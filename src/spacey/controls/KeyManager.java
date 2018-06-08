package spacey.controls;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

    private boolean[] keys, justPressed, cantPress;
    public boolean up, down, left, right, shift;

    public KeyManager() {
        keys = new boolean[256];
        justPressed = new boolean[keys.length];
        cantPress = new boolean[keys.length];
    }

    public void tick() {
        for (int i = 0; i < keys.length; i++) {
            if (cantPress[i] && !keys[i]) {
                cantPress[i] = false;
            } else if (justPressed[i]) {
                cantPress[i] = true;
                justPressed[i] = false;
            }
            if (!cantPress[i] && keys[i]) {
                justPressed[i] = true;
            }

        }

        up = keys[KeyEvent.VK_W];
        down = keys[KeyEvent.VK_S];
        left = keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_D];
        shift = keys[KeyEvent.VK_SHIFT];

    }

    public boolean keyJustPressed(int keyCode) {
        if (keyCode < 0 || keyCode >= keys.length) {
            return false;
        }
        return justPressed[keyCode];
    }

    public void keyTyped(KeyEvent ke) {

    }

    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() < 0 || ke.getKeyCode() >= keys.length) {
            return;
        }
        keys[ke.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent ke) {
        if (ke.getKeyCode() < 0 || ke.getKeyCode() >= keys.length) {
            return;
        }
        keys[ke.getKeyCode()] = false;
    }

}
