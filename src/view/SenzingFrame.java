package view;

import javax.swing.JFrame;

import model.Game;

public class SenzingFrame extends JFrame
{
	private static final long serialVersionUID = 1L;

	public SenzingFrame(Game game)
	{
		super("Senzing");
		setSize(640, 480);
		setContentPane(new SenzingPanel(game));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
}
