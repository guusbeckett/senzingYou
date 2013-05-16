package model.entities;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.List;

public class Entity
{
	protected Point2D velocity;
	protected double rotation;
	private Rectangle2D bounds;
	private int index;
	private List<BufferedImage> images;

	public Entity(Rectangle2D bounds, List<BufferedImage> images)
	{
		this.bounds = bounds;
		this.images = images;
	}	
	
	public double getRotation()
	{
		return rotation;
	}

	public void setRotation(double rotation)
	{
		this.rotation = rotation;
	}
	
	public Rectangle2D getBounds()
	{
		return bounds;
	}

	public void setBounds(Rectangle2D bounds)
	{
		this.bounds = bounds;
	}

	public BufferedImage getImage()
	{
		return images.get(index);
	}
	
	public Point2D getVelocity()
	{
		return velocity;
	}

	public void setVelocity(Point2D velocity)
	{
		this.velocity = velocity;
	}

	public void update(double time)
	{
		
	}
}