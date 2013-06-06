package model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class MediaProvider
{
	private static MediaProvider instance = new MediaProvider();
	private Map<String, Image> images = new HashMap<String, Image>();

	public static MediaProvider getInstance()
	{
		if (instance == null)
			instance = new MediaProvider();
		
		return instance;
	}
	
	public Image getImage(String name)
	{
        if (!images.containsKey(name))
        {
            try
            {
                images.put(name, ImageIO.read(model.MediaProvider.class.getResource("/" + name)));
            } catch (IOException e)
            {
                return new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
            }
        }

		return images.get(name);
	}
}
