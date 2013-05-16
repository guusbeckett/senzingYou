package control;

import model.Game;
import view.SenzingFrame;

public class Senzing
{

	public static void main(String[] args)
	{
		Game game = new Game();
		new GameController(game);
		new SenzingFrame(game);
	}

}
