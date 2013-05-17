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

	private static final int height = 300;
	private static final int width = 80;
	private boolean jumpingUp = false;

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
			break;
		case 2:
			setX(200);
			setY(-height);
			break;
		}
	}

	public void update(double time)
	{
		Point2D userP = users.get(0).getMidpoint();

		if (getBounds().getMaxY() <= CameraData.VIEW_HEIGHT)
		{
			jumpingUp = true;
		}

		if (jumpingUp)
		{
			setVelocity(new Point2D.Double(getVelocity().getX(), 50
					- getVelocity().getY() / 5 * 0.001 * time));
			if (getVelocity().getY() < 1)
				jumpingUp = false;
		} else
		{
			setVelocity(new Point2D.Double(getVelocity().getX(), -1
					- getVelocity().getY() / 5 * 0.001 * time));
		}

		setY(getVelocity().getY());
		if(userP.getX() > getBounds().getCenterX())
			setX(getBounds().getX()+ 0.001*time);
	}
}
