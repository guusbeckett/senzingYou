package control.levels;

import java.awt.Image;
import java.awt.Toolkit;

import control.Climate;
import control.Hardware;

import model.Game;
import model.entities.Entity;
import model.entities.HostileEntity;
import model.entities.rainforest.Banana;
import model.entities.rainforest.Bird;
import model.entities.rainforest.Snake;
import model.entities.rainforest.Tree;
import view.ground.GroundRenderer;
import view.ground.RainforestGround;

public class RainforestLevel extends DodgeLevel
{
	private GroundRenderer groundRenderer = new RainforestGround();
	
	public RainforestLevel(Game game)
	{
		super(game);
		Hardware.getInstance().setClimate(Climate.MOIST);
		game.getEntities().add(new Tree());
	}

	@Override
	public Entity getRandomEntity()
	{
		if(Math.random() > 0.5){
			return new Bird();
		}
		else{
			return new Snake();
		}
	}

	@Override
	public HostileEntity getRandomHostileEntity()
	{
		return new Banana(getGame().getCamera().getUsers());
	}

	@Override
	public int getEntitySpawnRate()
	{
		return 100;
	}

	@Override
	public int getHostileEntitySpawnRate()
	{
		return 300;
	}

	@Override
	public Image getBackground()
	{
		return Toolkit.getDefaultToolkit().getImage("./images/rainforest/background.png");
	}

	@Override
	public GroundRenderer getGroundRenderer()
	{
		return groundRenderer;
	}
}

