package control.levels;

import java.util.List;

import model.Game;
import model.entities.Entity;

public class Level
{
	protected Game game;

	public Level(Game game)
	{
		super();
		this.game = game;
	}
	
	public void update(double time)
	{
		List<Entity> entities = game.getEntities();
		for(Entity entity : entities)
			entity.update(time);
	}
}
