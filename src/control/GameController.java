package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Random;

import javax.swing.Timer;

import model.Drive;
import model.Game;
import control.levels.Level;
import control.levels.RainforestLevel;

public class GameController implements ActionListener
{
	private Game game;
	private final int UPDATES_PER_SECOND = 30;

	public GameController(Game g)
	{
		this.game = g;
		
		(new Timer(1000/UPDATES_PER_SECOND, this)).start();
		(new Timer(200, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				List<Drive> justConnected = game.getJustConnectedDrives();
				
				for (Drive drive : justConnected)
				{
					List<File> songs = drive.getSongs();
					File file = songs.get((new Random()).nextInt(songs.size()));
					System.out.println(file);
					
					try
					{
						game.setSong(new Song(file));
						game.getSong().play();				
						game.setLevel(new RainforestLevel(game));
					} catch (Exception ex)
					{
						ex.printStackTrace();
					}
					
				}
			}
		})).start();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Level level = game.getLevel();
		
		if (level != null)
		{
			level.update(1000 / UPDATES_PER_SECOND);
		}
	}
}
