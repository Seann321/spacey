package spacey.gfx.sounds;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import spacey.states.Handler;

public class SoundController {

    public static boolean activeTimer = false;
    public static double volume = .1;
    public static int mp3sLocation = 0;
    public static String mp3s[] = new String[]{"src\\spacey\\gfx\\sounds\\MenuMusic.mp3",
        "src\\spacey\\gfx\\sounds\\TrackA.mp3",
        "src\\spacey\\gfx\\sounds\\TrackB.mp3",
        "src\\spacey\\gfx\\sounds\\TrackC.mp3",
        "src\\spacey\\gfx\\sounds\\TrackD.mp3",
        "src\\spacey\\gfx\\sounds\\TrackE.mp3"};
    public static MediaPlayer mediaPlayer;
    public static Media hit;
    public static TimerTask task;

    public SoundController() {
        JFXPanel fxPanel = new JFXPanel();
    }

    boolean playing = false;

    public void tick() {
        if (!playing) {
            if (mp3sLocation == mp3s.length - 1) {
                mp3sLocation = 0;
                playSound(mp3s[mp3sLocation]);
            } else {
                mp3sLocation++;
                playSound(mp3s[mp3sLocation]);
            }
        }
    }

    static Timer timer = new Timer();

    public synchronized void timing() {
        task = new TimerTask() {
            private final double MAX_SECONDS = hit.getDuration().toSeconds() + 5;
            double seconds;

            @Override
            public void run() {
                if (seconds < MAX_SECONDS) {
                    seconds++;
                } else {
                    playing = false;
                    cancel();
                }
            }
        };
        timer.schedule(task, 0, 1000);
        activeTimer = true;
    }

    public void playSound(String path) {
        playing = true;
        hit = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.setVolume(volume);
        if (activeTimer) {
            timer.cancel();
            timer = new Timer();
            activeTimer = false;
        }
        mediaPlayer.stop();
        mediaPlayer.play();
        try {
            Thread.sleep(65);
        } catch (InterruptedException ex) {
            Logger.getLogger(SoundController.class.getName()).log(Level.SEVERE, null, ex);
        }
        timing();
    }

    public void changeVolume(double v) {
        volume = v;
        mediaPlayer.setVolume(v);
    }
}
