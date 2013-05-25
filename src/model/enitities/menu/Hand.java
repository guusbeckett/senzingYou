package model.enitities.menu;


import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;

import model.entities.Entity;

public class Hand extends Entity
{
	public Hand()
	{
		super();
	}
	
	public void setPosition(Point2D p2){
		position = p2;
	}
	
	@Override
	public Point2D getRotationPoint()
	{
		// TODO Auto-generated method stub
		return new Point2D.Double(getDimensions().getWidth()/2,getDimensions().getHeight()/2);
	}
	@Override
	public Dimension2D getDimensions()
	{
		// TODO Auto-generated method stub
		return new Dimension(25, 25);
	}
	@Override
	public List<Image> getImages()
	{
		// TODO Auto-generated method stub
		ArrayList<Image> images = new ArrayList<Image>();
		images.add(Toolkit.getDefaultToolkit().getImage("./images/menu/hand.png"));
		return images;
		
	}
	@Override
	public AudioInputStream getSound() throws UnsupportedAudioFileException,
			IOException
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public AudioInputStream getHitSound() throws UnsupportedAudioFileException,
			IOException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
