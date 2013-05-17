package model.entities;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.List;

public class Entity
{
	protected Point2D velocity;
	protected double rotation;
	protected Point2D rotationPoint;
	private Rectangle2D bounds;
	private int index;
	private List<BufferedImage> images;

	public Entity(Rectangle2D bounds, List<BufferedImage> images)
	{
		this.bounds = bounds;
		this.images = images;
		this.rotationPoint = new Point2D.Double(0, 0);
	}	
	
	public Point2D getRotationPoint()
	{
		return rotationPoint;
	}

	public void setRotationPoint(Point2D rotationPoint)
	{
		this.rotationPoint = rotationPoint;
	}
	
	public double getRotation()
	{
		return rotation;
	}

	public void setRotation(double rotation)
	{
		this.rotation = rotation;
	}
	
	public void setX(double x)
	{
		bounds.setRect(x, bounds.getY(), bounds.getWidth(), bounds.getHeight());
	}
	
	public void setY(double y)
	{
		bounds.setRect(bounds.getX(), y, bounds.getWidth(), bounds.getHeight());
	}
	
	public void setWidth(double width)
	{
		bounds.setRect(bounds.getX(), bounds.getY(), width, bounds.getHeight());
	}
	
	public void setHeight(double height)
	{
		bounds.setRect(bounds.getX(), bounds.getY(), bounds.getWidth(), height);
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