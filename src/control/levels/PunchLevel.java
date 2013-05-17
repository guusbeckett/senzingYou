package control.levels;

import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.List;

import model.CameraData;
import model.Game;
import model.User;
import model.entities.Entity;
import model.entities.HostileEntity;

public class PunchLevel extends Level
{

	public PunchLevel(Game game)
	{
		super(game);
	}

	@Override
	public void update(double time)
	{
		super.update(time);

		List<Entity> entities = game.getEntities();
		Iterator<Entity> it = entities.iterator();
		while (it.hasNext())
		{
			Entity entity = it.next();
			if (entity instanceof HostileEntity)
			{
				HostileEntity hostile = (HostileEntity) entity;
				if (!hostile.isAlive())
				{
					Rectangle2D bounds = entity.getBounds();
					if ((bounds.getMaxX() < 0 || bounds.getMaxY() < 0)
							|| (bounds.getMinX() > CameraData.VIEW_WIDTH || bounds
									.getMinY() > CameraData.VIEW_HEIGHT))
						it.remove();
				} else
				{
					
					for (User user : game.getCameraData().getUsers())
					{
						// TODO: check collision with hands
						if(	hostile.getBounds().contains(user.getLeftHand()) ||
							hostile.getBounds().contains(user.getRightHand())){
							it.remove();
						}
						
						
						// TODO: collision with other parts
					}
					
				}
			}
		}
	}
}
