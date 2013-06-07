package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

import javax.swing.JPanel;
import javax.swing.Timer;

import model.Camera;
import model.Game;
import model.User;
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

		if (!game.getCamera().getUsers().isEmpty())
		{
			Color[] colors = new Color[] { Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.WHITE, Color.YELLOW, Color.LIGHT_GRAY };

			// Now do nothing with X it just print on the head position
			for (User u : game.getCamera().getUsers())
			{
				if (u.isVisible())
				{
					Text scoreText = new Text(colors[(u.getId() - 1) % colors.length], 45, true, true);
					scoreText.draw(g2, new Point2D.Double(u.getHead().getX(), 50), u.getScore() + "");
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		repaint();
	}
}
