package Game.Entities.Dynamic;

import java.awt.Graphics;
import java.util.Random;

import Game.Entities.Static.Burger;
import Game.Entities.Static.Item;
import Game.Entities.Static.Order;
import Main.Handler;
import Resources.Images;

public class Client extends BaseDynamicEntity {
    int patience;
    int OGpatience;
    Order order;
    
    
    public boolean isLeaving = false;
    public Client(int xPos, int yPos, Handler handler) {
        super(Images.people[new Random().nextInt(11)] , xPos, yPos,64,72, handler);
        patience = (int) (new Random().nextInt(120*60)+60*60 * Math.pow(1.1, handler.getPlayer().getApprovals()) * Math.pow(0.94, handler.getPlayer().inspectorleft));		
        														// ^^^ modifies patience based on reviews from inspectors
        OGpatience = patience;
        int numOfIngredients = new Random().nextInt(4)+1;
        order = new Order();
        order.food = new Burger(xPos +72,yPos,52,22);
        ((Burger) order.food).addIngredient(Item.botBread);
        ((Burger) order.food).addIngredient(Item.burger);
        order.value += 1.0;
        for(int i = 0;i<numOfIngredients;i++){
            int ingredients = new Random().nextInt(4)+1;
            order.value += 0.5;
            switch (ingredients){
                case 1:
                    ((Burger) order.food).addIngredient(Item.lettuce);

                    break;
                case 2:
                    ((Burger) order.food).addIngredient(Item.tomato);

                    break;

                case 3:
                    ((Burger) order.food).addIngredient(Item.cheese);

                    break;
                
                case 4: //added fish as an option to order
                    ((Burger) order.food).addIngredient(Item.fish);

                    break;

            }

        }
        ((Burger) order.food).addIngredient(Item.topBread);

    }
    
    

    public void setOGpatience(int oGpatience) {
		OGpatience = oGpatience;
	}
	public int getOGpatience() {
		return OGpatience;
	}
	public void setPatience(int Patience) {
		patience = Patience;
	}
	public int getPatience() {
		return patience;
	}
	
	
	
	



	public void tick(){
    	patience--;
    	if(patience<=0){
    		isLeaving=true;
    	}
    	}
    	
    	
    	
    public void render(Graphics g){

        if(!isLeaving){
            g.drawImage(Images.tint(sprite,1.0f,((float)patience/(float)OGpatience),((float)patience/(float)OGpatience)),xPos,yPos,width,height,null);

            ((Burger) order.food).render(g);
        }
    }

    public void move(){
        yPos+=102;
        ((Burger) order.food).y+=102;
        
    }
    public void reverseMove(){
        yPos-=102;
        ((Burger) order.food).y-=102;
        
    }
    
    public boolean isInspector() { //identifies if it's inspector 
    	return sprite.equals(Images.people[9]);//what position the image is in the array
    }
    
    public boolean isAntiV() { //identifies if it's AntiV
    	return sprite.equals(Images.people[10]);
    }
    
}
