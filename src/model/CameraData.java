package model;

import java.util.List;

import org.OpenNI.Context;
import org.OpenNI.DepthGenerator;
import org.OpenNI.GeneralException;
import org.OpenNI.HandsGenerator;
import org.OpenNI.UserGenerator;

public class CameraData
{
	private Context context;
	private DepthGenerator depthGenerator;
	private UserGenerator userGenerator;
	private HandsGenerator handsGenerator;
	private List<Hand> hands;
	private List<User> users;
	
	public CameraData(Context context){
		this.context = context;
		try
		{
			this.depthGenerator = DepthGenerator.create(context);
			this.userGenerator = UserGenerator.create(context);
			this.handsGenerator = HandsGenerator.create(context);
		} catch (GeneralException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Context getContext()
	{
		return context;
	}

	public DepthGenerator getDepthGenerator()
	{
		return depthGenerator;
	}

	public UserGenerator getUserGenerator()
	{
		return userGenerator;
	}

	public HandsGenerator getHandsGenerator()
	{
		return handsGenerator;
	}

	public List<Hand> getHands()
	{
		return hands;
	}

	public List<User> getUsers()
	{
		return users;
	}
	
	
	
}
