package Game.Entities.Static;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import Main.Handler;

public class BaseCounter extends BaseStaticEntity {

    public Item item;
    public static int DEFAULTCOUNTERWIDTH = 96;
    public int tickCountdown;
    Random RandNum = new Random();
    
    BaseCounter(BufferedImage sprite, int xPos, int yPos,int width,int height, Handler handler) {
        super(sprite, xPos, yPos,width, height, handler);
    }

    public boolean isInteractable(){
        return handler.getPlayer().xPos + width/2 >= xPos && handler.getPlayer().xPos + width/2 < xPos + width;
    }

    public void interact(){
        if (item != null) {
            handler.getPlayer().getBurger().addIngredient(item);
        } 
    }
    
    public void tick(){
    	
    }

    public void render(Graphics g){
        g.drawImage(sprite,xPos,yPos,width,height,null);
        if(isInteractable() && item != null){
            g.drawImage(item.sprite,xPos + width/2 - 25,yPos -30,50,30,null);
        }
    }
}