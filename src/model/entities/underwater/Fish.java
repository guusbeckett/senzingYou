package model.entities.underwater;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import model.Camera;
import model.entities.Entity;

public class Fish extends Entity
{
	private double baseY;
	private List<Image> images;
	
	public Fish()
	{
		super();
		
		baseY = Math.random() * (Camera.VIEW_HEIGHT - getDimensions().getHeight() * 4) + getDimensions().getHeight();
		position.setLocation(0, baseY);
		velocity = new Point2D.Double(Math.random() * 0.2 + 0.01, 0.0);
		
		images = new ArrayList<Image>();
		
		if (Math.random() <= 0.5)
			images.add(Toolkit.getDefaultToolkit().getImage("./images/underwater/blueFish.png"));
		else
			images.add(Toolkit.getDefaultToolkit().getImage("./images/underwater/orangeFish.png"));
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
		return images;
	}
	
	public File getSound()
	{
		File file = new File("./audio/underwater/bubbles.wav");
		return file; 
	}
	
	public File getHitSound()
	{
//		File file = new File("./audio/underwater/");
//		return file;
		return null; 
	}
}
