package view;

import javax.swing.JFrame;

import model.Game;

public class BeamerFrame extends JFrame
{
	private static final long serialVersionUID = 1L;

	public BeamerFrame(Game game)
	{
		super("Senzing");
		setResizable(false);

		setContentPane(new BeamerPanel(game));

		setUndecorated(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
