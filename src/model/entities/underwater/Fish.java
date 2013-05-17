package model.entities.underwater;

import java.awt.geom.Rectangle2D;

import model.CameraData;
import model.entities.Entity;

public class Fish extends Entity
{
	public Fish()
	{
		super(new Rectangle2D.Double(0, 0, 60, 40), null);
	}

	@Override
	public void update(double time)
	{
		setX(getBounds().getX() + 0.01 * time);
		setY(Math.sin(getBounds().getX() / CameraData.VIEW_WIDTH * 2 * Math.PI) * 30);
	}
}
