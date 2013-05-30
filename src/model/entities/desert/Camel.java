package model.entities.desert;

import java.awt.Image;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;

import model.entities.Entity;

public class Camel extends Entity
{
	public Camel()
	{
		super();
		// TODO Auto-generated constructor stub
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
