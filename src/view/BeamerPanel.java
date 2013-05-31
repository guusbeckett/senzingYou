package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;


import model.Camera;
import model.Game;
import model.levels.Level;

public class BeamerPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private Game game;

	public BeamerPanel(Game game)
	{
		super();
		this.game = game;
		setBackground(Color.BLACK);
		new Timer(1000 / 30, this).start();
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		double scaleFactor = (double) getHeight() / (double) Camera.VIEW_HEIGHT;

		g2.translate((getWidth() - (Camera.VIEW_WIDTH * scaleFactor)) / 2, 0);
		g2.scale(scaleFactor, scaleFactor);

		Level level = game.getLevel();
		
		if (level != null)
		{
			level.getGroundRenderer().draw(g2);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		repaint();
	}
}
