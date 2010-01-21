
import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics2D;
import java.awt.Point;
public class Block implements Drawable
{
    private int numStates;
    private int stateIndex;
    private int x;
    private int y;
    private int blocktype;
    private String color;
    private ArrayList<ArrayList<Point>> statePoints;
    private ArrayList<Square> block;
    
    public Block(int x, int y, int blocktype)
    {
        this.x = x;
        this.y = y;
        this.blocktype = blocktype;
        this.stateIndex = 0;
        int size = GSettings.size;
        statePoints = new ArrayList<ArrayList<Point>>();
        ArrayList<Point> tmp;
        
        //  [1][3]
        //  [2][4]
        if(blocktype == GSettings.SQUARE_BLOCK){
            tmp = new ArrayList<Point>();
            tmp.add(new Point(0,0));
            tmp.add(new Point(0,1));
            tmp.add(new Point(1,0));
            tmp.add(new Point(1,1));
            statePoints.add(tmp);
            color = "red";
        //     [1]
        //  [3][2][4]
        }else if(blocktype == GSettings.T_BLOCK){
            tmp = new ArrayList<Point>();
            tmp.add(new Point(1,0));
            tmp.add(new Point(0,1));
            tmp.add(new Point(1,1));
            tmp.add(new Point(2,1));
            statePoints.add(tmp);
            tmp = new ArrayList<Point>();
            tmp.add(new Point(1,0));
            tmp.add(new Point(1,1));
            tmp.add(new Point(1,2));
            tmp.add(new Point(2,1));
            statePoints.add(tmp);
            tmp = new ArrayList<Point>();
            tmp.add(new Point(0,1));
            tmp.add(new Point(1,1));
            tmp.add(new Point(2,1));
            tmp.add(new Point(1,2));
            statePoints.add(tmp);
            tmp = new ArrayList<Point>();
            tmp.add(new Point(1,0));
            tmp.add(new Point(1,1));
            tmp.add(new Point(1,2));
            tmp.add(new Point(0,1));
            statePoints.add(tmp);
            color = "blue";
        //  [1]
        //  [2]
        //  [3][4]
        }else if(blocktype == GSettings.L_BLOCK){
            tmp = new ArrayList<Point>();
            tmp.add(new Point(0,0));
            tmp.add(new Point(1,0));
            tmp.add(new Point(1,1));
            tmp.add(new Point(1,2));
            statePoints.add(tmp);
            tmp = new ArrayList<Point>();
            tmp.add(new Point(2,0));
            tmp.add(new Point(0,1));
            tmp.add(new Point(1,1));
            tmp.add(new Point(2,1));
            statePoints.add(tmp);
            tmp = new ArrayList<Point>();
            tmp.add(new Point(1,0));
            tmp.add(new Point(1,1));
            tmp.add(new Point(1,2));
            tmp.add(new Point(2,2));
            statePoints.add(tmp);
            tmp = new ArrayList<Point>();
            tmp.add(new Point(0,1));
            tmp.add(new Point(1,1));
            tmp.add(new Point(2,1));
            tmp.add(new Point(0,2));
            statePoints.add(tmp);
            color = "green";
        //     [1]
        //     [2]
        //  [4][3]
        }else if(blocktype == GSettings.BACKWARDS_L_BLOCK){
            tmp = new ArrayList<Point>();
            tmp.add(new Point(1,0));
            tmp.add(new Point(1,1));
            tmp.add(new Point(1,2));
            tmp.add(new Point(2,0));
            statePoints.add(tmp);
            tmp = new ArrayList<Point>();
            tmp.add(new Point(0,1));
            tmp.add(new Point(1,1));
            tmp.add(new Point(2,1));
            tmp.add(new Point(2,2));
            statePoints.add(tmp);
            tmp = new ArrayList<Point>();
            tmp.add(new Point(1,0));
            tmp.add(new Point(1,1));
            tmp.add(new Point(1,2));
            tmp.add(new Point(0,2));
            statePoints.add(tmp);
            tmp = new ArrayList<Point>();
            tmp.add(new Point(0,0));
            tmp.add(new Point(0,1));
            tmp.add(new Point(1,1));
            tmp.add(new Point(2,1));
            statePoints.add(tmp);
            color = "yellow";
        //  [1]
        //  [2]
        //  [3]
        //  [4]
        }else if(blocktype == GSettings.STRAIGHT_BLOCK){
            tmp = new ArrayList<Point>();
            tmp.add(new Point(1,0));
            tmp.add(new Point(1,1));
            tmp.add(new Point(1,2));
            tmp.add(new Point(1,3));
            statePoints.add(tmp);
            tmp = new ArrayList<Point>();
            tmp.add(new Point(0,1));
            tmp.add(new Point(1,1));
            tmp.add(new Point(2,1));
            tmp.add(new Point(3,1));
            statePoints.add(tmp);
            color = "red";
        //     [2][1]
        //  [4][3]
        }else if(blocktype == GSettings.S_BLOCK){
            tmp = new ArrayList<Point>();
            tmp.add(new Point(1,1));
            tmp.add(new Point(2,1));
            tmp.add(new Point(0,2));
            tmp.add(new Point(1,2));
            statePoints.add(tmp);
            tmp = new ArrayList<Point>();
            tmp.add(new Point(1,0));
            tmp.add(new Point(1,1));
            tmp.add(new Point(2,1));
            tmp.add(new Point(2,2));
            statePoints.add(tmp);
            color = "yellow";
        //  [2][1]
        //     [4][3]
        }else if(blocktype == GSettings.BACKWARDS_S_BLOCK){
            tmp = new ArrayList<Point>();
            tmp.add(new Point(0,1));
            tmp.add(new Point(1,1));
            tmp.add(new Point(1,2));
            tmp.add(new Point(2,2));
            statePoints.add(tmp);
            tmp = new ArrayList<Point>();
            tmp.add(new Point(2,0));
            tmp.add(new Point(2,1));
            tmp.add(new Point(1,1));
            tmp.add(new Point(1,2));
            statePoints.add(tmp);
            color = "green";
        }
        numStates = statePoints.size();
        stateIndex = 0;
        block = new ArrayList<Square>();
        for(Point point : statePoints.get(stateIndex)){
            block.add(new Square( ((int)point.getX()*size)+x+10, ((int)point.getY()*size)+y+10, color));
        }
    }

    public void draw(Graphics2D g)
    {
        for(Square square : block){
            square.fill(g);
        }
        for(Square square : block){
            square.draw2(g);
        }
    }
    public void rotate(boolean clockwise)
    {
        if(clockwise)
            this.stateIndex = ++stateIndex % numStates;
        else
            if(this.stateIndex == 0)
                this.stateIndex = numStates-1;
            else
                this.stateIndex = --stateIndex % numStates;
                
        for(int i = 0; i < 4; i++){    
            block.get(i).moveTo((int)(statePoints.get(stateIndex).get(i).getX()*GSettings.size)+x,
                                (int)(statePoints.get(stateIndex).get(i).getY()*GSettings.size)+y);
        }
    }
    public void move(int deltaX, int deltaY)
    {
        x += deltaX;
        y += deltaY;
        for(Square square : block){
            square.move(deltaX, deltaY);
        }
    }
    public void moveTo(int x, int y)
    {
        this.x = x;
        this.y = y;
        for(int i = 0; i < 4; i++){    
            block.get(i).moveTo((int)(statePoints.get(stateIndex).get(i).getX()*GSettings.size)+x,
                                (int)(statePoints.get(stateIndex).get(i).getY()*GSettings.size)+y);
        }
    }
    public List<Square> getSquares()
    {
        return block;
    }
}
