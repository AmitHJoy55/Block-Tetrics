import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Title extends JPanel implements KeyListener {
    private static final long serialVersionUID = 1L;
    private BufferedImage instructions,HomeScreen;
    private WindowGame window;
    private BufferedImage[] playButton = new BufferedImage[2];
    private Timer timer;



    public Title(WindowGame window){
        HomeScreen = ImageLoader.loadImage("/HomeScreen.png") ;
        instructions = ImageLoader.loadImage("/arrow.png");
        timer = new Timer(1000/60, new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }

        });
        timer.start();
        this.window = window;

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setColor(Color.BLACK);

        g.fillRect(0, 0, WindowGame.WIDTH, WindowGame.HEIGHT);

        String gameNAME = "BLOCK TETRICS";
        g.setColor(Color.GREEN);
        g.setFont(new Font("Georgia", Font.BOLD, 45));
        g.drawString(gameNAME, 15, WindowGame.HEIGHT-575);

        g.drawImage(instructions, WindowGame.WIDTH/2 - instructions.getWidth()/2,
                    100 - instructions.getHeight()/2 + 150, null);
        String gameOverString = "Press Enter to play!";
        g.setColor(Color.LIGHT_GRAY);
        g.setFont(new Font("Georgia", Font.BOLD, 30));
        g.drawString(gameOverString, 60, WindowGame.HEIGHT -180);





    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_ENTER) {
            window.startTetris();
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}