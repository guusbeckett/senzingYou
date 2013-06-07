package model;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.entities.Entity;
import model.levels.Level;

public class Game
{
	private List<Entity> entities;
	private Camera camera;
	private Song song;
	private Level level;
	private List<Drive> drives;
	private boolean loading;
	private Image screenCapture;
	private Highscore highscore;

	public Game()
	{
		this.entities = Collections.synchronizedList(new ArrayList<Entity>());
		this.drives = new ArrayList<Drive>();
		this.highscore = new Highscore();
		
		for (char a = 'A'; a <= 'I'; a++)
		{
			drives.add(new Drive(a));
		}
		
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

	public Song getSong()
	{
		return song;
	}

	public void setSong(Song song)
	{
		this.song = song;
	}

	public Level getLevel()
	{
		return level;
	}

	public void setLevel(Level level)
	{
		this.level = level;
	}
	
	public List<Drive> getJustConnectedDrives()
	{
		List<Drive> justConnected = new ArrayList<Drive>();
		
		for (Drive drive : drives)
		{
			if (drive.wasJustConnected())
			{
				justConnected.add(drive);
			}
		}
		
		return justConnected;
	}

	public boolean isLoading()
	{
		return loading;
	}

	public void setLoading(boolean loading)
	{
		this.loading = loading;
	}
	
	public Image getScreenCapture()
	{
		return screenCapture;
	}

	public void setScreenCapture(Image screenCapture)
	{
		this.screenCapture = screenCapture;
	}
	
	public Highscore getHighscore(){
		return highscore;
	}
}
