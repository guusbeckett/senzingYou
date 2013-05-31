package model.entities;

import java.awt.geom.Point2D;
import java.util.List;

import model.User;


public abstract class HostileEntity extends Entity
{
	protected boolean alive = true;
	protected List<User> users;
	private int deadTime;
	private Point2D deadLocation;
	
	public HostileEntity(List<User> users)
	{
		this.users = users;
		deadLocation = new Point2D.Double(0, 0);
	}

	public boolean isAlive()
	{
		return alive;
	}

	public void kill()
	{
		if(alive){
			deadLocation.setLocation(position);
		}
		alive = false;
	}
	
	public int getDeadTime(){
		return deadTime;
	}
	
	public void update(double time){
		super.update(time);
		if(!isAlive()){
			deadTime+=time;
		}
	}

	public abstract int getReward();

	public Point2D getDeadLocation()
	{
		return deadLocation;
	}
}
