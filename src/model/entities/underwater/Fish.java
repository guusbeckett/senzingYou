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
		Rectangle2D bounds = getBounds();
		
		double x = bounds.getX() + 10;
		double y = Math.sin(x / CameraData.VIEW_WIDTH * 2 * Math.PI);
		
		bounds.setRect(x, y, bounds.getWidth(), bounds.getHeight());
	}
}
