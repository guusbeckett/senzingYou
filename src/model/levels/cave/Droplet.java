package model.levels.cave;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

import model.Camera;
import model.entities.Entity;

public class Droplet extends Entity
{
	public Droplet()
	{
		super();
		position.setLocation((Math.random() * (Camera.VIEW_WIDTH - 1) + 1), -30);
	}

	@Override
	public Point2D getRotationPoint()
	{
		return new Point2D.Double(getDimensions().getWidth() / 2,
				getDimensions().getHeight() / 2);
	}

	@Override
	public Dimension2D getDimensions()
	{
		return new Dimension(30, 30);
	}

	@Override
	public String[] getImageNames()
	{
		return new String[] { "cave/droplet.png" };
	}

	@Override
	public void update(double time)
	{
		super.update(time);
		position.setLocation(position.getX(), position.getY() + 0.25 * time);
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