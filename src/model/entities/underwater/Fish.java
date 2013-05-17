package model.entities.underwater;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import model.CameraData;
import model.entities.Entity;

public class Fish extends Entity
{
	private double baseY;
	private final static double WIDTH = 60;
	private final static double HEIGHT = 40;
	
	public Fish()
	{
		super(new Rectangle2D.Double(-WIDTH, 0, WIDTH, HEIGHT), null);
		
		setY((baseY = Math.random() * (CameraData.VIEW_HEIGHT - HEIGHT * 4) + HEIGHT * 2));
		setVelocity(new Point2D.Double(Math.random() * 0.2 + 0.01, 0));
	}

	@Override
	public void update(double time)
	{
		setX(getBounds().getX() + getVelocity().getX() * time);
		setY(baseY + Math.sin(getBounds().getX() / CameraData.VIEW_WIDTH * 2 * Math.PI) * HEIGHT);
	}
}
