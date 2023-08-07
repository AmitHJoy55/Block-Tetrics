import javax.swing.*;

public class WindowGame {
    public static final int WIDTH = 445, HEIGHT = 637;

    private JFrame window ;
    private Board board;

    private Title title ;
    public WindowGame()
    {
        window = new JFrame("Block Tetrics") ;
        window.setSize(WIDTH , HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);

        board= new Board();
        title = new Title(this) ;

        window.addKeyListener(board);
        window.addKeyListener(title);

        window.add(title) ;
        window.setVisible(true);
    }

    public void startTetris()
    {
        window.remove(title);
        window.addMouseMotionListener(board);
        window.addMouseListener(board);
        window.add(board);
        board.startGame();
        window.revalidate();
    }

    public static  void main (String[] args)
    {

        new WindowGame();
    }
}
