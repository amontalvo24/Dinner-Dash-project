package Game.Entities.Dynamic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.Random;

import Game.Entities.Static.BaseCounter;
import Game.Entities.Static.Burger;
import Game.Entities.Static.Item;
import Game.Entities.Static.PatienceResetCounter;
import Game.Entities.Static.PlateCounter;
import Game.Entities.Static.StoveCounter;
import Main.Handler;
import Resources.Animation;
import Resources.Images;

public class Player extends BaseDynamicEntity {
	Random RandNum = new Random();
	Item item;
	public float money;
	int speed = 8;
	public int lefti; //variable that contains the customer that left from the class Restaurant_1
	public int inspectorleft = 0;//tracks the amount of inspectors that have left
	int clientsServed = 0; //counts everytime a client is served
	private Burger burger;
	private String direction = "right";
	private int interactionCounter = 0;
	private int activetime = 0;
	private int PatienceResetCounterTick = 1000 + RandNum.nextInt(10)*100 + RandNum.nextInt(10)*10 + RandNum.nextInt(10);
	public int UpOrDown;
	private Animation playerAnim;
	public boolean PatienceResetCounterActive = false;
	public boolean goodPatty = false;
	public int Approvals = 0;//tracks inspectors that were served
	public boolean feelingGood = false; 
	public int goodFeelingTimer = 0;
	public int playerTinter = 1; 


	public Player(BufferedImage sprite, int xPos, int yPos, Handler handler) {
		super(sprite, xPos, yPos,82,112, handler);
		createBurger();
		playerAnim = new Animation(120,Images.chef);
	}


	/**
	 * @return the money
	 */
	public float getMoney() {
		return money;
	}

	/**
	 * @param money the money to set
	 */
	public void setMoney(float money) {
		this.money = money;
	}


	public int getClientsServed() {
		return clientsServed;
	}
	public void setClientsServed(int clientsServed) {
		this.clientsServed = clientsServed;
	}


	public void createBurger(){
		burger = new Burger(handler.getWidth() - 110, 100, 100, 50);

	}

	public int getApprovals() {
		return Approvals;
	}

	public void tick(){
		playerAnim.tick();
		if(xPos + width >= handler.getWidth()){
			direction = "left";

		} else if(xPos <= 0){
			direction = "right";
		}
		if (direction.equals("right")){
			xPos+=speed;
		} else{
			xPos-=speed;
		}
		if (interactionCounter > 15 && handler.getKeyManager().attbut){
			interact();
			interactionCounter = 0;
			for(BaseCounter counter: handler.getWorld().Counters){
				if (counter instanceof PatienceResetCounter && counter.isInteractable() && PatienceResetCounterActive){ //Allows Power up process on PatienceResetCounter
					PatienceResetCounterTick = 1000 + RandNum.nextInt(10)*100 + RandNum.nextInt(10)*10 + RandNum.nextInt(10);  
					PatienceResetCounterActive = false;
					System.out.println("Everyone's patience has reset! You feel excited!");
					feelingGood = true; //A little extra power up for activating the counter and getting a little tipsy
					speed *= 2;
					goodFeelingTimer = (RandNum.nextInt(4) + 5) * 60; //5 to 8 second timer
					playerTinter = 2;
					for(Client client: handler.getWorld().clients){
						client.patience = client.OGpatience; //Resets each clients' patience when interacting with the activated counter
					}
				}
				if (counter instanceof StoveCounter && counter.isInteractable() 
						&& ((StoveCounter) counter).getGoodPatty()){ 
					goodPatty = true;
				}
			}
		} else {
			interactionCounter++;
		}

		if (PatienceResetCounterTick > 0) {
			PatienceResetCounterTick--;
			
		} else if (!PatienceResetCounterActive) {
			PatienceResetCounterActive =  true;
			activetime = 120;
			System.out.println("Reset counter active!");
		} else if (activetime > 0) {
			activetime--;
			
		} else {
			PatienceResetCounterActive = false;
			System.out.println("Reset counter turned off...");
			PatienceResetCounterTick = 1000 + RandNum.nextInt(10)*100 + RandNum.nextInt(10)*10 + RandNum.nextInt(10);
		}
		
		if (goodFeelingTimer > 0) {
			goodFeelingTimer--;
		} else if (feelingGood) {
			feelingGood = false;
			speed /= 2;
			playerTinter = 1;
		}
		
		if(handler.getKeyManager().fattbut){
			for(BaseCounter counter: handler.getWorld().Counters){
				if (counter instanceof PlateCounter && counter.isInteractable()){ //Allows "Clear" on PlateCounter
					createBurger();
					goodPatty = false;
				}
			}

		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_R)){
			for(BaseCounter counter: handler.getWorld().Counters) {
				if (counter instanceof PlateCounter && counter.isInteractable()) {
					ringCustomer();
				}
			}

		}if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_SHIFT)
				&& !feelingGood){ //Can't slow down when you're having fun!
			speed-=1;
			if (speed<=0) {
				speed=0;
			}


		}if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_1)) { 				//press 1-5 to serve specific customer
			for(BaseCounter counter: handler.getWorld().Counters) {				   //depending on the order the customers are positioned
				if (counter instanceof PlateCounter && counter.isInteractable()) {
					specificust(0);
				}
			}
		}if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_2)) {
			for(BaseCounter counter: handler.getWorld().Counters) {
				if (counter instanceof PlateCounter && counter.isInteractable()) {
					specificust(1);
				}
			}

		}if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_3)) {
			for(BaseCounter counter: handler.getWorld().Counters) {
				if (counter instanceof PlateCounter && counter.isInteractable()) {
					specificust(2);
				}
			}

		}if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_4)) {
			for(BaseCounter counter: handler.getWorld().Counters) {
				if (counter instanceof PlateCounter && counter.isInteractable()) {
					specificust(3);
				}
			}

		}if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_5)) {
			for(BaseCounter counter: handler.getWorld().Counters) {
				if (counter instanceof PlateCounter && counter.isInteractable()) {
					specificust(4);
				}
			}

		}
		
		for (int i = 0; i < handler.getWorld().clients.size(); i++) {
			if (handler.getWorld().clients.get(i).isAntiV() 
					&& (handler.getWorld().clients.get(i).getPatience() != handler.getWorld().clients.get(i).getOGpatience())
					&& ( (handler.getWorld().clients.get(i).getOGpatience() - handler.getWorld().clients.get(i).getPatience()) % ((int) (handler.getWorld().clients.get(i).getOGpatience()*0.08))) == 0
					&& handler.getWorld().clients.size() != 1) {
				System.out.println("AntiV at position " + (i + 1) + " is getting impatient and is bothering others");
				if (i == 0) {
					handler.getWorld().clients.get(i+1).setPatience( (int) (handler.getWorld().clients.get(i+1).getPatience() * 0.96));
				} else if (i == handler.getWorld().clients.size() - 1) {
					handler.getWorld().clients.get(i-1).setPatience( (int) (handler.getWorld().clients.get(i-1).getPatience() * 0.96));
				} else {
					UpOrDown = RandNum.nextInt(2);
					if (UpOrDown == 0) {
						handler.getWorld().clients.get(i+1).setPatience((int) (handler.getWorld().clients.get(i+1).getPatience() * 0.96));
					} else {
						handler.getWorld().clients.get(i-1).setPatience((int) (handler.getWorld().clients.get(i-1).getPatience() * 0.96));
					}
				}
			}
		}
	}



	private void ringCustomer() {
		for(Client client: handler.getWorld().clients){
			boolean matched = ((Burger)client.order.food).equals(handler.getCurrentBurger());
			if(matched){
				if (client.isInspector()) {
					if (!feelingGood) {
						System.out.println("Inspector gave good review :D");
						for(Client affectedClient: handler.getWorld().clients){													
							affectedClient.patience += affectedClient.patience * 0.12;//all clients will have 12% more patience	upon successful inspector serving	
						}																										
						Approvals++;
					} else {
						System.out.println("Inspector paid, but doesn't like you drinking while working. No good ratings.");
					}

				}
				if (goodPatty) { //12% tip if patty was good
					money+=client.order.value * 0.12;
					System.out.println("You gave a good patty!");
					goodPatty = false;
				}
				
				if (feelingGood && !client.isInspector()) {
					money += client.order.value * 0.1; //Gives a 10% tip
					System.out.println("Customer liked your enthusiasm!");
				} 

				for(Client affectedClient: handler.getWorld().clients){			
					affectedClient.patience += affectedClient.patience*0.25;//if order matches all clients gains 1/4 of their patience
					if (affectedClient.patience > affectedClient.OGpatience) { //makes sure the patience doesn't exceed the OG patience, resulting in unfair advantages and weirdly tinted clients
						affectedClient.patience = affectedClient.OGpatience;		//also fixes increment from serving an inspector, since this will always be checked upon any successful serving
					}
				}
				if (client.patience > client.OGpatience/2) {
					money+=client.order.value * 0.15; //15% tip if order is served before patience reaches half
					System.out.println("You got a tip!");
				}

				handler.getGame().getMusicHandler().playServed();
				clientsServed++;
				money+=client.order.value;
				if (money>=50) {
					handler.getGame().getMusicHandler().playwin();
					Game.GameStates.State.setState(handler.getGame().winState);
					System.out.println("Congratulations!!!");
				}

				handler.getWorld().clients.remove(client);
				handler.getPlayer().createBurger();

				System.out.println("Total money earned is: $" + String.valueOf(money));		

			}

			return;
		}

	}




	public void render(Graphics g) {
		if (PatienceResetCounterActive) {
			g.drawImage(Images.kitchenCounter[10], BaseCounter.DEFAULTCOUNTERWIDTH*8, 552, BaseCounter.DEFAULTCOUNTERWIDTH, 154, null);
		}
		if(direction=="right") {
			g.drawImage(Resources.Images.tint(playerAnim.getCurrentFrame(), 1, playerTinter, 1), xPos, yPos, width, height, null);
		}else{
			g.drawImage(Resources.Images.tint(playerAnim.getCurrentFrame(), 1, playerTinter, 1), xPos+width, yPos, -width, height, null);

		}
		g.setColor(Color.green);
		burger.render(g);
		g.setColor(Color.magenta);
		g.fillRect(handler.getWidth()/2 -210, 3, 350, 35);
		g.fillRect(handler.getWidth()/2 -210, 33, 460, 35);
		g.setColor(Color.darkGray);
		g.setFont(new Font("ComicSans", Font.BOLD, 32));

		g.drawString("Money Earned: $" + formating(money), handler.getWidth()/2 -200, 30);

	}

	public void interact(){
		for(BaseCounter counter: handler.getWorld().Counters){
			if (counter.isInteractable()){
				counter.interact();
			}
		}
	}
	public Burger getBurger(){
		return this.burger;
	}
	

	public String formating(float val) { 		//returns 2 digits after the decimal point as a string
		DecimalFormat df = new DecimalFormat("0.00");
		return String.valueOf(df.format(val));
	}

	public void specificust(int pos) { //method that identifies the order of a specific customer     Very similar to ringCustomer()
		Client client= handler.getWorld().clients.get(pos);//to call the class Client and position
		boolean matched = ((Burger)client.order.food).equals(handler.getCurrentBurger());
		if (matched) {																			//Not efficient, but it works

			if (client.isInspector()) {
				if (!feelingGood) {
					System.out.println("Inspector gave good review :D");
					for(Client affectedClient: handler.getWorld().clients){													
						affectedClient.patience += affectedClient.patience * 0.12;//all clients will have 12% more patience	upon successful inspector serving	
					}																										
					Approvals++;
				} else {
					System.out.println("Inspector paid, but doesn't like you drinking while working. No good ratings.");
				}

			}
			if (goodPatty) { //12% tip if patty was good
				money+=client.order.value * 0.12;
				System.out.println("You gave a good patty!");
				goodPatty = false;
			}
			
			if (feelingGood && !client.isInspector()) {
				money += client.order.value * 0.1; //Gives a 10% tip
				System.out.println("Customer liked your enthusiasm!");
			} 

			for(Client affectedClient: handler.getWorld().clients){			
				affectedClient.patience += affectedClient.patience*0.25;//if order matches all clients gains 1/4 of their patience
				if (affectedClient.patience > affectedClient.OGpatience) { //makes sure the patience doesn't exceed the OG patience, resulting in unfair advantages and weirdly tinted clients
					affectedClient.patience = affectedClient.OGpatience;		//also fixes increment from serving an inspector, since this will always be checked upon any successful serving
				}
			}
			if (client.patience > client.OGpatience/2) {
				money+=client.order.value * 0.15; //15% tip if order is served before patience reaches half
				System.out.println("You got a tip!");
			}

			handler.getGame().getMusicHandler().playServed();
			
			clientsServed++;
			money+=client.order.value;
			if (money>=50) {
				Game.GameStates.State.setState(handler.getGame().winState);
				System.out.println("Congratulations!!!");
			}

			handler.getWorld().clients.remove(client);//removes the client in that position
			handler.getPlayer().createBurger();

			for (int i = 1 ; i <= pos ; i++) { //backtracks clients ahead of the specifically served client so that they don't crash into the PlateCounter
				handler.getWorld().clients.get(pos - i).reverseMove();
			}
			System.out.println("Total money earned is: $" + String.valueOf(money));
		}
		return;
	}

}






