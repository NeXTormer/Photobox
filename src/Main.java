import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Main extends JPanel {

    public void start()
    {
        int counter = 0;
        try
        {
            Robot robot = new Robot();
            String format = "jpg";
            String fileName = "photos/" + counter + "ss." + format;

            Rectangle screenRect = new Rectangle(100, 100, 1920, 1080);
            BufferedImage screenshot;
            long started = System.currentTimeMillis();

            while(true)
            {
                screenshot = robot.createScreenCapture(screenRect);
                Graphics g = this.getGraphics();
                g.drawImage(screenshot, 0, 0, 1280, 720, null);


                counter++;

                if(counter == 1)
                {
                    System.out.println((System.currentTimeMillis() - started));
                    counter = 0;
                    started = System.currentTimeMillis();
                }
            }
            //ImageIO.write(screenFullImage, format, new File(fileName));


        }
        catch(Exception e) { }
    }




    public static void mains(String[] args)
    {
        JFrame frame = new JFrame("Photobox");
        Main main = new Main();
        frame.setContentPane(main);
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //showOnScreen(1, frame);

        main.start();
    }

    public static void showOnScreen( int screen, JFrame frame )
    {
        GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        if( screen > -1 && screen < gs.length )
        {
            gs[screen].setFullScreenWindow( frame );
        }
        else if( gs.length > 0 )
        {
            gs[0].setFullScreenWindow( frame );
        }
        else
        {
            throw new RuntimeException( "No Screens Found" );
        }
    }
}
