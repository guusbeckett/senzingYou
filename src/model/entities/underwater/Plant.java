package model.entities.underwater;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import model.entities.Entity;

public class Plant extends Entity
{

	private double degrees;
	
	AffineTransform identity = new AffineTransform();
	public Plant()
	{
		super(new Rectangle2D.Double(0, 0, 0, 0), null);
		setLocation();
	}
	
	public void setLocation()
	{
		setX((Math.random() * (640 - 1) + 1));
		setY(480);
	}
	
	public void update(double time)
	{
		if(time>=180)time-=180;
		degrees=Math.sin(Math.toRadians(time));
		degrees*=90;
		degrees-=45;
		setRotation(degrees);
	}

}