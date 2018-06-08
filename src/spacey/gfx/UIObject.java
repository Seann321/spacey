package spacey.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import spacey.states.Handler;

public class UIObject {

    FontMetrics fm;
    Font font = GUI.font;
    public String text;
    public Rectangle bounds;
    protected boolean clicked = false, rightClicked = false;
    public int x, xOffset = 0;
    public int y, yOffset = 0;
    public boolean center;
    public boolean hovering = false;
    public Color color;
    public Color newColor;
    public Color original;
    public boolean active = true;
    public Handler handler;

    public UIObject(String text, int x, int y, boolean center, Color color) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.center = center;
        this.color = color;
        original = color;
        bounds = new Rectangle(0, 0, 0, 0);
    }

    public UIObject(String text, int x, int y, boolean center, Color color, Color newColor) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.center = center;
        this.color = color;
        original = color;
        this.newColor = newColor;
        bounds = new Rectangle(0, 0, 0, 0);
    }

    public UIObject(String text, int x, int y, boolean center, Color color, Font font) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.center = center;
        this.color = color;
        original = color;
        this.font = font;
        bounds = new Rectangle(0, 0, 0, 0);
    }

    public UIObject(String text, int x, int y, boolean center, Color color, Color newColor, Font font) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.center = center;
        this.color = color;
        original = color;
        this.newColor = newColor;
        this.font = font;
        bounds = new Rectangle(0, 0, 0, 0);
    }

    public UIObject(String text, int x, int y, boolean center, Color color, ArrayList<UIObject> list) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.center = center;
        this.color = color;
        original = color;
        bounds = new Rectangle(0, 0, 0, 0);
        list.add(this);
    }

    public UIObject(String text, int x, int y, boolean center, Color color, Color newColor, ArrayList<UIObject> list) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.center = center;
        this.color = color;
        original = color;
        this.newColor = newColor;
        bounds = new Rectangle(0, 0, 0, 0);
        list.add(this);
    }

    public UIObject(String text, int x, int y, boolean center, Color color, Font font, ArrayList<UIObject> list) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.center = center;
        this.color = color;
        original = color;
        this.font = font;
        bounds = new Rectangle(0, 0, 0, 0);
        list.add(this);
    }

    public UIObject(String text, int x, int y, boolean center, Color color, Color newColor, Font font, ArrayList<UIObject> list) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.center = center;
        this.color = color;
        original = color;
        this.newColor = newColor;
        this.font = font;
        bounds = new Rectangle(0, 0, 0, 0);
        list.add(this);
    }

    public void onClick() {
        if (!active) {
            return;
        }
        clicked = true;
    }

    public void onRightClick() {
        if (!active) {
            return;
        }
        rightClicked = true;
    }

    public void tick() {
        if (!active) {
            return;
        }
        if (hovering) {
            color = newColor;
        } else {
            color = original;
        }
    }

    public void onMouseMove(MouseEvent e) {
        if (bounds.contains(e.getX(), e.getY())) {
            hovering = true;
        } else {
            hovering = false;
        }
    }

    public void onMouseReleased(MouseEvent e) {
        if (hovering) {
            if (e.getButton() == 1) {
                onClick();
            }
            if (e.getButton() == 3) {
                onRightClick();
            }
        }
    }

    public void render(Graphics g) {
        if (!active) {
            return;
        }
        if (fm == null) {
            fm = g.getFontMetrics(font);
        }
        drawString(g, text, x, y, center, color, font);
    }

    public void drawString(Graphics g, String text, int xPos, int yPos, boolean center, Color c, Font font) {
        g.setColor(c);
        g.setFont(font);
        int x = xPos;
        int y = yPos;

        if (center) {

            x = xPos - fm.stringWidth(text) / 2;
            y = (yPos - fm.getHeight() / 2) + fm.getAscent();

            bounds = new Rectangle((int) x - xOffset, (int) y - ((int) fm.getHeight() - 5), (int) fm.stringWidth(text) + xOffset, (int) fm.getHeight() + yOffset);
        }

        if (!center) {
            bounds = new Rectangle((int) x - xOffset, (int) y - ((int) fm.getHeight() - 5), (int) fm.stringWidth(text) + xOffset, (int) fm.getHeight() + yOffset);
        }

        g.drawString(text, x, y);
    }

    public boolean wasClicked() {
        if (clicked == true) {
            clicked = false;
            return true;
        }
        return false;
    }

    public boolean wasRightClicked() {
        if (rightClicked == true) {
            rightClicked = false;
            return true;
        }
        return false;
    }

    public void setFont(Font font) {
        this.font = font;

        if (center) {

            x = x - fm.stringWidth(text) / 2;
            y = (y - fm.getHeight() / 2) + fm.getAscent();

            bounds = new Rectangle((int) x - xOffset, (int) y - ((int) fm.getHeight() - 5), (int) fm.stringWidth(text) + xOffset, (int) fm.getHeight() + yOffset);
        }

        if (!center) {
            bounds = new Rectangle((int) x - xOffset, (int) y - ((int) fm.getHeight() - 5), (int) fm.stringWidth(text) + xOffset, (int) fm.getHeight() + yOffset);
        }
    }

    public void setAllColors(Color x) {
        color = x;
        newColor = x;
        original = x;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public boolean isCenter() {
        return center;
    }

    public void setCenter(boolean center) {
        this.center = center;
    }

    public boolean isHovering() {
        return hovering;
    }

    public void setHovering(boolean hovering) {
        this.hovering = hovering;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        original = color;
        this.color = color;
    }
}
