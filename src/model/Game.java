package model;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.entities.Entity;

public class Game
{
	private List<Entity> entities;
	private Camera camera;
	private Image background;

	public Game()
	{
		this.entities = Collections.synchronizedList(new ArrayList<Entity>());
		background = null;
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

	public Image getBackground()
	{
		return background;
	}

	public void setBackground(Image background)
	{
		this.background = background;
	}
}
