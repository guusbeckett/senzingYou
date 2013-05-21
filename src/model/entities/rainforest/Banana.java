package model.entities.rainforest;

import java.util.List;

import model.User;
import model.entities.HostileEntity;

public class Banana extends HostileEntity
{

	public Banana(List<User> users)
	{
		super(null, 0, users);
	}

}
