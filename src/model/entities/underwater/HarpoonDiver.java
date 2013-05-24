package model.entities.underwater;

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

public class HarpoonDiver extends HostileEntity
{
	private double startX;
	private double startY = 80;

	private double jumpX, jumpY;
	private final Point2D gravity = new Point2D.Double(0, 0.4);

	public HarpoonDiver(List<User> users)
	{
		super(users);
		Random rand = new Random();
		startX = rand.nextInt(Camera.VIEW_WIDTH);
		startY = -getDimensions().getHeight();
		jumpX = 1;
		position.setLocation(startX, startY);
		velocity = new Point2D.Double(0, 0);
	}

	private void initJump()
	{
		if (!users.isEmpty())
		{
			Random rd = new Random();
			Point2D userP = users.get(rd.nextInt(users.size())).getMidpoint();
			if (position.getX() < userP.getX())
				jumpX = 5;
			else
				jumpX = -5;
			jumpY = -30;
		}
	}

	public void update(double time)
	{
		if (!users.isEmpty())
		{
			Point2D userP = users.get(0).getMidpoint();
			if (userP != null)
			{
				jumpY += time / 30 + gravity.getY() * time / 30;
				position = new Point2D.Double(position.getX() + jumpX * time
						/ 30, position.getY() + jumpY);
				if (position.getY() >= Camera.VIEW_HEIGHT - 80)
					initJump();
			}
		}
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
		return new Dimension(60, 60);
	}

	@Override
	public List<Image> getImages()
	{
		ArrayList<Image> images = new ArrayList<Image>();

		images.add(Toolkit.getDefaultToolkit().getImage(
				"./images/underwater/harpoonDiver.png"));

		return images;
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
