package model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class MediaProvider
{
	private static MediaProvider instance = new MediaProvider();
	private Map<String, Image> images = new HashMap<String, Image>();
	private Map<String, AudioInputStream> audio = new HashMap<String, AudioInputStream>();
	
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
	
	public AudioInputStream getSound(String name)
	{
        if (!audio.containsKey(name))
        {
            try
            {
                audio.put(name, AudioSystem.getAudioInputStream(model.MediaProvider.class.getResource("/" + name)));
            } catch (Exception e)
            {
                return null;
            }
        }

		return audio.get(name);
	}
}
