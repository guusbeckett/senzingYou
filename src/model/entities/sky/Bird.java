package model.entities.sky;

import java.util.List;

import model.User;
import model.entities.HostileEntity;

public class Bird extends HostileEntity
{
	public Bird(List<User> users)
	{
		super(null, null, 0, users);
	}

}
