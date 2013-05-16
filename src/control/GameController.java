package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import model.Game;
import control.levels.Level;

public class GameController implements ActionListener
{
	private Game game;
	private Level level;
	private Timer timer;
	private double time = 1000;

	public GameController(Game game)
	{
		this.game = game;
		timer = new Timer((int) time, this);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		level.update(time);
	}
}
