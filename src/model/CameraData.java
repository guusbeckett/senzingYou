package model;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;

import org.OpenNI.Context;
import org.OpenNI.DepthGenerator;
import org.OpenNI.GeneralException;
import org.OpenNI.HandsGenerator;
import org.OpenNI.ImageGenerator;
import org.OpenNI.Point3D;
import org.OpenNI.SkeletonCapability;
import org.OpenNI.SkeletonJoint;
import org.OpenNI.StatusException;
import org.OpenNI.UserGenerator;

public class CameraData
{
	public static final int VIEW_WIDTH = 640; //640
	public static final int VIEW_HEIGHT = 480; //480
	private Context context;
	private DepthGenerator depthGenerator;
	private UserGenerator userGenerator;
	private HandsGenerator handsGenerator;
	private ImageGenerator imageGenerator;
	private SkeletonCapability skeletonCapability;
	
	private List<Hand> hands;
	private List<User> users;

	public CameraData(Context context)
	{
		this.context = context;
		this.users = new ArrayList<User>();
		
		try
		{
			this.depthGenerator = DepthGenerator.create(context);
			this.userGenerator = UserGenerator.create(context);
			this.handsGenerator = HandsGenerator.create(context);
			this.imageGenerator = ImageGenerator.create(context);
			this.skeletonCapability = userGenerator.getSkeletonCapability();
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
		for(User user: users){
			setUserSkeleton(user);
		}
		return users;
	}

	public ImageGenerator getImageGenerator()
	{
		return imageGenerator;
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
		try
		{
			context.waitAnyUpdateAll();
		} catch (StatusException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		int[] imageRGBArray = new int[VIEW_WIDTH * VIEW_HEIGHT];
		BufferedImage image = new BufferedImage(VIEW_WIDTH, VIEW_HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		int i = 0;
		int r = 0;
		int g = 0;
		int b = 0;
		ByteBuffer rgbBuffer = getImageGenerator().getMetaData().getData().createByteBuffer();
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

	public SkeletonCapability getSkeletonCapability()
	{
		return skeletonCapability;
	}
	
	
	private void getMeasurements(int user){
		try
		{
			 
			double leftHandElbow = distance(getSkeletonCapability().getSkeletonJointPosition(user, SkeletonJoint.LEFT_HAND).getPosition(), getSkeletonCapability().getSkeletonJointPosition(user, SkeletonJoint.LEFT_ELBOW).getPosition());
			double rightHandElbow = distance(getSkeletonCapability().getSkeletonJointPosition(user, SkeletonJoint.RIGHT_HAND).getPosition(), getSkeletonCapability().getSkeletonJointPosition(user, SkeletonJoint.RIGHT_ELBOW).getPosition());
			double leftElbowShoulder = distance(getSkeletonCapability().getSkeletonJointPosition(user, SkeletonJoint.LEFT_ELBOW).getPosition(), getSkeletonCapability().getSkeletonJointPosition(user, SkeletonJoint.LEFT_SHOULDER).getPosition());
			double rightElbowShoulder = distance(getSkeletonCapability().getSkeletonJointPosition(user, SkeletonJoint.RIGHT_ELBOW).getPosition(), getSkeletonCapability().getSkeletonJointPosition(user, SkeletonJoint.RIGHT_SHOULDER).getPosition());
			double leftFootKnee = distance(getSkeletonCapability().getSkeletonJointPosition(user, SkeletonJoint.LEFT_FOOT).getPosition(), getSkeletonCapability().getSkeletonJointPosition(user, SkeletonJoint.LEFT_KNEE).getPosition());
			double rightFootKnee = distance(getSkeletonCapability().getSkeletonJointPosition(user, SkeletonJoint.RIGHT_FOOT).getPosition(), getSkeletonCapability().getSkeletonJointPosition(user, SkeletonJoint.RIGHT_KNEE).getPosition());
			double leftKneeHip = distance(getSkeletonCapability().getSkeletonJointPosition(user, SkeletonJoint.LEFT_KNEE).getPosition(), getSkeletonCapability().getSkeletonJointPosition(user, SkeletonJoint.LEFT_HIP).getPosition());
			double rightKneeHip = distance(getSkeletonCapability().getSkeletonJointPosition(user, SkeletonJoint.RIGHT_KNEE).getPosition(), getSkeletonCapability().getSkeletonJointPosition(user, SkeletonJoint.RIGHT_HIP).getPosition());
			double shoulder = distance(getSkeletonCapability().getSkeletonJointPosition(user, SkeletonJoint.LEFT_SHOULDER).getPosition(), getSkeletonCapability().getSkeletonJointPosition(user, SkeletonJoint.RIGHT_SHOULDER).getPosition());
			
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
			user.setHead(convertPosition(getSkeletonCapability().getSkeletonJointPosition(user.getId(), SkeletonJoint.HEAD).getPosition()));
			user.setNeck(convertPosition(getSkeletonCapability().getSkeletonJointPosition(user.getId(), SkeletonJoint.NECK).getPosition()));
			
			user.setLeftShoulder(convertPosition(getSkeletonCapability().getSkeletonJointPosition(user.getId(), SkeletonJoint.LEFT_SHOULDER).getPosition()));
			user.setRightShoulder(convertPosition(getSkeletonCapability().getSkeletonJointPosition(user.getId(), SkeletonJoint.RIGHT_SHOULDER).getPosition()));
			
			user.setTorso(convertPosition(getSkeletonCapability().getSkeletonJointPosition(user.getId(), SkeletonJoint.TORSO).getPosition()));
			
			user.setLeftElbow(convertPosition(getSkeletonCapability().getSkeletonJointPosition(user.getId(), SkeletonJoint.LEFT_ELBOW).getPosition()));
			user.setRightElbow(convertPosition(getSkeletonCapability().getSkeletonJointPosition(user.getId(), SkeletonJoint.RIGHT_ELBOW).getPosition()));
			
			user.setLeftHand(convertPosition(getSkeletonCapability().getSkeletonJointPosition(user.getId(), SkeletonJoint.LEFT_HAND).getPosition()));
			user.setRightHand(convertPosition(getSkeletonCapability().getSkeletonJointPosition(user.getId(), SkeletonJoint.RIGHT_HAND).getPosition()));
			
			user.setLeftHip(convertPosition(getSkeletonCapability().getSkeletonJointPosition(user.getId(), SkeletonJoint.LEFT_HIP).getPosition()));
			user.setRightHip(convertPosition(getSkeletonCapability().getSkeletonJointPosition(user.getId(), SkeletonJoint.RIGHT_HIP).getPosition()));
			
			user.setLeftKnee(convertPosition(getSkeletonCapability().getSkeletonJointPosition(user.getId(), SkeletonJoint.LEFT_KNEE).getPosition()));
			user.setRightKnee(convertPosition(getSkeletonCapability().getSkeletonJointPosition(user.getId(), SkeletonJoint.RIGHT_KNEE).getPosition()));
			
			user.setLeftFoot(convertPosition(getSkeletonCapability().getSkeletonJointPosition(user.getId(), SkeletonJoint.LEFT_FOOT).getPosition()));
			user.setRightFoot(convertPosition(getSkeletonCapability().getSkeletonJointPosition(user.getId(), SkeletonJoint.RIGHT_FOOT).getPosition()));
			
			user.setMidpoint(convertPosition(getSkeletonCapability().getSkeletonJointPosition(user.getId(), SkeletonJoint.TORSO).getPosition()));
			user.setRightHandWorld(getSkeletonCapability().getSkeletonJointPosition(user.getId(), SkeletonJoint.RIGHT_HAND).getPosition());

			
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
				p3 = getDepthGenerator().convertRealWorldToProjective(p3);
			} catch (StatusException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return new Point2D.Double(p3.getX(), p3.getY());
	}
	

}
