package model.entities.desert;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.List;

import model.entities.HostileEntity;

public class Scorpion extends HostileEntity
{

	public Scorpion(Rectangle2D bounds, List<BufferedImage> images, int reward)
	{
		super(bounds, images, reward);
	}

}
