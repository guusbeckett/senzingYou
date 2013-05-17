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
		if (!users.isEmpty())
		{
			Point2D userP = users.get(0).getMidpoint();
			if (userP != null)
			{
				if (getBounds().getMaxY() > CameraData.VIEW_HEIGHT)
				{
					jumpingUp = true;
				}

				if (jumpingUp)
				{
					setVelocity(new Point2D.Double(getVelocity().getX(), 30
							- getVelocity().getY() / 3 * 0.01 * time));
					if (getVelocity().getY() < 3)
						jumpingUp = false;
				} else
				{
					if (getVelocity().getY() > 0)
						setVelocity(new Point2D.Double(getVelocity().getX(), -1
								- getVelocity().getY() / 3 * 0.01 * time));
					else
						setVelocity(new Point2D.Double(getVelocity().getX(), -1
								+ getVelocity().getY() / 3 * 0.01 * time));
				}
				setY(getBounds().getY() + getVelocity().getY());
				if (getBounds().getMaxY() >= CameraData.VIEW_HEIGHT)
					setY(CameraData.VIEW_HEIGHT - height);
				if (userP.getX() > getBounds().getCenterX())
					setX(getBounds().getX() + 0.01 * time);
				else
					setX(getBounds().getX() - 0.01 * time);

			}
		}
	}
}
