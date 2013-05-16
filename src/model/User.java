package model;

import org.OpenNI.SceneMetaData;

public class User
{
	private int id;
	private SceneMetaData userPixels;

	public User(int id, SceneMetaData userPixels)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}
	
	public SceneMetaData getUserPixels()
	{
		return userPixels;
	}

}
