package model.entities.underwater;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Camera;
import model.User;
import model.entities.HostileEntity;

public class HarpoonDiver extends HostileEntity
{
	private double valueX;
	private double startX;
	private double startY = 80;
	private boolean directionReversed = false;

	public HarpoonDiver(List<User> users)
	{
		super(users);
		Random rand = new Random();
		
		switch (rand.nextInt(3))
		{
		case 0:
			startX = -getDimensions().getWidth();
			startY = 80;
			break;
		case 1:
			startX = Camera.VIEW_WIDTH;
			break;
		case 2:
			startX = 200;
			startY = -getDimensions().getHeight();
			break;
		}
		
		position.setLocation(startX, startY);
		velocity = new Point2D.Double(0, 0);
		
		initJump();
	}
	
	private void initJump()
	{
		if (!users.isEmpty())
		{
			Point2D userP = users.get(0).getMidpoint();
			
			directionReversed = (position.getX() > userP.getX());
			//System.out.println(directionReversed);
			valueX = (directionReversed) ? 3 : -3;
		}
	}

	public void update(double time)
	{
		if (!users.isEmpty())
		{
			Point2D userP = users.get(0).getMidpoint();
			if (userP != null)
			{
				valueX += time * ((directionReversed) ? -0.001 : 0.001);
				double valueY = valueX * valueX;

				double x = valueX * 50 / 3 + startX;
				double y = valueY * 100 / 9 + startY;

				if (y >= Camera.VIEW_HEIGHT - getDimensions().getHeight())
				{
					initJump();
					startX = position.getX() - valueX * 50/3;
					startY = 130;
				}
				
				position.setLocation(x, y);
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
		return new Dimension(256, 256);
	}

	@Override
	public List<Image> getImages()
	{	
		ArrayList<Image> images = new ArrayList<Image>();
		
		images.add(Toolkit.getDefaultToolkit().getImage("./images/underwater/harpoonDiver.png"));
		
		return images;
	}
	
	public File getSound()
	{
//		File file = new File("./audio/underwater/");
//		return file; 
		return null;
	}
	
	public File getHitSound()
	{
//		File file = new File("./audio/underwater/");
//		return file;
		return null; 
	}
}
