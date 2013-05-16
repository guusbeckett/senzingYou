package model.entities;

import java.awt.geom.Rectangle2D;

public class HostileEntity extends Entity
{
	private int reward;
	protected boolean alive;
	
	public HostileEntity(Rectangle2D bounds, int reward)
	{
		super(bounds, null);
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
