package model.entities.cave;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.List;

import model.entities.HostileEntity;

public class Rock extends HostileEntity
{

	public Rock(Rectangle2D bounds, List<BufferedImage> images, int reward)
	{
		super(bounds, images, reward);
	}

}
