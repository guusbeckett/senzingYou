package model.levels;

import java.util.Iterator;
import java.util.List;

import model.Camera;
import model.Game;
import model.User;
import model.entities.Entity;
import model.entities.HostileEntity;

public abstract class PunchLevel extends Level
{
	public PunchLevel(Game game)
	{
		super(game);
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
			if ((entity.getBounds().getMaxX() < 0 || entity.getBounds().getMaxY() < 0) || 
				(entity.getBounds().getMinX() > Camera.VIEW_WIDTH || entity.getBounds().getMinY() > Camera.VIEW_HEIGHT))
			{
				it.remove();
			}
			else{
				if (entity instanceof HostileEntity)
				{
					HostileEntity hostile = (HostileEntity) entity;
						boolean kill = false;
						
						for (User user : getGame().getCamera().getUsers())
						{
							if (hostile.getBounds().contains(user.getLeftHand())
							|| hostile.getBounds().contains(user.getRightHand()))
							{
								user.setScore(user.getScore() + hostile.getReward());
								kill = true;
							}
						}
						
						if(hostile.isAlive() && kill){
							hostile.kill();
						}
						
						if(hostile.getDeadTime() >= 450){
							it.remove();
						}
				}
			}
		}
	}
}
