package model.entities.cave;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import model.Camera;
import model.entities.Entity;

public class Droplet extends Entity
{
	int xLocation = (int) (Math.random() * (Camera.VIEW_WIDTH - 1) + 1);
	int yLocation; 
	public Droplet()
	{
		super();
		position.setLocation(xLocation, 0);	
	}

	@Override
	public Point2D getRotationPoint()
	{
		return new Point2D.Double(getDimensions().getWidth()/2,getDimensions().getHeight()/2);
	}

	@Override
	public Dimension2D getDimensions()
	{
		return new Dimension(30, 30);
	}

	@Override
	public List<Image> getImages()
	{
		ArrayList<Image> images = new ArrayList<Image>();
		
		images.add(Toolkit.getDefaultToolkit().getImage("./images/cave/droplet.png"));
		
		return images;
	}

	@Override
	public void update(double time)
	{
		super.update(time);	
		yLocation += (int) (1/2 * 10 * Math.pow(time, 2));
		position.setLocation(xLocation, yLocation);
	}

}
