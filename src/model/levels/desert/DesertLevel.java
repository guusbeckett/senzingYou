package model.levels.desert;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;

import model.Game;
import model.GroundRenderer;
import model.entities.Entity;
import model.entities.HostileEntity;
import model.levels.PunchLevel;
import control.Climate;
import control.Hardware;
import control.Scent;

public class DesertLevel extends PunchLevel
{
	private GroundRenderer groundRenderer = new DesertGround();
	
	public DesertLevel(Game game)
	{
		super(game);
		Hardware.getInstance().setClimate(Climate.WARM);
		//Hardware.getInstance().sprayScent(Scent.UNKNOWN);
	}
	
	public void update(double time)
	{
		super.update(time);
	}

	@Override
	public Entity getRandomEntity()
	{
		Random r = new Random();
		if(r.nextBoolean())
			return new Camel();
		else
			return new Vulture();
	}

	@Override
	public HostileEntity getRandomHostileEntity()
	{
		Random r = new Random();
		if(r.nextBoolean())
			return new Snake(getGame().getCamera().getUsers());
		else
			return new Scorpion(getGame().getCamera().getUsers());
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
		return Toolkit.getDefaultToolkit().getImage("./images/desert/background.jpg");
	}

	@Override
	public GroundRenderer getGroundRenderer()
	{
		return groundRenderer;
	}
}
