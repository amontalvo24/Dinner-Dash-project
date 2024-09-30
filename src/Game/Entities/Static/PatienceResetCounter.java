package Game.Entities.Static;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import Main.Handler;
import Resources.Images;

public class PatienceResetCounter extends BaseCounter {
    public PatienceResetCounter(int xPos, int yPos, Handler handler) {
		super(Images.kitchenCounter[9], xPos, yPos,96,154,handler);
    }
    public void render(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g.drawImage(sprite,xPos,yPos,width,height,null);
        if(isInteractable()){
            g2.setColor(Color.RED);
            g.setFont(new Font("ComicSans", Font.ITALIC, 20));
            g2.drawString("Activate (E)",xPos + width/2 - 60,yPos -15);
        }
    }
}