package model.levels.desert;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.Random;

import model.Camera;
import model.entities.Entity;

public class Camel extends Entity
{
	public Camel()
	{
		super();
		Random r = new Random();
		if (r.nextBoolean())
		{
			position.setLocation(-getDimensions().getWidth(), 300);
			velocity = new Point2D.Double(Math.random() * 0.125 + 0.01, 0.0);
		} else
		{
			position.setLocation(Camera.VIEW_WIDTH, 300);
			velocity = new Point2D.Double(Math.random() * -0.125 + 0.01, 0.0);
		}
	}

	@Override
	public Point2D getRotationPoint()
	{
		return new Point2D.Double(0, 0);
	}

	@Override
	public Dimension2D getDimensions()
	{
		return new Dimension(150, 150);
	}

	@Override
	public String[] getImageNames()
	{
		return new String[] { "desert/camel/camel1.png",
				"desert/camel/camel1.png", "desert/camel/camel2.png",
				"desert/camel/camel2.png", "desert/camel/camel3.png",
				"desert/camel/camel3.png", "desert/camel/camel4.png",
				"desert/camel/camel4.png", "desert/camel/camel5.png",
				"desert/camel/camel5.png", "desert/camel/camel6.png",
				"desert/camel/camel6.png" };
	}

	@Override
	public void update(double time)
	{
		super.update(time);
		position.setLocation(position.getX() + velocity.getX() * time / 10, 300); 
	}

	@Override
	public String getHitSoundName()
	{
		return null;
	}
	
	@Override
	public boolean isMirrored()
	{
		return false;
	}
}
