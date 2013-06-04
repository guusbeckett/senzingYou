package model.levels.desert;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Random;

import model.Camera;
import model.User;
import model.entities.HostileEntity;

public class Scorpion extends HostileEntity
{
	private double baseY;

	public Scorpion(List<User> users)
	{
		super(users);
		baseY = Math.random()
				* (Camera.VIEW_HEIGHT / 2 - getDimensions().getHeight() * 4)
				+ getDimensions().getHeight() + Camera.VIEW_HEIGHT / 2;
		Random r = new Random();
		if (r.nextBoolean())
		{
			position.setLocation(-getDimensions().getWidth(), baseY);
			velocity = new Point2D.Double(Math.random() * 0.4 + 0.01, 0.0);
		} else
		{
			position.setLocation(Camera.VIEW_WIDTH, baseY);
			velocity = new Point2D.Double(Math.random() * -0.4 + 0.01, 0.0);
		}
	}

	@Override
	public int getReward()
	{
		return 100;
	}

	@Override
	public Point2D getRotationPoint()
	{
		return new Point2D.Double(0, 0);
	}

	@Override
	public Dimension2D getDimensions()
	{
		return new Dimension(90, 90);
	}
	
	@Override
	public String[] getImageNames()
	{
		return new String[] { "desert/scorpion.png" };
	}

	@Override
	public void update(double time)
	{
		super.update(time);
		position.setLocation(
				position.getX() + velocity.getX() * time / 30,
				baseY
						+ Math.sin(position.getX() / Camera.VIEW_WIDTH * 2
								* Math.PI) * getDimensions().getHeight());

	}

	@Override
	public String getHitSoundName()
	{
		return null;
	}
}
