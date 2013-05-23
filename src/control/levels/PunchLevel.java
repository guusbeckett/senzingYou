package control.levels;

import java.awt.geom.Rectangle2D;
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
			if (entity instanceof HostileEntity)
			{
				HostileEntity hostile = (HostileEntity) entity;
				if (!hostile.isAlive())
				{
					it.remove();
				} 
				else
				{
					
					for (User user : getGame().getCamera().getUsers())
					{
						if ((entity.getBounds().getMaxX() < 0 || entity.getBounds().getMaxY() < 0) || (entity.getBounds().getMinX() > Camera.VIEW_WIDTH || entity.getBounds().getMinY() > Camera.VIEW_HEIGHT))
						{
							it.remove();						
						}
						else if(	hostile.getBounds().contains(user.getLeftHand()) ||
							hostile.getBounds().contains(user.getRightHand()))
						{
							
							user.setScore(user.getScore()+hostile.getReward());
							it.remove();
						}
					}
					
				}
			}
		}
	}
	
	public abstract List<Entity> getEntities();
	public abstract List<HostileEntity> getHostileEntities();
	
	public void spawn(double time)
	{
		List<Entity> entities = getEntities();
		List<HostileEntity> hostiles = getHostileEntities();
		
		//TODO: get game, get Song, get time and do your thing!
	}
}
