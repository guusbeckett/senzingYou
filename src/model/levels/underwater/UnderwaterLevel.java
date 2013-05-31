package model.levels.underwater;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import model.Game;
import model.GroundRenderer;
import model.entities.Entity;
import model.entities.HostileEntity;
import model.levels.PunchLevel;
import control.Climate;
import control.Hardware;

public class UnderwaterLevel extends PunchLevel
{
	private GroundRenderer groundRenderer = new UnderwaterGround();
	
	public UnderwaterLevel(Game game)
	{
		super(game);
		Hardware.getInstance().setClimate(Climate.COLD);
		
		try
		{
			game.setBackgroundSound(getSound());
		} catch (IOException | UnsupportedAudioFileException e)
		{
			e.printStackTrace();
		}
		Random r = new Random();
		for (int i = 0; i < r.nextInt(10) + 10; i++)
			game.getEntities().add(new Plant());
	}

	public void update(double time)
	{
		super.update(time);
	}

	@Override
	public Entity getRandomEntity()
	{
		return new Fish();
	}

	@Override
	public HostileEntity getRandomHostileEntity()
	{
		return new HarpoonDiver(getGame().getCamera().getUsers());
	}

	public AudioInputStream getSound() throws UnsupportedAudioFileException,
			IOException
	{
		File file = new File("./audio/underwater/background sfx.wav");
		return AudioSystem.getAudioInputStream(file);
	}

	@Override
	public int getEntitySpawnRate()
	{
		return 100;
	}

	@Override
	public int getHostileEntitySpawnRate()
	{
		return 300;
	}

	@Override
	public Image getBackground()
	{
		return Toolkit.getDefaultToolkit().getImage("./images/underwater/background.png");
	}

	@Override
	public GroundRenderer getGroundRenderer()
	{
		return groundRenderer;
	}
}
