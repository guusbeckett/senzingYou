package control.levels;

import model.Game;
import model.entities.Entity;
import model.entities.HostileEntity;

public abstract class Level
{
	private float proportion;
	private float minThreshold;
	private int lastSpawnedHostile = 0;
	private int lastSpawned = 0;
	private Game game;

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

	public void update(double time)
	{
		if (getGame().getSong() != null)
		{
			float current = (getGame().getSong().getThreshold() - minThreshold)
					/ proportion;
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

	public abstract Entity getRandomEntity();

	public abstract HostileEntity getRandomHostileEntity();

	public void spawn(double time, float current)
	{

		if (current != 0)
		{
			if (lastSpawnedHostile > (100 / (current * current * current)))
			{
				getGame().getEntities().add(getRandomHostileEntity());
				lastSpawnedHostile = 0;
			} else
				lastSpawnedHostile += time;

			if (lastSpawned > 100 / current)
			{
				getGame().getEntities().add(getRandomEntity());
				lastSpawned = 0;
			} else
				lastSpawned += time;
		}
	}
}
