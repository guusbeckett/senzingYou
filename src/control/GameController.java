package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import model.Game;
import control.levels.Level;
import control.levels.UnderwaterLevel;

public class GameController implements ActionListener
{
	private Game game;
	private Level level;
	private final int UPDATES_PER_SECOND = 30;

	public GameController(Game game)
	{
		this.game = game;
		new CameraController(game);
		
		//TODO: implement random level selection.
		level = new UnderwaterLevel(game);
		(new Timer(1000/UPDATES_PER_SECOND, this)).start();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		level.update(1000 / UPDATES_PER_SECOND);
	}
}
