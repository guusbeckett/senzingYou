package model.entities;

import java.awt.geom.Rectangle2D;
import java.util.List;

import model.User;

public class HostileEntity extends Entity
{
	private int reward;
	protected boolean alive = true;
	protected List<User> users;
	
	public HostileEntity(Rectangle2D bounds, int reward, List<User> users)
	{
		super(bounds);
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
