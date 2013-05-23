package model.entities.underwater;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Camera;
import model.User;
import model.entities.HostileEntity;

public class HarpoonDiver extends HostileEntity
{

	private static final int height = 256;
	private static final int width = 256;
	private double valueX;
	private double startX = -width;
	private double startY = 100;
	private boolean directionReversed = false;

	public HarpoonDiver(List<User> users)
	{
		// TODO: implement images.
		super(new Rectangle2D.Double(0, 0, width, height), 100, users);
		
		ArrayList<Image> images = new ArrayList<Image>();
		images.add(Toolkit.getDefaultToolkit().getImage("./images/underwater/harpoonDiver.png"));
		setImages(images);
		
		setVelocity(new Point2D.Double(0, 0));
		setY(80);
		Random rand = new Random();
		switch (rand.nextInt(3))
		{
		case 0:
			setX(-width);
			break;
		case 1:
			setX(Camera.VIEW_WIDTH);
			startX = Camera.VIEW_WIDTH;
			break;
		case 2:
			setX(200);
			startX = 200;
			setY(-height);
			startY = -height;
			break;
		}
		initJump();
	}
	
	private void initJump()
	{
		if (!users.isEmpty())
		{
			Point2D userP = users.get(0).getMidpoint();
			
			directionReversed = (getBounds().getCenterX() > userP.getX());
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
//				if (getBounds().getCenterX() > userP.getX())
//					x = (3 - valueX) * 50 / 3 + startX;
				double y = valueY * 100 / 9 + startY;

				if (y >= Camera.VIEW_HEIGHT - height)
				{
					initJump();
					startX = getBounds().getX() - valueX * 50/3;
					startY = 130;
				}
				
				setX(x);
				setY(y);
			}
		}
	}
}
