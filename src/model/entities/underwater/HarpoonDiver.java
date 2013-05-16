package model.entities.underwater;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.List;

import model.entities.HostileEntity;

public class HarpoonDiver extends HostileEntity
{

	public HarpoonDiver(Rectangle2D bounds, List<BufferedImage> images, int reward)
	{
		super(bounds, images, reward);
	}
}
