package control.levels;

import model.Game;
import model.entities.underwater.Fish;
import model.entities.underwater.Plant;
import model.entities.underwater.HarpoonDiver;

public class UnderwaterLevel extends PunchLevel
{

	public UnderwaterLevel(Game game)
	{
		super(game);
		// TODO Auto-generated constructor stub
		game.getEntities().add(new Plant());
		game.getEntities().add(new Plant());
		game.getEntities().add(new Plant());
		game.getEntities().add(new Plant());
	}
	
	public void update(double time)
	{
		super.update(time);
		if((int)(Math.random() * time) == 1){
			game.getEntities().add(new Fish());
		}
		if((int)(Math.random() * (time * 1.5)) == 1){
			game.getEntities().add(new HarpoonDiver(game.getCamera().getUsers()));
		}
		
	}

}
