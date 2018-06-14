package spacey;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Display {

    private JFrame frame;
    private Canvas canvas;

    private final String title;
    private int width, height;

    public Display(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        createDisplay();
        //setBorderless(true);
    }

    private void createDisplay() {
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setFocusable(false);
        canvas.setBackground(Color.black);

        frame.add(canvas);
        frame.pack();

    }

    public void setBorderless(boolean x) {
        frame.dispose();
        frame.setUndecorated(x);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.pack();
    }

    public void changeSize(int x, int y) {
        width = x;
        height = y;

        frame.dispose();
        frame.setUndecorated(true);
        frame.setSize(x, y);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        canvas.setPreferredSize(new Dimension(x, y));
        canvas.setMaximumSize(new Dimension(x, y));
        canvas.setMinimumSize(new Dimension(x, y));
        canvas.setFocusable(false);
        canvas.setBackground(Color.black);

        frame.add(canvas);
        frame.pack();
    }

    public void setColor(Color color) {
        canvas.setBackground(color);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public JFrame getFrame() {
        return frame;
    }

    public int getX() {
        return frame.getX();
    }

    public int getY() {
        return frame.getY();
    }

    public void setLocation(int x, int y) {
        frame.setLocation(x, y);
    }

}
