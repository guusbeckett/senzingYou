package model.entities;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.List;

public class HostileEntity extends Entity
{
	private int reward;
	protected boolean alive;
	
	public HostileEntity(Rectangle2D bounds, List<BufferedImage> images, int reward)
	{
		super(bounds, images);
		this.reward = reward;
	}
	
	public int getReward()
	{
		return reward;
	}

	public void setReward(int reward)
	{
		this.reward = reward;
	}

	public boolean isAlive()
	{
		return alive;
	}

	public void kill()
	{
		alive = false;
	}
}
