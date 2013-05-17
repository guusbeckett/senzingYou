package control.levels;

import model.Game;
import model.entities.underwater.Fish;
import model.entities.underwater.HarpoonDiver;

public class UnderwaterLevel extends PunchLevel
{

	public UnderwaterLevel(Game game)
	{
		super(game);
		// TODO Auto-generated constructor stub
		game.getEntities().add(new Fish());
		game.getEntities().add(new Fish());
		game.getEntities().add(new Fish());
		game.getEntities().add(new HarpoonDiver(game.getCameraData().getUsers()));
	}
	
	public void update(double time)
	{
		super.update(time);
		
		if((time % 5) == 1)
		{
			game.getEntities().add(new HarpoonDiver(game.getCameraData().getUsers()));
			game.getEntities().add(new HarpoonDiver(game.getCameraData().getUsers()));
		}
	}

}
