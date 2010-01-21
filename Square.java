
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;

public class Square implements Drawable
{
    private Rectangle rectangle;
    private Color color;
    public Square(int x, int y, String color)
    {
        this.color = GSettings.getColor(color);
        rectangle = new Rectangle(x-(GSettings.size/2), y-(GSettings.size/2), GSettings.size, GSettings.size);
    }
    public void move(int deltaX, int deltaY)
    {
        rectangle.translate(deltaX, deltaY);
    }
    public void moveTo(int x, int y)
    {
        rectangle.setLocation(x, y);
    }
    public void draw(Graphics2D g)
    {
        fill(g);
        draw2(g);
    }
    public void draw2(Graphics2D g)
    {
        g.setColor(Color.black);
        g.draw(rectangle);
    }
    public void fill(Graphics2D g)
    {
        g.setColor(color);
        g.fill(rectangle);
    }
    public int getX()
    {
        return (int)rectangle.getX();
    }
    public int getY()
    {
        return (int)rectangle.getY();
    }
    public Rectangle getRectangle()
    {
        return rectangle;
    }
    public boolean intersects(Square square)
    {
        return rectangle.intersects(square.getRectangle());
    }
}
