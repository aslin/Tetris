
import javax.swing.*;
import java.awt.event.KeyEvent;
public class Input extends KeyboardInput implements Runnable 
{
    private GamePad gamepad;
    public Input(JFrame frame, GamePad gamepad)
    {
        super(frame);
        this.gamepad = gamepad;
    }
    public void run()
    {
        while(true){
            poll();
            if(keyDownOnce(KeyEvent.VK_UP))
                gamepad.rotateCurrBlock();
            if(keyDownOnce(KeyEvent.VK_LEFT))
                gamepad.moveCurrBlock(-GSettings.size, 0);
            if(keyDownOnce(KeyEvent.VK_RIGHT))
                gamepad.moveCurrBlock(GSettings.size, 0);
            if(keyDownOnce(KeyEvent.VK_DOWN))
                gamepad.moveCurrBlock(0,GSettings.size);
            try{
                Thread.yield();
            }catch(Exception ex){}
        }
    }
}
