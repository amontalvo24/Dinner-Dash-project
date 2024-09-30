package Game.GameStates;
import Main.Handler;
import Resources.Images;
import Display.UI.ClickListlener;
import Display.UI.UIImageButton;
import Display.UI.UIManager;

import java.awt.*;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class WinState extends State {

    private UIManager uiManager;

    public WinState(Handler handler) {
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
    	 g.drawImage(Images.Win,0,0,870,700,null);
         uiManager.Render(g);
         
         g.setColor(Color.white);
         g.setFont(new Font("ComicSans", Font.BOLD, 32));
         g.drawString("Money Earned: $" + handler.getPlayer().formating(handler.getPlayer().getMoney()), handler.getWidth()/2 -130, 110);
         g.drawString("Clients Served: " + handler.getPlayer().getClientsServed(), handler.getWidth()/2 -130, 140);
         g.drawString("Clients that left: " + handler.getPlayer().lefti, handler.getWidth()/2 -130, 170);


    }


}
