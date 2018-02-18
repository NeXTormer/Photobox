package util;

import gui.PhotoPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class PhotoTimer {

    private PhotoPanel panel;

    private BufferedImage timer1;
    private BufferedImage timer2;
    private BufferedImage timer3;
    private BufferedImage timer0;

    private int stage = -1;
    private int x;
    private int y;
    private int size;

    private Timer timer;

    public PhotoTimer(PhotoPanel panel, int x, int y, int size)
    {
        this.x = x;
        this.y = y;
        this.size = size;
        this.panel = panel;

        timer = new Timer();

        try {
            timer1 = ImageIO.read(new File("res/timer/timer1.png"));
            timer2 = ImageIO.read(new File("res/timer/timer2.png"));
            timer3 = ImageIO.read(new File("res/timer/timer3.png"));
            timer0 = ImageIO.read(new File("res/timer/timer0.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void draw(Graphics2D g)
    {
        if(stage == 0)
        {
            g.drawImage(timer0, x, y, size, size, null);
        }
        else if(stage == 1)
        {
            g.drawImage(timer1, x, y, size, size, null);
        }
        else if(stage == 2)
        {
            g.drawImage(timer2, x, y, size, size, null);
        }
        else if(stage == 3)
        {
            g.drawImage(timer3, x, y, size, size, null);
        }
    }

    public void runTimer(Runnable finished)
    {
        stage = 3;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                stage--;

                if(stage == -1)
                {
                    timer.purge();
                    timer.cancel();
                    finished.run();
                }
            }
        }, 1000, 1000);
    }
}
