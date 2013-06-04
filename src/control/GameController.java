package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.Timer;

import model.Camera;
import model.Drive;
import model.Game;
import model.entities.Entity;
import model.levels.Level;
import model.levels.cave.CaveLevel;
import model.levels.desert.DesertLevel;
import model.levels.rainforest.RainforestLevel;
import model.levels.sky.SkyLevel;
import model.levels.underwater.UnderwaterLevel;

public class GameController implements ActionListener
{
	private Game game;
	private Drive drive;
	private int activeStage;
	private final int UPDATES_PER_SECOND = 30;

	public GameController(Game g)
	{
		this.game = g;

		// Initialize the hardware in a seperate thread because it takes a while...
		(new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				Hardware.getInstance();
			}
			
		})).start();

		(new Timer(1000 / UPDATES_PER_SECOND, this)).start();
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
						game.getEntities().clear();
					}

					else
					{
						double lengthOfStage = game.getSong().getLength() / 5;
						int currentStage = (int) Math.floor(game.getSong()
								.getTime() / lengthOfStage);

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

					// Put all the songs into a list
					List<File> audioFiles = new ArrayList<File>();

					for (Drive d : justConnected)
					{
						audioFiles.addAll(d.getAudioFiles());
					}

					// Pick one
					if (audioFiles.size() > 0)
					{
						File file = audioFiles.get((new Random())
								.nextInt(audioFiles.size()));

						try
						{
							drive = justConnected.get(0);
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

		for (Iterator<Entity> it = game.getEntities().iterator(); it.hasNext();)
		{
			Entity entity = it.next();
			
			if ((entity.getBounds().getMaxX() < -100 || entity.getBounds().getMaxY() < -100)
					|| (entity.getBounds().getMinX() > (Camera.VIEW_WIDTH+100) || entity
							.getBounds().getMinY() > (Camera.VIEW_HEIGHT+100)))
			{
				it.remove();
			}
		}
	}
}
