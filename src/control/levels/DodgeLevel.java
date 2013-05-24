package control.levels;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.List;

import model.Camera;
import model.Game;
import model.User;
import model.entities.Entity;
import model.entities.HostileEntity;

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
						else if(user.getUserPixels().getData().readPixel((int)entity.getBounds().getX(), (int)entity.getBounds().getY()) == user.getId() ||
								user.getUserPixels().getData().readPixel((int)entity.getBounds().getMaxX(), (int)entity.getBounds().getY()) == user.getId() ||
								user.getUserPixels().getData().readPixel((int)entity.getBounds().getX(), (int)entity.getBounds().getMaxY()) == user.getId() ||
								user.getUserPixels().getData().readPixel((int)entity.getBounds().getMaxX(), (int)entity.getBounds().getMaxY()) == user.getId())
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
}
