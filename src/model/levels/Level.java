package model.levels;

import java.awt.Image;

import model.Game;
import model.GroundRenderer;
import model.entities.Entity;
import model.entities.HostileEntity;

public abstract class Level
{
	private float proportion;
	private float minThreshold;
	private int lastSpawnedHostile = 0;
	private int lastSpawned = 0;
	private Game game;
	private int counterDescription;

	public Level(Game game)
	{
		super();
		this.game = game;
		if (getGame().getSong() != null)
		{
			minThreshold = getGame().getSong().getMinThreshold();
			proportion = getGame().getSong().getMaxThreshold() - minThreshold;
		}
	}

	public boolean isDescriptionImageVisible()
	{
		if (counterDescription <= 1500)
			return true;
		return false;
	}

	public float getDescriptionImageOpacity()
	{

		if ((float) counterDescription <= 250)
			return ((float) counterDescription / 250);

		if ((float) counterDescription > 250 && (float) counterDescription < 1250)
			return 1f;

		if ((float) counterDescription >= 1250)
			return 1 - (((float) counterDescription - 1250) / 250);

		else
			return 0f;
	}
	
	public abstract Entity getRandomEntity();

	public abstract int getEntitySpawnRate();

	public abstract HostileEntity getRandomHostileEntity();

	public abstract int getHostileEntitySpawnRate();

	public abstract Image getBackground();

	public abstract GroundRenderer getGroundRenderer();

	public abstract Image getDescriptionImage();

	public void update(double time)
	{
		counterDescription += time;
		if (getGame().getSong() != null)
		{
			float current = (getGame().getSong().getThreshold() - minThreshold) / proportion;
			spawn(time, current);

			for (Entity entity : game.getEntities())
			{
				entity.update(time * current * 3.0);
			}
		}
	}

	public Game getGame()
	{
		return game;
	}

	public void spawn(double time, float current)
	{

		if (current != 0)
		{
			if (lastSpawnedHostile > ((float) getHostileEntitySpawnRate() / current))
			{
				getGame().getEntities().add(getRandomHostileEntity());
				lastSpawnedHostile = 0;
			} else
				lastSpawnedHostile += time;

			if (lastSpawned > ((float) getEntitySpawnRate() / current))
			{
				getGame().getEntities().add(getRandomEntity());
				lastSpawned = 0;
			} else
				lastSpawned += time;
		}
	}
}
