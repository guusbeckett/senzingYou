package model.entities.underwater;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import model.Camera;
import model.entities.Entity;

public class Fish extends Entity
{
	private double baseY;
	
	public Fish()
	{
		super();
		
		baseY = Math.random() * (Camera.VIEW_HEIGHT - getDimensions().getHeight() * 4) + getDimensions().getHeight();
		position.setLocation(0, baseY);
		velocity = new Point2D.Double(Math.random() * 0.2 + 0.01, 0.0);
	}

	@Override
	public void update(double time)
	{
		super.update(time);
		position.setLocation(position.getX() + velocity.getX() * time, baseY + Math.sin(position.getX() / Camera.VIEW_WIDTH * 2 * Math.PI) * getDimensions().getHeight());
	}

	@Override
	public Point2D getRotationPoint()
	{
		return new Point2D.Double(0, 0);
	}

	@Override
	public Dimension2D getDimensions()
	{
		return new Dimension(60, 40);
	}

	@Override
	public List<Image> getImages()
	{
		List<Image> images = new ArrayList<Image>();
		
		images.add(Toolkit.getDefaultToolkit().getImage("./images/underwater/blueFish.png"));
		images.add(Toolkit.getDefaultToolkit().getImage("./images/underwater/orangeFish.png"));
		
		return images;
	}
}
