package view;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import model.Game;
import model.entities.Entity;

public class SenzingPanel extends JPanel
{
	private static final  long serialVersionUID = 1L;
	private Game game;

	public SenzingPanel(Game game)
	{
		super();
		this.game = game;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		for (Entity entity : game.getEntities())
		{
			
		}
	}
}
