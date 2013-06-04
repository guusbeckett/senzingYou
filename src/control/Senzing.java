package control;

import java.awt.GraphicsEnvironment;

import model.Game;
import view.BeamerPanel;
import view.ContainerFrame;
import view.SenzingPanel;

public class Senzing
{
	public static void main(String[] args)
	{
		Game game = new Game();
		new GameController(game);
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		
		if (ge.getScreenDevices().length > 1)
			ge.getScreenDevices()[0].setFullScreenWindow(new ContainerFrame(new BeamerPanel(game)));
		
		ge.getDefaultScreenDevice().setFullScreenWindow(new ContainerFrame(new SenzingPanel(game)));
	}
}