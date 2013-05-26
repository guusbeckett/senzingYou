package control.levels;

import java.util.Iterator;
import java.util.List;

import model.Game;
import model.enitities.menu.Hand;
import model.entities.Entity;
import model.entities.MenuEntity;
import control.GameController;

public abstract class MenuLevel extends Level
{
	private Hand h;
	public MenuLevel(Game game)
	{
		super(game);
		game.setLevelMenu(true);
		game.getEntities().add(h = new Hand());
	}
	
	public void spawn(double time, float current){
		
	}
	
	public void update(double time){
		if(getGame().getCamera().getPreciseHand() != null){
			h.setPosition(getGame().getCamera().getPreciseHand());	
			h.setVisible(true);
		}
		else{
			h.setVisible(false);
		}
		

		List<Entity> entities = getGame().getEntities();
		Iterator<Entity> it = entities.iterator();
		while (it.hasNext())
		{
			Entity entity = it.next();
			if (entity instanceof MenuEntity)
			{
				MenuEntity mEntity = (MenuEntity)entity;
				if(mEntity.getBounds().contains(h.getBounds())){
					mEntity.actionToPerform(getGame());
				}
			}
		}
	}

}
