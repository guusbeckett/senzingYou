package model.entities.rainforest;

import java.util.List;

import model.User;
import model.entities.HostileEntity;

public class Snake extends HostileEntity
{
	public Snake(List<User> users)
	{
		super(null, 0, users);
	}
}
