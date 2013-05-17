package model;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
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
		this.hands = new ArrayList<Hand>();
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

	public BufferedImage getImage()
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
	
	
	private void setUserSkeleton(User user){
		try
		{
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
