package control.levels;

import java.util.Iterator;
import java.util.List;

import model.Camera;
import model.Game;
import model.User;
import model.entities.Entity;
import model.entities.HostileEntity;

public abstract class DodgeLevel extends Level
{
	private float proportion;
	private float minThreshold;
	private int lastSpawnedHostile = 0;
	private int lastSpawned = 0;

	public DodgeLevel(Game game)
	{
		super(game);
		if(getGame().getSong() != null){
			minThreshold = getGame().getSong().getMinThreshold();
			proportion = getGame().getSong().getMaxThreshold() - minThreshold;
		}
	}
	
	@Override
	public void update(double time)
	{
		super.update(time);
		spawn(time);

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
				} 
				else
				{
					boolean haveToDelete = false;
					for (User user : getGame().getCamera().getUsers())
					{
						if ((entity.getBounds().getMaxX() < 0 || entity.getBounds().getMaxY() < 0) || (entity.getBounds().getMinX() > Camera.VIEW_WIDTH || entity.getBounds().getMinY() > Camera.VIEW_HEIGHT))
						{
							haveToDelete = true;					
						}
						else if(user.getUserPixels().getData().readPixel((int)entity.getPosition().getX(), (int)entity.getPosition().getY()) == user.getId())
						{
							user.setScore(user.getScore()+hostile.getReward());
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
		float current = (getGame().getSong().getThreshold() - minThreshold)
				/ proportion;

		if (current != 0)
		{
			if (lastSpawnedHostile > (100 / (current * current * current)))
			{
				getGame().getEntities().add(getRandomHostileEntity());
				lastSpawnedHostile = 0;
			} else
				lastSpawnedHostile += time;
			
			if(lastSpawned > 100 / current)
			{
				getGame().getEntities().add(getRandomEntity());
				lastSpawned = 0;
			} else
				lastSpawned += time;
		}
	}
}
