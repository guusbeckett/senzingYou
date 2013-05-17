package model.entities.underwater;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import model.CameraData;
import model.entities.Entity;

public class Plant extends Entity
{

	private final static double  WIDTH = 30;
	private final static double  HEIGTH = 150;
	private double degrees;
	private int counter;
	
	public Plant()
	{
		super(new Rectangle2D.Double(0, 0, WIDTH, HEIGTH), null);
		setX((Math.random() * (CameraData.VIEW_WIDTH - 20) + 1));
		setY(CameraData.VIEW_HEIGHT);
		setRotationPoint(new Point2D.Double(WIDTH/2,0));
	}
	
	public void update(double time)
	{
		counter++;
		
		degrees=Math.sin(Math.toRadians(counter));
		degrees/=2;
		degrees+=3;
		setRotation(degrees);
		System.out.println("counter geeft: "+counter);
	}

}