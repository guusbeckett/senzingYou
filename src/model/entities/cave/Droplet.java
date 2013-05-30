package model.entities.cave;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import model.Camera;
import model.entities.Entity;
import sun.applet.Main;

public class Droplet extends Entity
{
	public Droplet()
	{
		super();
		position.setLocation((Math.random() * (Camera.VIEW_WIDTH - 1) + 1), -30);
	}

	@Override
	public Point2D getRotationPoint()
	{
		return new Point2D.Double(getDimensions().getWidth()/2,getDimensions().getHeight()/2);
	}

	@Override
	public Dimension2D getDimensions()
	{
		return new Dimension(30, 30);
	}

	@Override
	public List<Image> getImages()
	{
		ArrayList<Image> images = new ArrayList<Image>();
		
		images.add(Toolkit.getDefaultToolkit().getImage("./images/cave/droplet.png"));
		
		return images;
	}

	@Override
	public void update(double time)
	{
		super.update(time);	
		position.setLocation(position.getX(), position.getY()+ 0.25 * time);
		playSound(time);
	}
	
	@Override
	public AudioInputStream getSound() throws UnsupportedAudioFileException, IOException
	{
		return AudioSystem.getAudioInputStream(getClass().getResource("./audio/cave/droplet.wav"));
	}
	
	@Override
	public AudioInputStream getHitSound() throws UnsupportedAudioFileException, IOException
	{
		return AudioSystem.getAudioInputStream(Main.class.getResourceAsStream("./audio/cave/droplet.wav"));
	}
	
	public void playSound(double time)
	{
		if(position.getY() >= Camera.VIEW_HEIGHT)
		{
			try
			{
				AudioInputStream in = getSound();
				AudioFormat format = in.getFormat();
				DataLine.Info info = new DataLine.Info(Clip.class, format);
				Clip clip = (Clip) AudioSystem.getLine(info);
				clip.open(in);
				FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				volumeControl.setValue((float) (time/(1000/30)));
				clip.start();
				while(clip.isActive())
				{}
				clip.close();
			} catch (UnsupportedAudioFileException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LineUnavailableException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}