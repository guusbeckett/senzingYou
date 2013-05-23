package model.entities.underwater;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import model.Camera;
import model.entities.Entity;

public class Fish extends Entity
{
	private double baseY;
	private final static double WIDTH = 60;
	private final static double HEIGHT = 40;
	
	public Fish()
	{
		super(new Rectangle2D.Double(-WIDTH, 0, WIDTH, HEIGHT));
		
		setY((baseY = Math.random() * (Camera.VIEW_HEIGHT - HEIGHT * 4) + HEIGHT));
		setVelocity(new Point2D.Double(Math.random() * 0.2 + 0.01, 0));
		
		ArrayList<Image> images = new ArrayList<Image>();
		images.add(Toolkit.getDefaultToolkit().getImage("./images/underwater/blueFish.png"));
		images.add(Toolkit.getDefaultToolkit().getImage("./images/underwater/orangeFish.png"));
		setImages(images);
	}

	@Override
	public void update(double time)
	{
		setX(getBounds().getX() + getVelocity().getX() * time);
		setY(baseY + Math.sin(getBounds().getX() / Camera.VIEW_WIDTH * 2 * Math.PI) * HEIGHT);
	}
}
