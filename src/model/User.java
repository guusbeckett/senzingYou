package model;

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

}
