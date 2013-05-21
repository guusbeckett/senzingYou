package model.entities;

import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class Entity
{
	private Point2D velocity;
	private double rotation;
	private Point2D rotationPoint;
	private Rectangle2D bounds;
	private int index;
	private List<Image> images;

	public Entity(Rectangle2D bounds)
	{
		this.bounds = bounds;
		this.rotationPoint = new Point2D.Double(0, 0);
		this.images = new ArrayList<Image>();
		
	}	
	
	
	public void setImages(List<Image> images){
		this.images = images;
		if(!images.isEmpty()){
			this.index = (int)(Math.random() * images.size());
		}
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

	public Image getImage()
	{
		if(images.isEmpty()){
			return null;
		}
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