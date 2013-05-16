package model;

import org.OpenNI.Point3D;

public class Hand
{
	private int id;
	
	public Hand (int id)
	{
		this.id = id;
	}
	
	public int getId()
	{
		return id;
	}
	
	public Point3D getPosition()
	{
		return null;
	}
	
}
