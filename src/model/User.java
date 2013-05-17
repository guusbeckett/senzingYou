package model;

import java.awt.geom.Point2D;

import org.OpenNI.SceneMetaData;
import org.OpenNI.UserGenerator;

public class User
{
	private int id;
	private UserGenerator userGenerator;

	public User(int id, UserGenerator userGenerator)
	{
		this.id = id;
		this.userGenerator = userGenerator;
	}

	public int getId()
	{
		return id;
	}
	
	public SceneMetaData getUserPixels()
	{
		return userGenerator.getUserPixels(id);
	}
	
	public Point2D getMidpoint(){
		return null;
	}

}
