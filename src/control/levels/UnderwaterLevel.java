package control.levels;

import java.awt.Toolkit;

import model.Game;
import model.entities.Entity;
import model.entities.HostileEntity;
import model.entities.underwater.Fish;
import model.entities.underwater.HarpoonDiver;
import model.entities.underwater.Plant;

public class UnderwaterLevel extends PunchLevel
{
	public UnderwaterLevel(Game game)
	{
		super(game);
		game.setBackground(Toolkit.getDefaultToolkit().getImage("./images/underwater/background.png"));
		game.setGround(Toolkit.getDefaultToolkit().getImage("./images/underwater/ground.jpg"));
		game.getEntities().add(new Plant());
		game.getEntities().add(new Plant());
		game.getEntities().add(new Plant());
		game.getEntities().add(new Plant());
	}
	
	public void update(double time)
	{
		super.update(time);		
	}

	@Override
	public Entity getRandomEntity()
	{
		return new Fish();
	}

	@Override
	public HostileEntity getRandomHostileEntity()
	{
		return new HarpoonDiver(getGame().getCamera().getUsers());
	}
}
