package model.entities.desert;

import java.util.List;

import model.User;
import model.entities.HostileEntity;

public class Snake extends HostileEntity
{

	public Snake(List<User> users)
	{
		super(null, null, 0, users);
	}
}
