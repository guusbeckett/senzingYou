package model.entities.cave;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import sun.applet.Main;

import model.Camera;
import model.User;
import model.entities.HostileEntity;

public class Rock extends HostileEntity
{
	private int size;
	public Rock(List<User> users)
	{
		super(users);
		position.setLocation((Math.random() * (Camera.VIEW_WIDTH - 1) + 1), -30);
		size = (int) (Math.random() * (80 - 50) + 50);
	}

	@Override
	public Point2D getRotationPoint()
	{
		return new Point2D.Double(getDimensions().getWidth()/2,getDimensions().getHeight()/2);
	}

	@Override
	public Dimension2D getDimensions()
	{
		return new Dimension(size, size);
	}

	@Override
	public List<Image> getImages()
	{
		ArrayList<Image> images = new ArrayList<Image>();
		
		images.add(Toolkit.getDefaultToolkit().getImage("./images/cave/stone.png"));
		
		return images;
	}

	@Override
	public void update(double time)
	{
		super.update(time);	
		position.setLocation(position.getX(), position.getY() + 0.15 * time);
	}

	@Override
	public int getReward()
	{
		return -50;
	}
	
	@Override
	public AudioInputStream getSound() throws UnsupportedAudioFileException, IOException
	{
		return AudioSystem.getAudioInputStream(Main.class.getResourceAsStream("./audio/cave/rock.wav"));
	}
	
	@Override
	public AudioInputStream getHitSound() throws UnsupportedAudioFileException, IOException
	{
		return AudioSystem.getAudioInputStream(Main.class.getResourceAsStream("./audio/cave/rock.wav"));
	}

}
