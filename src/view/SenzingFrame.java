package view;

import javax.swing.JFrame;

public class SenzingFrame extends JFrame
{
	private static final long serialVersionUID = 1L;

	public SenzingFrame()
	{
		super("Senzing");
		setSize(640, 480);
		setContentPane(new SenzingPanel());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
}
