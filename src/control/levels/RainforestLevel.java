package control.levels;

import model.Game;
import model.entities.Entity;
import model.entities.HostileEntity;
import model.entities.rainforest.Banana;
import model.entities.rainforest.Bird;
import model.entities.rainforest.Snake;
import model.entities.rainforest.Tree;

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
		if(Math.random() < 0.3){
			return new Tree();
		}
		else if(Math.random() > 0.7){
			return new Bird();
		}
		else{
			return new Snake();
		}
	}

	@Override
	public HostileEntity getRandomHostileEntity()
	{
		// TODO Auto-generated method stub
		return new Banana(getGame().getCamera().getUsers());
	}
}

