package model.entities.sky;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.List;

import model.entities.HostileEntity;

public class Bird extends HostileEntity
{

	public Bird(Rectangle2D bounds, List<BufferedImage> images, int reward)
	{
		super(bounds, images, reward);
	}
	

}
