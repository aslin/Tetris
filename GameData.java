
import java.util.Observable;
public class GameData extends Observable
{
    private int score;
    private int level;
    private int lines;
    
    public GameData()
    {
        score = 0;
        level = 0;
        lines = 0;
    }
    
    public void increaseScore(int lines)
    {
        if(lines < 1 || 4 < lines)
            return;
        for(int i = 0; i < lines; i++){
            this.lines++;
            if(this.lines%10 == 0){
                level++;
                setChanged();
                notifyObservers();
            }
        }
        switch(lines){
            case 1: score += level*40+40; break;
            case 2: score += level*100+100; break;
            case 3: score += level*300+300; break;
            case 4: score += level*1200+1200; break;
        }
    }
    public String getScore()
    {
        return Integer.toString(score);
    }
    public String getLevel()
    {
        return Integer.toString(level);
    }
    public String getLines()
    {
        return Integer.toString(lines);
    }
    public int getLevelInt()
    {
        return level;
    }
    
}
