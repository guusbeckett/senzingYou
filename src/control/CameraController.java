package control;

import java.util.Iterator;

import model.CameraData;
import model.Game;
import model.Hand;
import model.User;

import org.OpenNI.ActiveHandEventArgs;
import org.OpenNI.Context;
import org.OpenNI.IObservable;
import org.OpenNI.IObserver;
import org.OpenNI.InactiveHandEventArgs;
import org.OpenNI.OutArg;
import org.OpenNI.Point3D;
import org.OpenNI.ScriptNode;
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
					game.getCameraData().getHands().add(new Hand(arg1.getId()));
					System.out.println("Hand aangemaakt!");
				}
			});
			
			//Updating the hand
			game.getCameraData().getHandsGenerator().getHandUpdateEvent().addObserver(new IObserver<ActiveHandEventArgs>(){
				@Override
				public void update(IObservable<ActiveHandEventArgs> arg0,
						ActiveHandEventArgs arg1){
						Iterator itr = game.getCameraData().getHands().iterator();
				      while(itr.hasNext()) {
				         Hand hand = (Hand)itr.next();
				         if(hand.getId() == arg1.getId()){
				        	 hand.setPosition(arg1.getPosition());
				         }
				      }
				}
			});
			
			//Removing the hand
			game.getCameraData().getHandsGenerator().getHandDestroyEvent().addObserver(new IObserver<InactiveHandEventArgs>(){
				@Override
				public void update(IObservable<InactiveHandEventArgs> arg0,
						InactiveHandEventArgs arg1){
					Iterator itr = game.getCameraData().getHands().iterator();
				    while(itr.hasNext()) {
				        Hand hand = (Hand)itr.next();
				        if(hand.getId() == arg1.getId()){
				        	 itr.remove();
				         }
				    }
				}
			});
			game.getCameraData().getHandsGenerator().StartTracking(new Point3D(10,10,10));
			game.getCameraData().getContext().startGeneratingAll();
		}
		catch(Exception e)
		{
			
		}
	}
	
}
