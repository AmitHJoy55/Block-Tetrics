import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Board extends JPanel implements KeyListener {


    private static int FPS = 60 ;
    private static int delay = FPS/1000 ;
    public static final int BOARD_WIDTH = 10 ;
    public static final int BOARD_HEIGHT = 20 ;
    public static final int BLOCK_SIZE = 30 ;
    private Timer looper ;
    private Color[][] board = new Color[BOARD_HEIGHT][BOARD_WIDTH];

    private Random random ;

    private Color[] colors = {Color.decode("#ed1c24"), Color.decode("#ff7f27"), Color.decode("#fff200"),
            Color.decode("#22b14c"), Color.decode("#00a2e8"), Color.decode("#a349a4"), Color.decode("#3f48cc")};

    private Shape[] shapes = new Shape[7] ;
    private Shape currentShape ;

    public Board()
    {
        random = new Random();

        shapes[0] = new Shape(new int[][]{
                {1, 1, 1, 1} // I shape;
        }, this, colors[0]);

        shapes[1] = new Shape(new int[][]{
                {1, 1, 1},
                {0, 1, 0}, // T shape;
        }, this, colors[1]);

        shapes[2] = new Shape(new int[][]{
                {1, 1, 1},
                {1, 0, 0}, // L shape;
        }, this, colors[2]);

        shapes[3] = new Shape(new int[][]{
                {1, 1, 1},
                {0, 0, 1}, // J shape;
        }, this, colors[3]);

        shapes[4] = new Shape(new int[][]{
                {0, 1, 1},
                {1, 1, 0}, // S shape;
        }, this, colors[4]);

        shapes[5] = new Shape(new int[][]{
                {1, 1, 0},
                {0, 1, 1}, // Z shape;
        }, this, colors[5]);

        shapes[6] = new Shape(new int[][]{
                {1, 1},
                {1, 1}, // O shape;
        }, this, colors[6]);

        currentShape = shapes[0] ;


        looper = new Timer(delay, new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                repaint() ;
            }
        }) ;
        looper.start();
    }

    public void update()
    {
        currentShape.update(); ;
    }

    public void setCurrentShape()
    {
        currentShape = shapes[random.nextInt(shapes.length)] ;
        currentShape.reset();
        checkGameOver();
    }

    private void checkGameOver()
    {
        int [][] coordis = currentShape.getCoordis();
        for(int row=0;row< coordis.length ;row++)
        {
            for(int col=0;col< coordis[0].length ; col++)
            {
                if(coordis[row][col]  != 0 )
                {
                    if(board[row+currentShape.getY()][col+currentShape.getX()] !=null)
                    {
                        System.out.println("Game Over ");
                    }
                }
            }
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,getWidth(),getHeight());

        currentShape.render(g);

        for(int row=0; row<board.length ; row++) {
            for (int col = 0; col < board[row].length; col++)
            {
                if(board[row][col] != null )
                {
                    g.setColor( board[row] [col] );
                    g.fillRect(col * BLOCK_SIZE , row*BLOCK_SIZE,BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }

        //Drawing the Board
        g.setColor(Color.DARK_GRAY);
        for(int row =0 ; row < BOARD_HEIGHT; row++)
        {
            g.drawLine(0,BLOCK_SIZE * row ,BLOCK_SIZE * BOARD_WIDTH,BLOCK_SIZE * row );
        }
        for(int col =0 ; col < BOARD_WIDTH+1; col++)
        {
            g.drawLine(col* BLOCK_SIZE,0 ,col* BLOCK_SIZE,BLOCK_SIZE * BOARD_HEIGHT );
        }


    }

    public Color[][] getBoard()
    {
        return board;
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
            currentShape.speedUp();
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            currentShape.moveRight();
        else if(e.getKeyCode() == KeyEvent.VK_LEFT)
            currentShape.moveLeft();
        else if(e.getKeyCode() == KeyEvent.VK_UP)
            currentShape.rotateShape();

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
            currentShape.speedDown();
    }
}
