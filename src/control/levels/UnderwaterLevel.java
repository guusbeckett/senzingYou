package control.levels;

import model.Game;
import model.entities.underwater.Fish;

public class UnderwaterLevel extends PunchLevel
{

	public UnderwaterLevel(Game game)
	{
		super(game);
		// TODO Auto-generated constructor stub
		game.getEntities().add(new Fish(game.getCameraData().getUsers()));
		game.getEntities().add(new Fish(game.getCameraData().getUsers()));
		game.getEntities().add(new Fish(game.getCameraData().getUsers()));
	}
	
	public void update(double time){
		if((time % 5) == 1){
			game.getEntities().add(new HarpoonDriver(game.getCameraData().getUsers()));
			game.getEntities().add(new HarpoonDriver(game.getCameraData().getUsers()));
		}
	}

}
