package control;

import java.awt.GraphicsEnvironment;

import model.Game;
import view.SenzingFrame;

public class Senzing
{
	public static void main(String[] args)
	{
		Game game = new Game();
		new GameController(game);
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.getDefaultScreenDevice().setFullScreenWindow(new SenzingFrame(game));
	}
}