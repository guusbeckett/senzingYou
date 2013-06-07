package model.levels.underwater;

import java.awt.Image;
import java.util.Random;

import model.Game;
import model.GroundRenderer;
import model.MediaProvider;
import model.entities.Entity;
import model.entities.HostileEntity;
import model.levels.PunchLevel;
import control.Climate;
import control.Hardware;
import control.Scent;

public class UnderwaterLevel extends PunchLevel
{
	private GroundRenderer groundRenderer = new UnderwaterGround();

	public UnderwaterLevel(Game game)
	{
		super(game);
		Hardware.getInstance().setClimate(Climate.UNDERWATER);
		Hardware.getInstance().sprayScent(Scent.OCEAN);

		Random r = new Random();
		for (int i = 0; i < r.nextInt(10) + 10; i++)
			getGame().getEntities().add(new Plant());
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
		return MediaProvider.getInstance().getImage("underwater/background.png");
	}

	@Override
	public GroundRenderer getGroundRenderer()
	{
		return groundRenderer;
	}

	@Override
	public Image getDescriptionImage()
	{
		return MediaProvider.getInstance().getImage("punch.png");
	}
}
