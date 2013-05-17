package model.entities;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.List;

import model.User;

import model.User;

public class HostileEntity extends Entity
{
	private int reward;
	protected boolean alive;
	protected List<User> users;
	
	public HostileEntity(Rectangle2D bounds, List<BufferedImage> images, int reward, List<User> users)
	{
		super(bounds, images);
		this.reward = reward;
		this.users = users;
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
