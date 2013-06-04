package model.levels.sky;

import java.awt.Image;

import model.Game;
import model.GroundRenderer;
import model.MediaProvider;
import model.entities.Entity;
import model.entities.HostileEntity;
import model.levels.PunchLevel;
import control.Climate;
import control.Hardware;
import control.Scent;

public class SkyLevel extends PunchLevel
{
	private GroundRenderer groundRenderer = new SkyGround();
	
	public SkyLevel(Game game)
	{
		super(game);
		Hardware.getInstance().setClimate(Climate.COLD);
		Hardware.getInstance().sprayScent(Scent.OCEAN);
	}

	public void update(double time)
	{
		super.update(time);
	}
	
	@Override
	public Entity getRandomEntity()
	{
		return new Cloud();
	}

	@Override
	public HostileEntity getRandomHostileEntity()
	{
		return new Bird(getGame().getCamera().getUsers());
	}

	@Override
	public int getEntitySpawnRate()
	{
		return 10;
	}

	@Override
	public int getHostileEntitySpawnRate()
	{
		return 300;
	}

	@Override
	public Image getBackground()
	{
		return MediaProvider.getInstance().getImage("sky/background.jpg");
	}

	@Override
	public GroundRenderer getGroundRenderer()
	{
		return groundRenderer;
	}
}
