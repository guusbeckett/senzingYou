package model.levels.rainforest;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.List;

import model.Camera;
import model.User;
import model.entities.HostileEntity;

public class Banana extends HostileEntity
{
	private int size;
	public Banana(List<User> users)
	{
		super(users);
		position.setLocation((Math.random() * (Camera.VIEW_WIDTH - 1) + 1), -30);
		size = (int) (Math.random() * (80 - 50) + 50);
	}

	@Override
	public int getReward()
	{
		// TODO Auto-generated method stub
		return -50;
	}

	@Override
	public Dimension2D getDimensions()
	{
		return new Dimension(size, size);
	}

	@Override
	public Point2D getRotationPoint()
	{
		return new Point2D.Double(getDimensions().getWidth() / 2,
				getDimensions().getHeight() / 2);
	}

	
	@Override
	public String[] getImageNames()
	{
		return new String[] { "rainforest/banana.png" };
	}


	@Override
	public void update(double time)
	{
		super.update(time);
		position.setLocation(position.getX(), position.getY() + 0.15 * time);
		//System.out.println("Banaan aangemaakt op: "+position.getX()+", "+position.getY()+" time: "+time);
		
	}

	@Override
	public String getHitSoundName()
	{
		return "rainforest/banana.wav";
	}
}
