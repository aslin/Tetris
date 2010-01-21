import java.util.Vector;
import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.Color;
import java.util.Random;

public class GamePad implements Drawable
{
    private int rows;
    private int cols;
    private int x;
    private int y;
    private UserInterface ui;
    private Color color;
    private LinkedList<Square> squares;
    private Block currBlock;
    private Block nextBlock;
    private Square[][] deadSquares;
    private GameData gamedata;
    private boolean gridOn;
    
    public GamePad(int rows, int cols, UserInterface ui,  GameData gamedata)
    {
        this.rows = rows;
        this.cols = cols;
        this.ui = ui;
        y = 40;
        x = 40;
        color = Color.black;
        squares = new LinkedList<Square>();
        this.currBlock = genBlock();
        currBlock.moveTo(x+(GSettings.size)*4, y);
        this.nextBlock = genBlock();
        deadSquares = new Square[rows][cols];
        this.gamedata = gamedata;
        gridOn = false;

    }
    public void draw(Graphics2D g)
    {
        g.setColor(color);
        if(gridOn){
            for(int i = 0; i <= rows; i++)
                g.drawLine(x,y+(i*GSettings.size), x+(cols*GSettings.size),y+(i*GSettings.size));
            for(int j = 0; j <= cols; j++)
                g.drawLine(x+(j*GSettings.size), y,x+(j*GSettings.size), y+(rows*GSettings.size));
        }else{
            g.drawLine(x,y,x,y+(GSettings.size*rows));
            g.drawLine(x+(GSettings.size*cols),y,x+(GSettings.size*cols),y+(GSettings.size*rows));
            g.drawLine(x,y,x+(GSettings.size*cols),y);
            g.drawLine(x,y+(GSettings.size*rows),x+(GSettings.size*cols),y+(GSettings.size*rows));
        }
        int x2 = x+(GSettings.size*(cols+2));
        int y2 = y+(GSettings.size*2);
        int w = 5;
        int h = 5;
        g.drawLine(x2,y2,x2+(GSettings.size*w),y2);
        g.drawLine(x2,y2+(GSettings.size*h),x2+(GSettings.size*w),y2+(GSettings.size*h));
        g.drawLine(x2,y2,x2,y2+(GSettings.size*h));
        g.drawLine(x2+(GSettings.size*w),y2,x2+(GSettings.size*w),y2+(GSettings.size*h));
        nextBlock.moveTo(x2+(GSettings.size), y2+(GSettings.size));
        nextBlock.draw(g);
        int x3 = x2;
        int y3 = x2+(GSettings.size*(h));
        g.drawString("Level: " + gamedata.getLevel(), x3, y3);
        g.drawString("Score: "+ gamedata.getScore(), x3, y3+GSettings.size);
        g.drawString("Lines: " + gamedata.getLines(), x3, y3+(GSettings.size*2));
    }
    public LinkedList<Square> getSquares()
    {
        return squares;
    }
    public synchronized Block getCurrBlock()
    {
        return currBlock;
    }
    private synchronized Block genBlock()
    {
        Random r = new Random();
        return new Block(0,0, r.nextInt(7));
    }
    
    public synchronized void redraw()
    {
        Vector<Drawable> tmp = new Vector<Drawable>();
        tmp.addAll(squares);
        tmp.add(currBlock);
        tmp.add(this);
        ui.redrawList(tmp);
    }
    public synchronized void update()
    {   
        if(currBlock == null) 
            return;
        moveCurrBlock(0, GSettings.size);
    }
    public synchronized void moveCurrBlock(int deltaX, int deltaY)
    {
        if(currBlock == null)
            return;
        currBlock.move(deltaX, deltaY);
        if(checkWallHit())
            currBlock.move(-deltaX, -deltaY);
        else if(checkBlockHit()){
            currBlock.move(-deltaX, -deltaY);
            if(deltaY > 0) // dvs blocket har förflyttats nedåt på spelplanen
                handleBottomHit();
        }
    }
    public synchronized void rotateCurrBlock()
    {
        if(currBlock == null)
            return;
        currBlock.rotate(true);
        if(checkWallHit())
            currBlock.rotate(false);
        else if(checkBlockHit()){
            currBlock.rotate(false);
        }
    }
    public synchronized boolean checkWallHit()
    {
        if(currBlock == null)
            return false;
        for(Square square : currBlock.getSquares()){
            if(square.getX() < x || (square.getX()+GSettings.size) > x+(cols*GSettings.size))
                return true;
        }
        return false;
    }
    public synchronized boolean checkBlockHit()
    {
        if(currBlock == null)
            return false;
        for(Square blockSquare : currBlock.getSquares()){
            if(blockSquare.getY() > y+((rows-1)*GSettings.size))
                return true;
            for(Square deadSquare : squares){
                if(blockSquare.intersects(deadSquare))
                    return true;
            }
        }
        return false;
    }
    private void handleBottomHit()
    {
        for(Square square : currBlock.getSquares()){
            int row = (square.getY()-y)/GSettings.size;
            int col = (square.getX()-x)/GSettings.size;
            deadSquares[row][col] = square;
        }
        squares.addAll(currBlock.getSquares());
        checkFullRow();
        currBlock = nextBlock;
        nextBlock = genBlock();
        currBlock.moveTo(x+(GSettings.size)*4, y);
    }
    private void checkFullRow()
    {
        boolean[] fullRows = new boolean[rows];
        int lineCnt = 0;
        for(int row = 0; row < rows; row++){
            int cnt = 0;
            for(int col = 0; col < cols; col++){
                if(deadSquares[row][col] == null)
                    break;
                cnt++;
            }
            if(cnt == cols){
                fullRows[row] = true;
                lineCnt++;
            }
        }
        if(lineCnt == 0)
            return;
        gamedata.increaseScore(lineCnt);
        for(int i = 0; i < rows; i++){
            if(fullRows[i]){
                for(int col = 0; col < cols; col++){
                    squares.remove(deadSquares[i][col]);
                    deadSquares[i][col] = null;
                }
                for(int j = i-1;  j > 0; j--){
                    for(int col = 0; col < cols; col++){
                        if(deadSquares[j][col] != null){
                            deadSquares[j][col].move(0,GSettings.size);
                            deadSquares[j+1][col] = deadSquares[j][col];
                            deadSquares[j][col] = null;
                        }
                    }
                }
            }
        }
        
    }
}
