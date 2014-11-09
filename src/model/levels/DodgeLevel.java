package model.levels;

import java.util.Iterator;
import java.util.List;

import model.Camera;
import model.Game;
import model.User;
import model.entities.Entity;
import model.entities.HostileEntity;

import org.openni.SceneMap;

public abstract class DodgeLevel extends Level
{
	public DodgeLevel(Game game)
	{
		super(game);
	}
	
	@Override
	public void update(double time)
	{
		super.update(time);
		
		List<User> users = getGame().getCamera().getUsers();
		
		if (!users.isEmpty())
		{
			SceneMap map = users.get(0).getUserPixels();
			
			List<Entity> entities = getGame().getEntities();
			Iterator<Entity> it = entities.iterator();
			while (it.hasNext())
			{
				Entity entity = it.next();
	
				if (entity instanceof HostileEntity)
				{
					HostileEntity hostile = (HostileEntity) entity;
					boolean killing = false;
					
					if(hostile.isAlive())
					{
						if (!(hostile.getBounds().getMinX() < 0 ||
								hostile.getBounds().getMaxX() >= Camera.VIEW_WIDTH ||
								hostile.getBounds().getMinY() < 0 ||
								hostile.getBounds().getMaxY() >= Camera.VIEW_HEIGHT))
						{
							for (User user : getGame().getCamera().getUsers())
							{
								if(hostile.isAlive() && (map.readPixel((int)entity.getBounds().getX(), (int)entity.getBounds().getY()) == user.getId() ||
										map.readPixel((int)entity.getBounds().getMaxX(), (int)entity.getBounds().getY()) == user.getId() ||
										map.readPixel((int)entity.getBounds().getX(), (int)entity.getBounds().getMaxY()) == user.getId() ||
										map.readPixel((int)entity.getBounds().getMaxX(), (int)entity.getBounds().getMaxY()) == user.getId()))
								{
									user.setScore(user.getScore()+hostile.getReward());
									killing = true;
								}
							}
						}
					}
						
					if(hostile.isAlive() && killing)
					{
						hostile.kill();
						hostile.playHitSound();
					}
					
					if(hostile.getDeadTime() >= 450)
					{
						it.remove();
					}
				}
			}
		}
	}
}
