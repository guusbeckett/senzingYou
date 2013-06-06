package model.levels.sky;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.Random;

import model.Camera;
import model.entities.Entity;

public class Cloud extends Entity
{
	private double baseY;

	public Cloud()
	{
		super();
		baseY = Math.random() * (Camera.VIEW_HEIGHT - getDimensions().getHeight() * 10) + getDimensions().getHeight() - 30;
		Random r = new Random();
		if (r.nextBoolean())
		{
			position.setLocation(-getDimensions().getWidth(), baseY);
			velocity = new Point2D.Double(Math.random() * 0.2 + 0.01, 0.0);
		} else
		{
			position.setLocation(Camera.VIEW_WIDTH, baseY);
			velocity = new Point2D.Double(-Math.random() * 0.2 - 0.01, 0.0);
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
		return new Dimension(150, 130);
	}

	@Override
	public String[] getImageNames()
	{
		return new String[] { "sky/cloud.png" };
	}

	@Override
	public void update(double time)
	{
		super.update(time);
		position.setLocation(position.getX() + velocity.getX() * time / 10, baseY);
	}

	@Override
	public String getHitSoundName()
	{
		return null;
	}
}
