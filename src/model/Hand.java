package model;

import org.OpenNI.Point3D;

public class Hand
{
	private int id;
	private Point3D position;
	
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
		return position;
	}
	
	public void setPosition(Point3D position)
	{
		this.position = position;
	}
	
}
