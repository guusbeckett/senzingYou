package model.entities;

import java.awt.Image;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import model.MediaProvider;

public abstract class Entity
{
	private int index;
	protected Point2D position;
	protected Point2D velocity;
	protected double rotation;
	private int timer;

	public Entity()
	{
		position = new Point2D.Double();
		velocity = new Point2D.Double();
	}

	public double getRotation()
	{
		return rotation;
	}

	public Point2D getPosition()
	{
		return position;
	}

	public Image getImage()
	{
		String[] images = getImageNames();

		if (images.length <= 0)
			return null;

		return MediaProvider.getInstance().getImage(images[index]);
	}

	public Rectangle2D getBounds()
	{
		return new Rectangle2D.Double(position.getX(), position.getY(), getDimensions().getWidth(), getDimensions().getHeight());
	}

	public void update(double time)
	{
		String[] images = getImageNames();
		timer += time;
		if (timer >= 10)
		{
			timer = 0;
			if (images.length > 0)
			{
				if (index >= (images.length - 1))
					index = 0;
				else
					index++;
			}
		}
	}

	public boolean isMirrored()
	{
		return (velocity.getX() < 0);
	}
	
	public void playHitSound()
	{
		String name = getHitSoundName();
		
		if (name != null)
		{
			AudioInputStream audio = MediaProvider.getInstance().getSound(name);
			
			try
			{
				Clip clip = AudioSystem.getClip();
				clip.open(audio);
				clip.start();
			} catch (LineUnavailableException e)
			{ }
			catch (IOException e)
			{ }
		}
	}

	public abstract Point2D getRotationPoint();

	public abstract Dimension2D getDimensions();

	public abstract String[] getImageNames();

	public abstract String getHitSoundName();
}