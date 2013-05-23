package control.levels;

import java.util.Iterator;
import java.util.List;

import model.Camera;
import model.Game;
import model.User;
import model.entities.Entity;
import model.entities.HostileEntity;

public abstract class PunchLevel extends Level
{
	private float proportion;
	private float minThreshold;
	private int lastSpawnedHostile = 0;
	private int lastSpawned = 0;

	public PunchLevel(Game game)
	{
		super(game);
		minThreshold = getGame().getSong().getMinThreshold();
		proportion = getGame().getSong().getMaxThreshold() - minThreshold;
	}

	@Override
	public void update(double time)
	{
		super.update(time);

		List<Entity> entities = getGame().getEntities();
		Iterator<Entity> it = entities.iterator();
		while (it.hasNext())
		{
			Entity entity = it.next();
			if (entity instanceof HostileEntity)
			{
				HostileEntity hostile = (HostileEntity) entity;
				if (!hostile.isAlive())
				{
					it.remove();
				} else
				{
					boolean haveToDelete = false;
					for (User user : getGame().getCamera().getUsers())
					{
						if ((entity.getBounds().getMaxX() < 0 || entity
								.getBounds().getMaxY() < 0)
								|| (entity.getBounds().getMinX() > Camera.VIEW_WIDTH || entity
										.getBounds().getMinY() > Camera.VIEW_HEIGHT))
						{
							haveToDelete = true;					
						}
						else if (hostile.getBounds().contains(user.getLeftHand())
								|| hostile.getBounds().contains(user.getRightHand()))
						{

							user.setScore(user.getScore() + hostile.getReward());
							haveToDelete = true;
						}
					}
					
					if(haveToDelete){
						it.remove();
					}
				}
			}
		}
	}

	public abstract Entity getRandomEntity();

	public abstract HostileEntity getRandomHostileEntity();

	public void spawn(double time)
	{
		// TODO: choose whether or not to spawn new entities
		float current = (getGame().getSong().getThreshold() - minThreshold)
				/ proportion;

//		if (lastSpawnedHostile > ((1 / current) * 10))
//		{
//			getGame().getEntities().add(getRandomHostileEntity());
//			lastSpawnedHostile = 0;
//		} else
//			lastSpawnedHostile += time;

		if (current > 0.9 && lastSpawnedHostile > 100)
		{
			getGame().getEntities().add(getRandomHostileEntity());
			lastSpawnedHostile = 0;
		} else if (current > 0.7 && lastSpawnedHostile > 400)
		{
			getGame().getEntities().add(getRandomHostileEntity());
			lastSpawnedHostile = 0;
		} else if (current > 0.5 && lastSpawnedHostile > 950)
		{
			getGame().getEntities().add(getRandomHostileEntity());
			lastSpawnedHostile = 0;
		} else if (current > 0.2 && lastSpawnedHostile > 1900)
		{
			getGame().getEntities().add(getRandomHostileEntity());
			lastSpawnedHostile = 0;
		} else if (current > 0 && lastSpawnedHostile > 3500)
		{
			getGame().getEntities().add(getRandomHostileEntity());
			lastSpawnedHostile = 0;
		} else
			lastSpawnedHostile += time;

	}
}
