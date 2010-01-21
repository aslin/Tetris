
/**
 * Write a description of class UserInterface here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class UserInterface
{
    private JFrame frame;
    private CanvasPane canvas;
    private Graphics2D graphic;
    private Color backgroundColor;
    private Image canvasImage;
    private boolean visible = false;
    private int xSize;
    private int ySize;
    
    public UserInterface()
    {
        this("TestSpel", 600, 600, Color.white);
    }
    public UserInterface(String title, int xSize, int ySize, Color bgColor)
    {
        this.xSize = xSize;
        this.ySize = ySize;
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas = new CanvasPane();
        frame.setContentPane(canvas);
        frame.setTitle(title);
        canvas.setPreferredSize(new Dimension(xSize, ySize));
        backgroundColor = bgColor;
        frame.pack(); 
    }
    /**
     * Sets the main frame visible and initialize some "graphics"
     * @param visible true to turn on visible
     */
    public void setVisible(boolean visible)
    {
        if(graphic == null) {
            // first time: instantiate the offscreen image and fill it with
            // the background color
            Dimension size = canvas.getSize();
            canvasImage = canvas.createImage(size.width, size.height);
            graphic = (Graphics2D)canvasImage.getGraphics();
            graphic.setColor(backgroundColor);
            graphic.fillRect(0, 0, size.width, size.height);
            graphic.setColor(Color.black);
        }
        this.visible = visible;
        frame.setVisible(visible);
    }
    public boolean getVisible()
    {
        return visible;
    }
    public void redrawObject(Drawable obj)
    {
        erase();
        obj.draw(graphic); 
        canvas.repaint();
    }
    public void redrawList(List<Drawable> list)
    {
        erase();
        for(Drawable obj : list)
            obj.draw(graphic);
            
        canvas.repaint();
    }
    public void draw()
    {
        Graphics2D g = graphic;
        g.setColor(Color.RED);
        int[] x = {50,60,50}, y = {10,20,30};
        g.drawPolygon(x,y, 3); 
        canvas.repaint();
    }
    public JFrame getFrame()
    {
        return frame;
    }
    public Graphics2D getGraphics()
    {
        return graphic;
    }
    public int getxSize()
    {
        return xSize;
    }
    public int getySize()
    {
        return ySize;
    }  
    public void erase()
    {
        Color original = graphic.getColor();
        graphic.setColor(backgroundColor);
        Dimension size = canvas.getSize();
        graphic.fill(new Rectangle(0, 0, size.width, size.height));
        graphic.setColor(original);
    }
    /************************************************************************
     * Inner class CanvasPane - the actual canvas component contained in the
     * Canvas frame. This is essentially a JPanel with added capability to
     * refresh the image drawn on it.
     */
    private class CanvasPane extends JPanel
    {
        public void paint(Graphics g)
        {
            g.drawImage(canvasImage, 0, 0, null);
        }
    }
}

