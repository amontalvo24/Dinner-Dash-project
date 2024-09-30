package Game.GameStates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import Game.Entities.Static.BaseCounter;
import Game.Entities.Static.BreadCounter;
import Game.Entities.Static.CheeseCounter;
import Game.Entities.Static.FishCounter;
import Game.Entities.Static.LettuceCounter;
import Game.Entities.Static.PatienceResetCounter;
import Game.Entities.Static.PlateCounter;
import Game.Entities.Static.StoveCounter;
import Game.Entities.Static.TomatoCounter;
import Game.Entities.Static.TopBreadCounter;
import Game.World.Restaurant_1;
import Main.Handler;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class GameState extends State {

    public GameState(Handler handler){
        super(handler);
        BaseCounter Counters[] = {

                new PlateCounter(0,584,handler),
                new CheeseCounter(BaseCounter.DEFAULTCOUNTERWIDTH,593,handler),
                new StoveCounter(BaseCounter.DEFAULTCOUNTERWIDTH*2,600,handler),
                new LettuceCounter(BaseCounter.DEFAULTCOUNTERWIDTH*3,600,handler),
                new FishCounter(BaseCounter.DEFAULTCOUNTERWIDTH*4,600,handler), //switched EmptyCounter for FishCounter
                new TomatoCounter(BaseCounter.DEFAULTCOUNTERWIDTH*5, 590, handler),
                new BreadCounter(BaseCounter.DEFAULTCOUNTERWIDTH*6, 568, handler),
                new TopBreadCounter(BaseCounter.DEFAULTCOUNTERWIDTH*7, 568, handler),
        		new PatienceResetCounter(BaseCounter.DEFAULTCOUNTERWIDTH*8, 552, handler)};//added counter that resets patience

    @SuppressWarnings("unused")
	Restaurant_1 World_1 = new Restaurant_1(Counters,handler);

    }


    @Override
    public void tick() {
        handler.getWorld().tick();
        
    }

    @Override
    public void render(Graphics g) {
        handler.getWorld().render(g);
        
    g.setColor(Color.white);
    g.setFont(new Font("ComicSans", Font.BOLD, 25));    
    g.drawString("Clients Served: " + handler.getPlayer().getClientsServed(), handler.getWidth()/2 -200, 60);
    g.drawString("Clients that left: " + handler.getPlayer().lefti, handler.getWidth()/2 + 30, 60);
    }
    
   

}
