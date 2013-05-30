package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.Timer;

import model.Drive;
import model.Game;
import control.levels.RainforestLevel;

public class GameController implements ActionListener
{
	private Game game;
	private final int UPDATES_PER_SECOND = 30;

	public GameController(Game g)
	{
		this.game = g;
		
		game.setLevel(new RainforestLevel(game));
		
		String fileName = "evil.mp3";
		try
		{
			if(game.getSong() != null){
				game.getSong().stop();
			}
			game.setSong(new Song("audio/"+fileName));
			game.getSong().play();
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		
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
					
					System.out.println(songs);
				}
			}
		})).start();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		game.getLevel().update(1000 / UPDATES_PER_SECOND);
	}
}
