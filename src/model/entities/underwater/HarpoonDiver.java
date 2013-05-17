package model.entities.underwater;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Random;

import model.CameraData;
import model.User;
import model.entities.HostileEntity;

public class HarpoonDiver extends HostileEntity
{

	private static final int height = 240;
	private static final int width = 80;
	private double valueX;
	private double startX = -width;
	private double startY = 100;

	public HarpoonDiver(List<User> users)
	{
		// TODO: implement images.
		super(new Rectangle2D.Double(0, 0, width, height), null, 100, users);
		setVelocity(new Point2D.Double(0, 0));
		setY(80);
		Random rand = new Random();
		switch (rand.nextInt(3))
		{
		case 0:
			setX(-width);
			break;
		case 1:
			setX(CameraData.VIEW_WIDTH);
			startX = CameraData.VIEW_WIDTH;
			break;
		case 2:
			setX(200);
			startX = 200;
			setY(-height);
			startY = -height;
			break;
		}
		valueX = -3;
	}

	public void update(double time)
	{
		if (!users.isEmpty())
		{
			Point2D userP = users.get(0).getMidpoint();
			if (userP != null)
			{
				valueX += time * 0.001;
				double valueY = valueX * valueX;

				double x = (valueX + 3) * 50 / 3 + startX;
				if (getBounds().getCenterX() > userP.getX())
					x = (3 - valueX) * 50 / 3 + startX;
				double y = valueY * 100 / 9 + startY;

				if (getBounds().getY() >= CameraData.VIEW_HEIGHT - height)
				{
					valueX = -3;
					startX = getBounds().getX();
					startY = 100;
				}
				setX(x);
				setY(y);
			}
		}
	}
}
