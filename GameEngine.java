import java.util.Vector;
import java.util.LinkedList;
import java.util.Observer;
import java.util.Observable;
import java.util.Random;

public class GameEngine implements Runnable, Observer
{
    private UserInterface ui;
    private GamePad gamepad;
    private Input input;
    private GameData gamedata;
    private int divisor;
    public GameEngine()
    {
        ui = new UserInterface();
        ui.setVisible(true);
        gamedata = new GameData();
        gamedata.addObserver(this);
        gamepad = new GamePad(20, 11, ui, gamedata);
        input = new Input(ui.getFrame(), gamepad);
        divisor = 15;
        Thread t1 = new Thread(input);
        Thread t2 = new Thread(this);
        t1.start();
        t2.start();
    }
    public void run()
    {   
        long currTime = System.currentTimeMillis();
        long lastTime = currTime;     
        int cnt = 0;
        
        while(true){
            currTime = System.currentTimeMillis();
            if(currTime - lastTime > 30){
                lastTime = currTime;
                cnt = ++cnt % divisor;
                gamepad.redraw();
                if(cnt >= divisor-1)
                    gamepad.update();
            }
            try{
                Thread.yield();
            }catch(Exception ex){}
        }
    }
    public void update(Observable obs, Object obj)
    {
        if(obs == gamedata && 5 < divisor)
            divisor -= 1; 
    }
}

