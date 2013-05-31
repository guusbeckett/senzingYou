package model.levels.rainforest;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

import model.entities.Entity;

public class Tree extends Entity
{
	public Tree()
	{
		super();
		// TODO Auto-generated constructor stub
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
	public String[] getImageNames()
	{
		return new String[] {  };
	}

	@Override
	public void update(double time)
	{
		super.update(time);
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String getHitSoundName()
	{
		return null;
	}
}
