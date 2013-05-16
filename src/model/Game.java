package model;

import java.util.Iterator;
import java.util.List;
import model.entities.Entity;

public class Game
{
	private List<Entity> entities;
	private CameraData cameraData;

	public Game()
	{

	}

	public void clearRoom()
	{

	}

	public List<Entity> getEntities()
	{
		return entities;
	}

	public void addEntity(Entity entity)
	{
		entities.add(entity);
	}

	public void removeEntity(Entity entity)
	{
		for (Iterator<Entity> itr = entities.iterator(); itr.hasNext();)
		{
			Entity enti = itr.next();
			if (enti == entity)
				itr.remove();
		}
	}

	public CameraData getCameraData()
	{
		return cameraData;
	}

	public void setCameraData(CameraData cameraData)
	{
		this.cameraData = cameraData;
	}

}
