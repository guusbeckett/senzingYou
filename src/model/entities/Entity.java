package model.entities;

import java.awt.Image;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.List;

public abstract class Entity
{
	private int index;
	protected Point2D position;
	protected Point2D velocity;
	protected double rotation;
	
	public Entity()
	{
		position = new Point2D.Double();
		velocity = new Point2D.Double();
	}
	
	public double getRotation()
	{
		return rotation;
	}
	
	public Point2D getPosition()
	{
		return position;
	}

	public Image getImage()
	{
		List<Image> images = getImages();
		
		if (images == null)
			return null;
		
		return images.get(index);
	}
	
	public Rectangle2D getBounds()
	{
		return new Rectangle2D.Double(position.getX(), position.getY(), getDimensions().getWidth(), getDimensions().getHeight());
	}
	
	public void update(double time)
	{
		// TODO animate
	}
	
	public abstract Point2D getRotationPoint();
	public abstract Dimension2D getDimensions();
	public abstract List<Image> getImages();
	public abstract File getSound();
	public abstract File getHitSound();
}