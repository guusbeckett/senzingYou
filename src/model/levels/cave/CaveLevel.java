package model.levels.cave;

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

public class CaveLevel extends DodgeLevel
{
	private GroundRenderer groundRenderer = new CaveGround();
	
	public CaveLevel(Game game)
	{
		super(game);
		Hardware.getInstance().setClimate(Climate.COLD);
		Hardware.getInstance().sprayScent(Scent.OCEAN);
	}

	public void update(double time)
	{
		super.update(time);

//		if((int)(Math.random() * time) == 1){
//			getGame().getEntities().add(new Droplet());
//		}
//		if((int)(Math.random() * (time * 0.5)) == 1){
//			getGame().getEntities().add(new Rock(getGame().getCamera().getUsers()));
//		}
	}

	@Override
	public Entity getRandomEntity()
	{
		return new Droplet();
	}

	@Override
	public HostileEntity getRandomHostileEntity()
	{
		return new Rock(getGame().getCamera().getUsers());
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
		return MediaProvider.getInstance().getImage("cave/background.png");
	}

	@Override
	public GroundRenderer getGroundRenderer()
	{
		return groundRenderer;
	}
	
	@Override
	public Image getDescriptionImage()
	{
		return MediaProvider.getInstance().getImage("dodgeRock.png");
	}
}
