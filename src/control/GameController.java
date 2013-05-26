package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.Timer;

import model.Game;
import control.levels.Level;
import control.levels.UnderwaterLevel;
import control.levels.WelcomeMenu;

public class GameController implements ActionListener
{
	private static final String audioURL = "audio/"; 
	private Game game;
	private final int UPDATES_PER_SECOND = 30;

	public GameController(Game game)
	{
		this.game = game;
		
		//Soon will add this to the menu choose!
		try
		{
			game.setSong(new Song(audioURL + "evil.mp3"));
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
		
		game.setLevel(new WelcomeMenu(game));
		(new Timer(1000/UPDATES_PER_SECOND, this)).start();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		game.getLevel().update(1000 / UPDATES_PER_SECOND);
	}
}
