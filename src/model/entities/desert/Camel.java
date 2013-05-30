package model.entities.desert;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;

import model.Camera;
import model.entities.Entity;

public class Camel extends Entity
{
	private List<Image> images;

	public Camel()
	{
		super();
		Random r = new Random();
		if (r.nextBoolean())
		{
			position.setLocation(0, 300);
			velocity = new Point2D.Double(Math.random() * 0.2 + 0.01, 0.0);
		} else
		{
			position.setLocation(Camera.VIEW_WIDTH, 300);
			velocity = new Point2D.Double(Math.random() * -0.2 + 0.01, 0.0);
		}

		images = new ArrayList<Image>();
		images.add(Toolkit.getDefaultToolkit().getImage(
				"./images/desert/camel.png"));
	}

	@Override
	public Point2D getRotationPoint()
	{
		return new Point2D.Double(0, 0);
	}

	@Override
	public Dimension2D getDimensions()
	{
		return new Dimension(150, 150);
	}

	@Override
	public List<Image> getImages()
	{
		return images;
	}

	@Override
	public void update(double time)
	{
		super.update(time);
		position.setLocation(position.getX() + velocity.getX() * time / 30, 300); //TODO test if "/30" is good enough
	}

	@Override
	public AudioInputStream getSound() throws UnsupportedAudioFileException,
			IOException
	{
		return null;
	}

	@Override
	public AudioInputStream getHitSound() throws UnsupportedAudioFileException,
			IOException
	{
		return null;
	}

}
