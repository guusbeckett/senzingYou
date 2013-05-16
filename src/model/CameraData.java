package model;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.List;

import org.OpenNI.Context;
import org.OpenNI.DepthGenerator;
import org.OpenNI.GeneralException;
import org.OpenNI.HandsGenerator;
import org.OpenNI.ImageGenerator;
import org.OpenNI.UserGenerator;

public class CameraData
{
	public static final int VIEW_WIDTH = 640;
	public static final int VIEW_HEIGHT = 480;
	private Context context;
	private DepthGenerator depthGenerator;
	private UserGenerator userGenerator;
	private HandsGenerator handsGenerator;
	private ImageGenerator imageGenerator;
	
	private BufferedImage image;
	
	private List<Hand> hands;
	private List<User> users;

	public CameraData(Context context)
	{
		this.context = context;
		try
		{
			this.depthGenerator = DepthGenerator.create(context);
			this.userGenerator = UserGenerator.create(context);
			this.handsGenerator = HandsGenerator.create(context);
			this.imageGenerator = ImageGenerator.create(context);
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

	public ImageGenerator getImageGenerator()
	{
		return imageGenerator;
	}

	public BufferedImage getImage()
	{
	int[] imageRGBArray = new int[getImageGenerator().getMetaData().getFullXRes() * getImageGenerator().getMetaData().getFullYRes()];
		
		int i = 0;
		int r = 0;
		int g = 0;
		int b = 0;
		ByteBuffer rgbBuffer = getImageGenerator().getMetaData().getData().createByteBuffer();
		for (int x = 0; x < getImageGenerator().getMetaData().getFullXRes(); x++) {
			for (int y = 0; y < getImageGenerator().getMetaData().getFullYRes(); y++) {
				i = y * getImageGenerator().getMetaData().getFullXRes() + x;
				r = rgbBuffer.get(i * 3) & 0xff;
				g = rgbBuffer.get(i * 3 + 1) & 0xff;
				b = rgbBuffer.get(i * 3 + 2) & 0xff;
				imageRGBArray[i] = (r << 16) | (g << 8) | b;
			}
		}
		getImage().setRGB(0, 0, getImageGenerator().getMetaData().getFullXRes(), getImageGenerator().getMetaData().getFullYRes(), imageRGBArray, 0, getImageGenerator().getMetaData().getFullXRes());
		return image;
	}

	public void setImage(BufferedImage image)
	{
		this.image = image;
	}

}
