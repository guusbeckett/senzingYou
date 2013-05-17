package model.entities.underwater;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import model.entities.Entity;

public class Plant extends Entity
{

	private static int with = 30;
	private static int height = 150;

	private double degrees;
	
	AffineTransform identity = new AffineTransform();
	public Plant()
	{
		super(new Rectangle2D.Double(0, 0, with, height), null);
		setX((Math.random() * (640 - 1) + 1));
		setY(480);
		setRotationPoint(new Point2D.Double(with/2,0));
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