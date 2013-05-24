package model;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.OpenNI.ActiveHandEventArgs;
import org.OpenNI.CalibrationProgressEventArgs;
import org.OpenNI.CalibrationProgressStatus;
import org.OpenNI.Context;
import org.OpenNI.DepthGenerator;
import org.OpenNI.GeneralException;
import org.OpenNI.HandsGenerator;
import org.OpenNI.IObservable;
import org.OpenNI.IObserver;
import org.OpenNI.ImageGenerator;
import org.OpenNI.InactiveHandEventArgs;
import org.OpenNI.OutArg;
import org.OpenNI.Point3D;
import org.OpenNI.ScriptNode;
import org.OpenNI.SkeletonCapability;
import org.OpenNI.SkeletonJoint;
import org.OpenNI.SkeletonProfile;
import org.OpenNI.StatusException;
import org.OpenNI.UserEventArgs;
import org.OpenNI.UserGenerator;

public class Camera
{
	public static final int VIEW_WIDTH = 640; //640
	public static final int VIEW_HEIGHT = 480; //480
	private Context context = null;
	private DepthGenerator depthGenerator;
	private UserGenerator userGenerator;
	private HandsGenerator handsGenerator;
	private ImageGenerator imageGenerator;
	private SkeletonCapability skeletonCapability;
	
	private Point2D preciseHand;
	
	private List<User> users;

	public Camera()
	{
		
		this.users = new ArrayList<User>();
		try
		{
			OutArg<ScriptNode> scriptNode = new OutArg<ScriptNode>();
			context = Context.createFromXmlFile("./OpenNIConfig.xml", scriptNode);	
		}
		catch (GeneralException e)
		{
			// No Kinect connected...
			return;
		}
		
		try
		{
			depthGenerator = DepthGenerator.create(context);
			userGenerator = UserGenerator.create(context);
			handsGenerator = HandsGenerator.create(context);
			imageGenerator = ImageGenerator.create(context);
			skeletonCapability = userGenerator.getSkeletonCapability();
			
			
			//
			//USERS
			//Check if there is a new user
			userGenerator.getNewUserEvent().addObserver(new IObserver<UserEventArgs>(){
					@Override
					public void update(IObservable<UserEventArgs> arg0,
							UserEventArgs arg1){
						try
						{
							if (!skeletonCapability.needPoseForCalibration())
							{
								skeletonCapability.requestSkeletonCalibration(arg1.getId(), true);
							} 
						}catch (StatusException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						getUsers().add(new User(arg1.getId(), userGenerator));
					}
			});
			
			//Check if the user is away
			userGenerator.getUserExitEvent().addObserver(new IObserver<UserEventArgs>(){
				@Override
				public void update(IObservable<UserEventArgs> arg0,
						UserEventArgs arg1){
						  Iterator itr = getUsers().iterator();
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
			handsGenerator.getHandCreateEvent().addObserver(new IObserver<ActiveHandEventArgs>(){
				@Override
				public void update(IObservable<ActiveHandEventArgs> arg0,
						ActiveHandEventArgs arg1){
					
				}
			});
			
			//Updating the hand
			handsGenerator.getHandUpdateEvent().addObserver(new IObserver<ActiveHandEventArgs>(){
				@Override
				public void update(IObservable<ActiveHandEventArgs> arg0,
						ActiveHandEventArgs arg1){
						setPreciseHand(convertPosition(arg1.getPosition()));
				}
			});
			
			//Removing the hand
			handsGenerator.getHandDestroyEvent().addObserver(new IObserver<InactiveHandEventArgs>(){
				@Override
				public void update(IObservable<InactiveHandEventArgs> arg0,
						InactiveHandEventArgs arg1){
						setPreciseHand(null);
				}
			});
			
			//Making Skeleton possible!
			skeletonCapability.getCalibrationCompleteEvent().addObserver(new IObserver<CalibrationProgressEventArgs>(){

				@Override
				public void update(
						IObservable<CalibrationProgressEventArgs> arg0,
						CalibrationProgressEventArgs arg1){
					try
					{
					if (arg1.getStatus() == CalibrationProgressStatus.OK){
						skeletonCapability.startTracking(arg1.getUser());
						for(User user: getUsers()){
							if(user.getId() == arg1.getUser()){
								handsGenerator.StartTracking(skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.RIGHT_HAND).getPosition());
							}
						}
					}
					else if (arg1.getStatus() != CalibrationProgressStatus.MANUAL_ABORT)
					{
						if (!skeletonCapability.needPoseForCalibration()){
							skeletonCapability.requestSkeletonCalibration(arg1.getUser(), true);
						}
					}
					} catch (StatusException e)
					{
						e.printStackTrace();
					}
				}
				 
			 });
			skeletonCapability.setSkeletonProfile(SkeletonProfile.ALL);
			
			context.startGeneratingAll();
			
		} catch (GeneralException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<User> getUsers()
	{
		for(User user: users){
			setUserSkeleton(user);
		}
		return users;
	}
	
	public BufferedImage getImage(){
		//return getImageRGB();
		return getImageCut();
	}
	
	private BufferedImage getImageCut(){
		ShortBuffer userBuffer = null;
		BufferedImage img = new BufferedImage(VIEW_WIDTH, VIEW_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		BufferedImage imgCam = getImageRGB();
		for(User user: getUsers()){
			userBuffer = user.getUserPixels().getData().createShortBuffer();
		}
		
		int x = 0;
		int y = 0;
		
		int loop = 0;
		int verbreed = 40;
		int lastX = (verbreed + 1) *-1;
		int lastY = (verbreed + 1) *-1;

		if(userBuffer != null){
			while (userBuffer.remaining() > 0) {
			      short userID = userBuffer.get();
			      if (userID == 0){ // if not a user then it is a background
			    	  if(((lastX +verbreed) > x && (lastX) < x) && (lastX != (verbreed + 1) *-1)){
			    		  Color color = new Color(imgCam.getRGB(x, y));
			    		  Color colorEdit = new Color(color.getRed(), color.getGreen(), color.getBlue(), 250);
			    		  img.setRGB(x, y, colorEdit.getRGB());
			    	  }
			    	  else{
			    		  Color color = new Color(imgCam.getRGB(x, y));
			    		  Color colorEdit = new Color(color.getRed(), color.getGreen(), color.getBlue(), 100);
			    		  img.setRGB(x, y, colorEdit.getRGB());
			    	  }
			    		  
			      }
			      else{
			    	  img.setRGB(x, y, imgCam.getRGB(x, y));
			    	  lastX = x;
			    	  lastY = y;
			      }
			      
			      //Handle the rest of the images
			      x++;
			      if(x >= img.getWidth()){
			    	  x = 0;
			    	  lastY = 0;
			    	  y++;
			      }	
			      loop++;
			}
			
		}
		
		return img;
	}

	private BufferedImage getImageRGB()
	{		
		int[] imageRGBArray = new int[VIEW_WIDTH * VIEW_HEIGHT];
		BufferedImage image = new BufferedImage(VIEW_WIDTH, VIEW_HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		if (context == null || imageGenerator == null)
		{
			return image;
		}
	
		try
		{
			context.waitAnyUpdateAll();
		} catch (StatusException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int i = 0;
		int r = 0;
		int g = 0;
		int b = 0;
		ByteBuffer rgbBuffer = imageGenerator.getMetaData().getData().createByteBuffer();
		for (int x = 0; x < VIEW_WIDTH; x++) {
			for (int y = 0; y < VIEW_HEIGHT; y++) {
				i = y * VIEW_WIDTH + x;
				r = rgbBuffer.get(i * 3) & 0xff;
				g = rgbBuffer.get(i * 3 + 1) & 0xff;
				b = rgbBuffer.get(i * 3 + 2) & 0xff;
				imageRGBArray[i] = (r << 16) | (g << 8) | b;
			}
		}
		image.setRGB(0, 0, VIEW_WIDTH, VIEW_HEIGHT, imageRGBArray, 0, VIEW_WIDTH);
		return image;
	}
	
	
	private void getMeasurements(int user){
		try
		{
			 
			double leftHandElbow = distance(skeletonCapability.getSkeletonJointPosition(user, SkeletonJoint.LEFT_HAND).getPosition(), skeletonCapability.getSkeletonJointPosition(user, SkeletonJoint.LEFT_ELBOW).getPosition());
			double rightHandElbow = distance(skeletonCapability.getSkeletonJointPosition(user, SkeletonJoint.RIGHT_HAND).getPosition(), skeletonCapability.getSkeletonJointPosition(user, SkeletonJoint.RIGHT_ELBOW).getPosition());
			double leftElbowShoulder = distance(skeletonCapability.getSkeletonJointPosition(user, SkeletonJoint.LEFT_ELBOW).getPosition(), skeletonCapability.getSkeletonJointPosition(user, SkeletonJoint.LEFT_SHOULDER).getPosition());
			double rightElbowShoulder = distance(skeletonCapability.getSkeletonJointPosition(user, SkeletonJoint.RIGHT_ELBOW).getPosition(), skeletonCapability.getSkeletonJointPosition(user, SkeletonJoint.RIGHT_SHOULDER).getPosition());
			double leftFootKnee = distance(skeletonCapability.getSkeletonJointPosition(user, SkeletonJoint.LEFT_FOOT).getPosition(), skeletonCapability.getSkeletonJointPosition(user, SkeletonJoint.LEFT_KNEE).getPosition());
			double rightFootKnee = distance(skeletonCapability.getSkeletonJointPosition(user, SkeletonJoint.RIGHT_FOOT).getPosition(), skeletonCapability.getSkeletonJointPosition(user, SkeletonJoint.RIGHT_KNEE).getPosition());
			double leftKneeHip = distance(skeletonCapability.getSkeletonJointPosition(user, SkeletonJoint.LEFT_KNEE).getPosition(), skeletonCapability.getSkeletonJointPosition(user, SkeletonJoint.LEFT_HIP).getPosition());
			double rightKneeHip = distance(skeletonCapability.getSkeletonJointPosition(user, SkeletonJoint.RIGHT_KNEE).getPosition(), skeletonCapability.getSkeletonJointPosition(user, SkeletonJoint.RIGHT_HIP).getPosition());
			double shoulder = distance(skeletonCapability.getSkeletonJointPosition(user, SkeletonJoint.LEFT_SHOULDER).getPosition(), skeletonCapability.getSkeletonJointPosition(user, SkeletonJoint.RIGHT_SHOULDER).getPosition());
			
			Recognition recog = new Recognition(leftHandElbow, rightHandElbow, leftElbowShoulder, rightElbowShoulder, leftFootKnee, rightFootKnee, leftKneeHip, rightKneeHip, shoulder, "Scanner");
			
			//System.out.println(recog.toString());
			
			ArrayList<Recognition> recogs = new ArrayList<Recognition>();
			//recogs.add(new Recognition(10, rightHandElbow, leftElbowShoulder, rightElbowShoulder, leftFootKnee, rightFootKnee, leftKneeHip, rightKneeHip, shoulder, "Scanner"));
			boolean someOneThere = false;
			for(Recognition r: recogs){
				if(r.compareTo(recog)){
					System.out.println("Welkom: "+r.getName());
					someOneThere = true;
				}
			}
			
			if(!someOneThere){
				//System.out.println("Niemand bekends!");
			}
			
			//System.out.println(recog.toString());
		} catch (StatusException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private double distance(Point3D p1, Point3D p2){
		double dx = p1.getX() - p2.getX();
		double dy = p1.getY() - p2.getY();
		double dz = p1.getZ() - p2.getZ();
		return dx*dx + dy*dy + dz*dz;
	}
	
	private void setUserSkeleton(User user){
		try
		{
			getMeasurements(user.getId());
			user.setHead(convertPosition(skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.HEAD).getPosition()));
			user.setNeck(convertPosition(skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.NECK).getPosition()));
			
			user.setLeftShoulder(convertPosition(skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.LEFT_SHOULDER).getPosition()));
			user.setRightShoulder(convertPosition(skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.RIGHT_SHOULDER).getPosition()));
			
			user.setTorso(convertPosition(skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.TORSO).getPosition()));
			
			user.setLeftElbow(convertPosition(skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.LEFT_ELBOW).getPosition()));
			user.setRightElbow(convertPosition(skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.RIGHT_ELBOW).getPosition()));
			
			user.setLeftHand(convertPosition(skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.LEFT_HAND).getPosition()));
			user.setRightHand(convertPosition(skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.RIGHT_HAND).getPosition()));
			
			user.setLeftHip(convertPosition(skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.LEFT_HIP).getPosition()));
			user.setRightHip(convertPosition(skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.RIGHT_HIP).getPosition()));
			
			user.setLeftKnee(convertPosition(skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.LEFT_KNEE).getPosition()));
			user.setRightKnee(convertPosition(skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.RIGHT_KNEE).getPosition()));
			
			user.setLeftFoot(convertPosition(skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.LEFT_FOOT).getPosition()));
			user.setRightFoot(convertPosition(skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.RIGHT_FOOT).getPosition()));
			
			user.setMidpoint(convertPosition(skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.TORSO).getPosition()));
			user.setRightHandWorld(skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.RIGHT_HAND).getPosition());

			
		} catch (StatusException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public Point2D convertPosition(Point3D p3){
		if(p3.getZ() != 0){
			try
			{
				p3 = depthGenerator.convertRealWorldToProjective(p3);
			} catch (StatusException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return new Point2D.Double(p3.getX(), p3.getY());
	}

	public Point2D getPreciseHand()
	{
		return preciseHand;
	}

	public void setPreciseHand(Point2D preciseHand)
	{
		this.preciseHand = preciseHand;
	}
	

}
