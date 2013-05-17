package model.entities.cave;

import java.util.List;

import model.User;
import model.entities.HostileEntity;

public class Rock extends HostileEntity
{

	public Rock(List<User> users)
	{
		super(null, null, 0, users);
	}

}
