package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.entities.Entity;

public class Game
{
	private List<Entity> entities;
	private Camera camera;

	public Game()
	{
		this.entities = Collections.synchronizedList(new ArrayList<Entity>());
		
		camera = new Camera();
	}

	public void clearRoom()
	{
		entities.clear();
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
		entities.remove(entity);
	}

	public Camera getCamera()
	{
		return camera;
	}
}
