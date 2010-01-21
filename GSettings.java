import java.awt.Color;
public class GSettings
{
    public static final int size = 20;
    public static final Color getColor(String colorString)
    {
        Color color;
        if(colorString.equals("red")) {
            color = Color.red;
        }
        else if(colorString.equals("black")) {
            color = Color.black;
        }
        else if(colorString.equals("blue")) {
            color = Color.blue;
        }
        else if(colorString.equals("yellow")) {
            color = Color.yellow;
        }
        else if(colorString.equals("green")) {
            color = Color.green;
        }
        else if(colorString.equals("magenta")) {
            color = Color.magenta;
        }
        else if(colorString.equals("white")) {
            color = Color.white;
        }
        else {
            color = Color.black;
        }
        return color;
    }
    public static final int SQUARE_BLOCK = 0, T_BLOCK = 1, L_BLOCK = 2, 
                            BACKWARDS_L_BLOCK = 3, STRAIGHT_BLOCK = 4, 
                            S_BLOCK = 5, BACKWARDS_S_BLOCK = 6;
    public static final int NONE = 0, LEFT = 1, RIGHT = 2, ROTATE = 3, DOWN = 4;
}
