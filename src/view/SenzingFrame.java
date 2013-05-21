package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Game;

public class SenzingFrame extends JFrame
{
	private static final long serialVersionUID = 1L;

	public SenzingFrame(Game game)
	{
		super("Senzing");
		setResizable(false);
		
		setContentPane(new SenzingPanel(game));
		
		setUndecorated(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
}
