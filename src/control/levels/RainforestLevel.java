package control.levels;

import model.Game;
import model.entities.Entity;
import model.entities.HostileEntity;
import model.entities.rainforest.Banana;
import model.entities.rainforest.Bird;

public class RainforestLevel extends DodgeLevel
{

	public RainforestLevel(Game game)
	{
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Entity getRandomEntity()
	{
		// TODO Auto-generated method stub
		return new Bird();
	}

	@Override
	public HostileEntity getRandomHostileEntity()
	{
		// TODO Auto-generated method stub
		return new Banana(getGame().getCamera().getUsers());
	}
}
