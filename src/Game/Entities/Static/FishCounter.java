package Game.Entities.Static;

import Main.Handler;
import Resources.Images;

public class FishCounter extends BaseCounter {
    public FishCounter(int xPos, int yPos, Handler handler) {
        super(Images.kitchenCounter[8], xPos, yPos,96,114,handler);
        item = Item.fish;
    }
}