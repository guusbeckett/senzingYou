package model.levels.desert;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.Random;

import model.Camera;
import model.entities.Entity;

public class Vulture extends Entity
{
	private double baseY;

	public Vulture()
	{
		super();
		baseY = Math.random()
				* (Camera.VIEW_HEIGHT / 3 - getDimensions().getHeight() * 4)
				+ getDimensions().getHeight() - 10;
		Random r = new Random();
		if (r.nextBoolean())
		{
			position.setLocation(-getDimensions().getWidth(), baseY);
			velocity = new Point2D.Double(Math.random() * 0.2 + 0.01, 0.0);
		} else
		{
			position.setLocation(Camera.VIEW_WIDTH, baseY);
			velocity = new Point2D.Double(Math.random() * -0.2 + 0.01, 0.0);
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
		return new Dimension(60, 50);
	}

	@Override
	public String[] getImageNames()
	{
		return new String[] { "desert/vulture.png" };
	}

	@Override
	public void update(double time)
	{
		super.update(time);
		position.setLocation(position.getX() + velocity.getX() * time, baseY
				+ Math.sin(position.getX() / Camera.VIEW_WIDTH * 2 * Math.PI)
				* getDimensions().getHeight());
	}

	@Override
	public String getHitSoundName()
	{
		return null;
	}
}
