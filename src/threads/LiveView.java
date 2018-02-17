package threads;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.prefs.Preferences;

public class LiveView implements Runnable {

    private Thread thread;
    private Preferences preferences;

    private boolean running = false;

    public BufferedImage currentImage;


    public LiveView(Preferences prefs)
    {
        this.preferences = prefs;
        this.running = true;
        thread = new Thread(this, "LiveView");
        thread.start();
    }

    @Override
    public void run() {
        int counter = 0;

        int cx = preferences.getInt("conx", -1);
        int cy = preferences.getInt("cony", -1);;
        int cw = preferences.getInt("conw", -1);;
        int ch = preferences.getInt("conh", -1);;

        try
        {
            Robot robot = new Robot();
            Rectangle rect = new Rectangle(cx, cy, cw, ch);
            long started = System.currentTimeMillis();

            while(running)
            {
                currentImage = robot.createScreenCapture(rect);
                counter++;

                if(counter == 30)
                {
                    System.out.println((System.currentTimeMillis() - started));
                    counter = 0;
                    started = System.currentTimeMillis();
                }
            }
        }
        catch(Exception e) { }

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void stop()
    {
        this.running = false;
    }
}
