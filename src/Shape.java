import java.awt.*;

public class Shape {

    private int x = 4,y = 0;
    private int normal = 600 ;
    private int fast = 50;
    private int delayTimeForMovement =  normal ;

    private long beginTime ;

    private int deltaX =0 ;
    private boolean collision = false ;

    private int[][] coordis ;
    private Board board;

    private Color color ;
    public Shape(int[][] coordis ,Board board , Color color )
    {
        this.coordis = coordis;
        this.board = board ;
        this.color = color ;
    }

    public void setX(int x)
    {
        this.x = x ;
    }

    public void setY(int y)
    {
        this.y = y  ;
    }

    public void reset ()
    {
        this.x = 4;
        this.y = 0;
        collision = false ;
    }




    public void update()
    {
        if(collision)
        {
            //Fill the color for board
            for(int row=0;row<coordis.length ; row++) {
                for (int col = 0; col < coordis[0].length; col++)
                {
                    if(coordis[row][col] !=0 )
                    {
                        board.getBoard()[ y+ row ][x + col] = color  ;
                    }
                }
            }
            //Set Current shape
            board.setCurrentShape();
            return ;
        }

        //Check Moving in Horizontal
        if( !(x+deltaX + coordis[0].length > 10) && !(x+deltaX < 0) )
        {
            x+= deltaX ;
        }
        deltaX = 0 ;
        if(System.currentTimeMillis() - beginTime > delayTimeForMovement)
        {
            //Checking Moving in Vertical if it hit the ground
            if( !(y+1+ coordis.length > Board.BOARD_HEIGHT ))
            {
                y++ ;
            }
            else
            {
                collision = true ;
            }

            beginTime = System.currentTimeMillis();
        }
    }
    public void render (Graphics g)
    {
        //Drawing the shape
        for(int row =0 ; row < coordis.length; row++)
        {
            for(int col =0 ; col < coordis[0].length; col++)
            {
                if(coordis[row][col] != 0 )
                {
                    g.setColor(Color.RED);
                    //Falling he blocks downward
                    g.fillRect(col * Board.BLOCK_SIZE + x * Board.BLOCK_SIZE, row * Board.BLOCK_SIZE + y *Board.BLOCK_SIZE, Board.BLOCK_SIZE, Board.BLOCK_SIZE);
                }
            }
        }
    }
    public void speedUp()
    {
        delayTimeForMovement = fast ;
    }
    public void speedDown()
    {
        delayTimeForMovement = normal  ;
    }
    public void moveRight()
    {
        deltaX = 1 ;
    }
    public void moveLeft()
    {
        deltaX = -1 ;
    }

}
