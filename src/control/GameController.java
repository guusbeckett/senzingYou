package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Random;

import javax.swing.Timer;

import model.Drive;
import model.Game;
import control.levels.CaveLevel;
import control.levels.DesertLevel;
import control.levels.Level;
import control.levels.RainforestLevel;
import control.levels.SkyLevel;
import control.levels.UnderwaterLevel;

public class GameController implements ActionListener
{
	private Game game;
	private Drive drive;
	private int activeStage;
	private final int UPDATES_PER_SECOND = 30;

	public GameController(Game g)
	{
		this.game = g;
		
		Hardware.getInstance(); // init hardware before loading music
		
		(new Timer(1000/UPDATES_PER_SECOND, this)).start();
		(new Timer(200, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (game.getSong() != null)
				{
					if (!drive.isConnected())
					{
						game.getSong().stop();
						game.setSong(null);
						game.setLevel(null);
					}
					
					else
					{
						double lengthOfStage = game.getSong().getLength() / 5;
						int currentStage = (int)Math.floor(game.getSong().getTime() / lengthOfStage);
						
						if (currentStage != activeStage)
						{
							switch (currentStage)
							{
							case 0:
								game.setLevel(new DesertLevel(game));
								break;
								
							case 1:
								game.setLevel(new RainforestLevel(game));
								break;
							
							case 2:
								game.setLevel(new CaveLevel(game));
								break;
							
							case 3:
								game.setLevel(new UnderwaterLevel(game));
								break;
							
							case 4:
								game.setLevel(new SkyLevel(game));
								break;
							}
							
							activeStage = currentStage;

						}
					}
				}
				
				else
				{
					List<Drive> justConnected = game.getJustConnectedDrives();
					
					if (justConnected.size() > 0)
					{
						drive = justConnected.get(0);
						List<File> songs = drive.getSongs();
						File file = songs.get((new Random()).nextInt(songs.size()));
						
						try
						{
							game.setSong(new Song(file));
							game.getSong().play();
							activeStage = -1;
						} catch (Exception ex)
						{
							ex.printStackTrace();
						}
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
