package spacey;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import spacey.controls.KeyManager;
import spacey.controls.MouseManager;
import spacey.gfx.GUI;
import spacey.gfx.image.Assets;
import spacey.states.GridState;
import spacey.states.Handler;
import spacey.states.MenuState;
import spacey.states.State;
import spacey.states.TutorialState;

public class Main implements Runnable {

    private Display display;
    private Thread thread;
    private BufferStrategy bs;
    private Graphics g;
    private MouseManager mouseManager;
    private KeyManager keyManager;
    private final Handler handler;

    private int width, height;
    public String title;
    private boolean running = false;

    //State
    MenuState menuState;
    GridState gridState;
    TutorialState tutorialState;

    public Main(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;
        handler = new Handler(this);
        GUI.init();
        mouseManager = new MouseManager(handler);
        keyManager = new KeyManager();
        menuState = new MenuState(handler);
        gridState = new GridState(handler);
        tutorialState = new TutorialState(handler);
        State.currentState = menuState;
    }

    private void init() throws IOException {
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);
        display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
        Assets.init();
    }

    private void tick() {
        keyManager.tick();
        handler.getCurrentState().tick();
        handler.getSC().tick();
    }

    private void render() {
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();

        g.clearRect(0, 0, width, height);

        //Everything below is what is drawn on the screen.
        handler.getCurrentState().render(g);

        //End Draw
        bs.show();

        g.dispose();
    }

    @Override
    public void run() {
        try {
            init();
        } catch (IOException ex) {

            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;
            if (delta >= 1) {
                tick();
                render();
                ticks++;
                delta--;
            }
            if (timer >= 1000000000) {
                if (ticks != fps) {
                    System.out.println("FPS: " + ticks);
                }
                ticks = 0;
                timer = 0;
            }

        }

        stop();
    }

    public synchronized void start() {
        if (running) {
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if (!running) {
            return;
        }
        running = false;
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Display getDisplay() {
        return display;
    }

    public Thread getThread() {
        return thread;
    }

    public BufferStrategy getBs() {
        return bs;
    }

    public Graphics getG() {
        return g;
    }

    public MouseManager getMouseManager() {
        return mouseManager;
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public Handler getHandler() {
        return handler;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

    public boolean isRunning() {
        return running;
    }

    public MenuState getMenuState() {
        return menuState;
    }

    public GridState getGridState() {
        return gridState;
    }

    public void setBs(BufferStrategy bs) {
        this.bs = bs;
    }

    public TutorialState getTutorialState() {
        return tutorialState;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setMenuState(MenuState menuState) {
        this.menuState = menuState;
    }

    public void setGridState(GridState gridState) {
        this.gridState = gridState;
    }

    public void setTutorialState(TutorialState tutorialState) {
        this.tutorialState = tutorialState;
    }

    public void setMouseManager(MouseManager mouseManager) {
        this.mouseManager = mouseManager;
    }

    public void setKeyManager(KeyManager keyManager) {
        this.keyManager = keyManager;
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    public void setG(Graphics g) {
        this.g = g;
    }

}
