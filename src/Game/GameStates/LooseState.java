package Game.GameStates;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import Display.UI.ClickListlener;
import Display.UI.UIImageButton;
import Display.UI.UIManager;
import Main.Handler;
import Resources.Images;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class LooseState extends State {

    private UIManager uiManager;

    public LooseState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);


        uiManager.addObjects(new UIImageButton(handler.getWidth()/2-64, handler.getHeight()/1-275, 128, 64, Images.butrestart, new ClickListlener() {
            @Override
            public void onClick() {
                handler.getMouseManager().setUimanager(null);
                handler.getGame().reStart();
                State.setState(handler.getGame().gameState);
            }
        }));
        
        uiManager.addObjects(new UIImageButton(handler.getWidth()/2-50, handler.getHeight()/1-200, 128, 64, Images.BTitle, new ClickListlener() {
            @Override
            public void onClick() {
                handler.getMouseManager().setUimanager(null);
                handler.getGame().reStart();
                State.setState(handler.getGame().menuState);
            }
        }));
        
    }

    @Override
    public void tick() {
        handler.getMouseManager().setUimanager(uiManager);
        uiManager.tick();

    }

    @Override
    public void render(Graphics g) {
    	 g.drawImage(Images.Loose,0,0,950,750,null);
         uiManager.Render(g);
         
         g.setColor(Color.white);
         g.setFont(new Font("ComicSans", Font.BOLD, 32));
         g.drawString("Money Earned: $" + handler.getPlayer().formating(handler.getPlayer().getMoney()), handler.getWidth()/2 -130, 190);
         g.drawString("Clients Served: " + handler.getPlayer().getClientsServed(), handler.getWidth()/2 -130, 220);
         g.drawString("Clients that left: " + handler.getPlayer().lefti, handler.getWidth()/2 -130, 250);


    }


}
