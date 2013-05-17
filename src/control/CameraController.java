package control;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.Iterator;

import model.CameraData;
import model.Game;
import model.User;

import org.OpenNI.ActiveHandEventArgs;
import org.OpenNI.CalibrationProgressEventArgs;
import org.OpenNI.CalibrationProgressStatus;
import org.OpenNI.Context;
import org.OpenNI.IObservable;
import org.OpenNI.IObserver;
import org.OpenNI.InactiveHandEventArgs;
import org.OpenNI.OutArg;
import org.OpenNI.Point3D;
import org.OpenNI.ScriptNode;
import org.OpenNI.SkeletonJoint;
import org.OpenNI.SkeletonProfile;
import org.OpenNI.StatusException;
import org.OpenNI.UserEventArgs;

public class CameraController
{
	private Game game;
	
	public CameraController(Game g)
	{
		this.game = g;
		try
		{
			OutArg<ScriptNode> scriptNode = new OutArg<ScriptNode>();
			game.setCameraData(new CameraData(Context.createFromXmlFile("./OpenNIConfig.xml", scriptNode)));	
			
			//USERS
			//Check if there is a new user
			game.getCameraData().getUserGenerator().getNewUserEvent().addObserver(new IObserver<UserEventArgs>(){
					@Override
					public void update(IObservable<UserEventArgs> arg0,
							UserEventArgs arg1){
						try
						{
							if (!game.getCameraData().getSkeletonCapability().needPoseForCalibration())
							{
								game.getCameraData().getSkeletonCapability().requestSkeletonCalibration(arg1.getId(), true);
							} 
						}catch (StatusException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						game.getCameraData().getUsers().add(new User(arg1.getId(), game.getCameraData().getUserGenerator()));
					}
			});
			
			//Check if the user is away
			game.getCameraData().getUserGenerator().getUserExitEvent().addObserver(new IObserver<UserEventArgs>(){
				@Override
				public void update(IObservable<UserEventArgs> arg0,
						UserEventArgs arg1){
						  Iterator itr = game.getCameraData().getUsers().iterator();
					      while(itr.hasNext()) {
					         User user = (User)itr.next();
					         if(user.getId() == arg1.getId()){
					        	 itr.remove();
					         }
					      }
				}
			});
			
			//HANDTRACKING
			//Making a new Hand
			game.getCameraData().getHandsGenerator().getHandCreateEvent().addObserver(new IObserver<ActiveHandEventArgs>(){
				@Override
				public void update(IObservable<ActiveHandEventArgs> arg0,
						ActiveHandEventArgs arg1){
					
				}
			});
			
			//Updating the hand
			game.getCameraData().getHandsGenerator().getHandUpdateEvent().addObserver(new IObserver<ActiveHandEventArgs>(){
				@Override
				public void update(IObservable<ActiveHandEventArgs> arg0,
						ActiveHandEventArgs arg1){
						for(User user: game.getCameraData().getUsers()){
							
							//I hope that the userId is the same as the HandId (but i doubt it)..
							if(arg1.getId() == user.getId()){
								user.setHandExact(game.getCameraData().convertPosition(arg1.getPosition()));
							}
						}
				}
			});
			
			//Removing the hand
			game.getCameraData().getHandsGenerator().getHandDestroyEvent().addObserver(new IObserver<InactiveHandEventArgs>(){
				@Override
				public void update(IObservable<InactiveHandEventArgs> arg0,
						InactiveHandEventArgs arg1){
					for(User user: game.getCameraData().getUsers()){
						
						//I hope that the userId is the same as the HandId (but i doubt it)..
						if(arg1.getId() == user.getId()){
							user.setHandExact(null);
						}
					}
				}
			});
			
			//Making Skeleton possible!
			game.getCameraData().getSkeletonCapability().getCalibrationCompleteEvent().addObserver(new IObserver<CalibrationProgressEventArgs>(){

				@Override
				public void update(
						IObservable<CalibrationProgressEventArgs> arg0,
						CalibrationProgressEventArgs arg1){
					try
					{
					if (arg1.getStatus() == CalibrationProgressStatus.OK){
						game.getCameraData().getSkeletonCapability().startTracking(arg1.getUser());
						for(User user: game.getCameraData().getUsers()){
							if(user.getId() == arg1.getUser()){
								//Create the Skeleton shit
								//Making the hand track a Hand!
								game.getCameraData().getHandsGenerator().StartTracking(game.getCameraData().getSkeletonCapability().getSkeletonJointPosition(user.getId(), SkeletonJoint.RIGHT_HAND).getPosition());
							}
						}
					}
					else if (arg1.getStatus() != CalibrationProgressStatus.MANUAL_ABORT)
					{
						if (!game.getCameraData().getSkeletonCapability().needPoseForCalibration()){
							game.getCameraData().getSkeletonCapability().requestSkeletonCalibration(arg1.getUser(), true);
						}
					}
					} catch (StatusException e)
					{
						e.printStackTrace();
					}
				}
				 
			 });
			game.getCameraData().getSkeletonCapability().setSkeletonProfile(SkeletonProfile.ALL);
			
			game.getCameraData().getContext().startGeneratingAll();
		}
		catch(Exception e)
		{
			
		}
	}
	
}
