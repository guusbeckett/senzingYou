package model.entities.underwater;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import model.CameraData;
import model.entities.HostileEntity;

public class HarpoonDiver extends HostileEntity
{

	private static final int height = 300;
	private static final int width = 80;

	public HarpoonDiver()
	{
		// TODO: implement images.
		super(new Rectangle2D.Double(0, 0, width, height), null, 100);

		int x = 0, y = 0;
		Random rand = new Random();
		switch (rand.nextInt(3))
		{
		case 0:
			x = -width;
			y = 80;
			setVelocity(new Point2D.Double(10, 0));
			break;
		case 1:
			x = CameraData.VIEW_WIDTH;
			y = 80;
			setVelocity(new Point2D.Double(-10, 0));
			break;
		case 2:
			x = 200;
			y = -height;
			setVelocity(new Point2D.Double(0, 0));
			break;
		}
		setBounds(new Rectangle2D.Double(x, y, width, height));
	}

	public void update(double time)
	{

	}
}
