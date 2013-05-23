package model.entities;

import java.util.List;

import model.User;

public abstract class HostileEntity extends Entity
{
	protected boolean alive = true;
	protected List<User> users;
	
	public HostileEntity(List<User> users)
	{
		this.users = users;
	}
	
	public boolean isAlive()
	{
		return alive;
	}

	public void kill()
	{
		alive = false;
	}

	public abstract int getReward();
}
