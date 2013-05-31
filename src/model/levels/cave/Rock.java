package model.levels.cave;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import model.Camera;
import model.User;
import model.entities.HostileEntity;

public class Rock extends HostileEntity
{
	private boolean playedSound = false;
	private int size;

	public Rock(List<User> users)
	{
		super(users);
		position.setLocation((Math.random() * (Camera.VIEW_WIDTH - 1) + 1), -30);
		size = (int) (Math.random() * (80 - 50) + 50);
	}

	@Override
	public Point2D getRotationPoint()
	{
		return new Point2D.Double(getDimensions().getWidth() / 2,
				getDimensions().getHeight() / 2);
	}

	@Override
	public Dimension2D getDimensions()
	{
		return new Dimension(size, size);
	}

	@Override
	public List<Image> getImages()
	{
		ArrayList<Image> images = new ArrayList<Image>();

		images.add(Toolkit.getDefaultToolkit().getImage(
				"./images/cave/stone.png"));

		return images;
	}

	@Override
	public void update(double time)
	{
		super.update(time);
		position.setLocation(position.getX(), position.getY() + 0.15 * time);
	}

	@Override
	public int getReward()
	{
		return -50;
	}

	@Override
	public AudioInputStream getSound() throws UnsupportedAudioFileException,
			IOException
	{
		File file = new File("./audio/cave/rock.wav");
		return AudioSystem.getAudioInputStream(file);
	}

	@Override
	public AudioInputStream getHitSound() throws UnsupportedAudioFileException,
			IOException
	{
		File file = new File("./audio/cave/rock.wav");
		return AudioSystem.getAudioInputStream(file);
	}

	public void playSound(double time)
	{
		if (position.getY() >= Camera.VIEW_HEIGHT)
		{
			if (!playedSound)
			{
				try
				{
					AudioInputStream stream = getSound();
					Clip clip = (Clip) AudioSystem.getClip();
					clip.open(stream);
					FloatControl volumeControl = (FloatControl) clip
							.getControl(FloatControl.Type.MASTER_GAIN);
					volumeControl.setValue((float) (time / (1000 / 30)));
					clip.loop(0);
					clip.start();
					playedSound = true;
				} catch (UnsupportedAudioFileException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (LineUnavailableException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
