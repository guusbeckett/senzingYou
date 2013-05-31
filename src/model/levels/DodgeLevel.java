package model.levels;

import java.util.Iterator;
import java.util.List;

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
				boolean killing = false;
					for (User user : getGame().getCamera().getUsers())
					{
						if(hostile.isAlive() && (user.getUserPixels().getData().readPixel((int)entity.getBounds().getX(), (int)entity.getBounds().getY()) == user.getId() ||
								user.getUserPixels().getData().readPixel((int)entity.getBounds().getMaxX(), (int)entity.getBounds().getY()) == user.getId() ||
								user.getUserPixels().getData().readPixel((int)entity.getBounds().getX(), (int)entity.getBounds().getMaxY()) == user.getId() ||
								user.getUserPixels().getData().readPixel((int)entity.getBounds().getMaxX(), (int)entity.getBounds().getMaxY()) == user.getId()))
						{
							user.setScore(user.getScore()+hostile.getReward());
							killing = true;
						}
					}
					
					if(hostile.isAlive() && killing){
						hostile.kill();
					}
					
					if(hostile.getDeadTime() >= 450){
						it.remove();
					}
			}
		}
	}
}
