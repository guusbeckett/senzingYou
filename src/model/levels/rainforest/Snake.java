package model.levels.rainforest;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

import model.Camera;
import model.entities.Entity;

public class Snake extends Entity
{
	private double baseY;

	public Snake()
	{
		super();
		baseY = Math.random() * (Camera.VIEW_HEIGHT - getDimensions().getHeight() * 4) + getDimensions().getHeight();
		position.setLocation(-getDimensions().getWidth(), baseY);
		velocity = new Point2D.Double(Math.random() * 0.2 + 0.01, Camera.VIEW_WIDTH);
	}

	@Override
	public String[] getImageNames()
	{
		// TODO Auto-generated method stub
		return new String[] { "rainforest/snake.png" };
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
	public void update(double time)
	{
		super.update(time);
		position.setLocation(position.getX() + velocity.getX() * time, baseY + Math.sin(position.getX() / Camera.VIEW_WIDTH * 2 * Math.PI) * getDimensions().getHeight());
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String getHitSoundName()
	{
		return null;
	}
}
