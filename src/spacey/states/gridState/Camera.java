package spacey.states.gridState;

import spacey.states.Handler;

public class Camera {

    private Handler handler;

    public Camera(Handler handler) {
        this.handler = handler;
    }

    public void move(int xAmt, int yAmt) {
        handler.xOffset -= xAmt;
        handler.yOffset -= yAmt;
    }
}
