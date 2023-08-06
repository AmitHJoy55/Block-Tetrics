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
            checkLine();
            //Set Current shape
            board.setCurrentShape();
            return ;
        }

        //Check Moving in Horizontal
        boolean moveX = true ;
        if( !( x + deltaX + coordis[0].length > 10) && !(x+deltaX < 0) )
        {
            for(int row=0 ;row <coordis.length ; row++)
            {
                for(int col=0;col < coordis[row].length; col++)
                {
                    if(coordis[row][col] != 0)
                    {
                        if( board.getBoard() [y+row][ x+deltaX+col] != null)
                            moveX = false ;
                    }

                }
            }
            if(moveX)
            {
                x+= deltaX ;
            }
        }
        deltaX = 0 ;
        //Checking Moving in Vertical if it hit the ground
        if(System.currentTimeMillis() - beginTime > delayTimeForMovement)
        {
            if( !( y+ 1+ coordis.length > Board.BOARD_HEIGHT ))
            {
                //Checking collision with other blocks
                for(int row=0 ;row <coordis.length ; row++)
                {
                    for(int col=0;col < coordis[row].length; col++)
                    {
                        if(coordis[row][col] != 0)
                            if(board.getBoard()[y +1+row][ x+deltaX+col] !=null  )
                                collision = true ;
                    }
                }
                if( !collision )
                {
                    y++ ;
                }
            }
            else
            {
                collision = true ;
            }

            beginTime = System.currentTimeMillis();
        }
    }

    public void checkLine()
    {
        int bottomLine = board.getBoard().length - 1 ;
        for(int topline = board.getBoard().length - 1 ; topline>0 ; topline--)
        {
            int count = 0;
            for(int col =0;col< board.getBoard()[0].length ; col++)
            {
                if(board.getBoard()[topline][col] != null)
                    count++;
                //Checking if the line is full with block then the top line will fall on bottonline
                board.getBoard()[bottomLine][col] = board.getBoard()[topline][col] ;

            }
            if(count< board.getBoard()[0].length  )
            {
                bottomLine--;
            }

        }
    }


    public void rotateShape()
    {
        int [] [] rotateShape = transposeMatrix(coordis) ;
        reverse(rotateShape);
        //Check for right side and bottom
        if( (x+rotateShape[0].length >Board.BOARD_WIDTH) || (y+rotateShape.length > 20))
        {
            return;
        }
        //Check for Collision with other shapes before rotated
        for(int row=0;row< rotateShape.length ;row++)
        {
            for(int col =0;col< rotateShape[row].length ; col++)
            {
                if(rotateShape[row][col] !=0)
                {
                    if(board.getBoard()[y+ row ][x+col] != null)
                        return;
                }
            }
        }
        coordis = rotateShape ;
    }

    public int[][] transposeMatrix(int[][] matrix)
    {
        int[][]temp = new int[matrix[0].length][matrix.length] ;
        for(int row=0 ; row< matrix.length ;row++)
        {
            for(int col =0;col<matrix[0].length;col++)
            {
                temp[col][row] = matrix[row][col] ;
            }
        }
        return temp ;
    }

    private void reverse(int [][] matrix)
    {
        int middle = matrix.length / 2 ;
        for(int row=0; row<middle ;row++)
        {
            int[] temp =matrix[row] ;
            matrix[row] = matrix[matrix.length - row -1 ] ;
            matrix[matrix.length-row-1] = temp;
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
                    g.setColor(color);
                    //Falling he blocks downward
                    g.fillRect(col * Board.BLOCK_SIZE + x * Board.BLOCK_SIZE, row * Board.BLOCK_SIZE + y *Board.BLOCK_SIZE, Board.BLOCK_SIZE, Board.BLOCK_SIZE);
                }
            }
        }
    }

    public int[][] getCoordis()
    {
        return coordis ;
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

    public  int getY()
    {
        return y;
    }

    public  int getX()
    {
        return x;
    }

}
