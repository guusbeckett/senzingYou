package model;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openni.ActiveHandEventArgs;
import org.openni.AlternativeViewpointCapability;
import org.openni.CalibrationProgressEventArgs;
import org.openni.CalibrationProgressStatus;
import org.openni.Context;
import org.openni.DepthGenerator;
import org.openni.GeneralException;
import org.openni.HandsGenerator;
import org.openni.IObservable;
import org.openni.IObserver;
import org.openni.ImageGenerator;
import org.openni.InactiveHandEventArgs;
import org.openni.License;
import org.openni.Point3D;
import org.openni.SkeletonCapability;
import org.openni.SkeletonJoint;
import org.openni.SkeletonProfile;
import org.openni.StatusException;
import org.openni.UserEventArgs;
import org.openni.UserGenerator;

public class Camera
{
	//The 640x480 is needed because of the Kinect resolution
	public static final int VIEW_WIDTH = 640; // 640
	public static final int VIEW_HEIGHT = 480; // 480
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
			context = new Context();

			// add the NITE License
			License license = new License("PrimeSense", "0KOIk2JeIBYClPWVnMoRKn5cdY4="); // vendor,
																							// key
			context.addLicense(license);

			// So that he doesn't do the rest when he crash on this..
			depthGenerator = DepthGenerator.create(context);
		} catch (Exception e)
		{
			return;
		}

		try
		{
			// Generator
			depthGenerator = DepthGenerator.create(context);
			userGenerator = UserGenerator.create(context);
			handsGenerator = HandsGenerator.create(context);
			imageGenerator = ImageGenerator.create(context);
			skeletonCapability = userGenerator.getSkeletonCapability();

			// RGB image and Scene merge
			boolean hasAltView = depthGenerator.isCapabilitySupported("AlternativeViewPoint");
			if (hasAltView)
			{
				AlternativeViewpointCapability altViewCap = depthGenerator.getAlternativeViewpointCapability();
				altViewCap.setViewpoint(imageGenerator);
			}

			// set Mirror mode for all
			context.setGlobalMirror(true);

			//
			// USERS
			// Check if there is a new user
			userGenerator.getNewUserEvent().addObserver(new IObserver<UserEventArgs>()
			{
				@Override
				public void update(IObservable<UserEventArgs> arg0, UserEventArgs arg1)
				{
					try
					{
						if (!skeletonCapability.needPoseForCalibration())
						{
							skeletonCapability.requestSkeletonCalibration(arg1.getId(), true);
						}
					} catch (StatusException e)
					{
						e.printStackTrace();
					}

					getUsers().add(new User(arg1.getId(), userGenerator));
				}
			});

			// Check if the user re-entered
			userGenerator.getUserExitEvent().addObserver(new IObserver<UserEventArgs>()
			{
				@Override
				public void update(IObservable<UserEventArgs> arg0, UserEventArgs arg1)
				{
					for (User user : getUsers())
					{
						if (user.getId() == arg1.getId())
						{
							user.setVisible(true);
						}
					}
				}
			});

			// Check if the user left
			userGenerator.getUserExitEvent().addObserver(new IObserver<UserEventArgs>()
			{
				@Override
				public void update(IObservable<UserEventArgs> arg0, UserEventArgs arg1)
				{
					for (User user : getUsers())
					{
						if (user.getId() == arg1.getId())
						{
							user.setVisible(false);
						}
					}
				}
			});

			// Check if the user was lost
			userGenerator.getLostUserEvent().addObserver(new IObserver<UserEventArgs>()
			{
				@Override
				public void update(IObservable<UserEventArgs> arg0, UserEventArgs arg1)
				{
					Iterator<User> itr = getUsers().iterator();
					while (itr.hasNext())
					{
						User user = (User) itr.next();
						if (user.getId() == arg1.getId())
						{
							itr.remove();
						}
					}
				}
			});

			// HANDTRACKING
			// Making a new Hand
			handsGenerator.getHandCreateEvent().addObserver(new IObserver<ActiveHandEventArgs>()
			{
				@Override
				public void update(IObservable<ActiveHandEventArgs> arg0, ActiveHandEventArgs arg1)
				{

				}
			});

			// Updating the hand
			handsGenerator.getHandUpdateEvent().addObserver(new IObserver<ActiveHandEventArgs>()
			{
				@Override
				public void update(IObservable<ActiveHandEventArgs> arg0, ActiveHandEventArgs arg1)
				{
					Point2D p2 = convertPosition(arg1.getPosition());
					setPreciseHand(p2);
					// System.out.println("HandTracking updated - X: "+p2.getX()+", Y: "+p2.getY());
				}
			});

			// Removing the hand
			handsGenerator.getHandDestroyEvent().addObserver(new IObserver<InactiveHandEventArgs>()
			{
				@Override
				public void update(IObservable<InactiveHandEventArgs> arg0, InactiveHandEventArgs arg1)
				{
					if (!getUsers().isEmpty())
					{
						try
						{
							handsGenerator.StartTracking(getUsers().get(0).getRightHandWorld());
						} catch (StatusException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else
					{
						setPreciseHand(null);
					}

				}
			});

			// Making Skeleton possible!
			skeletonCapability.getCalibrationCompleteEvent().addObserver(new IObserver<CalibrationProgressEventArgs>()
			{

				@Override
				public void update(IObservable<CalibrationProgressEventArgs> arg0, CalibrationProgressEventArgs arg1)
				{
					try
					{
						if (arg1.getStatus() == CalibrationProgressStatus.OK)
						{
							skeletonCapability.startTracking(arg1.getUser());
							handsGenerator.StartTracking(skeletonCapability.getSkeletonJointPosition(arg1.getUser(), SkeletonJoint.RIGHT_HAND).getPosition());
						} else if (arg1.getStatus() != CalibrationProgressStatus.MANUAL_ABORT)
						{
							if (!skeletonCapability.needPoseForCalibration())
							{
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
		for (User user : users)
		{
			setUserSkeleton(user);
		}
		return users;
	}

	public BufferedImage[] getImageBackgroundAndForeground()
	{
		BufferedImage[] result = new BufferedImage[2];

		result[0] = new BufferedImage(VIEW_WIDTH, VIEW_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		result[1] = new BufferedImage(VIEW_WIDTH, VIEW_HEIGHT, BufferedImage.TYPE_INT_ARGB);

		try
		{
			context.waitAnyUpdateAll();
		} catch (StatusException e)
		{
			return result;
		}

		BufferedImage imgCam = getImageRGB();

		if (getUsers().size() > 0)
		{
			ShortBuffer userBuffer = getUsers().get(0).getUserPixels().createShortBuffer();

			int x = 0;
			int y = 0;

			while (userBuffer.remaining() > 0)
			{
				short userID = userBuffer.get();

				if (userID == 0)
				{
					Color color = new Color(imgCam.getRGB(x, y));

					result[0].setRGB(x, y, new Color(color.getRed(), color.getGreen(), color.getBlue(), 100).getRGB());
					result[1].setRGB(x, y, Color.TRANSLUCENT);
				}
				else
				{
					result[1].setRGB(x, y, imgCam.getRGB(x, y));
				}

				x++;

				if (x >= imgCam.getWidth())
				{
					x = 0;
					y++;
				}
			}
		}

		else
		{
			// Turn into a transparent background
			for (int x = 0; x < Camera.VIEW_WIDTH; x++)
			{
				for (int y = 0; y < Camera.VIEW_HEIGHT; y++)
				{
					Color color = new Color(imgCam.getRGB(x, y));
					result[1].setRGB(x, y, new Color(color.getRed(), color.getGreen(), color.getBlue(), 100).getRGB());
				}
			}
		}

		return result;
	}

	private BufferedImage getImageRGB()
	{
		int[] imageRGBArray = new int[VIEW_WIDTH * VIEW_HEIGHT];
		BufferedImage image = new BufferedImage(VIEW_WIDTH, VIEW_HEIGHT, BufferedImage.TYPE_INT_RGB);

		if (context == null || imageGenerator == null)
		{
			return image;
		}

		int i = 0;
		int r = 0;
		int g = 0;
		int b = 0;
		ByteBuffer rgbBuffer = imageGenerator.getMetaData().getData().createByteBuffer();
		for (int x = 0; x < VIEW_WIDTH; x++)
		{
			for (int y = 0; y < VIEW_HEIGHT; y++)
			{
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

	private void getMeasurements(User user)
	{
		try
		{

			double leftHandElbow = distance(skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.LEFT_HAND).getPosition(), skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.LEFT_ELBOW).getPosition());
			double rightHandElbow = distance(skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.RIGHT_HAND).getPosition(), skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.RIGHT_ELBOW).getPosition());
			double leftElbowShoulder = distance(skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.LEFT_ELBOW).getPosition(), skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.LEFT_SHOULDER).getPosition());
			double rightElbowShoulder = distance(skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.RIGHT_ELBOW).getPosition(), skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.RIGHT_SHOULDER).getPosition());
			double leftFootKnee = distance(skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.LEFT_FOOT).getPosition(), skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.LEFT_KNEE).getPosition());
			double rightFootKnee = distance(skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.RIGHT_FOOT).getPosition(), skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.RIGHT_KNEE).getPosition());
			double leftKneeHip = distance(skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.LEFT_KNEE).getPosition(), skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.LEFT_HIP).getPosition());
			double rightKneeHip = distance(skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.RIGHT_KNEE).getPosition(), skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.RIGHT_HIP).getPosition());
			double shoulder = distance(skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.LEFT_SHOULDER).getPosition(), skeletonCapability.getSkeletonJointPosition(user.getId(), SkeletonJoint.RIGHT_SHOULDER).getPosition());

			Recognition recog = new Recognition(leftHandElbow, rightHandElbow, leftElbowShoulder, rightElbowShoulder, leftFootKnee, rightFootKnee, leftKneeHip, rightKneeHip, shoulder, "Scanner");

			// System.out.println(recog);

			ArrayList<Recognition> recogs = new ArrayList<Recognition>();
			recogs.add(new Recognition(387.156991895671, 362.86219911934603, 289.9022489645288, 247.87892129539745, 400.89900908176656, 400.89901113437736, 416.93502701957476, 416.93500264395664, 219.95290085537002, "Guus"));
			recogs.add(new Recognition(345.11650094536634, 359.1939648296733, 239.83571686168503, 217.9514548108559, 338.74434258901204, 338.74439159563866, 352.29415490163325, 352.2941458232829, 277.5311020488579, "JW"));
			recogs.add(new Recognition(307.2743321928763, 273.26605097189434, 234.52670942610283, 244.2972030081929, 354.84515516740237, 354.8450460962978, 369.03894354930435, 369.03895263433446, 396.54826718060207, "Sven"));
			recogs.add(new Recognition(342.32574430381396, 320.5748602741389, 239.16373011038402, 230.1869826676575, 339.31738759009187, 339.3173290627665, 352.89005446221404, 352.8900614152893, 332.22212317512304, "Johan"));

			double lowestAllowed = 100;

			for (Recognition r : recogs)
			{
				double dif = r.totalDif(recog);

				// System.out.println(r.getName()+": "+dif);

				if (dif < lowestAllowed)
				{
					user.setName(r.getName());
				}
			}
		} catch (StatusException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private double distance(Point3D p1, Point3D p2)
	{
		return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2) + Math.pow(p2.getZ() - p1.getZ(), 2));

	}

	private void setUserSkeleton(User user)
	{
		try
		{
			if (user.getName() == null)
			{
				getMeasurements(user);
			}
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

	public Point2D convertPosition(Point3D p3)
	{
		if (p3.getZ() != 0)
		{
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
