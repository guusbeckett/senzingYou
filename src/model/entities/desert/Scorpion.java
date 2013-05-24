package model.entities.desert;

import java.awt.Image;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;

import model.User;
import model.entities.HostileEntity;

public class Scorpion extends HostileEntity
{
	public Scorpion(List<User> users)
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

	@Override
	public AudioInputStream getSound() throws UnsupportedAudioFileException, IOException
	{
		return null;
	}
	
	@Override
	public AudioInputStream getHitSound() throws UnsupportedAudioFileException, IOException
	{
		return null;
	}
	
}
