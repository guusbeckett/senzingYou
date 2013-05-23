package model.entities.underwater;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import model.Camera;
import model.entities.Entity;

public class Plant extends Entity
{

	private final static double  WIDTH = 30;
	private final static double  HEIGTH = 150;
	private double degrees;
	private int counter;
	private double waveSpeed;
	
	public Plant()
	{
		super(new Rectangle2D.Double(0, 0, WIDTH, HEIGTH));
		
		ArrayList<Image> images = new ArrayList<Image>();
		images.add(Toolkit.getDefaultToolkit().getImage("./images/underwater/seaweed.png"));
		setImages(images);
		
		setX(Math.random() * (Camera.VIEW_WIDTH - 20) + 1);
		setY(Camera.VIEW_HEIGHT);
		setRotationPoint(new Point2D.Double(WIDTH/2,0));
		waveSpeed=(Math.random() * (1.1 - 1) + 1);
	}
	
	public void update(double time)
	{
		counter++;
		degrees=Math.sin(Math.toRadians(counter*waveSpeed));
		degrees/=2;
		degrees+=3;
		setRotation(degrees);
	}

}