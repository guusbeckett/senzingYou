package model.levels.rainforest;

import java.awt.Image;

import model.Game;
import model.GroundRenderer;
import model.MediaProvider;
import model.entities.Entity;
import model.entities.HostileEntity;
import model.levels.DodgeLevel;
import control.Climate;
import control.Hardware;
import control.Scent;

public class RainforestLevel extends DodgeLevel
{
	private GroundRenderer groundRenderer = new RainforestGround();
	
	public RainforestLevel(Game game)
	{
		super(game);
		Hardware.getInstance().setClimate(Climate.MOIST);
		Hardware.getInstance().sprayScent(Scent.FLOWER);
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
		return MediaProvider.getInstance().getImage("rainforest/background.png");
	}

	@Override
	public GroundRenderer getGroundRenderer()
	{
		return groundRenderer;
	}
	
	@Override
	public Image getDescriptionImage()
	{
		return MediaProvider.getInstance().getImage("dodgeBanana.png");
	}
}

