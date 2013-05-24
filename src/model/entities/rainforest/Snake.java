package model.entities.rainforest;

import java.awt.Image;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.List;

import model.User;
import model.entities.HostileEntity;

public class Snake extends HostileEntity
{
	public Snake(List<User> users)
	{
		super(users);
		// TODO Auto-generated method stub
	}

	@Override
	public int getReward()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Point2D getRotationPoint()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dimension2D getDimensions()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Image> getImages()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(double time)
	{
		super.update(time);
		// TODO Auto-generated method stub
		
	}
	
	public File getSound()
	{
//		File file = new File("./audio/rainforest/");
//		return file; 
		return null;
	}
	
	public File getHitSound()
	{
//		File file = new File("./audio/rainforest/");
//		return file;
		return null; 
	}
}
