package Game.World;

import Game.Entities.Dynamic.Client;
import Game.Entities.Dynamic.Player;
import Game.Entities.Static.*;
import Main.Handler;
import Resources.Images;

import java.awt.*;
import java.util.ArrayList;

public class Restaurant_1 extends BaseWorld {
    private int count=0;
    private int capacity = 5;
    public int Rejections = 0;
    float moneys;
    int clientsLeft = 0;//counts everytime a customer leaves
    public Restaurant_1(BaseCounter[] Counters, Handler handler) {
        super(Images.floor,Counters, handler, new Player(null,0,650,handler));

    }
    
    /**
	 * @return the clientsLeft
	 */
	public int getClientsLeft() {
		return clientsLeft;
	}
	
	public int getRejections() {
		return Rejections;
	}


	public void tick(){
        count++;
        if(count >= 5*60 && !isFull()){
            count = 0;
            for(Client client: this.clients){
                    client.move();
                }
            this.generateClient();
        }else if(isFull()){ /*	Removed count variable from if, since this delayed the evaluation of whether or not the client left, allowing
        					* the player to serve a customer that left with no repercussions, which impaired the clientsLeft counter,
        					* subsequently affecting the Lose State and losing mechanic. The trade-off being that as soon as one client
        					* leaves, another appears instantly.
        					*/
            count=0;
            boolean left=false;
            Client toLeave = null;
            ArrayList<Client> toMove = new ArrayList<>();
            for (Client client : this.clients) {
                if (client.isLeaving && !left) {
                    toLeave = client;
                    left=true;
                    
                moneys= handler.getPlayer().getMoney();
                    if (client.sprite == Images.people[9]) { //identifies if the client that left was the inspector
            			moneys-= moneys * 0.5;
            			System.out.println("Inspector gave a bad review :(");
            			handler.getGame().getMusicHandler().Inspeleft();
            			System.out.println("Now you have: $" + moneys);
            			if (moneys <= 0) {
							moneys =0;
						}
            			
            			Rejections++;
            			handler.getPlayer().inspectorleft = Rejections;
					}
            	
                }else if (left) {
                    toMove.add(client);
                                                
                }
            }
            if(left){
                this.clients.remove(toLeave);
                
                handler.getPlayer().money = moneys;
				clientsLeft++;	
				handler.getPlayer().lefti=clientsLeft; //easier to transfer info to a variable in a player and call it
            	if (clientsLeft>=10) { //if 10 customer leave because of their patience you loose
            		handler.getGame().getMusicHandler().playloose();
            		Game.GameStates.State.setState(handler.getGame().looseState);
					System.out.println("Better luck next time");
            	}
            	
                for (Client client : toMove) {
                    client.move();               
                }
                this.generateClient();
            }
            

            
        }




        for(Client client: this.clients){
            client.tick();
        }
        for(BaseCounter counter: Counters){
            counter.tick();
        }
        handler.getPlayer().tick();
    }

    public boolean isFull(){
        return this.clients.size() >=capacity;
    }
    public void render(Graphics g){
    	
        g.drawImage(Background,0,0,handler.getWidth(), handler.getHeight(),null);
        g.drawImage(Images.welcome,5,90,43,82,null);
        g.drawImage(Images.kitchenChairTable[0],handler.getWidth()/3,90,96,96,null);
        g.drawImage(Images.kitchenChairTable[1],handler.getWidth()/3+96,140,52,52,null);
        g.drawImage(Images.kitchenChairTable[1],handler.getWidth()/3-52,140,52,52,null);
        g.drawImage(Images.candle,handler.getWidth()/3+25,100,40,40,null);
        
        g.drawImage(Images.kitchenChairTable[0],handler.getWidth()/3+handler.getWidth()/6,190,96,96,null);
        g.drawImage(Images.kitchenChairTable[2],handler.getWidth()/3+handler.getWidth()/6+96,240,52,52,null);
        g.drawImage(Images.kitchenChairTable[2],handler.getWidth()/3+handler.getWidth()/6-52,240,52,52,null);
        g.drawImage(Images.candle,handler.getWidth()/3+handler.getWidth()/6+25,195,40,40,null);

        g.drawImage(Images.kitchenChairTable[0],handler.getWidth()/3+handler.getWidth()/3,90,96,96,null);
        g.drawImage(Images.kitchenChairTable[1],handler.getWidth()/3+handler.getWidth()/3+96,140,52,52,null);
        g.drawImage(Images.kitchenChairTable[2],handler.getWidth()/3+handler.getWidth()/3-52,140,52,52,null);
        g.drawImage(Images.candle,handler.getWidth()/3+handler.getWidth()/6+170,100,40,40,null);
        
        g.drawImage(Images.kitchenChairTable[0],handler.getWidth()/3+handler.getWidth()/3,292,96,96,null);
        g.drawImage(Images.kitchenChairTable[2],handler.getWidth()/3+handler.getWidth()/3+96,312,52,52,null);
        g.drawImage(Images.kitchenChairTable[1],handler.getWidth()/3+handler.getWidth()/3-52,312,52,52,null);
        g.drawImage(Images.candle,handler.getWidth()/3+handler.getWidth()/3+25,298,40,40,null);
        
        g.drawImage(Images.kitchenChairTable[0],handler.getWidth()/3,292,96,96,null);
        g.drawImage(Images.kitchenChairTable[2],handler.getWidth()/3+96,312,52,52,null);
        g.drawImage(Images.kitchenChairTable[2],handler.getWidth()/3-52,312,52,52,null);
        g.drawImage(Images.kitchenChairTable[1],handler.getWidth()/3+20,390,52,52,null);
        g.drawImage(Images.candle,handler.getWidth()/3+25,298,40,40,null);
        
        g.drawImage(Images.plant[0],handler.getWidth()/3+400,120,200,200,null);
        g.drawImage(Images.plant[1],handler.getWidth()/3+120,350,150,150,null);
        g.drawImage(Images.rope,8,93,150,350,null);
        g.drawImage(Images.rope,35,370,150,250,null);
        
        
        
        for(Client client: clients){
            client.render(g);
        }

        for(BaseCounter counter: Counters){
            counter.render(g);
        }
        handler.getPlayer().render(g);
    }
}
