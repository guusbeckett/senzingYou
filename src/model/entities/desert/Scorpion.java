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
import model.User;
import model.entities.HostileEntity;

public class Scorpion extends HostileEntity
{
	private List<Image> images;
	private double baseY;

	public Scorpion(List<User> users)
	{
		super(users);
		baseY = Math.random()
				* (Camera.VIEW_HEIGHT / 2 - getDimensions().getHeight() * 4)
				+ getDimensions().getHeight() + Camera.VIEW_HEIGHT / 2;
		Random r = new Random();
		if (r.nextBoolean())
		{
			position.setLocation(0, baseY);
			velocity = new Point2D.Double(Math.random() * 0.2 + 0.01, 0.0);
		} else
		{
			position.setLocation(Camera.VIEW_WIDTH, baseY);
			velocity = new Point2D.Double(Math.random() * -0.2 + 0.01, 0.0);
		}

		images = new ArrayList<Image>();
		images.add(Toolkit.getDefaultToolkit().getImage(
				"./images/desert/scorpion.png"));
	}

	@Override
	public int getReward()
	{
		return 100;
	}

	@Override
	public Point2D getRotationPoint()
	{
		return new Point2D.Double(0, 0);
	}

	@Override
	public Dimension2D getDimensions()
	{
		return new Dimension(90, 90);
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
		position.setLocation(
				position.getX() + velocity.getX() * time / 30,
				baseY
						+ Math.sin(position.getX() / Camera.VIEW_WIDTH * 2
								* Math.PI) * getDimensions().getHeight());

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
