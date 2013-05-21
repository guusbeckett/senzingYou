package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;
import javax.swing.Timer;

import model.CameraData;
import model.Game;
import model.entities.Entity;

public class SenzingPanel extends JPanel implements ActionListener
{
	private static final  long serialVersionUID = 1L;
	private Game game;

	public SenzingPanel(Game game)
	{
		super();
		this.game = game;
		setBackground(Color.BLACK);
		new Timer(1000/30, this).start();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		g2.scale((double)getHeight() / (double)CameraData.VIEW_HEIGHT, (double)getHeight() / (double)CameraData.VIEW_HEIGHT);
		CameraData cameraData = game.getCameraData();
		
		//This background is purelly for the test purpuse.
		g2.drawImage(Toolkit.getDefaultToolkit().getImage("./images/underwater/background.png"), 0, 0, null);
		
		if (cameraData != null)
		{
			g2.drawImage(cameraData.getImage(), null, 0, 0);
		}
				
		for (Entity entity : game.getEntities())
		{
			AffineTransform transform = AffineTransform.getRotateInstance(entity.getRotation(), entity.getRotationPoint().getX() + entity.getBounds().getX(), entity.getRotationPoint().getY() + entity.getBounds().getY());
			
			if (entity.getImage() != null)
			{
				transform.translate(entity.getBounds().getX(), entity.getBounds().getY());
				transform.scale(entity.getBounds().getWidth() / entity.getImage().getWidth(null), entity.getBounds().getHeight() / entity.getImage().getHeight(null));
				g2.drawImage(entity.getImage(), transform, null);
			}
			
			else
			{
				g2.fill(transform.createTransformedShape(entity.getBounds()));
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		repaint();
	}
}
