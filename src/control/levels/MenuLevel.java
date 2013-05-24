package control.levels;

import model.Game;
import model.enitities.menu.Hand;

public abstract class MenuLevel extends Level
{
	private Game game;
	private Hand h;
	public MenuLevel(Game game)
	{
		super(game);
		this.game = game;
		this.game.setLevelMenu(true);
		this.game.getEntities().add(h = new Hand());
	}
	
	public void spawn(double time, float current){
		
	}
	
	public void update(double time){
		if(game.getCamera().getPreciseHand() != null){
			if(!game.getCamera().getUsers().isEmpty()){
				if(game.getCamera().getUsers().get(0).getRightHand().distance(game.getCamera().getPreciseHand()) > 100){
					h.setPosition(game.getCamera().getUsers().get(0).getRightHand());	
				}
				else{
					h.setPosition(game.getCamera().getPreciseHand());	
				}
				
			}
			else{
				h.setPosition(game.getCamera().getPreciseHand());	
			}
			
		}
	}

}
